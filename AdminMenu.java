import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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

public class AdminMenu {

    public void adminMenu(Menu menu) {
        menu.dispose();

        menu.jFrame1 = new JFrame("Administrator Menu");
        menu.jFrame1.setSize(400, 400);
        menu.jFrame1.setLocation(200, 200);
        menu.jFrame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        menu.jFrame1.setVisible(true);

        JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteCustomerButton = new JButton("Delete Customer");
        deleteCustomerButton.setPreferredSize(new Dimension(250, 20));
        deleteCustomerPanel.add(deleteCustomerButton);

        JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setPreferredSize(new Dimension(250, 20));
        deleteAccountPanel.add(deleteAccountButton);

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
        menu.returnButton = new JButton("Exit Admin Menu");
        returnPanel.add(menu.returnButton);

        JLabel label1 = new JLabel("Please select an option");

        menu.contentContainer = menu.jFrame1.getContentPane();
        menu.contentContainer.setLayout(new GridLayout(9, 1));
        menu.contentContainer.add(label1);
        menu.contentContainer.add(accountPanel);
        menu.contentContainer.add(bankChargesPanel);
        menu.contentContainer.add(interestPanel);
        menu.contentContainer.add(editCustomerPanel);
        menu.contentContainer.add(navigatePanel);
        menu.contentContainer.add(summaryPanel);
        menu.contentContainer.add(deleteCustomerPanel);
        menu.contentContainer.add(returnPanel);

        bankChargesButton.addActionListener(
                action -> menu.adminMenu.addType(menu, "Charges", menu.adminMenu.checkEmpty(menu, menu.customerList)));

        interestButton.addActionListener(
                action -> menu.adminMenu.addType(menu, "Interest", menu.adminMenu.checkEmpty(menu, menu.customerList)));

        editCustomerButton.addActionListener(action -> menu.adminMenu.editCustomer(menu));

        summaryButton.addActionListener(action -> menu.adminMenu.createSummary(menu));

        navigateButton.addActionListener(action -> menu.adminMenu.viewCustomers(menu));

        accountButton.addActionListener(action -> menu.adminMenu.addAccountToCustomer(menu));

        deleteCustomerButton.addActionListener(action -> menu.adminMenu.deleteACustomer(menu));

        deleteAccountButton.addActionListener(action -> menu.adminMenu.deleteAnAccount(menu));

        menu.returnButton.addActionListener(action -> menu.menuStart());
    }

    void adminOption(Menu menu) {
        boolean loop = true;
        boolean cont = false;

        while (loop) {
            Object adminUsername = JOptionPane.showInputDialog(menu.jFrame1, "Enter Administrator Username:");
            // search admin list for admin with matching admin username
            if (!adminUsername.equals("admin")) {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
                        JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.NO_OPTION) {
                    menu.jFrame1.dispose();
                    loop = false;
                    menu.menuStart();
                }
            } else {
                loop = false;
            }
        }

