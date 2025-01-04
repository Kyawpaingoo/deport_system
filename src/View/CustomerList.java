package View;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerList extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextArea customerDetails;
    private JPanel customerPanel;
    private JButton addToQueueButton;
    private JButton deleteCustomerButton;
    private JButton addNewCustomerButton;

    public CustomerList() {
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        customerPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"First Name", "Last Name"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel.addRow(new Object[]{"John", "Doe"});
        tableModel.addRow(new Object[]{"Jane", "Smith"});

        customerDetails = new JTextArea();
        customerDetails.setEditable(false);

        addToQueueButton = new JButton("Add To Queue");
        deleteCustomerButton = new JButton("Delete Customer");
        addNewCustomerButton = new JButton("Add New Customer");

        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String firstName = (String) customerTable.getValueAt(selectedRow, 0);
                        String lastName = (String) customerTable.getValueAt(selectedRow, 1);
                        customerDetails.setText("First Name: " + firstName + "\nLast Name: " + lastName);
                    }
                }
            }
        });

        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });

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
            String lastName = addCustomerForm.getLastName();
            String parcelID = addCustomerForm.getParcelID();
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
}
