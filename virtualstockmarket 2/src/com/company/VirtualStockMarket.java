package com.company;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class VirtualStockMarket {
    private ArrayList<Order> orderList;
    private int numClients;
    private int numEquities;

    public VirtualStockMarket(ArrayList<Order> orderList, int numClients, int numEquities) {
        this.orderList = orderList;
        this.numClients = numClients;
        this.numEquities = numEquities;
    }

    public void computeTrans() {
        ArrayList<PriorityQueue<Order>> buyOrders = new ArrayList<>(numEquities);
        ArrayList<PriorityQueue<Order>> sellOrders = new  ArrayList<>(numEquities);
        ArrayList<PriorityQueue<Integer>> transLow = new ArrayList<>(numEquities);
        ArrayList<PriorityQueue<Integer>> transHigh = new ArrayList<>(numEquities);
        // these two are useful for the median part
        ArrayList<Integer> currentMedians = new ArrayList<>(numEquities); //keeps track of the current list of medians
        ArrayList<Pair<Integer, ArrayList<Integer>>> medians = new ArrayList<>(); //we want to match each median with its timestamp. A Pair basically.
        ArrayList<Client> clients = new ArrayList<>(numClients);
        ArrayList<PriorityQueue<Pair<Order,Order>>> pairs = new ArrayList<>(numEquities);
        ArrayList<Pair<Order,Order>> currentPairs = new ArrayList<>(numEquities);
        for (int i = 0; i < numEquities; i++) {
            currentMedians.set(i,-1);
            currentPairs.set(i, Pair.of(new Order(), new Order()));
            buyOrders.set(i, new PriorityQueue<>(orderList.size(),new BuyComp()));
            sellOrders.set(i, new PriorityQueue<>(orderList.size(),new SellComp()));
            transLow.set(i, new PriorityQueue<>(orderList.size()));
            transHigh.set(i, new PriorityQueue<>(orderList.size()));//stopped here 3/30/20 10:36PM
        }
        for (int i = 0; i < numClients; i++) {
            clients.set(i, new Client());
        }

        int currentTimestamp = 0;
        for (Order order:orderList) {  //iterate through the orders
            TimeInfo timeInfo = new TimeInfo(pairs.get(order.getEquityID()), currentPairs.get(order.getEquityID()));
            timeInfo = computeTime(timeInfo,order);
            pairs.set(order.getEquityID(),timeInfo.getPairList());
            currentPairs.set(order.getEquityID(),timeInfo.getCurrentPair());

            if(order.getTimestamp() != currentTimestamp) {
                medians.add(medians.size() - 1, Pair.of(currentTimestamp,currentMedians));
                currentTimestamp = order.getTimestamp();

            }
            Transaction trans = null;

            if(order.getType()) {

            }
        }

        //here we take in the list of orders and compute all the transactions

    }
    public void getTransBuyer(){

    }
    public void getTransSeller() {

    }

    public TimeInfo computeTime(TimeInfo timeInfo, Order order) {

        if(!order.getType()) {
            if(timeInfo.getCurrentPair().first.getPrice() == 0 || order.getPrice() < timeInfo.getCurrentPair().first.getPrice()) {
                if(timeInfo.getCurrentPair().first.getPrice() != 0 && timeInfo.getCurrentPair().first.getPrice() < timeInfo.getCurrentPair().second.getPrice()) {
                    PriorityQueue<Pair<Order,Order>> pairList = timeInfo.getPairList();
                    pairList.add(timeInfo.getCurrentPair());
                    timeInfo.setPairList(pairList);
                }
                Order sell = timeInfo.getCurrentPair().second;
                sell.setPrice(0);
                timeInfo.setCurrentPair(Pair.of(order,sell));

            }
        }
        else if(timeInfo.getCurrentPair().second.getPrice() == 0 || order.getPrice() > timeInfo.getCurrentPair().second.getPrice()) {
            timeInfo.setCurrentPair(Pair.of(timeInfo.getCurrentPair().first, order));
        }
        return timeInfo;
    }



}
