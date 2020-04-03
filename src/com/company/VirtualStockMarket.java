package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class VirtualStockMarket {
    private ArrayList<Order> orderList;
    private int numClients;
    private int numEquities;
    private  ArrayList<Client> clients;
    private ArrayList<Integer> currentMedians;
    private  ArrayList<PriorityQueue<Integer>> transLow;
    private ArrayList<PriorityQueue<Integer>> transHigh;
    private ArrayList<PriorityQueue<Pair<Order,Order>>> pairs;
    private ArrayList<Pair<Order,Order>> currentPairs;
    private ArrayList<PriorityQueue<Order>> buyOrders;
    private ArrayList<PriorityQueue<Order>> sellOrders;
    private ArrayList<Pair<Integer, ArrayList<Integer>>> medians;
    private ArrayList<Pair<Integer, ArrayList<Transaction>>> transactions;
    private int numTrans;

    public VirtualStockMarket(ArrayList<Order> orderList, int numClients, int numEquities) {
        this.orderList = orderList;
        this.numClients = numClients;
        this.numEquities = numEquities;
        clients = new ArrayList<>(numClients);
        currentMedians = new ArrayList<>(numEquities); //keeps track of the current list of medians
        transLow = new ArrayList<>(numEquities);
        transHigh = new ArrayList<>(numEquities);
        pairs = new ArrayList<>(numEquities);
        currentPairs = new ArrayList<>(numEquities);
        buyOrders = new ArrayList<>(numEquities);
        sellOrders = new ArrayList<>(numEquities);
        for (int i = 0; i < numClients; i++) {
            clients.set(i, new Client());
        }
        for (int i = 0; i < numEquities; i++) {
            currentMedians.set(i,0);
            transLow.set(i, new PriorityQueue<>(orderList.size()));
            transHigh.set(i, new PriorityQueue<>(orderList.size(), Collections.reverseOrder()));
            currentPairs.set(i, Pair.of(new Order(), new Order()));
            pairs.set(i, new PriorityQueue<>(orderList.size(), new TimeComp()));
            buyOrders.set(i, new PriorityQueue<>(orderList.size(),new BuyComp()));
            sellOrders.set(i, new PriorityQueue<>(orderList.size(),new SellComp()));
        }
        transactions = new ArrayList<>();
        medians = new ArrayList<>(); //we want to match each median with its timestamp. A Pair basically.
        numTrans = 0;
    }

    public int getNumClients() {
        return numClients;
    }

    public int getNumEquities() {
        return numEquities;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<PriorityQueue<Pair<Order, Order>>> getPairs() {
        return pairs;
    }

    public ArrayList<Pair<Order, Order>> getCurrentPairs() {
        return currentPairs;
    }

    public ArrayList<Pair<Integer, ArrayList<Integer>>> getMedians() {
        return medians;
    }

    public ArrayList<Pair<Integer, ArrayList<Transaction>>> getTransactions() {
        return transactions;
    }

    public int getNumTrans() {
        return numTrans;
    }

    public void computeTrans() {
        ArrayList<Transaction> currentTrans = new ArrayList<>();
        int currentTimestamp = 0;
        for (int i = 0; i < orderList.size(); i++) {  //iterate through the orders
            computeTime(i);

            if(orderList.get(i).getTimestamp() != currentTimestamp) {
                transactions.add(medians.size() - 1, Pair.of(currentTimestamp,currentTrans));
                medians.add(medians.size() - 1, Pair.of(currentTimestamp,currentMedians));
                currentTimestamp = orderList.get(i).getTimestamp();

            }
            Transaction trans = null;

            if (orderList.get(i).getType()) {
                trans = getTransBuyer(i);
            } else {
                trans = getTransSeller(i);
            }

            while(trans != null) {
                computeMedian(trans, i);

                if (orderList.get(i).getType()) {
                    trans = getTransBuyer(i);
                } else {
                    trans = getTransSeller(i);
                }
                currentTrans.add(trans);
                numTrans++;
            }

            if(orderList.get(i).getQuantity() != 0) {
                if(orderList.get(i).getType()) {
                    buyOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                } else {
                    sellOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                }
            }

        }

        //here we take in the list of orders and compute all the transactions

    }
    public Transaction getTransBuyer(int idx) {
        if (orderList.get(idx).getQuantity() == 0 || sellOrders.get(orderList.get(idx).getEquityID()).isEmpty() ||
            orderList.get(idx).getPrice() < sellOrders.get(orderList.get(idx).getEquityID()).peek().getPrice())
            return null;
        Order matchedSeller = sellOrders.get(orderList.get(idx).getEquityID()).poll();
        int quantity = getTransQuantity(orderList.get(idx), matchedSeller);
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

    public Transaction getTransSeller(int idx) {
        if (orderList.get(idx).getQuantity() == 0 || buyOrders.get(orderList.get(idx).getEquityID()).isEmpty() ||
            orderList.get(idx).getPrice() < buyOrders.get(orderList.get(idx).getEquityID()).peek().getPrice())
            return null;
        Order matchedBuyer = buyOrders.get(orderList.get(idx).getEquityID()).poll();
        int quantity = getTransQuantity(matchedBuyer, orderList.get(idx));
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

    public int getTransQuantity(Order buyer, Order seller) {
        int min = 0;
        if(buyer.getQuantity() > seller.getQuantity()){
            min = seller.getQuantity();
        } else {
            min = buyer.getQuantity();
        }
        return min;
    }

    public void updateClients(Transaction trans) {
        int quantity = clients.get(trans.getBuyer()).getBought();
        clients.get(trans.getBuyer()).setBought(quantity + trans.getQuantity());
        int netTrade = clients.get(trans.getBuyer()).getNetTrade();
        clients.get(trans.getBuyer()).setNetTrade(netTrade - trans.getQuantity() * trans.getPrice());
        clients.get(trans.getSeller()).setSold(quantity + trans.getQuantity());
        clients.get(trans.getSeller()).setNetTrade(netTrade + trans.getQuantity() * trans.getPrice());


    }

    public void computeMedian(Transaction trans,int idx) {
        if(trans.getPrice() < currentMedians.get(orderList.get(idx).getEquityID())) {
           transLow.get(orderList.get(idx).getEquityID()).add(trans.getPrice());
        } else {
            transHigh.get(orderList.get(idx).getEquityID()).add(trans.getPrice());
        }
        int numLow =   transLow.get(orderList.get(idx).getEquityID()).size();
        int numHigh = transHigh.get(orderList.get(idx).getEquityID()).size();
        int temp = 0;
        int median =0;
        switch (numLow - numHigh) {
        case -2:
            temp = transHigh.get(orderList.get(idx).getEquityID()).poll();
            transLow.get(orderList.get(idx).getEquityID()).add(temp);
            median = (transLow.get(orderList.get(idx).getEquityID()).peek() + transHigh.get(orderList.get(idx).getEquityID()).peek())/2;
            break;
        case -1:
            median = transHigh.get(orderList.get(idx).getEquityID()).peek();
            break;
        case 0:
            median = (transLow.get(orderList.get(idx).getEquityID()).peek() + transHigh.get(orderList.get(idx).getEquityID()).peek())/2;
            break;
        case 1:
            median = transLow.get(orderList.get(idx).getEquityID()).peek();
            break;
        case 2:
            temp = transLow.get(orderList.get(idx).getEquityID()).poll();
            transHigh.get(orderList.get(idx).getEquityID()).add(temp);
            median = (transLow.get(orderList.get(idx).getEquityID()).peek() + transHigh.get(orderList.get(idx).getEquityID()).peek())/2;
            break;
        }
        currentMedians.set(orderList.get(idx).getEquityID(),median);
    }

    public void computeTime(int idx) {

        if(!orderList.get(idx).getType()) {
            if(currentPairs.get(orderList.get(idx).getEquityID()).first.getPrice() == 0 || orderList.get(idx).getPrice() < currentPairs.get(orderList.get(idx).getEquityID()).first.getPrice()) {
                if(currentPairs.get(orderList.get(idx).getEquityID()).first.getPrice() != 0 && currentPairs.get(orderList.get(idx).getEquityID()).first.getPrice() < currentPairs.get(orderList.get(idx).getEquityID()).second.getPrice()) {
                    pairs.get(orderList.get(idx).getEquityID()).add(currentPairs.get(orderList.get(idx).getEquityID()));
                }
                currentPairs.get(orderList.get(idx).getEquityID()).second.setPrice(0);


            }
        }
        else if(currentPairs.get(orderList.get(idx).getEquityID()).second.getPrice() == 0 || orderList.get(idx).getPrice() > currentPairs.get(orderList.get(idx).getEquityID()).second.getPrice()) {
            currentPairs.set(orderList.get(idx).getEquityID(),Pair.of(currentPairs.get(orderList.get(idx).getEquityID()).first, orderList.get(idx)));
        }
    }



}
