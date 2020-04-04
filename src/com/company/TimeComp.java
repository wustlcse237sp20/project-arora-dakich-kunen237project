package com.company;

import java.util.Comparator;

public class TimeComp implements Comparator<Pair<Order,Order>> {

    @Override
    public int compare(Pair<Order,Order> p1, Pair<Order,Order> p2) {
        if (p1.second.getPrice() - p1.first.getPrice() < p2.second.getPrice() - p2.first.getPrice()) {
            return 1;
        } else if (p1.second.getPrice() - p1.first.getPrice() > p2.second.getPrice() - p2.first.getPrice()) {
            return -1;
        } else return Integer.compare(p1.first.getRelTimestamp(), p2.first.getRelTimestamp());
    }
}
