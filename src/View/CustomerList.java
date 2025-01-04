package View;

import Controller.CustomerController;
import Model.CustomerModel;
import Infra.Extension;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static Infra.Extension.generateNumber;

public class CustomerList extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextArea customerDetails;
    private JPanel customerPanel;
    private JButton addToQueueButton;
    private JButton deleteCustomerButton;
    private JButton addNewCustomerButton;
    private CustomerController _customerController;

    public CustomerList(CustomerController customerController) {
        this._customerController = customerController;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
        loadCustomers();
    }

    private void initializeComponents() {
        customerPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Queue Number","First Name", "SurName", "Parcel ID"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customerDetails = new JTextArea();
        customerDetails.setEditable(false);

        addToQueueButton = new JButton("Add To Queue");
        deleteCustomerButton = new JButton("Delete Customer");
        addNewCustomerButton = new JButton("Add New Customer");

        // Listener for row selection to display details
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Object queueNumberObj = customerTable.getValueAt(selectedRow, 0);
                        int queueNumber = parseQueueNumber(queueNumberObj); // Ensure type safety

                        String firstName = (String) customerTable.getValueAt(selectedRow, 1);
                        String lastName = (String) customerTable.getValueAt(selectedRow, 2);
                        String parcelID = (String) customerTable.getValueAt(selectedRow, 3);

                        customerDetails.setText("Queue Number: " + queueNumber + "\nFirst Name: " + firstName +
                                "\nLast Name: " + lastName + "\nParcel ID: " + parcelID);
                    }
                }
            }
        });

        // Listener for delete button
        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object queueNumberObj = customerTable.getValueAt(selectedRow, 0);
                    int queueNumber = parseQueueNumber(queueNumberObj);

                    if (queueNumber != -1) { // Ensure valid queueNumber
                        _customerController.removeCustomer(queueNumber); // Remove from controller
                        tableModel.removeRow(selectedRow); // Remove from table
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Queue Number. Unable to delete customer.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });

        // Listener for add button
        addNewCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewCustomerDialog();
            }
        });
    }

    private void showAddNewCustomerDialog() {
        AddCustomerForm addCustomerForm = new AddCustomerForm((Frame) SwingUtilities.getWindowAncestor(this));
        addCustomerForm.setVisible(true);

        if (addCustomerForm.isSucceeded()) {
            String firstName = addCustomerForm.getFirstName();
            String lastName = addCustomerForm.getSurName();
            String parcelID = addCustomerForm.getParcelID();
            int queueNumber = generateNumber();
            CustomerModel newCustomer = new CustomerModel(queueNumber, firstName, lastName, parcelID);
            _customerController.addCustomer(newCustomer);
            tableModel.addRow(new Object[]{firstName, lastName, parcelID});
        }
    }

    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(customerTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, new JScrollPane(customerDetails));
        splitPane.setDividerLocation(0.7);

        customerPanel.add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addToQueueButton);
        buttonPanel.add(deleteCustomerButton);
        buttonPanel.add(addNewCustomerButton);

        customerPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(customerPanel, BorderLayout.CENTER);
    }

    private void loadCustomers() {
        List<CustomerModel> customers = _customerController.getAll();
        for (CustomerModel customer : customers) {
            tableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getFirstName(), customer.getSurName(), customer.getParcelID()});
        }
    }

    private int parseQueueNumber(Object queueNumberObj) {
        if (queueNumberObj instanceof Integer) {
            return (int) queueNumberObj;
        } else if (queueNumberObj instanceof String) {
            try {
                return Integer.parseInt((String) queueNumberObj);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid Queue Number format: " + queueNumberObj);
            }
        }
        return -1;
    }
}
