package com.company;

import java.util.PriorityQueue;

public class TimeInfo {

    private PriorityQueue<Pair<Order,Order>> pairList;
    private Pair<Order,Order> currentPair;


    public TimeInfo(PriorityQueue<Pair<Order, Order>> pairList, Pair<Order, Order> currentPair) {
        this.pairList = pairList;
        this.currentPair = currentPair;
    }

    public PriorityQueue<Pair<Order, Order>> getPairList() {
        return pairList;
    }

    public Pair<Order, Order> getCurrentPair() {
        return currentPair;
    }

    public void setPairList(PriorityQueue<Pair<Order, Order>> pairList) {
        this.pairList = pairList;
    }

    public void setCurrentPair(Pair<Order, Order> currentPair) {
        this.currentPair = currentPair;
    }
}


