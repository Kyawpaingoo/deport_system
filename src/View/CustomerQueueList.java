package View;

import Controller.CustomerController;
import Controller.ParcelController;
import Controller.StaffController;
import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.Dtos.UpdateStatusDto;
import Model.ParcelModel;

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
    private StaffController _staffController;
    private ParcelController _parcelController;
    private CustomerController _customerController;
    private double totoalFee;
    private double discount;

    public CustomerQueueList(StaffController staffController, ParcelController parcelController, CustomerController customerController) {
        this._staffController = staffController;
        this._parcelController = parcelController;
        this._customerController = customerController;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
        loadCustomers();
    }

    private void initializeComponents() {
        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name", "Parcel ID"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customerDetails = new JTextArea();
        customerDetails.setEditable(false);
        parcelDetails = new JTextArea();
        parcelDetails.setEditable(false);

        calculateFeeButton = new JButton("Calculate Fee");
        updateStatusButton = new JButton("Update Status");
        removeCustomerButton = new JButton("Remove Customer");

        updateStatusButton.setEnabled(false);
        removeCustomerButton.setEnabled(false);

        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int queueNumber = (int) customerTable.getValueAt(selectedRow, 0);

                        CustomerModel customer = _customerController.getByQueueNumber(queueNumber);
                        if(customer != null)
                        {
                            customerDetails.setText("Queue Number: " + customer.getQueueNumber() +
                                    "\nFirst Name: " + customer.getFirstName() +
                                    "\nSurname: " + customer.getSurName() +
                                    "\nParcel ID: " + customer.getParcelID());
                        } else {
                            customerDetails.setText("No customer details available.");
                        }

                        ParcelModel parcel = _parcelController.getParcelDetail(customer.getParcelID());
                        if (parcel != null) {
                            parcelDetails.setText("Parcel ID: " + parcel.getParcelID() +
                                    "\nDays in Deport: " + parcel.getDaysInDepot() +
                                    "\nWeight: " + parcel.getWeight() +
                                    "\nDimensions: " + parcel.getDimensions() +
                                    "\nStatus: " + parcel.getParcelStatus() +
                                    "\nReceived Date: " + parcel.getReceivedDate() +
                                    "\nCollected Date: " + parcel.getCollectedDate() +
                                    "\nCustomer Surname: " + parcel.getCustomerSurname());
                        } else {
                            parcelDetails.setText("No parcel details available.");
                        }
                    }
                }
            }
        });

        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    int queueNumber = (int) customerTable.getValueAt(selectedRow, 0);
                    CustomerModel customer = _customerController.getByQueueNumber(queueNumber);

                    if (customer != null) {
                        _staffController.removeCustomerFromQueue(customer);
                        tableModel.removeRow(selectedRow);
                        customerDetails.setText("");
                        parcelDetails.setText("");
                        updateStatusButton.setEnabled(false);
                        removeCustomerButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });

        calculateFeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String discountStr = JOptionPane.showInputDialog("Enter discount percentage:");
                discount = Double.parseDouble(discountStr);
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    String parcelID = (String) customerTable.getValueAt(selectedRow, 2);
                    ParcelModel parcel = _parcelController.getParcelDetail(parcelID);
                    if (parcel != null) {
                        double fee = _parcelController.calculatedParcelFee(parcel.getDimensions(), parcel.getWeight(), parcel.getDaysInDepot(), discount);
                        totoalFee += fee;
                        JOptionPane.showMessageDialog(null, "Calculated Fee: $" + fee);
                        updateStatusButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Parcel details not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });

        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    String parcelID = (String) customerTable.getValueAt(selectedRow, 2);
                    ParcelModel parcel = _parcelController.getParcelDetail(parcelID);
                    if (parcel != null) {
                        parcel.setParcelStatus(ParcelStatus.Collected);
                        UpdateStatusDto dto = new UpdateStatusDto(parcel.getParcelID(), ParcelStatus.Collected, discount, totoalFee);
                        _staffController.updateParcelStatus(dto);
                        JOptionPane.showMessageDialog(null, "Status Updated: Collected");
                        removeCustomerButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Parcel details not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
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

        detailsPanel.add(detailsSplitPane, BorderLayout.CENTER);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        mainSplitPane.setDividerLocation(0.7);

        add(mainSplitPane, BorderLayout.CENTER);
    }

    private void loadCustomers() {
        QueueOfCustomer customerQueue = _staffController.getQueueList();
        for (CustomerModel customer : customerQueue.getCustomerList()) {
            tableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getSurName(), customer.getParcelID()});
        }
    }
}
