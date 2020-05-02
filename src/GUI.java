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
import java.util.ArrayList;

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
		frame.getContentPane().setBackground(new Color(183, 255, 168));
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTextArea welcome = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, welcome, 25, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, welcome, 61, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, welcome, 45, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welcome, -49, SpringLayout.EAST, frame.getContentPane());
		welcome.setBackground(new Color(183, 255, 168));
		welcome.setForeground(new Color(153, 50, 204));
		welcome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		welcome.setEditable(false);
		frame.getContentPane().add(welcome);
		welcome.setText(" Welcome to a Virtual Stock Market!");


		JButton btnClickHereFor = new JButton("Click here for more information");
		springLayout.putConstraint(SpringLayout.NORTH, btnClickHereFor, 31, SpringLayout.SOUTH, welcome);
		springLayout.putConstraint(SpringLayout.WEST, btnClickHereFor, 0, SpringLayout.WEST, frame.getContentPane());
		btnClickHereFor.setForeground(new Color(0, 153, 255));
		btnClickHereFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "To run our program please click on the input file button. You will have the option\nto input a file or generate a random"
						+ " input. After you have done this, you will be able\nto click on the execute button to run the program. Once the program has run, you\ncan click on"
						+ " the median output and time traveler output buttons to see information\nregarding the transactions that took place.");
			}
		});
		frame.getContentPane().add(btnClickHereFor);
		
		JFrame inf = new JFrame("Can re-run existing input file with \"Input file\" (make sure that if x is typed in, input/x.in exists)");
		inf.setSize(300, 210);
		
		JButton btnInputFileHere = new JButton("Input file");
		springLayout.putConstraint(SpringLayout.NORTH, btnInputFileHere, 6, SpringLayout.SOUTH, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.WEST, btnInputFileHere, 0, SpringLayout.WEST, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.EAST, btnInputFileHere, 0, SpringLayout.EAST, btnClickHereFor);
		btnInputFileHere.setForeground(new Color(0, 153, 255));
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
		springLayout.putConstraint(SpringLayout.NORTH, btnExecute, 0, SpringLayout.NORTH, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.WEST, btnExecute, 25, SpringLayout.EAST, btnClickHereFor);
		btnExecute.setForeground(Color.RED);
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
		springLayout.putConstraint(SpringLayout.NORTH, btnMedianOutput, 6, SpringLayout.SOUTH, btnExecute);
		springLayout.putConstraint(SpringLayout.WEST, btnMedianOutput, 0, SpringLayout.WEST, btnExecute);
		springLayout.putConstraint(SpringLayout.EAST, btnMedianOutput, 0, SpringLayout.EAST, btnExecute);
		btnMedianOutput.setForeground(new Color(255, 165, 0));
		frame.getContentPane().add(btnMedianOutput);
		btnMedianOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (inputFile == null || inputFile.getStatus() != 2) {
						throw new RuntimeException("No data to show. Make sure to run \"Execute\" first!");
					}
					int timestamp;
					ArrayList<Pair<Integer, ArrayList<Integer>>> medianData = inputFile.getStockMarket().getMedians();
					int limit = medianData.get(medianData.size() - 1).first;
					try {
						String timestampStr = (String) JOptionPane.showInputDialog(
								frame,
								"Please enter your timestamp of choice (0-" + limit + ").",
								"",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								null);
						if (timestampStr == null) {
							return;
						}
						timestamp = Integer.parseInt(timestampStr);
						if (timestamp < 0 || timestamp > limit) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException s) {
						throw new RuntimeException("Invalid timestamp!");
					}
					String message = "Median match prices at timestamp " + timestamp + ":\n\nName\tMedian\n";
					int equityCount = inputFile.getStockMarket().getEquityCount();
					ArrayList<Integer> equityMedians = new ArrayList<>();
					for (int i = 0; i < equityCount; i++) {
						equityMedians.add(0);
					}
					for (Pair<Integer, ArrayList<Integer>> medianList : medianData) {
						if (medianList.first > timestamp) {
							break;
						}
						for (int i = 0; i < equityCount; i++) {
							equityMedians.set(i, medianList.second.get(i));
						}
					}
					for (int i = 0; i < equityCount; i++) {
						int median = equityMedians.get(i);
						message += inputFile.getFileHandler().getEquityNames().get(i) + "\t" +
								(median == 0 ? "N/A" : median) + "\n";
					}
					JTextArea textArea = new JTextArea(message);
					textArea.setEditable(false);
					JOptionPane.showMessageDialog(frame, textArea);
				} catch (RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});
		
		
		JButton btnTimeTravelerOutput = new JButton("Time traveler output");
		springLayout.putConstraint(SpringLayout.NORTH, btnTimeTravelerOutput, 6, SpringLayout.SOUTH, btnMedianOutput);
		springLayout.putConstraint(SpringLayout.WEST, btnTimeTravelerOutput, 268, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnExecute, 0, SpringLayout.EAST, btnTimeTravelerOutput);
		//btnTimeTravelerOutput.setEnabled(false);
		btnTimeTravelerOutput.setForeground(new Color(255, 165, 0));
		btnTimeTravelerOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (inputFile == null || inputFile.getStatus() != 2) {
						throw new RuntimeException("No data to show. Make sure to run \"Execute\" first!");
					}
					String message = "Ideal times to buy and sell stocks:\t\n\nName\tBuy\tSell\n";
					int equityCount = inputFile.getStockMarket().getEquityCount();
					for (int i = 0; i < equityCount; i++) {
						Pair<Integer, Integer> buySell = inputFile.getStockMarket().getTimeTravelers().get(i);
						message += inputFile.getFileHandler().getEquityNames().get(i) + "\t" +
								(buySell.first == -1 ? "N/A\tN/A" : buySell.first + "\t" + buySell.second + "\n");
					}
					JTextArea textArea = new JTextArea(message);
					textArea.setEditable(false);
					JOptionPane.showMessageDialog(frame, textArea);
				} catch (RuntimeException s) {
					JOptionPane.showMessageDialog(frame, "Error: " + s.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnTimeTravelerOutput);


	}
}
