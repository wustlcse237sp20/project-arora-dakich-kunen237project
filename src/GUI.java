import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTextArea welcome = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, welcome, 24, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, welcome, -331, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, welcome, 46, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welcome, -105, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(welcome);
		welcome.setText("Welcome to a virtual Stock Market");

		JButton btnInputFileHere = new JButton("Input file here");
		frame.getContentPane().add(btnInputFileHere);
		btnInputFileHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String fileName = (String)JOptionPane.showInputDialog(
						frame,
						"What is the name of the input file?",
						"",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						null);
				System.out.println(fileName);
			}
		});

		JButton btnClickHereFor = new JButton("Click here for more information");
		btnClickHereFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "program information");
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnClickHereFor, 76, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnInputFileHere, 43, SpringLayout.SOUTH, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.WEST, btnInputFileHere, 0, SpringLayout.WEST, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.WEST, btnClickHereFor, 34, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnClickHereFor);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnExecute, 76, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnExecute, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnExecute);

		JButton btnMedianOutput = new JButton("Median output");
		springLayout.putConstraint(SpringLayout.NORTH, btnMedianOutput, 28, SpringLayout.SOUTH, btnExecute);
		frame.getContentPane().add(btnMedianOutput);

		JButton btnTimeTravelerOutput = new JButton("Time traveler output");
		btnTimeTravelerOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "Time traveler output");
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnTimeTravelerOutput, 36, SpringLayout.SOUTH, btnMedianOutput);
		springLayout.putConstraint(SpringLayout.EAST, btnMedianOutput, 0, SpringLayout.EAST, btnTimeTravelerOutput);
		springLayout.putConstraint(SpringLayout.EAST, btnTimeTravelerOutput, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnTimeTravelerOutput);

		JButton btnRandomInputButton = new JButton("Generate random input");
		btnRandomInputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = (String)JOptionPane.showInputDialog(
						frame,
						"What is the name of the input file?",
						"",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						null);
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
				System.out.println(name);
				System.out.println(numOrders);
				System.out.println(priceLimit);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnRandomInputButton, 33, SpringLayout.SOUTH, btnInputFileHere);
		springLayout.putConstraint(SpringLayout.WEST, btnRandomInputButton, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnRandomInputButton);


	}
}

