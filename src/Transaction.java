import java.util.Objects;

public class Transaction { //information for each transaction that took place

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return buyer == that.buyer &&
                seller == that.seller &&
                equity == that.equity &&
                price == that.price &&
                quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyer, seller, equity, price, quantity);
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
