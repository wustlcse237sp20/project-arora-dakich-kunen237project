import java.awt.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFile {

    private JFrame frame;
    private JTextField inputFileTextField;
    private JTextField randomTextField;
    private JTextField clientTextField;
    private JTextField stockTextField;
    private VirtualStockMarket stockMarket;
    private FileInputOutput fileHandler;
    private int status;

    public InputFile() {
        initialize();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        inputFileTextField = new JTextField();
        inputFileTextField.setBounds(168, 25, 130, 26);
        frame.getContentPane().add(inputFileTextField);
        inputFileTextField.setColumns(10);

        JLabel infi = new JLabel("Stored in input/<filename>.in:");
        infi.setBounds(14, 0, 150, 29);
        infi.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        frame.getContentPane().add(infi);

        JButton btnInputFileButton = new JButton("Input file");
        btnInputFileButton.setForeground(new Color(255, 0, 0));
        btnInputFileButton.setBounds(6, 25, 117, 29);
        frame.getContentPane().add(btnInputFileButton);
        btnInputFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (inputFileTextField.getText().equals("")) {
                        throw new RuntimeException("Input filename missing!");
                    }
                    fileHandler = new FileInputOutput(inputFileTextField.getText());
                    ArrayList<Order> orderList = fileHandler.readInput();
                    stockMarket = new VirtualStockMarket(orderList, fileHandler.getClientNames().size(),
                            fileHandler.getEquityNames().size());
                    status = 1;
                    JOptionPane.showMessageDialog(frame, "Success: input/" + inputFileTextField.getText() +
                            ".in loaded!");
                } catch (RuntimeException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        randomTextField = new JTextField();
        randomTextField.setBounds(223, 109, 130, 26);
        frame.getContentPane().add(randomTextField);
        randomTextField.setColumns(10);

        JLabel gri = new JLabel("Add input file to input/<filename>.in:");
        gri.setBounds(14, 84, 190, 29);
        gri.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        frame.getContentPane().add(gri);

        JButton btnRandomInputButton = new JButton("Generate random input");
        btnRandomInputButton.setForeground(Color.GREEN);
        btnRandomInputButton.setBounds(6, 109, 170, 29);
        frame.getContentPane().add(btnRandomInputButton);
        btnRandomInputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numOrders, priceLimit, quantityLimit;
                try {
                    String numOrdersStr = (String)JOptionPane.showInputDialog(
                            frame,
                            "Please input the number of orders",
                            "",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            null);
                    if (numOrdersStr == null) {
                        return;
                    }
                    numOrders = Integer.parseInt(numOrdersStr);
                    if (numOrders <= 0) {
                        throw new NumberFormatException();
                    }
                    String priceLimitStr = (String)JOptionPane.showInputDialog(
                            frame,
                            "What is the price limit?",
                            "",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            null);
                    if (priceLimitStr == null) {
                        return;
                    }
                    priceLimit = Integer.parseInt(priceLimitStr);
                    if (priceLimit <= 0) {
                        throw new NumberFormatException();
                    }
                    String quantityLimitStr = (String)JOptionPane.showInputDialog(
                            frame,
                            "What is the quantity limit?",
                            "",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            null);
                    if (quantityLimitStr == null) {
                        return;
                    }
                    quantityLimit = Integer.parseInt(quantityLimitStr);
                    if (quantityLimit <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException s) {
                    JOptionPane.showMessageDialog(frame, "Error: Not a positive integer!");
                    return;
                }
                try {
                    if (randomTextField.getText().equals("")) {
                        throw new RuntimeException("Input filename missing!");
                    }
                    fileHandler = new FileInputOutput(randomTextField.getText());
                    ArrayList<Order> orderList = fileHandler.generateInput(numOrders, priceLimit, quantityLimit);
                    stockMarket = new VirtualStockMarket(orderList, fileHandler.getClientNames().size(),
                            fileHandler.getEquityNames().size());
                    status = 1;
                    JOptionPane.showMessageDialog(frame, "Success: input/" + randomTextField.getText() +
                            ".in generated and loaded!");
                } catch (RuntimeException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        JLabel cl = new JLabel("Manage clients:");
        cl.setBounds(50, 235, 190, 29);
        cl.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        frame.getContentPane().add(cl);

        clientTextField = new JTextField();
        clientTextField.setBounds(45, 260, 185, 26);
        frame.getContentPane().add(clientTextField);
        clientTextField.setColumns(10);

        JLabel st = new JLabel("Manage stocks:");
        st.setBounds(392, 235, 190, 29);
        st.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        frame.getContentPane().add(st);

        stockTextField = new JTextField();
        stockTextField.setBounds(387, 260, 185, 26);
        frame.getContentPane().add(stockTextField);
        stockTextField.setColumns(10);

        JButton btnAddClientButton = new JButton("Add client");
        btnAddClientButton.setForeground(new Color(0, 0, 255));
        btnAddClientButton.setBounds(45, 298, 117, 29);
        frame.getContentPane().add(btnAddClientButton);
        btnAddClientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (clientTextField.getText().equals("")) {
                        throw new IllegalArgumentException("No client name provided!");
                    }
                    ArrayList<String> clientNames = new ArrayList<>();
                    //add ../ eventually
                    Scanner input = new Scanner(new File("clients.txt"));
                    while (input.hasNextLine()) {
                        clientNames.add(input.nextLine());
                    }
                    input.close();
                    if (clientNames.indexOf(clientTextField.getText()) != -1) {
                        throw new IllegalArgumentException("Client " + clientTextField.getText() + " already exists!");
                    }
                    //add ../ eventually
                    FileWriter writer = new FileWriter("clients.txt",true);
                    writer.write(clientTextField.getText() + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(frame, "Success: Client " + clientTextField.getText() +
                            " added!");
                } catch (IOException s) {
                    JOptionPane.showMessageDialog(frame, "Error: clients.txt not found!");
                } catch (IllegalArgumentException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        JButton btnRemoveClientButton = new JButton("Remove client");
        btnRemoveClientButton.setForeground(Color.ORANGE);
        btnRemoveClientButton.setBounds(45, 339, 117, 29);
        frame.getContentPane().add(btnRemoveClientButton);
        btnRemoveClientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (clientTextField.getText().equals("")) {
                        throw new RuntimeException("No client name provided!");
                    }
                    ArrayList<String> clientNames = new ArrayList<>();
                    Scanner input;
                    try {
                        //add ../ eventually
                        input = new Scanner(new File("clients.txt"));
                    } catch (FileNotFoundException s) {
                        throw new RuntimeException("clients.txt not found!");
                    }
                    while (input.hasNextLine()) {
                        clientNames.add(input.nextLine());
                    }
                    input.close();
                    int index = clientNames.indexOf(clientTextField.getText());
                    if (index == -1) {
                        throw new RuntimeException("Client " + clientTextField.getText() + " not found!");
                    }
                    clientNames.remove(index);
                    try {
                        //add ../ eventually
                        FileWriter writer = new FileWriter("clients.txt");
                        for (String client : clientNames) {
                            writer.write(client + "\n");
                        }
                        writer.close();
                        JOptionPane.showMessageDialog(frame, "Success: Client " + clientTextField.getText() +
                                " removed!");
                    } catch (IOException s) {
                        throw new RuntimeException("clients.txt not found!");
                    }
                } catch (RuntimeException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        JButton btnAddStockButton = new JButton("Add stock");
        btnAddStockButton.setForeground(new Color(0, 0, 255));
        btnAddStockButton.setBounds(397, 298, 117, 29);
        frame.getContentPane().add(btnAddStockButton);
        btnAddStockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (stockTextField.getText().equals("")) {
                        throw new IllegalArgumentException("No stock name provided!");
                    }
                    ArrayList<String> equityNames = new ArrayList<>();
                    //add ../ eventually
                    Scanner input = new Scanner(new File("equities.txt"));
                    while (input.hasNextLine()) {
                        equityNames.add(input.nextLine());
                    }
                    input.close();
                    if (equityNames.indexOf(stockTextField.getText()) != -1) {
                        throw new IllegalArgumentException("Stock " + stockTextField.getText() + " already exists!");
                    }
                    //add ../ eventually
                    FileWriter writer = new FileWriter("equities.txt",true);
                    writer.write(stockTextField.getText() + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(frame, "Success: Stock " + stockTextField.getText() +
                            " added!");
                } catch (IOException s) {
                    JOptionPane.showMessageDialog(frame, "Error: equities.txt not found!");
                } catch (IllegalArgumentException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        JButton btnRemoveStockButton = new JButton("Remove stock");
        btnRemoveStockButton.setForeground(Color.ORANGE);
        btnRemoveStockButton.setBounds(397, 339, 117, 29);
        frame.getContentPane().add(btnRemoveStockButton);
        btnRemoveStockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (stockTextField.getText().equals("")) {
                        throw new RuntimeException("No stock name provided!");
                    }
                    ArrayList<String> equityNames = new ArrayList<>();
                    Scanner input = null;
                    try {
                        //add ../ eventually
                        input = new Scanner(new File("equities.txt"));
                    } catch (FileNotFoundException s) {
                        throw new RuntimeException("equities.txt not found!");
                    }
                    while (input.hasNextLine()) {
                        equityNames.add(input.nextLine());
                    }
                    input.close();
                    int index = equityNames.indexOf(stockTextField.getText());
                    if (index == -1) {
                        throw new RuntimeException("Stock " + stockTextField.getText() + " not found!");
                    }
                    equityNames.remove(index);
                    try {
                        //add ../ eventually
                        FileWriter writer = new FileWriter("equities.txt");
                        for (String equity : equityNames) {
                            writer.write(equity + "\n");
                        }
                        writer.close();
                        JOptionPane.showMessageDialog(frame, "Success: Stock " + stockTextField.getText() +
                                " removed!");
                    } catch (IOException s) {
                        throw new RuntimeException("clients.txt not found!");
                    }
                } catch (RuntimeException s) {
                    JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
                }
            }
        });

        JButton btnDoneButton = new JButton("Done");
        btnDoneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { frame.setVisible(false); }
        });
        btnDoneButton.setBounds(455, 531, 117, 29);
        frame.getContentPane().add(btnDoneButton);

        try {
            this.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public VirtualStockMarket getStockMarket() {
        return stockMarket;
    }

    public FileInputOutput getFileHandler() {
        return fileHandler;
    }
}

