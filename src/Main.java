import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
        FileInputOutput fileHandler = new FileInputOutput("example");
        orderList = fileHandler.generateInput(1000,500, 100);
        vs = new VirtualStockMarket(orderList, fileHandler.getClientNames().size(), fileHandler.getEquityNames().size());
        vs.computeTrans();
        fileHandler.writeOutput(vs.getTransactionCount(), vs.getTransactions(), vs.getClients());
    }
}
