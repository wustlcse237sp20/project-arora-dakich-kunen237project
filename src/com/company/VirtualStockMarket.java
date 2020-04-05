package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class VirtualStockMarket {

    private ArrayList<Order> orderList;
    private int numClients;
    private int numEquities;
    private ArrayList<Client> clients;
    private ArrayList<Integer> currentMedians;
    private ArrayList<PriorityQueue<Integer>> transLow;
    private ArrayList<PriorityQueue<Integer>> transHigh;
    private ArrayList<PriorityQueue<Pair<Order, Order>>> pairs;
    private ArrayList<Pair<Order, Order>> currentPairs;
    private ArrayList<PriorityQueue<Order>> buyOrders;
    private ArrayList<PriorityQueue<Order>> sellOrders;
    private ArrayList<Pair<Integer, ArrayList<Integer>>> medians;
    private ArrayList<Pair<Integer, ArrayList<Transaction>>> transactions;
    private int numTrans;
    private ArrayList<Pair<Integer, Integer>> timeTravelers;

    public VirtualStockMarket(ArrayList<Order> orderList, int numClients, int numEquities) {
        this.orderList = orderList;
        this.numClients = numClients;
        this.numEquities = numEquities;
        this.clients = new ArrayList<>(numClients);
        this.currentMedians = new ArrayList<>(numEquities); //keeps track of the current list of medians
        this.transLow = new ArrayList<>(numEquities);
        this.transHigh = new ArrayList<>(numEquities);
        this.pairs = new ArrayList<>(numEquities);
        this.currentPairs = new ArrayList<>(numEquities);
        this.buyOrders = new ArrayList<>(numEquities);
        this.sellOrders = new ArrayList<>(numEquities);
        for (int i = 0; i < numClients; i++) {
            this.clients.add(new Client());
        }
        for (int i = 0; i < numEquities; i++) {
            this.currentMedians.add(0);
            this.transLow.add(new PriorityQueue<>(orderList.size(), Collections.reverseOrder()));
            this.transHigh.add(new PriorityQueue<>(orderList.size()));
            this.currentPairs.add(Pair.of(new Order(true), new Order(false)));
            this.pairs.add(new PriorityQueue<>(orderList.size(), new TimeComp()));
            this.buyOrders.add(new PriorityQueue<>(orderList.size(), new BuyComp()));
            this.sellOrders.add(new PriorityQueue<>(orderList.size(), new SellComp()));
        }
        this.transactions = new ArrayList<>();
        this.medians = new ArrayList<>(); //we want to match each median with its timestamp. A Pair basically.
        this.numTrans = 0;
        this.timeTravelers = new ArrayList<>(numEquities);
    }

    public int getNumClients() {
        return numClients;
    }

    public int getNumEquities() {
        return numEquities;
    }

    public ArrayList<Client> getClients() {
        return clients;
    } //transaction record for each client

    public ArrayList<Pair<Integer, ArrayList<Integer>>> getMedians() {
        return medians;
    } //all equities medians for each timestamp

    public ArrayList<Pair<Integer, ArrayList<Transaction>>> getTransactions() {
        return transactions;
    } //a list of transactions that occured for each timestamp

    public int getNumTrans() {
        return numTrans;
    }//total number of transactions

    public ArrayList<Pair<Integer, Integer>> getTimeTravelers() {
        return timeTravelers;
    }// ideal buy and sell times for each equity

    public void computeTrans() {
        //here we take in the list of orders and compute all the transactions
        ArrayList<Transaction> currentTrans = new ArrayList<>();
        int currentTimestamp = 0;
        for (int i = 0; i < orderList.size(); i++) {  //iterate through the orders
            orderList.get(i).setRelTimestamp(i);
            computeTime(i);
            if(orderList.get(i).getTimestamp() != currentTimestamp) {
                if (!currentTrans.isEmpty()) {
                    transactions.add(Pair.of(currentTimestamp, currentTrans));
                    currentTrans = new ArrayList<>();
                }
                if (Collections.max(currentMedians) != 0) {
                    medians.add(Pair.of(currentTimestamp, new ArrayList<>(currentMedians)));
                }
                currentTimestamp = orderList.get(i).getTimestamp();
            }
            Transaction trans;
            if (orderList.get(i).getType()) {
                trans = getTransBuyer(i);
            } else {
                trans = getTransSeller(i);
            }
            while(trans != null) {
                computeMedian(trans, i);
                currentTrans.add(trans);
                numTrans++;
                if (orderList.get(i).getType()) {
                    trans = getTransBuyer(i);
                } else {
                    trans = getTransSeller(i);
                }
            }

            if(orderList.get(i).getQuantity() != 0) {
                if(orderList.get(i).getType()) {
                    buyOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                } else {
                    sellOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                }
            }
        }
        if (!currentTrans.isEmpty()) {
            transactions.add(Pair.of(currentTimestamp, currentTrans));
        }
        if (Collections.max(currentMedians) != 0) {
            medians.add(Pair.of(currentTimestamp, new ArrayList<>(currentMedians)));
        }
        generateTimeList();
    }
    private Transaction getTransBuyer(int idx) {
        if (orderList.get(idx).getQuantity() == 0 || sellOrders.get(orderList.get(idx).getEquityID()).isEmpty() ||
            orderList.get(idx).getPrice() < sellOrders.get(orderList.get(idx).getEquityID()).peek().getPrice())
            return null;
        Order matchedSeller = sellOrders.get(orderList.get(idx).getEquityID()).poll();
        int quantity = Math.min(orderList.get(idx).getQuantity(), matchedSeller.getQuantity());
        Transaction trans = new Transaction(orderList.get(idx).getClientID(),
                                matchedSeller.getClientID(), orderList.get(idx).getEquityID(),
                                matchedSeller.getPrice(), quantity);
        orderList.get(idx).setQuantity(orderList.get(idx).getQuantity() - quantity);
        matchedSeller.setQuantity(matchedSeller.getQuantity() - quantity);
        updateClients(trans);
        if (matchedSeller.getQuantity() != 0) {
            sellOrders.get(orderList.get(idx).getEquityID()).add(matchedSeller);
        }
        return trans;
    }

    private Transaction getTransSeller(int idx) { //CHANGE IDX
        if (orderList.get(idx).getQuantity() == 0 || buyOrders.get(orderList.get(idx).getEquityID()).isEmpty() ||
            orderList.get(idx).getPrice() > buyOrders.get(orderList.get(idx).getEquityID()).peek().getPrice())
            return null;
        Order matchedBuyer = buyOrders.get(orderList.get(idx).getEquityID()).poll();
        int quantity = Math.min(matchedBuyer.getQuantity(), orderList.get(idx).getQuantity());
        Transaction trans = new Transaction(matchedBuyer.getClientID(),
                            orderList.get(idx).getClientID(), orderList.get(idx).getEquityID(),
                            matchedBuyer.getPrice(), quantity);
        orderList.get(idx).setQuantity(orderList.get(idx).getQuantity() - quantity);
        matchedBuyer.setQuantity(matchedBuyer.getQuantity() - quantity);
        updateClients(trans);
        if (matchedBuyer.getQuantity() != 0) {
            buyOrders.get(orderList.get(idx).getEquityID()).add(matchedBuyer);
        }
        return trans;
    }

    private void updateClients(Transaction trans) {
        int quantity = clients.get(trans.getBuyer()).getBought();
        int netTrade = clients.get(trans.getBuyer()).getNetTrade();
        clients.get(trans.getBuyer()).setBought(quantity + trans.getQuantity());
        clients.get(trans.getBuyer()).setNetTrade(netTrade - trans.getQuantity() * trans.getPrice());
        quantity = clients.get(trans.getSeller()).getSold();
        netTrade = clients.get(trans.getSeller()).getNetTrade();
        clients.get(trans.getSeller()).setSold(quantity + trans.getQuantity());
        clients.get(trans.getSeller()).setNetTrade(netTrade + trans.getQuantity() * trans.getPrice());
    }

    private void computeMedian(Transaction trans,int idx) {
        int equityID = orderList.get(idx).getEquityID();
        if(trans.getPrice() < currentMedians.get(equityID)) {
           transLow.get(equityID).add(trans.getPrice());
        } else {
            transHigh.get(equityID).add(trans.getPrice());
        }
        int numLow = transLow.get(equityID).size();
        int numHigh = transHigh.get(equityID).size();
        int median = 0;
        int temp;
        switch (numLow - numHigh) {
        case -2:
            temp = transHigh.get(equityID).poll();
            transLow.get(equityID).add(temp);
            median = (transLow.get(equityID).peek() + transHigh.get(equityID).peek())/2;
            break;
        case -1:
            median = transHigh.get(equityID).peek();
            break;
        case 0:
            median = (transLow.get(equityID).peek() + transHigh.get(equityID).peek())/2;
            break;
        case 1:
            median = transLow.get(equityID).peek();
            break;
        case 2:
            temp = transLow.get(equityID).poll();
            transHigh.get(equityID).add(temp);
            median = (transLow.get(equityID).peek() + transHigh.get(equityID).peek())/2;
            break;
        }
        currentMedians.set(equityID,median);
    }

    private void computeTime(int idx) {
        int equityID = orderList.get(idx).getEquityID();
        if(!orderList.get(idx).getType()) {
            if(currentPairs.get(equityID).first.getPrice() == 0 ||
                    orderList.get(idx).getPrice() < currentPairs.get(equityID).first.getPrice()) {
                if(currentPairs.get(equityID).first.getPrice() != 0 &&
                        currentPairs.get(equityID).first.getPrice() < currentPairs.get(equityID).second.getPrice()) {
                    pairs.get(equityID).add(currentPairs.get(equityID));
                }
                currentPairs.set(equityID, Pair.of(new Order(orderList.get(idx)), currentPairs.get(equityID).second));
                currentPairs.get(equityID).second.setPrice(0);
            }
        }
        else if(currentPairs.get(equityID).second.getPrice() == 0 ||
                orderList.get(idx).getPrice() > currentPairs.get(equityID).second.getPrice()) {
            currentPairs.set(equityID, Pair.of(currentPairs.get(equityID).first, new Order(orderList.get(idx))));
        }
    }

    private void generateTimeList() {
        for (int i = 0; i < numEquities; i++) {
            int buyTime = -1;
            int sellTime = -1;
            if (currentPairs.get(i).first.getPrice() != 0 && currentPairs.get(i).second.getPrice() != 0 &&
                    currentPairs.get(i).first.getPrice() < currentPairs.get(i).second.getPrice()) {
                pairs.get(i).add(currentPairs.get(i));
            }
            if (!pairs.get(i).isEmpty()) {
                buyTime = pairs.get(i).peek().first.getTimestamp();
                sellTime = pairs.get(i).peek().second.getTimestamp();
            }
            timeTravelers.add(Pair.of(buyTime, sellTime));
        }
    }
}
