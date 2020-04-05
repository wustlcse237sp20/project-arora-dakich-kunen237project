package com.company;

public class Client { //keeps track of the clients progress

    private int bought;
    private int sold;
    private int netTrade;

    public Client() {
        this.bought = 0;
        this.sold = 0;
        this.netTrade = 0;
    }

    public Client(int bought, int sold, int netTrade) {
        this.bought = bought;
        this.sold = sold;
        this.netTrade = netTrade;
    }

    public int getBought() {
        return bought;
    }

    public int getSold() {
        return sold;
    }

    public int getNetTrade() {
        return netTrade;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public void setNetTrade(int netTrade) {
        this.netTrade = netTrade;
    }
}
