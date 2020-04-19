import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return bought == client.bought &&
                sold == client.sold &&
                netTrade == client.netTrade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bought, sold, netTrade);
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
