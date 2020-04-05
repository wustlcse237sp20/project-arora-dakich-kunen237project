import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI {

	private JFrame frame;
	private JTextField txtWelcomeToA;

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
		
		JButton btnInputFileHere = new JButton("Input file here");
		frame.getContentPane().add(btnInputFileHere);
		
		txtWelcomeToA = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtWelcomeToA, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtWelcomeToA, -208, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtWelcomeToA, 110, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtWelcomeToA, -114, SpringLayout.EAST, frame.getContentPane());
		txtWelcomeToA.setText("Welcome to a virtual Stock Market");
		frame.getContentPane().add(txtWelcomeToA);
		txtWelcomeToA.setColumns(10);
		
		JButton btnClickHereFor = new JButton("Click here for more information");
		springLayout.putConstraint(SpringLayout.NORTH, btnInputFileHere, 43, SpringLayout.SOUTH, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.WEST, btnInputFileHere, 0, SpringLayout.WEST, btnClickHereFor);
		springLayout.putConstraint(SpringLayout.NORTH, btnClickHereFor, 6, SpringLayout.SOUTH, txtWelcomeToA);
		springLayout.putConstraint(SpringLayout.WEST, btnClickHereFor, 34, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnClickHereFor);
		
		JButton btnExecute = new JButton("Execute");
		springLayout.putConstraint(SpringLayout.NORTH, btnExecute, 6, SpringLayout.SOUTH, txtWelcomeToA);
		springLayout.putConstraint(SpringLayout.EAST, btnExecute, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnExecute);
		
		JButton btnMedianOutput = new JButton("Median output");
		springLayout.putConstraint(SpringLayout.NORTH, btnMedianOutput, 28, SpringLayout.SOUTH, btnExecute);
		frame.getContentPane().add(btnMedianOutput);
		
		JButton btnTimeTravelerOutput = new JButton("Time traveler output");
		springLayout.putConstraint(SpringLayout.NORTH, btnTimeTravelerOutput, 36, SpringLayout.SOUTH, btnMedianOutput);
		springLayout.putConstraint(SpringLayout.EAST, btnMedianOutput, 0, SpringLayout.EAST, btnTimeTravelerOutput);
		springLayout.putConstraint(SpringLayout.EAST, btnTimeTravelerOutput, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnTimeTravelerOutput);
	}
}
