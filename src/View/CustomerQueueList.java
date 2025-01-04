package View;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerQueueList extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextArea customerDetails;
    private JTextArea parcelDetails;
    private JPanel detailsPanel;
    private JButton calculateFeeButton;
    private JButton updateStatusButton;
    private JButton removeCustomerButton;

    public CustomerQueueList() {
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel.addRow(new Object[]{"1", "John Doe"});
        tableModel.addRow(new Object[]{"2", "Jane Smith"});

        customerDetails = new JTextArea();
        customerDetails.setEditable(false);
        parcelDetails = new JTextArea();
        parcelDetails.setEditable(false);

        calculateFeeButton = new JButton("Calculate Fee");
        updateStatusButton = new JButton("Update Status");
        removeCustomerButton = new JButton("Remove Customer from Queue");

        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String customerId = (String) customerTable.getValueAt(selectedRow, 0);
                        String customerName = (String) customerTable.getValueAt(selectedRow, 1);
                        customerDetails.setText("Customer ID: " + customerId + "\nCustomer Name: " + customerName);
                        // Assuming parcel details can be fetched based on customer ID
                        parcelDetails.setText("Parcel details for Customer ID: " + customerId);
                    }
                }
            }
        });

        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    customerDetails.setText("");
                    parcelDetails.setText("");
                }
            }
        });
    }

    private void layoutComponents() {
        JScrollPane tableScrollPane = new JScrollPane(customerTable);

        detailsPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(calculateFeeButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(removeCustomerButton);

        JSplitPane detailsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(customerDetails), new JScrollPane(parcelDetails));
        detailsSplitPane.setDividerLocation(100);

        JScrollPane customerDetailsScrollPane = new JScrollPane(customerDetails);
        customerDetailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        detailsPanel.add(detailsSplitPane, BorderLayout.CENTER);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        mainSplitPane.setDividerLocation(0.7);

        add(mainSplitPane, BorderLayout.CENTER);
    }
}
