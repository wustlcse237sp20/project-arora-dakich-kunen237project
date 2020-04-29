import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;

public class GUI {

	private JFrame frame;
	private InputFile inputFile;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				GUI window = new GUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();

			}
		});

	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 218, 185));
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTextArea welcome = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, welcome, 25, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, welcome, -350, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, welcome, 45, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welcome, -69, SpringLayout.EAST, frame.getContentPane());
		welcome.setBackground(new Color(255, 255, 255));
		welcome.setForeground(new Color(153, 50, 204));
		welcome.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		welcome.setEditable(false);
		frame.getContentPane().add(welcome);
		welcome.setText(" Welcome to a virtual Stock Market!");


		JButton btnClickHereFor = new JButton("Click here for more information");
		springLayout.putConstraint(SpringLayout.NORTH, btnClickHereFor, 31, SpringLayout.SOUTH, welcome);
		springLayout.putConstraint(SpringLayout.WEST, btnClickHereFor, 0, SpringLayout.WEST, frame.getContentPane());
		btnClickHereFor.setForeground(Color.CYAN);
		btnClickHereFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "To run our program please click on the input file button. You will have the option\nto input a file or generate a random"
						+ " input. After you have done this, you will be able\nto click on the execute button to run the program. Once the program has run, you\ncan click on"
						+ " the median output and time traveler output buttons to see information\nregarding the transactions that took place.");
			}
		});
		frame.getContentPane().add(btnClickHereFor);

		JButton btnInputFileHere = new JButton("Input file");
		springLayout.putConstraint(SpringLayout.NORTH, btnInputFileHere, 150, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnInputFileHere, 53, SpringLayout.WEST, frame.getContentPane());
		btnInputFileHere.setForeground(Color.RED);
		frame.getContentPane().add(btnInputFileHere);
		btnInputFileHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					inputFile = new InputFile();
				} catch(RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});

		JButton btnExecute = new JButton("Execute");
		btnExecute.setForeground(Color.ORANGE);
		springLayout.putConstraint(SpringLayout.NORTH, btnExecute, 0, SpringLayout.NORTH, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.EAST, btnExecute, -29, SpringLayout.EAST, frame.getContentPane());
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (inputFile == null || inputFile.getStatus() == 0) {
						throw new RuntimeException("No input file chosen!");
					}
					VirtualStockMarket stockMarket = inputFile.getStockMarket();
					stockMarket.computeTrans();
					inputFile.getFileHandler().writeOutput(stockMarket.getTransactionCount(),
							stockMarket.getTransactions(), stockMarket.getClients());
					inputFile.setStatus(2);
					JOptionPane.showMessageDialog(frame, "Success: Verbose output written to output/" +
							inputFile.getFileHandler().getFileName() + ".out!");
				} catch (RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnExecute);

		JButton btnMedianOutput = new JButton("Median output");
		btnMedianOutput.setForeground(Color.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, btnMedianOutput, 0, SpringLayout.NORTH, btnInputFileHere);
		springLayout.putConstraint(SpringLayout.EAST, btnMedianOutput, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnMedianOutput);
		btnMedianOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (inputFile == null || inputFile.getStatus() != 2) {
						throw new RuntimeException("No data to show. Make sure to run \"Execute\" first!");
					}
					JOptionPane.showMessageDialog(frame, "Median output");
				} catch (RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});

		JButton btnTimeTravelerOutput = new JButton("Time traveler output");
		//btnTimeTravelerOutput.setEnabled(false);
		btnTimeTravelerOutput.setForeground(Color.MAGENTA);
		springLayout.putConstraint(SpringLayout.NORTH, btnTimeTravelerOutput, 36, SpringLayout.SOUTH, btnMedianOutput);
		btnTimeTravelerOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (inputFile == null || inputFile.getStatus() != 2) {
						throw new RuntimeException("No data to show. Make sure to run \"Execute\" first!");
					}
					JOptionPane.showMessageDialog(frame, "Time traveler output");
				} catch (RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, btnTimeTravelerOutput, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnTimeTravelerOutput);


	}
}
