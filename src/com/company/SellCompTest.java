package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellCompTest {
    Order order1 = new Order(0, 1, 1, 200, 10, true);
    Order order2 = new Order(1, 2, 1, 100, 10, true);

    @Test
    public void compare() {
        SellComp sell = new SellComp();
        Assert.assertEquals(sell.compare(order1,order2), 1);

    }
}