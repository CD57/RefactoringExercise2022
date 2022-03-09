import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Menu extends JFrame {
	ArrayList<Customer> customerList = new ArrayList<>();
	AdminMenu adminMenu = new AdminMenu();
	CustomerMenu customerMenu = new CustomerMenu();
	Customer aCustomer = null;
	CustomerAccount acc = new CustomerAccount();
	String password;
	int position = 0;

	JFrame jFrame1;
	JFrame jFrame2;
	JLabel firstNameLabel;
	JLabel surnameLabel;
	JLabel ppsJLabel;
	JLabel dobJLabel;
	JTextField firstNameTextField;
	JTextField surnameTextField;
	JTextField ppsTextField;
	JTextField dobTextField;
	JLabel customerIDLabel;
	JLabel passwordLabel;
	JTextField customerIDTextField;
	JTextField passwordTextField;
	Container contentContainer;

	JButton addButton;
	JButton returnButton;

	public static void main(String[] args) {
		Menu driver = new Menu();
		driver.menuStart();
	}

	/*
	 * The menuStart method asks the user if they are a new customer, an existing
	 * customer or an admin. It will then start the create customer process
	 * if they are a new customer, or will ask them to log in if they are an
	 * existing customer or admin.
	 */
	public void menuStart() {
		if (jFrame1 != null) {
			jFrame1.dispose();
		}

		jFrame1 = new JFrame("User Type");
		jFrame1.setSize(400, 300);
		jFrame1.setLocation(200, 200);
		jFrame1.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioButton;
		radioButton = new JRadioButton("Existing Customer");
		userTypePanel.add(radioButton);
		radioButton.setActionCommand("Customer");
		userType.add(radioButton);
		radioButton = new JRadioButton("Administrator");
		userTypePanel.add(radioButton);
		radioButton.setActionCommand("Administrator");
		userType.add(radioButton);
		radioButton = new JRadioButton("New Customer");
		userTypePanel.add(radioButton);
		radioButton.setActionCommand("New Customer");
		userType.add(radioButton);

		JPanel continuePanel = new JPanel();
		JButton continueButton = new JButton("Continue");
		continuePanel.add(continueButton);

		contentContainer = jFrame1.getContentPane();
		contentContainer.setLayout(new GridLayout(2, 1));
		contentContainer.add(userTypePanel);
		contentContainer.add(continuePanel);

		continueButton.addActionListener(action -> {
			String optionSelected = userType.getSelection().getActionCommand();

			switch (optionSelected) {
				case "New Customer":
					customerMenu.newCustomerOption(this);
					break;
				case "Administrator":
					adminMenu.adminOption(this);
					break;
				case "Customer":
					customerMenu.customerOption(this);
					break;
				default:
					break;
			}
		});
		jFrame1.setVisible(true);

	}

	// a method that tests if a string is numeric
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}