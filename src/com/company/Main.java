package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

        VirtualStockMarketTest tests = new VirtualStockMarketTest();
        tests.simpleTransClient();
        tests.simpleTransMedian();
        tests.simpleTransTime();
        tests.twoBOneSClient();
        tests.twoBOneSNumber();
        tests.twoBOneSMedian();
        tests.twoSOneBClient();
        tests.twoSOneBMedian();
        tests.sellerMoreExpensive();
        tests.complexTransClient();
        tests.complexTransNumber();
        tests.complexTransMedian();
        tests.complexTransTime();

        BuyCompTest buyCompTest = new BuyCompTest();
        buyCompTest.compare();

        SellCompTest sellCompTest = new SellCompTest();
        sellCompTest.compare();

        TimeCompTest timeCompTest = new TimeCompTest();
        timeCompTest.compare();
    }
}
