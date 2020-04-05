package com.company;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VirtualStockMarketTest {

    @Test
    public void simpleTransClient() { //simple trade, shows client stats
        Order order1 = new Order( 0, 0, 0, 100, 10, true);
        Order order2 = new Order( 1, 1, 0, 100, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
        ArrayList<Client> correctClients = new ArrayList<>();
        correctClients.add(new Client(10,0,-1000));
        correctClients.add(new Client(0,10,1000));
        Assert.assertEquals(correctClients, vs.getClients());
    }

    @Test
    public void simpleTransMedian() { //simple trade, median should be value of trade
        Order order1 = new Order( 0, 0, 0, 100, 10, true);
        Order order2 = new Order( 1, 1, 0, 100, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
        ArrayList<Pair<Integer, ArrayList<Integer>>> correctMedians = new ArrayList<>();
        ArrayList<Integer> currentMedians = new ArrayList<>();
        currentMedians.add(100);
        correctMedians.add(Pair.of(1, currentMedians));
        Assert.assertEquals(correctMedians, vs.getMedians());
    }

    @Test
    public void simpleTransTime() { //simple trade, no opportunity for profit
        Order order1 = new Order( 0, 0, 0, 100, 10, true);
        Order order2 = new Order( 1, 1, 0, 100, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
        ArrayList<Pair<Integer, Integer>> correctTime = new ArrayList<>();
        correctTime.add(Pair.of(-1, -1));
    }

    @Test
    public void twoBOneSClient(){ //two buys one sell test, client stats should reflect two transactions
        Order order1 = new Order( 0,0,0,100,5,true);
        Order order2 = new Order(1,1,0,200,5,true);
        Order order3 = new Order(2,2,0,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
        ArrayList<Client> correctClients = new ArrayList<>();
        correctClients.add(new Client(5,0,-500));
        correctClients.add(new Client(5,0,-1000));
        correctClients.add(new Client(0,10,1500));
        Assert.assertEquals(correctClients, vs.getClients());
    }

    @Test
    public void twoBOneSNumber(){ //two buys one sell test, two total transactions
        Order order1 = new Order( 0,0,0,100,5,true);
        Order order2 = new Order(1,1,0,200,5,true);
        Order order3 = new Order(2,2,0,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
        Assert.assertEquals(2, vs.getNumTrans());
    }

    @Test
    public void twoBOneSMedian(){ //two buys one sell test, median should be average of two values
        Order order1 = new Order( 0,0,0,100,5,true);
        Order order2 = new Order(1,1,0,200,5,true);
        Order order3 = new Order(2,2,0,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
        ArrayList<Pair<Integer, ArrayList<Integer>>> correctMedians = new ArrayList<>();
        ArrayList<Integer> currentMedians = new ArrayList<>();
        currentMedians.add(150);
        correctMedians.add(Pair.of(2, currentMedians));
        Assert.assertEquals(correctMedians, vs.getMedians());
    }

    @Test
    public void twoSOneBClient(){ //two sell, one buy test, only one transaction should take place
        Order order1 = new Order( 0,0,0,100,5,true);
        Order order2 = new Order(1,1,0,50,5,false);
        Order order3 = new Order(2,2,0,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
        ArrayList<Client> correctClients = new ArrayList<>();
        correctClients.add(new Client(5,0,-500));
        correctClients.add(new Client(0,5,500));
        correctClients.add(new Client(0,0,0));
        Assert.assertEquals(correctClients, vs.getClients());
    }

    @Test
    public void twoSOneBMedian(){ //two sell, one buy test, median should be the higher of the two values
        Order order1 = new Order( 0,0,0,100,5,true);
        Order order2 = new Order(1,1,0,50,5,false);
        Order order3 = new Order(2,2,0,100,10,false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 3, 1);
        vs.computeTrans();
        ArrayList<Pair<Integer, ArrayList<Integer>>> correctMedians = new ArrayList<>();
        ArrayList<Integer> currentMedians = new ArrayList<>();
        currentMedians.add(100);
        correctMedians.add(Pair.of(1, currentMedians));
        currentMedians = new ArrayList<>();
        currentMedians.add(100);
        correctMedians.add(Pair.of(2, currentMedians));
        Assert.assertEquals(correctMedians, vs.getMedians());
    }

    @Test
    public void sellerMoreExpensive(){
        Order order1 = new Order( 0, 0, 0, 100, 10, true);
        Order order2 = new Order(1, 1, 0, 200, 10, false);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        VirtualStockMarket vs = new VirtualStockMarket(orderList, 2, 1);
        vs.computeTrans();
        ArrayList<Client> correctClients = new ArrayList<>();
        correctClients.add(new Client(0,0,0));
        correctClients.add(new Client(0,0,0));
        Assert.assertEquals(correctClients, vs.getClients());
    }

    @Test
    public void complexTransClient(){ //many transactions test, client stats more complex
        Order order1 = new Order(0, 1, 0, 250, 10, true);
        Order order2 = new Order( 0, 1, 1, 200, 20, true);
        Order order3 = new Order( 1, 0, 1, 75, 30, true);
        Order order4 = new Order( 2, 0, 0, 20, 1, true);
        Order order5 = new Order(2, 2, 0, 150, 50, false);
        //client 2 sells 10 shares of equity 0 to client 1 for 250
        Order order6 = new Order( 2, 1, 1, 300, 20, true);
        Order order7 = new Order( 3, 2, 0, 500, 20, true);
        //client 2 sells 20 shares of equity 0 to client 2 for 150
        Order order8 = new Order( 4, 1, 1, 100, 20, true);
        Order order9 = new Order(5, 2, 1, 10, 50, false);
        //client 2 sells 20 shares of equity 1 to client 1 for 300
        //client 2 sells 20 shares of equity 1 to client 1 for 200
        //client 2 sells 10 shares of equity 1 to client 1 for 100
        Order order10 = new Order( 5, 0, 1, 25, 20, true);
        Order order11 = new Order(6, 0, 1, 50, 50, false);
        //client 0 sells 10 shares of equity 1 to client 1 for 100
        //client 0 sells 30 shares of equity 1 to client 0 for 75
        Order order12 = new Order(7, 1, 0, 50, 5, true);
        //client 0 bought 30 sold 40 nettrade 1000
        //client 1 bought 70 sold 0 nettrade -2500-6000-4000-1000-1000=-14500
        //client 2 bought 20 sold 80 nettrade 2500+6000+4000+1000=13500
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
        ArrayList<Client> correctClients = new ArrayList<>();
        correctClients.add(new Client(30,40,1000));
        correctClients.add(new Client(70,0,-14500));
        correctClients.add(new Client(20,80,13500));
        Assert.assertEquals(correctClients, vs.getClients());
    }

    @Test
    public void complexTransNumber(){ //many transactions test, should be 7 total
        Order order1 = new Order(0, 1, 0, 250, 10, true);
        Order order2 = new Order( 0, 1, 1, 200, 20, true);
        Order order3 = new Order( 1, 0, 1, 75, 30, true);
        Order order4 = new Order( 2, 0, 0, 20, 1, true);
        Order order5 = new Order(2, 2, 0, 150, 50, false);
        //client 2 sells 10 shares of equity 0 to client 1 for 250
        Order order6 = new Order( 2, 1, 1, 300, 20, true);
        Order order7 = new Order( 3, 2, 0, 500, 20, true);
        //client 2 sells 20 shares of equity 0 to client 2 for 150
        Order order8 = new Order( 4, 1, 1, 100, 20, true);
        Order order9 = new Order(5, 2, 1, 10, 50, false);
        //client 2 sells 20 shares of equity 1 to client 1 for 300
        //client 2 sells 20 shares of equity 1 to client 1 for 200
        //client 2 sells 10 shares of equity 1 to client 1 for 100
        Order order10 = new Order( 5, 0, 1, 25, 20, true);
        Order order11 = new Order(6, 0, 1, 50, 50, false);
        //client 0 sells 10 shares of equity 1 to client 1 for 100
        //client 0 sells 30 shares of equity 1 to client 0 for 75
        Order order12 = new Order(7, 1, 0, 50, 5, true);
        //client 0 bought 30 sold 40 nettrade 1000
        //client 1 bought 70 sold 0 nettrade -2500-6000-4000-1000-1000=-14500
        //client 2 bought 20 sold 80 nettrade 2500+6000+4000+1000=13500
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
        Assert.assertEquals(7, vs.getNumTrans());
    }

    @Test
    public void complexTransMedian(){ //many transactions test, dynamic medians
        Order order1 = new Order(0, 1, 0, 250, 10, true);
        Order order2 = new Order( 0, 1, 1, 200, 20, true);
        Order order3 = new Order( 1, 0, 1, 75, 30, true);
        Order order4 = new Order( 2, 0, 0, 20, 1, true);
        Order order5 = new Order(2, 2, 0, 150, 50, false);
        //client 2 sells 10 shares of equity 0 to client 1 for 250
        Order order6 = new Order( 2, 1, 1, 300, 20, true);
        Order order7 = new Order( 3, 2, 0, 500, 20, true);
        //client 2 sells 20 shares of equity 0 to client 2 for 150
        Order order8 = new Order( 4, 1, 1, 100, 20, true);
        Order order9 = new Order(5, 2, 1, 10, 50, false);
        //client 2 sells 20 shares of equity 1 to client 1 for 300
        //client 2 sells 20 shares of equity 1 to client 1 for 200
        //client 2 sells 10 shares of equity 1 to client 1 for 100
        Order order10 = new Order( 5, 0, 1, 25, 20, true);
        Order order11 = new Order(6, 0, 1, 50, 50, false);
        //client 0 sells 10 shares of equity 1 to client 1 for 100
        //client 0 sells 30 shares of equity 1 to client 0 for 75
        Order order12 = new Order(7, 1, 0, 50, 5, true);
        //client 0 bought 30 sold 40 nettrade 1000
        //client 1 bought 70 sold 0 nettrade -2500-6000-4000-1000-1000=-14500
        //client 2 bought 20 sold 80 nettrade 2500+6000+4000+1000=13500
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
        ArrayList<Pair<Integer, ArrayList<Integer>>> correctMedians = new ArrayList<>();
        ArrayList<Integer> currentMedians = new ArrayList<>();
        //timestamp 2
        currentMedians.add(250); //equity 0
        currentMedians.add(0); //equity 1
        correctMedians.add(Pair.of(2, currentMedians));
        currentMedians = new ArrayList<>();
        //timestamp 3
        currentMedians.add(200); //equity 0
        currentMedians.add(0); //equity 1
        correctMedians.add(Pair.of(3, currentMedians));
        currentMedians = new ArrayList<>();
        //etc.
        currentMedians.add(200);
        currentMedians.add(0);
        correctMedians.add(Pair.of(4, currentMedians));
        currentMedians = new ArrayList<>();
        currentMedians.add(200);
        currentMedians.add(200);
        correctMedians.add(Pair.of(5, currentMedians));
        currentMedians = new ArrayList<>();
        currentMedians.add(200);
        currentMedians.add(100);
        correctMedians.add(Pair.of(6, currentMedians));
        currentMedians = new ArrayList<>();
        currentMedians.add(200);
        currentMedians.add(100);
        correctMedians.add(Pair.of(7, currentMedians));
        Assert.assertEquals(correctMedians, vs.getMedians());
    }

    @Test
    public void complexTransTime(){ //many transactions test, there are opportunities for profit with both equities
        Order order1 = new Order(0, 1, 0, 250, 10, true);
        Order order2 = new Order( 0, 1, 1, 200, 20, true);
        Order order3 = new Order( 1, 0, 1, 75, 30, true);
        Order order4 = new Order( 2, 0, 0, 20, 1, true);
        Order order5 = new Order(2, 2, 0, 150, 50, false);
        //client 2 sells 10 shares of equity 0 to client 1 for 250
        Order order6 = new Order( 2, 1, 1, 300, 20, true);
        Order order7 = new Order( 3, 2, 0, 500, 20, true);
        //client 2 sells 20 shares of equity 0 to client 2 for 150
        Order order8 = new Order( 4, 1, 1, 100, 20, true);
        Order order9 = new Order(5, 2, 1, 10, 50, false);
        //client 2 sells 20 shares of equity 1 to client 1 for 300
        //client 2 sells 20 shares of equity 1 to client 1 for 200
        //client 2 sells 10 shares of equity 1 to client 1 for 100
        Order order10 = new Order( 5, 0, 1, 25, 20, true);
        Order order11 = new Order(6, 0, 1, 50, 50, false);
        //client 0 sells 10 shares of equity 1 to client 1 for 100
        //client 0 sells 30 shares of equity 1 to client 0 for 75
        Order order12 = new Order(7, 1, 0, 50, 5, true);
        //client 0 bought 30 sold 40 nettrade 1000
        //client 1 bought 70 sold 0 nettrade -2500-6000-4000-1000-1000=-14500
        //client 2 bought 20 sold 80 nettrade 2500+6000+4000+1000=13500
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
        ArrayList<Pair<Integer, Integer>> correctTime = new ArrayList<>();
        correctTime.add(Pair.of(2, 3));
        correctTime.add(Pair.of(5, 5));
    }


}
