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

public class InputFile {

    private JFrame frame;
    private JTextField inputFileTextField;
    private JTextField randomTextField;
    private JTextField clientTextField;
    private JTextField stockTextField;

    /**
     * Launch the application.
     */
    public static void fileFrame() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InputFile window = new InputFile();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public InputFile() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnInputFileButton = new JButton("Input file");
        btnInputFileButton.setForeground(new Color(255, 0, 0));
        btnInputFileButton.setBounds(6, 18, 117, 29);
        frame.getContentPane().add(btnInputFileButton);

        inputFileTextField = new JTextField();
        inputFileTextField.setBounds(168, 18, 130, 26);
        frame.getContentPane().add(inputFileTextField);
        inputFileTextField.setColumns(10);

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
            }
        });


        randomTextField = new JTextField();
        randomTextField.setBounds(223, 59, 130, 26);
        frame.getContentPane().add(randomTextField);
        randomTextField.setColumns(10);

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

        JButton btnRemoveClientButton = new JButton("Remove client");
        btnRemoveClientButton.setForeground(Color.ORANGE);
        btnRemoveClientButton.setBounds(45, 339, 117, 29);
        frame.getContentPane().add(btnRemoveClientButton);

        JButton btnAddStockButton = new JButton("Add stock");
        btnAddStockButton.setForeground(new Color(0, 0, 255));
        btnAddStockButton.setBounds(397, 298, 117, 29);
        frame.getContentPane().add(btnAddStockButton);

        JButton btnRemoveStockButton = new JButton("Remove stock");
        btnRemoveStockButton.setForeground(Color.ORANGE);
        btnRemoveStockButton.setBounds(397, 339, 117, 29);
        frame.getContentPane().add(btnRemoveStockButton);

        JButton btnDoneButton = new JButton("Done");
        btnDoneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
            }
        });
        btnDoneButton.setBounds(455, 531, 117, 29);
        frame.getContentPane().add(btnDoneButton);


    }
}
