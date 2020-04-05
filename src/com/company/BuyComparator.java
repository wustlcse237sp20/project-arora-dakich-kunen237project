package com.company;

import java.util.Comparator;

public class BuyComparator implements Comparator<Order> {

    //comparator used for buyOrders priority queue. Lower prices are preferred, otherwise lower timestamps.
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPrice() < o2.getPrice()) {
            return 1;
        } else if (o1.getPrice() > o2.getPrice()) {
            return -1;
        } else return Integer.compare(o2.getRelTimestamp(), o1.getRelTimestamp());
    }
}
