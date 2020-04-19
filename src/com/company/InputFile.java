package com.company;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
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

    public InputFile() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        inputFileTextField = new JTextField();
        inputFileTextField.setBounds(168, 18, 130, 26);
        frame.getContentPane().add(inputFileTextField);
        inputFileTextField.setColumns(10);

        JButton btnInputFileButton = new JButton("Input file");
        btnInputFileButton.setForeground(new Color(255, 0, 0));
        btnInputFileButton.setBounds(6, 18, 117, 29);
        frame.getContentPane().add(btnInputFileButton);
        btnInputFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileHandler = new FileInputOutput(inputFileTextField.getText());
                ArrayList<Order> orderList = fileHandler.readInput();
                stockMarket = new VirtualStockMarket(orderList, fileHandler.getClientNames().size(),
                        fileHandler.getEquityNames().size());
            }
        });

        randomTextField = new JTextField();
        randomTextField.setBounds(223, 59, 130, 26);
        frame.getContentPane().add(randomTextField);
        randomTextField.setColumns(10);

        JButton btnRandomInputButton = new JButton("Generate random input");
        btnRandomInputButton.setForeground(Color.GREEN);
        btnRandomInputButton.setBounds(6, 59, 170, 29);
        frame.getContentPane().add(btnRandomInputButton);
        btnRandomInputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String numOrders = (String)JOptionPane.showInputDialog(
                        frame,
                        "Please input the number of orders",
                        "",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null);
                String priceLimit = (String)JOptionPane.showInputDialog(
                        frame,
                        "What is the price limit?",
                        "",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null);
                String quantityLimit = (String)JOptionPane.showInputDialog(
                        frame,
                        "What is the quantity limit?",
                        "",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null);
                fileHandler = new FileInputOutput(randomTextField.getText());
                ArrayList<Order> orderList = fileHandler.generateInput(Integer.parseInt(numOrders),
                        Integer.parseInt(priceLimit), Integer.parseInt(quantityLimit));
                stockMarket = new VirtualStockMarket(orderList, fileHandler.getClientNames().size(),
                        fileHandler.getEquityNames().size());
            }
        });

        clientTextField = new JTextField();
        clientTextField.setBounds(45, 260, 185, 26);
        frame.getContentPane().add(clientTextField);
        clientTextField.setColumns(10);

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
                    FileWriter writer = new FileWriter("clients.txt",true);
                    writer.write(clientTextField.getText() + "\n");
                    writer.close();
                } catch (IOException s) {
                    //throw exception here
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
                ArrayList<String> clientNames = new ArrayList<>();
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
                int index = clientNames.indexOf(clientTextField.getText());
                if (index == -1) {
                    return;
                }
                clientNames.remove(index);
                try {
                    FileWriter writer = new FileWriter("clients.txt");
                    for (String client : clientNames) {
                        writer.write(client + "\n");
                    }
                    writer.close();
                } catch (IOException s) {
                    //throw exception here
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
                    FileWriter writer = new FileWriter("equities.txt",true);
                    writer.write(stockTextField.getText() + "\n");
                    writer.close();
                } catch (IOException s) {
                    //throw exception here
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
                ArrayList<String> equityNames = new ArrayList<>();
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
                int index = equityNames.indexOf(stockTextField.getText());
                if (index == -1) {
                    return;
                }
                equityNames.remove(index);
                try {
                    FileWriter writer = new FileWriter("equities.txt");
                    for (String equity : equityNames) {
                        writer.write(equity + "\n");
                    }
                    writer.close();
                } catch (IOException s) {
                    //throw exception here
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
