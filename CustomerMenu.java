import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomerMenu {

    void newCustomerOption(Menu menu) {
        menu.jFrame1.dispose();
        menu.jFrame2 = new JFrame("Create New Customer");
        menu.jFrame2.setSize(400, 300);
        menu.jFrame2.setLocation(200, 200);
        menu.jFrame2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        menu.contentContainer = menu.jFrame2.getContentPane();
        menu.contentContainer.setLayout(new BorderLayout());

        menu.firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
        menu.surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
        menu.ppsJLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
        menu.dobJLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
        menu.firstNameTextField = new JTextField(20);
        menu.surnameTextField = new JTextField(20);
        menu.ppsTextField = new JTextField(20);
        menu.dobTextField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(menu.firstNameLabel);
        panel.add(menu.firstNameTextField);
        panel.add(menu.surnameLabel);
        panel.add(menu.surnameTextField);
        panel.add(menu.ppsJLabel);
        panel.add(menu.ppsTextField);
        panel.add(menu.dobJLabel);
        panel.add(menu.dobTextField);

        JPanel panel2 = new JPanel();
        menu.addButton = new JButton("Add");

        menu.addButton.addActionListener(action2 -> {
            String pps = menu.ppsTextField.getText();
            String firstName = menu.firstNameTextField.getText();
            String surname = menu.surnameTextField.getText();
            String dob = menu.dobTextField.getText();
            menu.password = "";

            String customerId = "ID" + pps;

            menu.addButton.addActionListener(action3 -> {
                menu.jFrame2.dispose();
                // Making sure password is 7 characters
                while (menu.password.length() != 7) {
                    menu.password = JOptionPane.showInputDialog(menu.jFrame1, "Enter 7 character Password;");
                    if (menu.password.length() != 7) {
                        JOptionPane.showMessageDialog(null, null,
                                "Password must be 7 charatcers long", JOptionPane.OK_OPTION);
                    }
                }

                ArrayList<CustomerAccount> accounts = new ArrayList<>();

                menu.customerList.add(new Customer(pps, surname, firstName, dob, customerId, menu.password,
                        accounts));

                JOptionPane.showMessageDialog(menu.jFrame1,
                        "customerId = " + customerId + "\n Password = " + menu.password,
                        "Customer created.", JOptionPane.INFORMATION_MESSAGE);
                menu.menuStart();
                menu.jFrame1.dispose();
            });
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(action2 -> {
            menu.jFrame2.dispose();
            menu.menuStart();
        });

        panel2.add(menu.addButton);
        panel2.add(cancel);
        menu.contentContainer.add(panel, BorderLayout.CENTER);
        menu.contentContainer.add(panel2, BorderLayout.SOUTH);
        menu.jFrame2.setVisible(true);
    }

    void customerOption(Menu menu) {
        boolean loop = true;
        boolean cont = false;
        boolean found = false;
        menu.aCustomer = null;
        while (loop) {
            Object customerID = JOptionPane.showInputDialog(menu.jFrame1, "Enter Customer ID:");
            for (Customer c : menu.customerList) {
                if (c.getCustomerID().equals(customerID))// search customer list for matching customer ID
                {
                    found = true;
                    menu.aCustomer = c;
                }
            }
            if (!found) {
                int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) {
                    loop = false;
                    menu.menuStart();
                }
            } else {
                loop = false;
            }
        }

        // check if customer password is correct
        menu.customerMenu.checkCustomerPassword(menu, cont);
    }

    public void customerMenu(Menu menu, Customer aCustomer) {
        menu.jFrame1.dispose();
        menu.jFrame1 = new JFrame("Customer Menu");
        menu.jFrame1.setSize(400, 300);
        menu.jFrame1.setLocation(200, 200);
        menu.jFrame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        menu.jFrame1.setVisible(true);

        if (aCustomer.getAccounts().isEmpty()) {
            JOptionPane.showMessageDialog(menu.jFrame1,
                    "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
                    "Oops!", JOptionPane.INFORMATION_MESSAGE);
            menu.menuStart();
        } else {
            JPanel buttonPanel = new JPanel();
            JPanel boxPanel = new JPanel();
            JPanel labelPanel = new JPanel();

            JLabel label = new JLabel("Select Account:");
            labelPanel.add(label);

            menu.returnButton = new JButton("Return");
            buttonPanel.add(menu.returnButton);
            JButton continueButton = new JButton("Continue");
            buttonPanel.add(continueButton);

            JComboBox<String> box = new JComboBox<>();
            for (int i = 0; i < aCustomer.getAccounts().size(); i++) {
                box.addItem(aCustomer.getAccounts().get(i).getNumber());
            }

            for (int i = 0; i < aCustomer.getAccounts().size(); i++) {
                if (aCustomer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                    menu.acc = aCustomer.getAccounts().get(i);
                }
            }

            boxPanel.add(box);
            menu.contentContainer = menu.jFrame1.getContentPane();
            menu.contentContainer.setLayout(new GridLayout(3, 1));
            menu.contentContainer.add(labelPanel);
            menu.contentContainer.add(boxPanel);
            menu.contentContainer.add(buttonPanel);

            menu.returnButton.addActionListener(action -> {
                menu.jFrame1.dispose();
                menu.menuStart();
            });

            continueButton.addActionListener(actionListener -> {
                menu.jFrame1.dispose();
                menu.jFrame1 = new JFrame("Customer Menu");
                menu.jFrame1.setSize(400, 300);
                menu.jFrame1.setLocation(200, 200);
                menu.jFrame1.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                menu.jFrame1.setVisible(true);

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
                menu.returnButton = new JButton("Exit Customer Menu");
                returnPanel.add(menu.returnButton);

                JLabel label1 = new JLabel("Please select an option");

                menu.contentContainer = menu.jFrame1.getContentPane();
                menu.contentContainer.setLayout(new GridLayout(5, 1));
                menu.contentContainer.add(label1);
                menu.contentContainer.add(statementPanel);
                menu.contentContainer.add(lodgementPanel);
                menu.contentContainer.add(withdrawalPanel);
                menu.contentContainer.add(returnPanel);

                statementButton.addActionListener(action -> menu.customerMenu.createStatement(menu, aCustomer));

                lodgementButton.addActionListener(action -> menu.customerMenu.lodgement(menu, aCustomer));

                withdrawButton.addActionListener(action -> menu.customerMenu.withdrawal(menu, aCustomer));

                menu.returnButton.addActionListener(action -> menu.menuStart());
            });
        }
    }

    void createStatement(Menu menu, Customer aCustomer) {
        menu.jFrame1.dispose();
        menu.jFrame1 = new JFrame("Customer Menu");
        menu.jFrame1.setSize(400, 600);
        menu.jFrame1.setLocation(200, 200);
        menu.jFrame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        menu.jFrame1.setVisible(true);

        JLabel label2 = new JLabel("Summary of account transactions: ");

        JPanel returnPane2 = new JPanel();
        menu.returnButton = new JButton("Return");
        returnPane2.add(menu.returnButton);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(40, 20);
        textArea.setEditable(false);
        textPanel.add(label2, BorderLayout.NORTH);
        textPanel.add(textArea, BorderLayout.CENTER);
        textPanel.add(menu.returnButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);

        for (int i = 0; i < menu.acc.getTransactionList().size(); i++) {
            textArea.append(menu.acc.getTransactionList().get(i).toString());

        }

        textPanel.add(textArea);
        menu.contentContainer.removeAll();

        menu.contentContainer = menu.jFrame1.getContentPane();
        menu.contentContainer.setLayout(new GridLayout(1, 1));
        menu.contentContainer.add(textPanel);

        menu.returnButton.addActionListener(action2 -> {
            menu.jFrame1.dispose();
            customerMenu(menu, aCustomer);
        });
    }

    void withdrawal(Menu menu, Customer aCustomer) {
        double withdraw = 0;
        // the isNumeric method tests to see if the string entered was numeric.
        if (!menu.customerMenu.enterPin(menu, aCustomer)) {
            String balanceTest = JOptionPane.showInputDialog(menu.jFrame1,
                    "Enter amount you wish to withdraw (max 500):");

            if (Menu.isNumeric(balanceTest)) {
                withdraw = Double.parseDouble(balanceTest);
            } else {
                JOptionPane.showMessageDialog(menu.jFrame1, "You must enter a numerical value!", "Oops!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            if (withdraw > 500) {
                JOptionPane.showMessageDialog(menu.jFrame1,
                        "500 is the maximum you can withdraw at a time.",
                        "Oops!", JOptionPane.INFORMATION_MESSAGE);
                withdraw = 0;
            }
            if (withdraw > menu.acc.getBalance()) {
                JOptionPane.showMessageDialog(menu.jFrame1, "Insufficient funds.", "Oops!",
                        JOptionPane.INFORMATION_MESSAGE);
                withdraw = 0;
            }

            String euro = "\u20ac";
            menu.acc.setBalance(menu.acc.getBalance() - withdraw);
            // recording transaction:
            Date date = new Date();
            String date2 = date.toString();

            String type = "Withdraw";
            double amount = withdraw;

            AccountTransaction transaction = new AccountTransaction(date2, type, amount);
            menu.acc.getTransactionList().add(transaction);

            JOptionPane.showMessageDialog(menu.jFrame1, withdraw + euro + " withdrawn.", type,
                    JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(menu.jFrame1, "New balance = " + menu.acc.getBalance() + euro,
                    type,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void lodgement(Menu menu, Customer aCustomer) {
        double balance = 0;
        // the isNumeric method tests to see if the string entered was numeric.
        if (!menu.customerMenu.enterPin(menu, aCustomer)) {
            String balanceTest = JOptionPane.showInputDialog(menu.jFrame1,
                    "Enter amount you wish to lodge:");
            if (Menu.isNumeric(balanceTest)) {
                balance = Double.parseDouble(balanceTest);
            } else {
                JOptionPane.showMessageDialog(menu.jFrame1, "You must enter a numerical value!", "Oops!",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            String euro = "\u20ac";
            menu.acc.setBalance(menu.acc.getBalance() + balance);
            Date date = new Date();
            String date2 = date.toString();
            String type = "Lodgement";
            double amount = balance;

            AccountTransaction transaction = new AccountTransaction(date2, type, amount);
            menu.acc.getTransactionList().add(transaction);

            JOptionPane.showMessageDialog(menu.jFrame1, balance + euro + " added do you account!",
                    type,
                    JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(menu.jFrame1, "New balance = " + menu.acc.getBalance() + euro,
                    type, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void checkCustomerPassword(Menu menu, boolean cont) {
        boolean loop;
        loop = true;
        while (loop) {
            Object customerPassword = JOptionPane.showInputDialog(menu.jFrame1, "Enter Customer Password;");

            if (!menu.aCustomer.getPassword().equals(customerPassword)) {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) {
                    loop = false;
                    menu.menuStart();
                }
            } else {
                loop = false;
                cont = true;
            }
        }

        if (cont) {
            menu.jFrame1.dispose();
            customerMenu(menu, menu.aCustomer);
        }
    }

    boolean enterPin(Menu menu, Customer aCustomer) {
        boolean cardLocked = false;

        if (menu.acc instanceof CustomerCurrentAccount customerCurrentAccount) {
            boolean loop = true;
            int count = 3;
            int checkPin = customerCurrentAccount.getAtm().getPin();

            while (loop) {
                if (count == 0) {
                    JOptionPane.showMessageDialog(menu.jFrame1,
                            "Pin entered incorrectly 3 times. ATM card locked.", "Pin",
                            JOptionPane.INFORMATION_MESSAGE);
                    customerCurrentAccount.getAtm().setValid(false);
                    customerMenu(menu, aCustomer);
                    loop = false;
                    cardLocked = true;
                }

                int pin = Integer
                        .parseInt(JOptionPane.showInputDialog(menu.jFrame1, "Enter 4 digit PIN;"));

                if (!cardLocked && checkPin == pin) {
                    loop = false;
                    JOptionPane.showMessageDialog(menu.jFrame1, "Pin entry successful", "Pin",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    count--;
                    JOptionPane.showMessageDialog(menu.jFrame1,
                            "Incorrect pin. " + count + " attempts remaining.", "Pin",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        return cardLocked;
    }

}
