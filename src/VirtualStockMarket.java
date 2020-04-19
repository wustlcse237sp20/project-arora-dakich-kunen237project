import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class VirtualStockMarket {

    private ArrayList<Order> orderList; //all the orders
    private int clientCount;
    private int equityCount;
    private ArrayList<Client> clients;
    private ArrayList<Integer> currentMedians;
    private ArrayList<PriorityQueue<Integer>> lowTransactions; //equity values below the median
    private ArrayList<PriorityQueue<Integer>> highTransactions; //equity values above the median
    private ArrayList<PriorityQueue<Pair<Order, Order>>> buySellPairs; //buy/sell pairs used for time travelers portion
    private ArrayList<Pair<Order, Order>> currentBuySellPairs;
    private ArrayList<PriorityQueue<Order>> buyOrders; //only buy orders
    private ArrayList<PriorityQueue<Order>> sellOrders; //only sell orders
    private ArrayList<Pair<Integer, ArrayList<Integer>>> medians; //list of all equities medians at the end of each timestamp.
    //0 means no transaction occurred for a given equity
    private ArrayList<Pair<Integer, ArrayList<Transaction>>> transactions; //list if transactions that occurred for each timestamp.
    private int transactionCount; //total number of transactions that occurred
    private ArrayList<Pair<Integer, Integer>> timeTravelers; //list of times to buy and sell stocks to maximize profit

    public VirtualStockMarket(ArrayList<Order> orderList, int clientCount, int equityCount) {
        this.orderList = orderList;
        this.clientCount = clientCount;
        this.equityCount = equityCount;
        this.clients = new ArrayList<>(clientCount);
        this.currentMedians = new ArrayList<>(equityCount); //keeps track of the current list of medians
        this.lowTransactions = new ArrayList<>(equityCount);
        this.highTransactions = new ArrayList<>(equityCount);
        this.buySellPairs = new ArrayList<>(equityCount);
        this.currentBuySellPairs = new ArrayList<>(equityCount);
        this.buyOrders = new ArrayList<>(equityCount);
        this.sellOrders = new ArrayList<>(equityCount);
        for (int i = 0; i < clientCount; i++) {
            this.clients.add(new Client());
        }
        for (int i = 0; i < equityCount; i++) {
            this.currentMedians.add(0);
            this.lowTransactions.add(new PriorityQueue<>(orderList.size(), Collections.reverseOrder()));
            this.highTransactions.add(new PriorityQueue<>(orderList.size()));
            this.currentBuySellPairs.add(Pair.of(new Order(true), new Order(false)));
            this.buySellPairs.add(new PriorityQueue<>(orderList.size(), new TimeComparator()));
            this.buyOrders.add(new PriorityQueue<>(orderList.size(), new BuyComparator()));
            this.sellOrders.add(new PriorityQueue<>(orderList.size(), new SellComparator()));
        }
        this.transactions = new ArrayList<>();
        this.medians = new ArrayList<>(); //we want to match each median with its timestamp. A Pair basically.
        this.transactionCount = 0;
        this.timeTravelers = new ArrayList<>(equityCount);
    }

    public int getClientCount() {
        return clientCount;
    }

    public int getEquityCount() {
        return equityCount;
    }

    public ArrayList<Client> getClients() {
        return clients;
    } //transaction record for each client

    public ArrayList<Pair<Integer, ArrayList<Integer>>> getMedians() {
        return medians;
    } //all equities medians for each timestamp

    public ArrayList<Pair<Integer, ArrayList<Transaction>>> getTransactions() {
        return transactions;
    } //a list of transactions that occured for each timestamp

    public int getTransactionCount() {
        return transactionCount;
    }//total number of transactions

    public ArrayList<Pair<Integer, Integer>> getTimeTravelers() {
        return timeTravelers;
    }// ideal buy and sell times for each equity

    public void computeTrans() { //process orders, store data in member variables
        //here we take in the list of orders and compute all the transactions
        ArrayList<Transaction> currentTrans = new ArrayList<>();
        int currentTimestamp = 0;
        for (int i = 0; i < orderList.size(); i++) {  //iterate through the orders
            orderList.get(i).setRelTimestamp(i);
            computeTime(i);
            if(orderList.get(i).getTimestamp() != currentTimestamp) {
                if (!currentTrans.isEmpty()) {
                    transactions.add(Pair.of(currentTimestamp, currentTrans));
                    currentTrans = new ArrayList<>();
                }
                if (Collections.max(currentMedians) != 0) {
                    medians.add(Pair.of(currentTimestamp, new ArrayList<>(currentMedians)));
                }
                currentTimestamp = orderList.get(i).getTimestamp();
            }
            Transaction transaction;
            if (orderList.get(i).getType()) {
                transaction = getTransBuyer(i);
            } else {
                transaction = getTransSeller(i);
            }
            while(transaction != null) {
                computeMedian(transaction, i);
                currentTrans.add(transaction);
                transactionCount++;
                if (orderList.get(i).getType()) {
                    transaction = getTransBuyer(i);
                } else {
                    transaction = getTransSeller(i);
                }
            }

            if(orderList.get(i).getQuantity() != 0) {
                if(orderList.get(i).getType()) {
                    buyOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                } else {
                    sellOrders.get(orderList.get(i).getEquityID()).add(orderList.get(i));
                }
            }
        }
        if (!currentTrans.isEmpty()) {
            transactions.add(Pair.of(currentTimestamp, currentTrans));
        }
        if (Collections.max(currentMedians) != 0) {
            medians.add(Pair.of(currentTimestamp, new ArrayList<>(currentMedians)));
        }
        generateTimeList();
    }

    //given buy order, find optimal sell match, if it exists
    private Transaction getTransBuyer(int orderIndex) {
        if (orderList.get(orderIndex).getQuantity() == 0 || sellOrders.get(orderList.get(orderIndex).getEquityID()).isEmpty() ||
            orderList.get(orderIndex).getPrice() < sellOrders.get(orderList.get(orderIndex).getEquityID()).peek().getPrice())
            return null;
        Order matchedSeller = sellOrders.get(orderList.get(orderIndex).getEquityID()).poll();
        int quantity = Math.min(orderList.get(orderIndex).getQuantity(), matchedSeller.getQuantity());
        Transaction transaction = new Transaction(orderList.get(orderIndex).getClientID(),
                                matchedSeller.getClientID(), orderList.get(orderIndex).getEquityID(),
                                matchedSeller.getPrice(), quantity);
        orderList.get(orderIndex).setQuantity(orderList.get(orderIndex).getQuantity() - quantity);
        matchedSeller.setQuantity(matchedSeller.getQuantity() - quantity);
        updateClients(transaction);
        if (matchedSeller.getQuantity() != 0) {
            sellOrders.get(orderList.get(orderIndex).getEquityID()).add(matchedSeller);
        }
        return transaction;
    }

    //given sell order, find optimal buy match, if it exists
    private Transaction getTransSeller(int orderIndex) { //CHANGE IDX
        if (orderList.get(orderIndex).getQuantity() == 0 || buyOrders.get(orderList.get(orderIndex).getEquityID()).isEmpty() ||
            orderList.get(orderIndex).getPrice() > buyOrders.get(orderList.get(orderIndex).getEquityID()).peek().getPrice())
            return null;
        Order matchedBuyer = buyOrders.get(orderList.get(orderIndex).getEquityID()).poll();
        int quantity = Math.min(matchedBuyer.getQuantity(), orderList.get(orderIndex).getQuantity());
        Transaction transaction = new Transaction(matchedBuyer.getClientID(),
                            orderList.get(orderIndex).getClientID(), orderList.get(orderIndex).getEquityID(),
                            matchedBuyer.getPrice(), quantity);
        orderList.get(orderIndex).setQuantity(orderList.get(orderIndex).getQuantity() - quantity);
        matchedBuyer.setQuantity(matchedBuyer.getQuantity() - quantity);
        updateClients(transaction);
        if (matchedBuyer.getQuantity() != 0) {
            buyOrders.get(orderList.get(orderIndex).getEquityID()).add(matchedBuyer);
        }
        return transaction;
    }

    //given a transaction, update buyer and seller's data
    private void updateClients(Transaction transaction) {
        int quantity = clients.get(transaction.getBuyer()).getBought();
        int netTrade = clients.get(transaction.getBuyer()).getNetTrade();
        clients.get(transaction.getBuyer()).setBought(quantity + transaction.getQuantity());
        clients.get(transaction.getBuyer()).setNetTrade(netTrade - transaction.getQuantity() * transaction.getPrice());
        quantity = clients.get(transaction.getSeller()).getSold();
        netTrade = clients.get(transaction.getSeller()).getNetTrade();
        clients.get(transaction.getSeller()).setSold(quantity + transaction.getQuantity());
        clients.get(transaction.getSeller()).setNetTrade(netTrade + transaction.getQuantity() * transaction.getPrice());
    }

    //insert new transaction value to list of equity values (low and/or high) and update median
    private void computeMedian(Transaction transaction,int orderIndex) {
        int equityID = orderList.get(orderIndex).getEquityID();
        if(transaction.getPrice() < currentMedians.get(equityID)) {
           lowTransactions.get(equityID).add(transaction.getPrice());
        } else {
            highTransactions.get(equityID).add(transaction.getPrice());
        }
        int lowTransactionSize = lowTransactions.get(equityID).size();
        int highTransactionSize = highTransactions.get(equityID).size();
        int median = 0;
        int transferElement;
        switch (lowTransactionSize - highTransactionSize) {
        case -2:
            transferElement = highTransactions.get(equityID).poll();
            lowTransactions.get(equityID).add(transferElement);
            median = (lowTransactions.get(equityID).peek() + highTransactions.get(equityID).peek())/2;
            break;
        case -1:
            median = highTransactions.get(equityID).peek();
            break;
        case 0:
            median = (lowTransactions.get(equityID).peek() + highTransactions.get(equityID).peek())/2;
            break;
        case 1:
            median = lowTransactions.get(equityID).peek();
            break;
        case 2:
            transferElement = lowTransactions.get(equityID).poll();
            highTransactions.get(equityID).add(transferElement);
            median = (lowTransactions.get(equityID).peek() + highTransactions.get(equityID).peek())/2;
            break;
        }
        currentMedians.set(equityID,median);
    }

    //update buy/sell pair if better pair can be created for time traveling
    private void computeTime(int orderIndex) {
        int equityID = orderList.get(orderIndex).getEquityID();
        if(!orderList.get(orderIndex).getType()) {
            if(currentBuySellPairs.get(equityID).first.getPrice() == 0 ||
                    orderList.get(orderIndex).getPrice() < currentBuySellPairs.get(equityID).first.getPrice()) {
                if(currentBuySellPairs.get(equityID).first.getPrice() != 0 &&
                        currentBuySellPairs.get(equityID).first.getPrice() < currentBuySellPairs.get(equityID).second.getPrice()) {
                    buySellPairs.get(equityID).add(currentBuySellPairs.get(equityID));
                }
                currentBuySellPairs.set(equityID, Pair.of(new Order(orderList.get(orderIndex)), currentBuySellPairs.get(equityID).second));
                currentBuySellPairs.get(equityID).second.setPrice(0);
            }
        }
        else if(currentBuySellPairs.get(equityID).second.getPrice() == 0 ||
                orderList.get(orderIndex).getPrice() > currentBuySellPairs.get(equityID).second.getPrice()) {
            currentBuySellPairs.set(equityID, Pair.of(currentBuySellPairs.get(equityID).first, new Order(orderList.get(orderIndex))));
        }
    }

    //compute optimal buy/sell times for each equity
    private void generateTimeList() {
        for (int i = 0; i < equityCount; i++) {
            int buyTime = -1;
            int sellTime = -1;
            if (currentBuySellPairs.get(i).first.getPrice() != 0 && currentBuySellPairs.get(i).second.getPrice() != 0 &&
                    currentBuySellPairs.get(i).first.getPrice() < currentBuySellPairs.get(i).second.getPrice()) {
                buySellPairs.get(i).add(currentBuySellPairs.get(i));
            }
            if (!buySellPairs.get(i).isEmpty()) {
                buyTime = buySellPairs.get(i).peek().first.getTimestamp();
                sellTime = buySellPairs.get(i).peek().second.getTimestamp();
            }
            timeTravelers.add(Pair.of(buyTime, sellTime));
        }
    }
}
