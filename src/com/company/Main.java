package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

//        VirtualStockMarketTest tests = new VirtualStockMarketTest();
//        tests.simpleTransactionClient();
//        System.out.println("simpleTransactionClient test passed!");
//        tests.simpleTransactionMedian();
//        System.out.println("simpleTransactionMedian test passed!");
//        tests.simpleTransactionTime();
//        System.out.println("simpleTransactionTime test passed!");
//        tests.twoBuysOneSellClient();
//        System.out.println("twoBuysOneSellClient test passed!");
//        tests.twoBuysOneSellNumber();
//        System.out.println("twoBuysOneSellNumber test passed!");
//        tests.twoBuyOneSellMedian();
//        System.out.println("twoBuyOneSellMedian test passed!");
//        tests.twoSellsOneBuyClient();
//        System.out.println("twoSellsOneBuyClient test passed!");
//        tests.twoSellsOneBuyMedian();
//        System.out.println("twoSellsOneBuyMedian test passed!");
//        tests.sellerMoreExpensive();
//        System.out.println("sellerMoreExpensive test passed!");
//        tests.complexTransactionClient();
//        System.out.println("complexTransactionClient test passed!");
//        tests.complexTransactionNumber();
//        System.out.println("complexTransactionNumber test passed!");
//        tests.complexTransactionMedian();
//        System.out.println("complexTransactionMedian test passed!");
//        tests.complexTransactionTime();
//        System.out.println("complexTransactionTime test passed!");
//        System.out.println();
//        BuyComparatorTest buyComparatorTest = new BuyComparatorTest();
//        buyComparatorTest.compare();
//        System.out.println("buyCompTest passed!");
//
//        SellComparatorTest sellCompTest = new SellComparatorTest();
//        sellCompTest.compare();
//        System.out.println("sellCompTest passed!");
//
//        TimeComparatorTest timeCompTest = new TimeComparatorTest();
//        timeCompTest.compare();
//        System.out.println("timeCompTest passed!");
//        System.out.println();
//        System.out.println("Running complex test...");
//        System.out.println();

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

        System.out.println("---end of day---");
        System.out.println("orders processed: "+ vs.getTransactionCount());
        for (Pair<Integer, ArrayList<Transaction>> transList: vs.getTransactions()) {
            System.out.println("timestamp " + transList.first + ":");
            for (Transaction trans:transList.second) {
                System.out.println("client " + trans.getBuyer() + " purchased " + trans.getQuantity() +
                        " shares of equity " + trans.getEquity() + " from client " + trans.getSeller() + " for $" +
                        trans.getPrice() + "/share");

            }

        }
        System.out.println("---client info---");
        for (int i = 0; i < vs.getClientCount() ; i++) {
            System.out.println("client " + i + " bought " + vs.getClients().get(i).getBought() + " and sold " +
                    vs.getClients().get(i).getSold() + " for a net transfer of $ " +
                    vs.getClients().get(i).getNetTrade());

        }
    }
    public static ArrayList<Order> readInput(String fileName, ArrayList<String> clientNames, ArrayList<String> equityNames) {
        ArrayList<Order> orderList = new ArrayList<>();
        Scanner input = null;
        try{
             input = new Scanner(new File("../../../input/" + fileName));
        }
        catch(FileNotFoundException s){
        //throw exception here
        }
        while(input.hasNext()){
            String contents = input.next();
            String[] elements = contents.split(" ");
            int clientID, equityID, timestamp,quantity, price;
            if(elements[1].length() > 1 && elements[1].charAt(0) == 'C' && Character.isDigit(elements[1].charAt(1))){
                clientID = Integer.parseInt(elements[1].substring(1));
            } else {
                clientID = clientNames.indexOf(elements[1]);
                if(clientID == -1) {
                    //throw exception here--need to add
                }
            }
            if(elements[3].length() > 1 && elements[3].charAt(0) == 'E' && Character.isDigit(elements[3].charAt(1))){
                equityID = Integer.parseInt(elements[3].substring(1));
            } else {
                equityID = equityNames.indexOf(elements[3]);
                if(equityID == -1) {
                    //throw exception here--need to add
                }
            }
            timestamp = Integer.parseInt(elements[0]);
            price = Integer.parseInt(elements[4]);
            quantity = Integer.parseInt(elements[5]);
            orderList.add(new Order(timestamp, clientID, equityID, price, quantity, elements[2].equals("BUY")));

        }
        input.close();

        return orderList;
    }

    public static ArrayList<String> readClients() {
        ArrayList<String> clientNames = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(new File("../../../clients.txt"));
        } catch (FileNotFoundException s) {
            //throw exception here
        }
        while (input.hasNext()) {
            clientNames.add(input.next());
        }
        input.close();

        return clientNames;
    }

    public static ArrayList<String> readEquities() {
        ArrayList<String> equityNames = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(new File("../../../equities.txt"));
        } catch (FileNotFoundException s) {
            //throw exception here
        }
        while (input.hasNext()) {
            equityNames.add(input.next());
        }
        input.close();

        return equityNames;
    }

    public static ArrayList<Order> generateInput(String fileName, int orderCount) {

        try {
            FileWriter writer = new FileWriter("../../../input/" + fileName);
            for (int i = 0; i < orderCount; i++) {
                writer.write();
            }

        } catch(IOException e) {

        }


    }





}
