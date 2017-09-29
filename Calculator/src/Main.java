//Project Name: Calculator
//Class: CS 480
//Name: Michael Cruz
//Date: 9/28/17

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.Color;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main extends JFrame {

	private JPanel contentPane;
	
	//Will server as user interface for both input and output
	private JTextField display;
	
	//Will serve as a signifier that both an operation and a variable has been saved allowing for a full eqn expression to be parsed.
	private JLabel eqnDisplay;
	
	
	//Allows for easier bulk event handling, see around line 200
	private JButton[] nums;
	private JButton[] ops;
	
	//We need to store the operator
	private char operator;
	//We need to store at most one number in this simple calculator
	private float num;
	
	//Handles Calculator Logic
	private static ScriptEngineManager mgr;
	private static ScriptEngine engine;
	
	/**
	 * Launch the application, initialize Javascript Engine 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    mgr = new ScriptEngineManager();
			    engine = mgr.getEngineByName("JavaScript");
				JOptionPane.showMessageDialog(null, "Simple Calculator\n\nCoded by Michael Cruz\nCPP CS 480 Assignment 1\nCompleted Date: 9/28/2017");
			}
		});
	}

	/**
	 * Create the frame and buttons, functionality all handled in events.
	 */
	public Main() {
		
		//Main Frame
		setTitle("Calculator\r\n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 250, 225, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		//Display First since most buttons will interact with this.
		display = new JTextField();
		display.setBounds(13, 31, 192, 48);
		display.setBackground(Color.WHITE);
		display.setForeground(Color.BLACK);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setEditable(false);
		display.setText("0");
		display.setFont(new Font("SimSun", Font.PLAIN, 18));
		display.setColumns(10);
		
		//Initialize operators so theres no confusion w/ default initalization of primiatives
		num = 0;
		operator = 0;
		
		JButton backspace = new JButton("\u2190");
		backspace.setBounds(129, 97, 76, 23);
		backspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!display.getText().matches("[A-Za-z]+")) {
					if (display.getText().length() != 1) {
						display.setText(display.getText().substring(0, display.getText().length() - 1));
					} else {
						display.setText("0");
					}
				} else {
					display.setText("0");
				}
			}
		});
		
		//Function: Clears both Display and Stored Equation (basically reset)
		JButton clearE = new JButton("CE");
		clearE.setBounds(15, 97, 52, 23);
		clearE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eqnDisplay.setText("");
				display.setText("0");
			}
		});
		
		//Function: Clear only Display
		JButton clear = new JButton("C");
		clear.setBounds(77, 97, 43, 23);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText("0");
			}
		});
		
		//Function: Toggle Display between Negative and Positive
		JButton Negative = new JButton("\u00B1");
		Negative.setBounds(15, 252, 43, 23);
		Negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (display.getText().startsWith("-")) {
					display.setText(display.getText().substring(1));
				} else {
					display.setText("-" + display.getText());
				}
			} 
		});
		
		//Function: Forms an Expression then calculates it
		JButton equals = new JButton("=");
		equals.setBounds(68, 252, 137, 23);
		equals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Regex is an easy way to verify this.
				if (display.getText().matches("(-)?([0-9]+(.[0-9]*(E[0-9]+)?)?)|(.[0-9]+)|(Infinity)")) {
					if (!eqnDisplay.getText().equals("")) {
					    String foo = num + " " + operator + " " + Float.parseFloat(display.getText());
					    try {
							display.setText(engine.eval(foo) + "");
						} catch (ScriptException e1) {
							e1.printStackTrace();
						}
						eqnDisplay.setText("");
					}
				} else {
					display.setText("Error");
				}
			} 
		});
		
		eqnDisplay = new JLabel("");
		eqnDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		eqnDisplay.setBounds(15, 11, 190, 14);
		
		nums = new JButton[11];
		
		for(int i = 0; i <= 9; i++) {
			nums[i] = new JButton(i + "");
		}
		
		nums[1].setBounds(15, 189, 43, 23);
		nums[2].setBounds(64, 189, 43, 23);
		nums[3].setBounds(113, 189, 43, 23);
		nums[4].setBounds(15, 160, 43, 23);
		nums[5].setBounds(64, 160, 43, 23);
		nums[6].setBounds(113, 160, 43, 23);
		nums[7].setBounds(15, 131, 43, 23);
		nums[8].setBounds(64, 131, 43, 23);
		nums[9].setBounds(113, 131, 43, 23);
		nums[0].setBounds(15, 218, 92, 23);
		
		nums[10] = new JButton(".");
		nums[10].setBounds(113, 218, 43, 23);
		
		ops = new JButton[4];
		
		ops[0] = new JButton("/");
		ops[0].setBounds(162, 131, 43, 23);
		
		ops[1] = new JButton("*");
		ops[1].setBounds(162, 160, 43, 23);
		
		ops[2] = new JButton("-");
		ops[2].setBounds(162, 189, 43, 23);
		
		ops[3]= new JButton("+");
		ops[3].setBounds(162, 218, 43, 23);
		
		contentPane.setLayout(null);
		contentPane.add(equals);
		contentPane.add(backspace);
		contentPane.add(clearE);
		contentPane.add(clear);
		contentPane.add(Negative);
		
		//Function: Calculate operations. Depending on whether there is already a saved value, either save the current display or take the current form and create the expression and calculate it immediately. Has safety checking.
		for(JButton i : ops) {
			i.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					//Regex to verify Number Format
					if (display.getText().matches("(-)?([0-9]+(.[0-9]*(E[0-9]+)?)?)|(.[0-9]+)|(Infinity)")) {
						if (eqnDisplay.getText().equals("")) {
							operator = i.getText().charAt(0);
							num = Float.parseFloat(display.getText());
							eqnDisplay.setText( num + " " + operator );
							display.setText("0");
						} else {
						    String foo = num + " " + operator + " " + Float.parseFloat(display.getText());
						    try {
								display.setText(engine.eval(foo) + "");
							} catch (ScriptException e1) {
								e1.printStackTrace();
							}
							eqnDisplay.setText("");
						}
					} else {
						display.setText("Error");
					}
				}
			});
			contentPane.add(i);
		}
		
		//Function: allows for input of [0-9.]. Has safety checking.
		for (JButton i : nums) {
			i.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ( !display.getText().equals("0") && !display.getText().matches("[a-zA-Z]+") ) {
						display.setText((display.getText() + i.getText()) + "");
					//If you hit infinity already, don't bother adding more numbers.
					} else if (!display.getText().equals("Infinity")) {
						display.setText(i.getText());
					}
				}
			});
			contentPane.add(i);
		}

		contentPane.add(eqnDisplay);
		contentPane.add(display);
	}
}
