import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Date;

public class Menu extends JFrame {
	private ArrayList<Customer> customerList = new ArrayList<>();
	private Customer customer = null;
	private CustomerAccount acc = new CustomerAccount();
	private int position = 0;
	private String password;

	Customer e;
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

	JButton add;
	JButton returnButton;

	public static void main(String[] args) {
		Menu driver = new Menu();
		driver.menuStart();
	}

	public void menuStart() {
		/*
		 * The menuStart method asks the user if they are a new customer, an existing
		 * customer or an admin. It will then start the create customer process
		 * if they are a new customer, or will ask them to log in if they are an
		 * existing customer or admin.
		 */
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
			String user = userType.getSelection().getActionCommand();

			// if user selects NEW
			// CUSTOMER--------------------------------------------------------------------------------------
			if (user.equals("New Customer")) {
				jFrame1.dispose();
				jFrame2 = new JFrame("Create New Customer");
				jFrame2.setSize(400, 300);
				jFrame2.setLocation(200, 200);
				jFrame2.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});
				contentContainer = jFrame2.getContentPane();
				contentContainer.setLayout(new BorderLayout());

				firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
				surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
				ppsJLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
				dobJLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
				firstNameTextField = new JTextField(20);
				surnameTextField = new JTextField(20);
				ppsTextField = new JTextField(20);
				dobTextField = new JTextField(20);
				JPanel panel = new JPanel(new GridLayout(6, 2));
				panel.add(firstNameLabel);
				panel.add(firstNameTextField);
				panel.add(surnameLabel);
				panel.add(surnameTextField);
				panel.add(ppsJLabel);
				panel.add(ppsTextField);
				panel.add(dobJLabel);
				panel.add(dobTextField);

				JPanel panel2 = new JPanel();
				add = new JButton("Add");

				add.addActionListener(action2 -> {
					String pps = ppsTextField.getText();
					String firstName = firstNameTextField.getText();
					String surname = surnameTextField.getText();
					String dob = dobTextField.getText();
					password = "";

					String customerId = "ID" + pps;

					add.addActionListener(action3 -> {
						jFrame2.dispose();

						boolean loop = true;
						while (loop) {
							password = JOptionPane.showInputDialog(jFrame1, "Enter 7 character Password;");

							if (password.length() != 7)// Making sure password is 7 characters
							{
								JOptionPane.showMessageDialog(null, null,
										"Password must be 7 charatcers long", JOptionPane.OK_OPTION);
							} else {
								loop = false;
							}
						}

						ArrayList<CustomerAccount> accounts = new ArrayList<>();
						customer = new Customer(pps, surname, firstName, dob, customerId, password,
								accounts);

						customerList.add(customer);

						JOptionPane.showMessageDialog(jFrame1,
								"customerId = " + customerId + "\n Password = " + password,
								"Customer created.", JOptionPane.INFORMATION_MESSAGE);
						menuStart();
						jFrame1.dispose();
					});
				});
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(action2 -> {
					jFrame2.dispose();
					menuStart();
				});

				panel2.add(add);
				panel2.add(cancel);

				contentContainer.add(panel, BorderLayout.CENTER);
				contentContainer.add(panel2, BorderLayout.SOUTH);

