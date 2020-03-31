package com.company;

import java.util.Comparator;

public class SellComp implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPrice() > o2.getPrice()) {
            return 1;
        } else if (o1.getPrice() < o2.getPrice()) {
            return -1;
        } else if (o1.getTimestamp() < o2.getTimestamp()) {
            return 1;
        } else if(o1.getTimestamp() > o2.getTimestamp()) {
            return -1;
        } else {
            return 0;
        }

    }
}