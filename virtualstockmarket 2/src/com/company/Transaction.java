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

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public void setEquity(int equity) {
        this.equity = equity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
