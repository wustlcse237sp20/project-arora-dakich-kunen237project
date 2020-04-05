package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Equity {

    private int currentMedian;  //keeps track of the current list of medians
    private PriorityQueue<Integer> transLow;
    private PriorityQueue<Integer> transHigh;
    private PriorityQueue<Pair<Order,Order>> pairs;
    private Pair<Order,Order> currentPair;
    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;
    private Pair<Integer, Integer> timeTraveler;

    public Equity(int numOrders) {
        currentMedian = 0;
        transLow = new PriorityQueue<>(numOrders);
        transHigh = new PriorityQueue<>(numOrders, Collections.reverseOrder());
        currentPair = Pair.of(new Order(true), new Order(false));
        pairs = new PriorityQueue<>(numOrders, new TimeComp());
        buyOrders = new PriorityQueue<>(numOrders,new BuyComp());
        sellOrders = new PriorityQueue<>(numOrders,new SellComp());
    }

    public int getCurrentMedian() {
        return currentMedian;
    }

    public PriorityQueue<Integer> getTransLow() {
        return transLow;
    }

    public PriorityQueue<Integer> getTransHigh() {
        return transHigh;
    }

    public PriorityQueue<Pair<Order, Order>> getPairs() {
        return pairs;
    }

    public Pair<Order, Order> getCurrentPair() {
        return currentPair;
    }

    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }
}