        loop = true;
        // search admin list for admin with matching admin password
        while (loop) {
            Object adminPassword = JOptionPane.showInputDialog(menu.jFrame1, "Enter Administrator Password;");

            if (!adminPassword.equals("admin11")) {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
                        JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.NO_OPTION) {
                    menu.jFrame1.dispose();
                    loop = false;
                    menu.menuStart();
                }
            } else {
                loop = false;
                cont = true;
            }
        }
        if (cont) {
            adminMenu(menu);
        }
    }

    void editCustomer(Menu menu) {
        boolean loop = true;
        boolean found = false;

        if (menu.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(menu.jFrame1, "There are no customers yet!", "Oops!",
                    JOptionPane.INFORMATION_MESSAGE);
            menu.jFrame1.dispose();
            adminMenu(menu);

        } else {
            while (loop) {
                found = menu.adminMenu.findCustomer(menu, "Enter Customer ID:");
                if (!found) {
                    notFound(menu);
                } else {
                    loop = false;
                }
            }

            menu.jFrame1.dispose();
            menu.jFrame1.dispose();
            menu.jFrame1 = new JFrame("Administrator Menu");
            menu.jFrame1.setSize(400, 300);
            menu.jFrame1.setLocation(200, 200);
            menu.jFrame1.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });

            menu.firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
            menu.surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
            menu.ppsJLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
            menu.dobJLabel = new JLabel("Date of birth", SwingConstants.LEFT);
            menu.customerIDLabel = new JLabel("customerId:", SwingConstants.LEFT);
            menu.passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
            menu.firstNameTextField = new JTextField(20);
            menu.surnameTextField = new JTextField(20);
            menu.ppsTextField = new JTextField(20);
            menu.dobTextField = new JTextField(20);
            menu.customerIDTextField = new JTextField(20);
            menu.passwordTextField = new JTextField(20);

            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JPanel cancelPanel = new JPanel();

            textPanel.add(menu.firstNameLabel);
            textPanel.add(menu.firstNameTextField);
            textPanel.add(menu.surnameLabel);
            textPanel.add(menu.surnameTextField);
            textPanel.add(menu.ppsJLabel);
            textPanel.add(menu.ppsTextField);
            textPanel.add(menu.dobJLabel);
            textPanel.add(menu.dobTextField);
            textPanel.add(menu.customerIDLabel);
            textPanel.add(menu.customerIDTextField);
            textPanel.add(menu.passwordLabel);
            textPanel.add(menu.passwordTextField);

            menu.firstNameTextField.setText(menu.aCustomer.getFirstName());
            menu.surnameTextField.setText(menu.aCustomer.getSurname());
            menu.ppsTextField.setText(menu.aCustomer.getPPS());
            menu.dobTextField.setText(menu.aCustomer.getDOB());
            menu.customerIDTextField.setText(menu.aCustomer.getCustomerID());
            menu.passwordTextField.setText(menu.aCustomer.getPassword());

            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Exit");

            cancelPanel.add(cancelButton, BorderLayout.SOUTH);
            cancelPanel.add(saveButton, BorderLayout.SOUTH); //
            menu.contentContainer = menu.jFrame1.getContentPane();
            menu.contentContainer.setLayout(new GridLayout(2, 1));
            menu.contentContainer.add(textPanel, BorderLayout.NORTH);
            menu.contentContainer.add(cancelPanel, BorderLayout.SOUTH);

            menu.jFrame1.setContentPane(menu.contentContainer);
            menu.jFrame1.setSize(340, 350);
            menu.jFrame1.setLocation(200, 200);
            menu.jFrame1.setVisible(true);
            menu.jFrame1.setResizable(false);

            saveButton.addActionListener(action2 -> {
                menu.aCustomer.setFirstName(menu.firstNameTextField.getText());
                menu.aCustomer.setSurname(menu.surnameTextField.getText());
                menu.aCustomer.setPPS(menu.ppsTextField.getText());
                menu.aCustomer.setDOB(menu.dobTextField.getText());
                menu.aCustomer.setCustomerID(menu.customerIDTextField.getText());
                menu.aCustomer.setPassword(menu.passwordTextField.getText());
                JOptionPane.showMessageDialog(null, "Changes Saved.");
            });

            cancelButton.addActionListener(action2 -> {
                menu.jFrame1.dispose();
                adminMenu(menu);
            });
        }
    }

    void addAccountToCustomer(Menu menu) {
        menu.jFrame1.dispose();

        if (menu.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(menu.jFrame1, "There are no customers yet!", "Oops!",
                    JOptionPane.INFORMATION_MESSAGE);
            menu.jFrame1.dispose();
            adminMenu(menu);
        } else {
            boolean loop = true;

            while (loop) {
                if (!menu.adminMenu.findCustomer(menu, "Customer ID of Customer You Wish to Add an Account to: ")) {
                    notFound(menu);
                } else {
                    loop = false;
                    // a combo box in an dialog box that asks the admin what type of account they
                    // wish to create (deposit/current)
                    String[] choices = { "Current Account", "Deposit Account" };
                    String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
                            "Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

                    if (account.equals("Current Account")) {
                        displayCurrentAccount(menu);
                    } else {
                        displayDepositAccount(menu);
                    }
                }
            }
        }
    }

    private void displayCurrentAccount(Menu menu) {
        // create current account
        boolean valid = true;
        double balance = 0;
        String number = String.valueOf("C" + (menu.customerList.indexOf(menu.aCustomer) + 1) * 10
                + (menu.aCustomer.getAccounts().size() + 1));// this simple algorithm generates the
        // account number
        ArrayList<AccountTransaction> transactionList = new ArrayList<>();
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        String pinString = String.valueOf(randomPIN);

        ATMCard atm = new ATMCard(randomPIN, valid);

        CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
                transactionList);

        menu.aCustomer.getAccounts().add(current);
        JOptionPane.showMessageDialog(menu.jFrame1,
                "Account number = " + number + "\n PIN = " + pinString,
                "Account created.", JOptionPane.INFORMATION_MESSAGE);

        menu.jFrame1.dispose();
        adminMenu(menu);
    }

    private void displayDepositAccount(Menu menu) {
        // create deposit account
        double balance = 0;
        double interest = 0;
        String number = String.valueOf("D" + (menu.customerList.indexOf(menu.aCustomer) + 1) * 10
                + (menu.aCustomer.getAccounts().size() + 1));
        // this simple algorithm generates the account number
        ArrayList<AccountTransaction> transactionList = new ArrayList<>();

        CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
                transactionList);

        menu.aCustomer.getAccounts().add(deposit);
        JOptionPane.showMessageDialog(menu.jFrame1, "Account number = " + number, "Account created.",
                JOptionPane.INFORMATION_MESSAGE);

        menu.jFrame1.dispose();
        adminMenu(menu);
    }

    void viewCustomers(Menu menu) {
        menu.jFrame1.dispose();

        if (menu.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
            adminMenu(menu);
        } else {
            JButton first;
            JButton previous;
            JButton next;
            JButton last;
            JButton cancel;
            JPanel gridPanel;
            JPanel buttonPanel;
            JPanel cancelPanel;

            menu.contentContainer = menu.getContentPane();

            menu.contentContainer.setLayout(new BorderLayout());

            buttonPanel = new JPanel();
            gridPanel = new JPanel(new GridLayout(8, 2));
            cancelPanel = new JPanel();

            menu.firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
            menu.surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
            menu.ppsJLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
            menu.dobJLabel = new JLabel("Date of birth", SwingConstants.LEFT);
            menu.customerIDLabel = new JLabel("customerId:", SwingConstants.LEFT);
            menu.passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
            menu.firstNameTextField = new JTextField(20);
            menu.surnameTextField = new JTextField(20);
            menu.ppsTextField = new JTextField(20);
            menu.dobTextField = new JTextField(20);
            menu.customerIDTextField = new JTextField(20);
            menu.passwordTextField = new JTextField(20);

            first = new JButton("First");
            previous = new JButton("Previous");
            next = new JButton("Next");
            last = new JButton("Last");
            cancel = new JButton("Cancel");

            menu.firstNameTextField.setText(menu.customerList.get(0).getFirstName());
            menu.surnameTextField.setText(menu.customerList.get(0).getSurname());
            menu.ppsTextField.setText(menu.customerList.get(0).getPPS());
            menu.dobTextField.setText(menu.customerList.get(0).getDOB());
            menu.customerIDTextField.setText(menu.customerList.get(0).getCustomerID());
            menu.passwordTextField.setText(menu.customerList.get(0).getPassword());

            menu.firstNameTextField.setEditable(false);
            menu.surnameTextField.setEditable(false);
            menu.ppsTextField.setEditable(false);
            menu.dobTextField.setEditable(false);
            menu.customerIDTextField.setEditable(false);
            menu.passwordTextField.setEditable(false);

            gridPanel.add(menu.firstNameLabel);
            gridPanel.add(menu.firstNameTextField);
            gridPanel.add(menu.surnameLabel);
            gridPanel.add(menu.surnameTextField);
            gridPanel.add(menu.ppsJLabel);
            gridPanel.add(menu.ppsTextField);
            gridPanel.add(menu.dobJLabel);
            gridPanel.add(menu.dobTextField);
            gridPanel.add(menu.customerIDLabel);
            gridPanel.add(menu.customerIDTextField);
            gridPanel.add(menu.passwordLabel);
            gridPanel.add(menu.passwordTextField);

            buttonPanel.add(first);
            buttonPanel.add(previous);
            buttonPanel.add(next);
            buttonPanel.add(last);

            cancelPanel.add(cancel);

            menu.contentContainer.add(gridPanel, BorderLayout.NORTH);
            menu.contentContainer.add(buttonPanel, BorderLayout.CENTER);
            menu.contentContainer.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
            first.addActionListener(action2 -> {
                menu.position = 0;
                menu.firstNameTextField.setText(menu.customerList.get(0).getFirstName());
                menu.surnameTextField.setText(menu.customerList.get(0).getSurname());
                menu.ppsTextField.setText(menu.customerList.get(0).getPPS());
                menu.dobTextField.setText(menu.customerList.get(0).getDOB());
                menu.customerIDTextField.setText(menu.customerList.get(0).getCustomerID());
                menu.passwordTextField.setText(menu.customerList.get(0).getPassword());
            });

            previous.addActionListener(action2 -> {
                if (menu.position >= 1) {
                    menu.position = menu.position - 1;
                    menu.firstNameTextField.setText(menu.customerList.get(menu.position).getFirstName());
                    menu.surnameTextField.setText(menu.customerList.get(menu.position).getSurname());
                    menu.ppsTextField.setText(menu.customerList.get(menu.position).getPPS());
                    menu.dobTextField.setText(menu.customerList.get(menu.position).getDOB());
                    menu.customerIDTextField.setText(menu.customerList.get(menu.position).getCustomerID());
                    menu.passwordTextField.setText(menu.customerList.get(menu.position).getPassword());
                }
            });

            next.addActionListener(action2 -> {
                if (menu.position != menu.customerList.size() - 1) {
                    menu.firstNameTextField.setText(menu.customerList.get(menu.position).getFirstName());
                    menu.surnameTextField.setText(menu.customerList.get(menu.position).getSurname());
                    menu.ppsTextField.setText(menu.customerList.get(menu.position).getPPS());
                    menu.dobTextField.setText(menu.customerList.get(menu.position).getDOB());
                    menu.customerIDTextField.setText(menu.customerList.get(menu.position).getCustomerID());
                    menu.passwordTextField.setText(menu.customerList.get(menu.position).getPassword());
                }
            });

            last.addActionListener(action2 -> {
                menu.position = menu.customerList.size() - 1;
                menu.firstNameTextField.setText(menu.customerList.get(menu.position).getFirstName());
                menu.surnameTextField.setText(menu.customerList.get(menu.position).getSurname());
                menu.ppsTextField.setText(menu.customerList.get(menu.position).getPPS());
                menu.dobTextField.setText(menu.customerList.get(menu.position).getDOB());
                menu.customerIDTextField.setText(menu.customerList.get(menu.position).getCustomerID());
                menu.passwordTextField.setText(menu.customerList.get(menu.position).getPassword());
            });

            cancel.addActionListener(action2 -> {
                menu.dispose();
                adminMenu(menu);
            });
            menu.setContentPane(menu.contentContainer);
            menu.setSize(400, 300);
            menu.setVisible(true);
        }
    }

    void addType(Menu menu, String type, boolean loop) {
        boolean found = false;

        while (loop) {
            found = menu.adminMenu.findCustomer(menu, "Customer ID of Customer You Wish to Apply ");
            if (!found) {
                notFound(menu);
            } else {
                loop = false;
                menu.jFrame1.dispose();
                menu.jFrame1 = new JFrame("Administrator Menu");
                menu.jFrame1.setSize(400, 300);
                menu.jFrame1.setLocation(200, 200);
                menu.jFrame1.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                menu.jFrame1.setVisible(true);
                JComboBox<String> box = new JComboBox<>();
                for (int i = 0; i < menu.aCustomer.getAccounts().size(); i++) {
                    box.addItem(menu.aCustomer.getAccounts().get(i).getNumber());
                }
                box.getSelectedItem();
                JPanel boxPanel = new JPanel();
                JLabel label = new JLabel("Select an account to apply " + type + " to:");
                boxPanel.add(label);
                boxPanel.add(box);
                JPanel buttonPanel = new JPanel();
                JButton continueButton = new JButton("Apply " + type);
                menu.returnButton = new JButton("Return");
                buttonPanel.add(continueButton);
                buttonPanel.add(menu.returnButton);
                menu.contentContainer = menu.jFrame1.getContentPane();
                menu.contentContainer.setLayout(new GridLayout(2, 1));
                menu.contentContainer.add(boxPanel);
                menu.contentContainer.add(buttonPanel);

                if (menu.aCustomer.getAccounts().isEmpty()) {
                    JOptionPane.showMessageDialog(menu.jFrame1,
                            "This customer has no accounts! \n The admin must add acounts to this customer.",
                            "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    menu.jFrame1.dispose();
                    adminMenu(menu);
                } else {
                    for (int i = 0; i < menu.aCustomer.getAccounts().size(); i++) {
                        if (menu.aCustomer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                            menu.acc = menu.aCustomer.getAccounts().get(i);
                        }
                    }
                    if (type.equals("Interest")) {
                        continueButton.addActionListener(action2 -> menu.adminMenu.addInterestContinue(menu));
                    } else {
                        continueButton.addActionListener(action2 -> menu.adminMenu.addChargesContinue(menu));
                    }

                    menu.returnButton.addActionListener(action2 -> {
                        menu.jFrame1.dispose();
                        menu.menuStart();
                    });
                }
            }
        }
    }

    void addInterestContinue(Menu menu) {
        String euro = "\u20ac";
        double interest = 0;
        boolean loop2 = true;
        while (loop2) {
            String interestString = JOptionPane.showInputDialog(menu.jFrame1,
                    "Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
            // the isNumeric method tests to see if the string entered was numeric.
            if (Menu.isNumeric(interestString)) {

                interest = Double.parseDouble(interestString);
                loop2 = false;

                menu.acc.setBalance(
                        menu.acc.getBalance() + (menu.acc.getBalance() * (interest / 100)));

                JOptionPane.showMessageDialog(menu.jFrame1,
                        interest + "% interest applied. \n new balance = "
                                + menu.acc.getBalance() + euro,
                        "Success!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(menu.jFrame1,
                        "You must enter a numerical value!",
                        "Oops!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        menu.jFrame1.dispose();
        adminMenu(menu);
    }

    void addChargesContinue(Menu menu) {
        String euro = "\u20ac";
        if (menu.acc instanceof CustomerDepositAccount) {
            JOptionPane.showMessageDialog(menu.jFrame1,
                    "25" + euro + " deposit account fee aplied.", "",
                    JOptionPane.INFORMATION_MESSAGE);
            menu.acc.setBalance(menu.acc.getBalance() - 25);
            JOptionPane.showMessageDialog(menu.jFrame1, "New balance = " + menu.acc.getBalance(),
                    "Success!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (menu.acc instanceof CustomerCurrentAccount) {

            JOptionPane.showMessageDialog(menu.jFrame1,
                    "15" + euro + " current account fee aplied.", "",
                    JOptionPane.INFORMATION_MESSAGE);
            menu.acc.setBalance(menu.acc.getBalance() - 25);
            JOptionPane.showMessageDialog(menu.jFrame1, "New balance = " + menu.acc.getBalance(),
                    "Success!", JOptionPane.INFORMATION_MESSAGE);
        }
        menu.jFrame1.dispose();
        adminMenu(menu);
    }

    void deleteACustomer(Menu menu) {
        if (!menu.adminMenu.checkEmpty(menu, menu.customerList)) {
            if (!menu.adminMenu.findCustomer(menu, "Customer ID of Customer You Wish to Delete:"))
                menu.adminMenu.notFound(menu);
            else {
                if (!menu.aCustomer.getAccounts().isEmpty()) {
                    JOptionPane.showMessageDialog(menu.jFrame1,
                            "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
                            "Oops!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    menu.customerList.remove(menu.aCustomer);
                    JOptionPane.showMessageDialog(menu.jFrame1, "Customer Deleted ", "Success.",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }
    }

    void deleteAnAccount(Menu menu) {
        if (!menu.adminMenu.findCustomer(menu, "Customer ID of Customer from which you wish to delete an account"))
            menu.adminMenu.notFound(menu);
        else {
            // Here I would make the user select a an account to delete from a combo box. If
            // the account had a balance of 0 then it would be deleted. (I do not have time
            // to do this)
        }
    }

    boolean findCustomer(Menu menu, String message) {
        boolean found = false;
        Object customerID = JOptionPane.showInputDialog(menu.jFrame1,
                message);
        for (Customer c : menu.customerList) {
            if (c.getCustomerID().equals(customerID)) {
                menu.aCustomer = c;
                found = true;
            }
        }
        return found;
    }

    public boolean checkEmpty(Menu menu, List<Customer> customerList) {
        if (customerList.isEmpty()) {
            JOptionPane.showMessageDialog(menu.jFrame1, "There are no customers yet!", "Oops!",
                    JOptionPane.INFORMATION_MESSAGE);
            menu.jFrame1.dispose();
            adminMenu(menu);
            return true;
        }
        return false;
    }

    void notFound(Menu menu) {
        if (JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            menu.jFrame1.dispose();
            adminMenu(menu);
        }
    }

    void createSummary(Menu menu) {
        menu.jFrame1.dispose();

        menu.jFrame1 = new JFrame("Summary of Transactions");
        menu.jFrame1.setSize(400, 700);
        menu.jFrame1.setLocation(200, 200);
        menu.jFrame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        menu.jFrame1.setVisible(true);

        JLabel label3 = new JLabel("Summary of all transactions: ");

        JPanel returnPane3 = new JPanel();
        menu.returnButton = new JButton("Return");
        returnPane3.add(menu.returnButton);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(40, 20);
        textArea.setEditable(false);
        textPanel.add(label3, BorderLayout.NORTH);
        textPanel.add(textArea, BorderLayout.CENTER);
        textPanel.add(menu.returnButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);

        // For each customer, for each account, it displays each transaction.
        for (int a = 0; a < menu.customerList.size(); a++) {
            for (int b = 0; b < menu.customerList.get(a).getAccounts().size(); b++) {
                menu.acc = menu.customerList.get(a).getAccounts().get(b);
                for (int c = 0; c < menu.customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {
                    textArea.append(menu.acc.getTransactionList().get(c).toString());
                    // Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use
                    // this to keep a running total but I couldnt get it working.
                }
            }
        }

        textPanel.add(textArea);
        menu.contentContainer.removeAll();

        menu.contentContainer = menu.jFrame1.getContentPane();
        menu.contentContainer.setLayout(new GridLayout(1, 1));
        menu.contentContainer.add(textPanel);

        menu.returnButton.addActionListener(action2 -> {
            menu.jFrame1.dispose();
            adminMenu(menu);
        });
    }

}
