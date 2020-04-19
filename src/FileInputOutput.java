import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInputOutput {

    private String fileName;
    private ArrayList<String> clientNames;
    private ArrayList<String> equityNames;

    public FileInputOutput(String fileName) {
        this.fileName = fileName;
        readClients();
        readEquities();
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getClientNames() {
        return clientNames;
    }

    public ArrayList<String> getEquityNames() {
        return equityNames;
    }

    private void readClients() {
        clientNames = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(new File("clients.txt"));
        } catch (FileNotFoundException s) {
            //throw exception here
        }
        while (input.hasNextLine()) {
            clientNames.add(input.nextLine());
        }
        input.close();

    }

    private void readEquities() {
        equityNames = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(new File("equities.txt"));
        } catch (FileNotFoundException s) {
            //throw exception here
        }
        while (input.hasNextLine()) {
            equityNames.add(input.nextLine());
        }
        input.close();
    }

    public ArrayList<Order> readInput() {
        ArrayList<Order> orderList = new ArrayList<>();
        Scanner input = null;
        try{
            input = new Scanner(new File("input/" + fileName + ".in"));
        }
        catch(FileNotFoundException s){
            //throw exception here
        }
        while(input.hasNextLine()){
            String contents = input.nextLine();
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
            price = Integer.parseInt(elements[4].substring(1));
            quantity = Integer.parseInt(elements[5].substring(1));
            orderList.add(new Order(timestamp, clientID, equityID, price, quantity, elements[2].equals("BUY")));
        }
        input.close();
        return orderList;
    }

    public ArrayList<Order> generateInput(int orderCount, int priceLimit, int quantityLimit) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            FileWriter writer = new FileWriter("input/" + fileName + ".in");
            int absoluteTime = 0;
            for (int i = 0; i < orderCount; i++) {
                int clientID = (int)(Math.random() * clientNames.size());
                String randomClient = clientNames.get(clientID);
                String randomType = Math.random() < 0.5 ? "BUY" : "SELL";
                int equityID = (int)(Math.random() * equityNames.size());
                String randomEquity = equityNames.get(equityID);
                int randomPrice = (int)(Math.random() * priceLimit) + 1;
                int randomQuantity = (int)(Math.random() * quantityLimit) + 1;
                writer.write(absoluteTime + " " + randomClient + " " + randomType + " " + randomEquity + " $" +
                        randomPrice + " #" + randomQuantity + "\n");
                orderList.add(new Order(absoluteTime, clientID, equityID, randomPrice, randomQuantity,
                        randomType.equals("BUY")));
                int timeIncrement = (int)(Math.random() * (orderCount / 10 + 1)) - orderCount / 20;
                if (timeIncrement < 0) {
                    timeIncrement = 0;
                }
                absoluteTime += timeIncrement;
            }
            writer.close();
        } catch(IOException e) {
            //throw exception here
        }
        return orderList;
    }
    
    public void writeOutput(int transactionCount, ArrayList<Pair<Integer, ArrayList<Transaction>>> transactions,
                            ArrayList<Client> clients) {
        try {
            FileWriter writer = new FileWriter("output/" + fileName + ".out");
            writer.write("---end of day---\n");
            writer.write("orders processed: " + transactionCount + "\n");
            for (Pair<Integer, ArrayList<Transaction>> transList : transactions) {
                writer.write("timestamp " + transList.first + ":\n");
                for (Transaction transaction : transList.second) {
                    writer.write(clientNames.get(transaction.getBuyer()) + " purchased " +
                            transaction.getQuantity() + " shares of " + equityNames.get(transaction.getEquity()) +
                            " from " + clientNames.get(transaction.getSeller()) + " for $" + transaction.getPrice() +
                            "/share\n");
                }
            }
            writer.write("---client info---\n");
            for (int i = 0; i < clientNames.size(); i++) {
                writer.write(clientNames.get(i) + " bought " + clients.get(i).getBought() + " and sold " +
                        clients.get(i).getSold() + " for a net transfer of $" + clients.get(i).getNetTrade() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            //throw exception here
        }
    }
}
