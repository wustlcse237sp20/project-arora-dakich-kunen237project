package com.company;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VirtualStockMarketTest {

    @Test
    public void simpleTransTest() {
        Order order1 = new Order( 0, 0, 0, 100, 10, true);
        Order order2 = new Order( 1, 1, 0, 100, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
        ArrayList<Client> correct = new ArrayList<>();
        correct.add(new Client(10,0,-1000));
        correct.add(new Client(0,10,1000));
            Assert.assertEquals(correct, vs.getClients());
    }
    @Test
    public void twoBOneS(){
        Order order1 = new Order( 0,1,1,100,5,true);
        Order order2 = new Order(1,2,1,200,5,true);
        Order order3 = new Order(2,3,1,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
    }
    @Test
    public void twoSOneB(){
        Order order1 = new Order( 0,1,1,100,5,true);
        Order order2 = new Order(1,2,1,50,5,false);
        Order order3 = new Order(2,3,1,100,10,false);
    }

    @Test
    public void buyerHasGreaterQuantity() {
        Order order1 = new Order( 0, 1, 1, 100, 20, true);
        Order order2 = new Order( 1, 2, 1, 100, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
    }

    @Test
    public void sellerHasGreaterQuantity(){
        Order order1 = new Order( 0, 1, 1, 100, 10, true);
        Order order2 = new Order(1, 2, 1, 100, 20, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
    }

    @Test
    public void complexTrans(){
        Order order1 = new Order(0, 1, 0, 81, 11, true);
        Order order2 = new Order( 1, 1, 1, 92, 15, true);
        Order order3 = new Order( 2, 0, 1, 15, 26, true);
        Order order4 = new Order( 3, 0, 0, 7, 2, true);
        Order order5 = new Order(4, 2, 0, 71, 45, false);
        Order order6 = new Order( 5, 1, 1, 83, 20, true);
        Order order7 = new Order( 6, 2, 0, 75, 24, true);
        Order order8 = new Order( 7, 1, 1, 84, 17, true);
        Order order9 = new Order(8, 2, 1, 5, 50, false);
        Order order10 = new Order( 9, 0, 1, 13, 17, true);
        Order order11 = new Order(10, 0, 1, 58, 45, false);
        Order order12 = new Order(11, 1, 0, 17, 4, true);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);
        orderList.add(order5);
        orderList.add(order6);
        orderList.add(order7);
        orderList.add(order8);
        orderList.add(order9);
        orderList.add(order10);
        orderList.add(order11);
        orderList.add(order12);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 2);
        vs.computeTrans();
    }


}
