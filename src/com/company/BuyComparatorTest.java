package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BuyComparatorTest {
    Order order1 = new Order(0, 1, 1, 50, 10, true);
    Order order2 = new Order(1, 2, 1, 100, 10, true);

    @Test
    public void compare() {
        BuyComparator buy = new BuyComparator();
        Assert.assertEquals(buy.compare(order1,order2), 1);

    }
}