				jFrame2.setVisible(true);
			}
			// ----------------------
			// if user select ADMIN
			// ----------------------
			if (user.equals("Administrator")) {
				boolean loop = true;
				boolean loop2 = true;
				boolean cont = false;
				while (loop) {
					Object adminUsername = JOptionPane.showInputDialog(jFrame1, "Enter Administrator Username:");

					if (!adminUsername.equals("admin"))// search admin list for admin with matching admin username
					{
						int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							jFrame2.dispose();
							loop = false;
							loop2 = false;
							menuStart();
						}
					} else {
						loop = false;
					}
				}

				while (loop2) {
					Object adminPassword = JOptionPane.showInputDialog(jFrame1, "Enter Administrator Password;");

					if (!adminPassword.equals("admin11"))// search admin list for admin with matching admin password
					{
						int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.NO_OPTION) {
							jFrame2.dispose();
							loop2 = false;
							menuStart();
						}
					} else {
						loop2 = false;
						cont = true;
					}
				}
				if (cont) {
					jFrame2.dispose();
					admin();
				}
			}
			// -------------------------
			// if user selects CUSTOMER
			// -------------------------
			if (user.equals("Customer")) {
				boolean loop = true;
				boolean loop2 = true;
				boolean cont = false;
				boolean found = false;
				customer = null;
				while (loop) {
					Object customerID = JOptionPane.showInputDialog(jFrame1, "Enter Customer ID:");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID))// search customer list for matching
																			// customer ID
						{
							found = true;
							customer = aCustomer;
						}
					}

					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop = false;
							loop2 = false;
							menuStart();
						}
					} else {
						loop = false;
					}
				}

				while (loop2) {
					Object customerPassword = JOptionPane.showInputDialog(jFrame1, "Enter Customer Password;");

					if (!customer.getPassword().equals(customerPassword))// check if custoemr password is correct
					{
						int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop2 = false;
							menuStart();
						}
					} else {
						loop2 = false;
						cont = true;
					}
				}

				if (cont) {
					jFrame1.dispose();
					customer(customer);
				}
			}
		});
		jFrame1.setVisible(true);

	}

	public void admin() {
		dispose();

		jFrame1 = new JFrame("Administrator Menu");
		jFrame1.setSize(400, 400);
		jFrame1.setLocation(200, 200);
		jFrame1.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		jFrame1.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomer);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccount = new JButton("Delete Account");
		deleteAccount.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccount);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesButton = new JButton("Apply Bank Charges");
		bankChargesButton.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesButton);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestButton = new JButton("Apply Interest");
		interestPanel.add(interestButton);
		interestButton.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerButton = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerButton);
		editCustomerButton.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateButton = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateButton);
		navigateButton.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryButton = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryButton);
		summaryButton.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountButton = new JButton("Add an Account to a Customer");
		accountPanel.add(accountButton);
		accountButton.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		returnButton = new JButton("Exit Admin Menu");
		returnPanel.add(returnButton);

		JLabel label1 = new JLabel("Please select an option");

		contentContainer = jFrame1.getContentPane();
		contentContainer.setLayout(new GridLayout(9, 1));
		contentContainer.add(label1);
		contentContainer.add(accountPanel);
		contentContainer.add(bankChargesPanel);
		contentContainer.add(interestPanel);
		contentContainer.add(editCustomerPanel);
		contentContainer.add(navigatePanel);
		contentContainer.add(summaryPanel);
		contentContainer.add(deleteCustomerPanel);
		contentContainer.add(returnPanel);

		bankChargesButton.addActionListener(action -> {
			boolean loop = true;
			boolean found = false;

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(jFrame1, "There are no customers yet!", "Oops!",
						JOptionPane.INFORMATION_MESSAGE);
				jFrame1.dispose();
				admin();
			} else {
				while (loop) {
					Object customerID = JOptionPane.showInputDialog(jFrame1,
							"Customer ID of Customer You Wish to Apply Charges to:");
					for (Customer aCustomer : customerList) {
						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}
					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop = false;

							admin();
						}
					} else {
						jFrame1.dispose();
						jFrame1 = new JFrame("Administrator Menu");
						jFrame1.setSize(400, 300);
						jFrame1.setLocation(200, 200);
						jFrame1.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent we) {
								System.exit(0);
							}
						});
						jFrame1.setVisible(true);

						JComboBox<String> box = new JComboBox<>();
						for (int i = 0; i < customer.getAccounts().size(); i++) {

							box.addItem(customer.getAccounts().get(i).getNumber());
						}

						box.getSelectedItem();
						JPanel boxPanel = new JPanel();
						boxPanel.add(box);

						JPanel buttonPanel = new JPanel();
						JButton continueButton = new JButton("Apply Charge");
						returnButton = new JButton("Return");
						buttonPanel.add(continueButton);
						buttonPanel.add(returnButton);
						contentContainer = jFrame1.getContentPane();
						contentContainer.setLayout(new GridLayout(2, 1));

						contentContainer.add(boxPanel);
						contentContainer.add(buttonPanel);

						if (customer.getAccounts().isEmpty()) {
							JOptionPane.showMessageDialog(jFrame1,
									"This customer has no accounts! \n The admin must add acounts to this customer.",
									"Oops!", JOptionPane.INFORMATION_MESSAGE);
							jFrame1.dispose();
							admin();
						} else {

							for (int i = 0; i < customer.getAccounts().size(); i++) {
								if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
									acc = customer.getAccounts().get(i);
								}
							}

							continueButton.addActionListener(action2 -> {
								String euro = "\u20ac";

								if (acc instanceof CustomerDepositAccount) {

									JOptionPane.showMessageDialog(jFrame1,
											"25" + euro + " deposit account fee aplied.", "",
											JOptionPane.INFORMATION_MESSAGE);
									acc.setBalance(acc.getBalance() - 25);
									JOptionPane.showMessageDialog(jFrame1, "New balance = " + acc.getBalance(),
											"Success!", JOptionPane.INFORMATION_MESSAGE);
								}

								if (acc instanceof CustomerCurrentAccount) {

									JOptionPane.showMessageDialog(jFrame1,
											"15" + euro + " current account fee aplied.", "",
											JOptionPane.INFORMATION_MESSAGE);
									acc.setBalance(acc.getBalance() - 25);
									JOptionPane.showMessageDialog(jFrame1, "New balance = " + acc.getBalance(),
											"Success!", JOptionPane.INFORMATION_MESSAGE);
								}
								jFrame1.dispose();
								admin();
							});

							returnButton.addActionListener(action2 -> {
								jFrame1.dispose();
								menuStart();
							});
						}
					}
				}
			}
		});

		interestButton.addActionListener(action -> {
			boolean loop = true;
			boolean found = false;

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(jFrame1, "There are no customers yet!", "Oops!",
						JOptionPane.INFORMATION_MESSAGE);
				jFrame1.dispose();
				admin();
			} else {
				while (loop) {
					Object customerID = JOptionPane.showInputDialog(jFrame1,
							"Customer ID of Customer You Wish to Apply Interest to:");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}

					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop = false;

							admin();
						}
					} else {
						jFrame1.dispose();
						jFrame1 = new JFrame("Administrator Menu");
						jFrame1.setSize(400, 300);
						jFrame1.setLocation(200, 200);
						jFrame1.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent we) {
								System.exit(0);
							}
						});
						jFrame1.setVisible(true);

						JComboBox<String> box = new JComboBox<>();
						for (int i = 0; i < customer.getAccounts().size(); i++) {

							box.addItem(customer.getAccounts().get(i).getNumber());
						}

						box.getSelectedItem();

						JPanel boxPanel = new JPanel();

						JLabel label = new JLabel("Select an account to apply interest to:");
						boxPanel.add(label);
						boxPanel.add(box);
						JPanel buttonPanel = new JPanel();
						JButton continueButton = new JButton("Apply Interest");
						returnButton = new JButton("Return");
						buttonPanel.add(continueButton);
						buttonPanel.add(returnButton);
						contentContainer = jFrame1.getContentPane();
						contentContainer.setLayout(new GridLayout(2, 1));

						contentContainer.add(boxPanel);
						contentContainer.add(buttonPanel);

						if (customer.getAccounts().isEmpty()) {
							JOptionPane.showMessageDialog(jFrame1,
									"This customer has no accounts! \n The admin must add acounts to this customer.",
									"Oops!", JOptionPane.INFORMATION_MESSAGE);
							jFrame1.dispose();
							admin();
						} else {

							for (int i = 0; i < customer.getAccounts().size(); i++) {
								if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
									acc = customer.getAccounts().get(i);
								}
							}

							continueButton.addActionListener(action2 -> {
								String euro = "\u20ac";
								double interest = 0;
								boolean loop2 = true;

								while (loop2) {
									String interestString = JOptionPane.showInputDialog(jFrame1,
											"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
									// the isNumeric method tests to see if the string entered was numeric.
									if (isNumeric(interestString)) {

										interest = Double.parseDouble(interestString);
										loop2 = false;

										acc.setBalance(
												acc.getBalance() + (acc.getBalance() * (interest / 100)));

										JOptionPane.showMessageDialog(jFrame1,
												interest + "% interest applied. \n new balance = "
														+ acc.getBalance() + euro,
												"Success!", JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(jFrame1,
												"You must enter a numerical value!",
												"Oops!", JOptionPane.INFORMATION_MESSAGE);
									}
								}
								jFrame1.dispose();
								admin();
							});
							returnButton.addActionListener(action2 -> {
								jFrame1.dispose();
								menuStart();
							});
						}
					}
				}
			}
		});

		editCustomerButton.addActionListener(action -> {
			boolean loop = true;
			boolean found = false;

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(jFrame1, "There are no customers yet!", "Oops!",
						JOptionPane.INFORMATION_MESSAGE);
				jFrame1.dispose();
				admin();

			} else {
				while (loop) {
					Object customerID = JOptionPane.showInputDialog(jFrame1, "Enter Customer ID:");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
						}
					}

					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop = false;
							admin();
						}
					} else {
						loop = false;
					}
				}

				jFrame1.dispose();
				jFrame1.dispose();
				jFrame1 = new JFrame("Administrator Menu");
				jFrame1.setSize(400, 300);
				jFrame1.setLocation(200, 200);
				jFrame1.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});

				firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
				surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
				ppsJLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
				dobJLabel = new JLabel("Date of birth", SwingConstants.LEFT);
				customerIDLabel = new JLabel("customerId:", SwingConstants.LEFT);
				passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
				firstNameTextField = new JTextField(20);
				surnameTextField = new JTextField(20);
				ppsTextField = new JTextField(20);
				dobTextField = new JTextField(20);
				customerIDTextField = new JTextField(20);
				passwordTextField = new JTextField(20);

				JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

				JPanel cancelPanel = new JPanel();

				textPanel.add(firstNameLabel);
				textPanel.add(firstNameTextField);
				textPanel.add(surnameLabel);
				textPanel.add(surnameTextField);
				textPanel.add(ppsJLabel);
				textPanel.add(ppsTextField);
				textPanel.add(dobJLabel);
				textPanel.add(dobTextField);
				textPanel.add(customerIDLabel);
				textPanel.add(customerIDTextField);
				textPanel.add(passwordLabel);
				textPanel.add(passwordTextField);

				firstNameTextField.setText(customer.getFirstName());
				surnameTextField.setText(customer.getSurname());
				ppsTextField.setText(customer.getPPS());
				dobTextField.setText(customer.getDOB());
				customerIDTextField.setText(customer.getCustomerID());
				passwordTextField.setText(customer.getPassword());

				JButton saveButton = new JButton("Save");
				JButton cancelButton = new JButton("Exit");

				cancelPanel.add(cancelButton, BorderLayout.SOUTH);
				cancelPanel.add(saveButton, BorderLayout.SOUTH); //
				contentContainer = jFrame1.getContentPane();
				contentContainer.setLayout(new GridLayout(2, 1));
				contentContainer.add(textPanel, BorderLayout.NORTH);
				contentContainer.add(cancelPanel, BorderLayout.SOUTH);

				jFrame1.setContentPane(contentContainer);
				jFrame1.setSize(340, 350);
				jFrame1.setLocation(200, 200);
				jFrame1.setVisible(true);
				jFrame1.setResizable(false);

				saveButton.addActionListener(action2 -> {
					customer.setFirstName(firstNameTextField.getText());
					customer.setSurname(surnameTextField.getText());
					customer.setPPS(ppsTextField.getText());
					customer.setDOB(dobTextField.getText());
					customer.setCustomerID(customerIDTextField.getText());
					customer.setPassword(passwordTextField.getText());

					JOptionPane.showMessageDialog(null, "Changes Saved.");
				});

				cancelButton.addActionListener(action2 -> {
					jFrame1.dispose();
					admin();
				});
			}
		});

		summaryButton.addActionListener(action -> {
			jFrame1.dispose();

			jFrame1 = new JFrame("Summary of Transactions");
			jFrame1.setSize(400, 700);
			jFrame1.setLocation(200, 200);
			jFrame1.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					System.exit(0);
				}
			});
			jFrame1.setVisible(true);

			JLabel label3 = new JLabel("Summary of all transactions: ");

			JPanel returnPane3 = new JPanel();
			returnButton = new JButton("Return");
			returnPane3.add(returnButton);

			JPanel textPanel = new JPanel();

			textPanel.setLayout(new BorderLayout());
			JTextArea textArea = new JTextArea(40, 20);
			textArea.setEditable(false);
			textPanel.add(label3, BorderLayout.NORTH);
			textPanel.add(textArea, BorderLayout.CENTER);
			textPanel.add(returnButton, BorderLayout.SOUTH);

			JScrollPane scrollPane = new JScrollPane(textArea);
			textPanel.add(scrollPane);

			// For each customer, for each account, it displays each transaction.
			for (int a = 0; a < customerList.size(); a++) {
				for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
					acc = customerList.get(a).getAccounts().get(b);
					for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {
						textArea.append(acc.getTransactionList().get(c).toString());
						// Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use
						// this to keep a running total but I couldnt get it working.
					}
				}
			}

			textPanel.add(textArea);
			contentContainer.removeAll();

			contentContainer = jFrame1.getContentPane();
			contentContainer.setLayout(new GridLayout(1, 1));
			contentContainer.add(textPanel);

			returnButton.addActionListener(action2 -> {
				jFrame1.dispose();
				admin();
			});
		});

		navigateButton.addActionListener(action -> {
			jFrame1.dispose();

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
				admin();
			} else {
				JButton first;
				JButton previous;
				JButton next;
				JButton last;
				JButton cancel;
				JPanel gridPanel;
				JPanel buttonPanel;
				JPanel cancelPanel;

				contentContainer = getContentPane();

				contentContainer.setLayout(new BorderLayout());

				buttonPanel = new JPanel();
				gridPanel = new JPanel(new GridLayout(8, 2));
				cancelPanel = new JPanel();

				firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
				surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
				ppsJLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
				dobJLabel = new JLabel("Date of birth", SwingConstants.LEFT);
				customerIDLabel = new JLabel("customerId:", SwingConstants.LEFT);
				passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
				firstNameTextField = new JTextField(20);
				surnameTextField = new JTextField(20);
				ppsTextField = new JTextField(20);
				dobTextField = new JTextField(20);
				customerIDTextField = new JTextField(20);
				passwordTextField = new JTextField(20);

				first = new JButton("First");
				previous = new JButton("Previous");
				next = new JButton("Next");
				last = new JButton("Last");
				cancel = new JButton("Cancel");

				firstNameTextField.setText(customerList.get(0).getFirstName());
				surnameTextField.setText(customerList.get(0).getSurname());
				ppsTextField.setText(customerList.get(0).getPPS());
				dobTextField.setText(customerList.get(0).getDOB());
				customerIDTextField.setText(customerList.get(0).getCustomerID());
				passwordTextField.setText(customerList.get(0).getPassword());

				firstNameTextField.setEditable(false);
				surnameTextField.setEditable(false);
				ppsTextField.setEditable(false);
				dobTextField.setEditable(false);
				customerIDTextField.setEditable(false);
				passwordTextField.setEditable(false);

				gridPanel.add(firstNameLabel);
				gridPanel.add(firstNameTextField);
				gridPanel.add(surnameLabel);
				gridPanel.add(surnameTextField);
				gridPanel.add(ppsJLabel);
				gridPanel.add(ppsTextField);
				gridPanel.add(dobJLabel);
				gridPanel.add(dobTextField);
				gridPanel.add(customerIDLabel);
				gridPanel.add(customerIDTextField);
				gridPanel.add(passwordLabel);
				gridPanel.add(passwordTextField);

				buttonPanel.add(first);
				buttonPanel.add(previous);
				buttonPanel.add(next);
				buttonPanel.add(last);

				cancelPanel.add(cancel);

				contentContainer.add(gridPanel, BorderLayout.NORTH);
				contentContainer.add(buttonPanel, BorderLayout.CENTER);
				contentContainer.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
				first.addActionListener(action2 -> {
					position = 0;
					firstNameTextField.setText(customerList.get(0).getFirstName());
					surnameTextField.setText(customerList.get(0).getSurname());
					ppsTextField.setText(customerList.get(0).getPPS());
					dobTextField.setText(customerList.get(0).getDOB());
					customerIDTextField.setText(customerList.get(0).getCustomerID());
					passwordTextField.setText(customerList.get(0).getPassword());
				});

				previous.addActionListener(action2 -> {
					if (position >= 1) {
						position = position - 1;
						firstNameTextField.setText(customerList.get(position).getFirstName());
						surnameTextField.setText(customerList.get(position).getSurname());
						ppsTextField.setText(customerList.get(position).getPPS());
						dobTextField.setText(customerList.get(position).getDOB());
						customerIDTextField.setText(customerList.get(position).getCustomerID());
						passwordTextField.setText(customerList.get(position).getPassword());
					}
				});

				next.addActionListener(action2 -> {
					if (position != customerList.size() - 1) {
						firstNameTextField.setText(customerList.get(position).getFirstName());
						surnameTextField.setText(customerList.get(position).getSurname());
						ppsTextField.setText(customerList.get(position).getPPS());
						dobTextField.setText(customerList.get(position).getDOB());
						customerIDTextField.setText(customerList.get(position).getCustomerID());
						passwordTextField.setText(customerList.get(position).getPassword());
					}
				});

				last.addActionListener(action2 -> {
					position = customerList.size() - 1;
					firstNameTextField.setText(customerList.get(position).getFirstName());
					surnameTextField.setText(customerList.get(position).getSurname());
					ppsTextField.setText(customerList.get(position).getPPS());
					dobTextField.setText(customerList.get(position).getDOB());
					customerIDTextField.setText(customerList.get(position).getCustomerID());
					passwordTextField.setText(customerList.get(position).getPassword());
				});

				cancel.addActionListener(action2 -> {
					dispose();
					admin();
				});
				setContentPane(contentContainer);
				setSize(400, 300);
				setVisible(true);
			}
		});

		accountButton.addActionListener(action -> {
			jFrame1.dispose();

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(jFrame1, "There are no customers yet!", "Oops!",
						JOptionPane.INFORMATION_MESSAGE);
				jFrame1.dispose();
				admin();
			} else {

				boolean loop = true;

				boolean found = false;

				while (loop) {
					Object customerID = JOptionPane.showInputDialog(jFrame1,
							"Customer ID of Customer You Wish to Add an Account to:");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
						}
					}

					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							loop = false;
							admin();
						}
					} else {
						loop = false;
						// a combo box in an dialog box that asks the admin what type of account they
						// wish to create (deposit/current)
						String[] choices = { "Current Account", "Deposit Account" };
						String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
								"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

						if (account.equals("Current Account")) {
							// create current account
							boolean valid = true;
							double balance = 0;
							String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10
									+ (customer.getAccounts().size() + 1));// this simple algorithm generates the
																			// account number
							ArrayList<AccountTransaction> transactionList = new ArrayList<>();
							int randomPIN = (int) (Math.random() * 9000) + 1000;
							String pinString = String.valueOf(randomPIN);

							ATMCard atm = new ATMCard(randomPIN, valid);

							CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
									transactionList);

							customer.getAccounts().add(current);
							JOptionPane.showMessageDialog(jFrame1,
									"Account number = " + number + "\n PIN = " + pinString,
									"Account created.", JOptionPane.INFORMATION_MESSAGE);

							jFrame1.dispose();
							admin();
						}

						if (account.equals("Deposit Account")) {
							// create deposit account
							double balance = 0;
							double interest = 0;
							String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10
									+ (customer.getAccounts().size() + 1));
							// this simple algorithm generates the account number
							ArrayList<AccountTransaction> transactionList = new ArrayList<>();

							CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
									transactionList);

							customer.getAccounts().add(deposit);
							JOptionPane.showMessageDialog(jFrame1, "Account number = " + number, "Account created.",
									JOptionPane.INFORMATION_MESSAGE);

							jFrame1.dispose();
							admin();
						}
					}
				}
			}
		});

		deleteCustomer.addActionListener(action -> {
			boolean found = true;

			if (customerList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
				dispose();
				admin();
			} else {
				{
					Object customerID = JOptionPane.showInputDialog(jFrame1,
							"Customer ID of Customer You Wish to Delete:");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							customer = aCustomer;
						}
					}

					if (!found) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.NO_OPTION) {
							jFrame1.dispose();
							admin();
						}
					} else {
						if (!customer.getAccounts().isEmpty()) {
							JOptionPane.showMessageDialog(jFrame1,
									"This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
									"Oops!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							customerList.remove(customer);
							JOptionPane.showMessageDialog(jFrame1, "Customer Deleted ", "Success.",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}

				}
			}
		});

		deleteAccount.addActionListener(action -> {
			boolean found = true;
			{
				Object customerID = JOptionPane.showInputDialog(jFrame1,
						"Customer ID of Customer from which you wish to delete an account");
				for (Customer aCustomer : customerList) {
					if (aCustomer.getCustomerID().equals(customerID)) {
						customer = aCustomer;
					}
				}
				if (!found) {
					int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.NO_OPTION) {
						jFrame1.dispose();
						admin();
					}
				} else {
					// Here I would make the user select a an account to delete from a combo box. If
					// the account had a balance of 0 then it would be deleted. (I do not have time
					// to do this)
				}
			}
		});
		returnButton.addActionListener(action -> {
			jFrame1.dispose();
			menuStart();
		});
	}

	public void customer(Customer e1) {
		jFrame1 = new JFrame("Customer Menu");
		e1 = e;
		jFrame1.setSize(400, 300);
		jFrame1.setLocation(200, 200);
		jFrame1.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		jFrame1.setVisible(true);

		if (e.getAccounts().isEmpty()) {
			JOptionPane.showMessageDialog(jFrame1,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			jFrame1.dispose();
			menuStart();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			returnButton = new JButton("Return");
			buttonPanel.add(returnButton);
			JButton continueButton = new JButton("Continue");
			buttonPanel.add(continueButton);

			JComboBox<String> box = new JComboBox<>();
			for (int i = 0; i < e.getAccounts().size(); i++) {
				box.addItem(e.getAccounts().get(i).getNumber());
			}

			for (int i = 0; i < e.getAccounts().size(); i++) {
				if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
					acc = e.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			contentContainer = jFrame1.getContentPane();
			contentContainer.setLayout(new GridLayout(3, 1));
			contentContainer.add(labelPanel);
			contentContainer.add(boxPanel);
			contentContainer.add(buttonPanel);

			returnButton.addActionListener(action -> {
				jFrame1.dispose();
				menuStart();
			});

			continueButton.addActionListener(actionListener -> {
				jFrame1.dispose();
				jFrame1 = new JFrame("Customer Menu");
				jFrame1.setSize(400, 300);
				jFrame1.setLocation(200, 200);
				jFrame1.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});
				jFrame1.setVisible(true);

				JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JButton statementButton = new JButton("Display Bank Statement");
				statementButton.setPreferredSize(new Dimension(250, 20));

				statementPanel.add(statementButton);

				JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JButton lodgementButton = new JButton("Lodge money into account");
				lodgementPanel.add(lodgementButton);
				lodgementButton.setPreferredSize(new Dimension(250, 20));

				JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JButton withdrawButton = new JButton("Withdraw money from account");
				withdrawalPanel.add(withdrawButton);
				withdrawButton.setPreferredSize(new Dimension(250, 20));

				JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				returnButton = new JButton("Exit Customer Menu");
				returnPanel.add(returnButton);

				JLabel label1 = new JLabel("Please select an option");

				contentContainer = jFrame1.getContentPane();
				contentContainer.setLayout(new GridLayout(5, 1));
				contentContainer.add(label1);
				contentContainer.add(statementPanel);
				contentContainer.add(lodgementPanel);
				contentContainer.add(withdrawalPanel);
				contentContainer.add(returnPanel);

				statementButton.addActionListener(action -> {
					jFrame1.dispose();
					jFrame1 = new JFrame("Customer Menu");
					jFrame1.setSize(400, 600);
					jFrame1.setLocation(200, 200);
					jFrame1.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					jFrame1.setVisible(true);

					JLabel label2 = new JLabel("Summary of account transactions: ");

					JPanel returnPane2 = new JPanel();
					returnButton = new JButton("Return");
					returnPane2.add(returnButton);

					JPanel textPanel = new JPanel();

					textPanel.setLayout(new BorderLayout());
					JTextArea textArea = new JTextArea(40, 20);
					textArea.setEditable(false);
					textPanel.add(label2, BorderLayout.NORTH);
					textPanel.add(textArea, BorderLayout.CENTER);
					textPanel.add(returnButton, BorderLayout.SOUTH);

					JScrollPane scrollPane = new JScrollPane(textArea);
					textPanel.add(scrollPane);

					for (int i = 0; i < acc.getTransactionList().size(); i++) {
						textArea.append(acc.getTransactionList().get(i).toString());

					}

					textPanel.add(textArea);
					contentContainer.removeAll();

					contentContainer = jFrame1.getContentPane();
					contentContainer.setLayout(new GridLayout(1, 1));
					contentContainer.add(textPanel);

					returnButton.addActionListener(action2 -> {
						jFrame1.dispose();
						customer(e);
					});
				});

				lodgementButton.addActionListener(action -> {
					boolean loop = true;
					boolean on = true;
					double balance = 0;

					if (acc instanceof CustomerCurrentAccount customerCurrentAccount) {
						int count = 3;
						int checkPin = customerCurrentAccount.getAtm().getPin();
						loop = true;

						while (loop) {
							if (count == 0) {
								JOptionPane.showMessageDialog(jFrame1,
										"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
										JOptionPane.INFORMATION_MESSAGE);
								customerCurrentAccount.getAtm().setValid(false);
								customer(e);
								loop = false;
								on = false;
							}

							int pin = Integer
									.parseInt(JOptionPane.showInputDialog(jFrame1, "Enter 4 digit PIN;"));

							if (on) {
								if (checkPin == pin) {
									loop = false;
									JOptionPane.showMessageDialog(jFrame1, "Pin entry successful", "Pin",
											JOptionPane.INFORMATION_MESSAGE);

								} else {
									count--;
									JOptionPane.showMessageDialog(jFrame1,
											"Incorrect pin. " + count + " attempts remaining.", "Pin",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					}
					if (on) {
						String balanceTest = JOptionPane.showInputDialog(jFrame1,
								"Enter amount you wish to lodge:");
						// the isNumeric method tests to see if the string entered was numeric.
						if (isNumeric(balanceTest)) {
							balance = Double.parseDouble(balanceTest);
						} else {
							JOptionPane.showMessageDialog(jFrame1, "You must enter a numerical value!", "Oops!",
									JOptionPane.INFORMATION_MESSAGE);
						}

						String euro = "\u20ac";
						acc.setBalance(acc.getBalance() + balance);
						// String date = new //
						Date date = new Date();
						String date2 = date.toString();
						String type = "Lodgement";
						double amount = balance;

						AccountTransaction transaction = new AccountTransaction(date2, type, amount);
						acc.getTransactionList().add(transaction);

						JOptionPane.showMessageDialog(jFrame1, balance + euro + " added do you account!",
								"Lodgement",
								JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showMessageDialog(jFrame1, "New balance = " + acc.getBalance() + euro,
								"Lodgement", JOptionPane.INFORMATION_MESSAGE);
					}
				});

				withdrawButton.addActionListener(action -> {
					boolean loop = true;
					boolean on = true;
					double withdraw = 0;

					if (acc instanceof CustomerCurrentAccount customerCurrentAccount) {
						int count = 3;
						int checkPin = customerCurrentAccount.getAtm().getPin();
						loop = true;

						while (loop) {
							if (count == 0) {
								JOptionPane.showMessageDialog(jFrame1,
										"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
										JOptionPane.INFORMATION_MESSAGE);
								((CustomerCurrentAccount) acc).getAtm().setValid(false);
								customer(e);
								loop = false;
								on = false;
							}

							int pin = Integer
									.parseInt(JOptionPane.showInputDialog(jFrame1, "Enter 4 digit PIN;"));

							if (on) {
								if (checkPin == pin) {
									loop = false;
									JOptionPane.showMessageDialog(jFrame1, "Pin entry successful", "Pin",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									count--;
									JOptionPane.showMessageDialog(jFrame1,
											"Incorrect pin. " + count + " attempts remaining.", "Pin",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					}
					if (on) {
						String balanceTest = JOptionPane.showInputDialog(jFrame1,
								"Enter amount you wish to withdraw (max 500):");// the isNumeric method tests to
																				// see if the string entered was
																				// numeric.
						if (isNumeric(balanceTest)) {
							withdraw = Double.parseDouble(balanceTest);
						} else {
							JOptionPane.showMessageDialog(jFrame1, "You must enter a numerical value!", "Oops!",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if (withdraw > 500) {
							JOptionPane.showMessageDialog(jFrame1,
									"500 is the maximum you can withdraw at a time.",
									"Oops!", JOptionPane.INFORMATION_MESSAGE);
							withdraw = 0;
						}
						if (withdraw > acc.getBalance()) {
							JOptionPane.showMessageDialog(jFrame1, "Insufficient funds.", "Oops!",
									JOptionPane.INFORMATION_MESSAGE);
							withdraw = 0;
						}

						String euro = "\u20ac";
						acc.setBalance(acc.getBalance() - withdraw);
						// recording transaction:
						Date date = new Date();
						String date2 = date.toString();

						String type = "Withdraw";
						double amount = withdraw;

						AccountTransaction transaction = new AccountTransaction(date2, type, amount);
						acc.getTransactionList().add(transaction);

						JOptionPane.showMessageDialog(jFrame1, withdraw + euro + " withdrawn.", "Withdraw",
								JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showMessageDialog(jFrame1, "New balance = " + acc.getBalance() + euro,
								"Withdraw",
								JOptionPane.INFORMATION_MESSAGE);
					}
				});

				returnButton.addActionListener(action -> {
					jFrame1.dispose();
					menuStart();
				});
			});
		}
	}

	public static boolean isNumeric(String str) // a method that tests if a string is numeric
	{
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}