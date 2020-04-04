package com.company;

public class Transaction {

    private int buyer;
    private int seller;
    private int equity;
    private int price;
    private int quantity;


    public Transaction(int buyer, int seller, int equity, int price, int quantity) {
        this.buyer = buyer;
        this.seller = seller;
        this.equity = equity;
        this.price = price;
        this.quantity = quantity;
    }

    public int getBuyer() {
        return buyer;
    }

    public int getSeller() {
        return seller;
    }

    public int getEquity() {
        return equity;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
