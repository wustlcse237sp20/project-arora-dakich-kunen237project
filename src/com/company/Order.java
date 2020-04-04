package com.company;

public class Order {

    private int timestamp;
    private int relTimestamp; //first come first serve if theres a tie
    private int clientID;
    private int equityID;
    private int price;
    private int quantity;
    private boolean type; //true = buys and false = sell

    public Order(boolean type) {
        this.timestamp = 0;
        this.relTimestamp = 0;
        this.clientID = 0;
        this.equityID = 0;
        this.price = 0;
        this.quantity = 0;
        this.type = type;
    }

    public Order(int timestamp, int clientID, int equityID, int price, int quantity, boolean type) {
        this.timestamp = timestamp;
        this.relTimestamp = 0;
        this.clientID = clientID;
        this.equityID = equityID;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getRelTimestamp() {
        return relTimestamp;
    }

    public int getClientID() {
        return clientID;
    }

    public int getEquityID() {
        return equityID;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getType() {return type;}

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRelTimestamp(int relTimestamp) { this.relTimestamp = relTimestamp; }
}


