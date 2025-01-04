package View;

import Controller.ParcelController;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Infra.Extension;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;

import static Infra.Extension.generateNumber;

public class ParcelTable extends JPanel{
    private JTable parcelTable;
    private DefaultTableModel tableModel;
    private JTextArea parcelDetails;
    private JPanel parcelPanel;
    private JButton addNewParcelButton;
    private JButton deleteButton;
    private ParcelController _parcelController;

    public ParcelTable(ParcelController parcelController)
    {
        this._parcelController = parcelController;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
        loadParcels();
    }

    private void initializeComponents() {
        parcelPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{
                "No","Parcel ID", "Days in Deport", "Weight", "Dimensions",
                "Status", "Received Date", "Collected Date", "Customer Surname"}, 0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 2:
                        return Integer.class;
                    case 3:
                        return Double.class;
                    case 5:
                        return ParcelStatus.class;
                    case 6:
                    case 7:
                        return LocalDate.class;
                    default:
                        return String.class;
                }
            }
        };


        parcelTable = new JTable(tableModel);
        parcelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        parcelDetails = new JTextArea();
        parcelDetails.setEditable(false);

        addNewParcelButton = new JButton("Add New Parcel");
        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = parcelTable.getSelectedRow();
                if (selectedRow != -1) {
                    int parcelNumber = (int) parcelTable.getValueAt(selectedRow, 0);

                    if (parcelNumber != -1) { // Ensure valid queueNumber
                        _parcelController.removeParcel(parcelNumber); // Remove from controller
                        tableModel.removeRow(selectedRow); // Remove from table
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Queue Number. Unable to delete customer.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customer selected.");
                }
            }
        });

        parcelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = parcelTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int parcelNumber = (int) parcelTable.getValueAt(selectedRow, 0);
                        String parcelId = (String) parcelTable.getValueAt(selectedRow, 1);
                        int daysInDeport = (int) parcelTable.getValueAt(selectedRow, 2);
                        double weight = (double) parcelTable.getValueAt(selectedRow, 3);
                        String dimension = (String) parcelTable.getValueAt(selectedRow, 4);
                        ParcelStatus  status = (ParcelStatus ) parcelTable.getValueAt(selectedRow, 5);
                        LocalDate receivedDate = (LocalDate) parcelTable.getValueAt(selectedRow, 6);
                        LocalDate collectedDate = (LocalDate) parcelTable.getValueAt(selectedRow, 7);
                        String customerSurname = (String) parcelTable.getValueAt(selectedRow, 8);
                        parcelDetails.setText("Parcel Number: " + parcelNumber + "\nParcel ID: " + parcelId +
                                "\nDays in Deport: " + daysInDeport + "\nWeight:" + weight  +
                                "\nDimensions: " + dimension + "\nStatus: " + status + "\nReceived Date: " + receivedDate
                               + "\nCollected Date: " + collectedDate + "\nCustomer Surname: " + customerSurname);
                    }
                }
            }
        });

        addNewParcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewParcelDialog();
            }
        });
    }

    private void showAddNewParcelDialog()
    {
        AddParcelForm addParcelForm = new AddParcelForm((Frame) SwingUtilities.getWindowAncestor(this));
        addParcelForm.setVisible(true);
        if (addParcelForm.isSucceeded()) {
            int parcelNumber = generateNumber();
            String parcelID = addParcelForm.getParcelID();
            int daysInDeport = addParcelForm.getDaysInDeport();
            double weight = addParcelForm.getWeight();
            String dimension = addParcelForm.getDimension();
            ParcelStatus status = addParcelForm.getStatus();
            LocalDate receivedDate = addParcelForm.getReceivedDate();
            LocalDate collected = addParcelForm.getCollected();
            String customerSurname = addParcelForm.getCustomerSurname();

            ParcelModel parcelModel = new ParcelModel(parcelNumber, parcelID, daysInDeport, weight, dimension, status, receivedDate, collected, customerSurname);

            _parcelController.addParcel(parcelModel);

            tableModel.addRow(new Object[]{
                    parcelNumber,
                    parcelID,
                    daysInDeport,
                    weight,
                    dimension,
                    status,
                    receivedDate,
                    collected,
                    customerSurname
            });

        }
    }
    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(parcelTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, new JScrollPane(parcelDetails));
        splitPane.setDividerLocation(0.7);

        parcelPanel.add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(addNewParcelButton);

        parcelPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(parcelPanel, BorderLayout.CENTER);
    }

    private void loadParcels() {
        Map<Integer, ParcelModel> parcels = _parcelController.sortBySurname();
        for (Map.Entry<Integer, ParcelModel> entry : parcels.entrySet()) {
            ParcelModel parcel = entry.getValue();
            tableModel.addRow(new Object[]{
                    parcel.getNo(),
                    parcel.getParcelID(),
                    parcel.getDaysInDepot(),
                    parcel.getWeight(),
                    parcel.getDimensions(),
                    parcel.getParcelStatus(),
                    parcel.getReceivedDate(),
                    parcel.getCollectedDate(),
                    parcel.getCustomerSurname()
            });
        }
    }

//    private int parseNumber(Object queueNumberObj) {
//        if (queueNumberObj instanceof Integer) {
//            return (int) queueNumberObj;
//        } else if (queueNumberObj instanceof String) {
//            try {
//                return Integer.parseInt((String) queueNumberObj);
//            } catch (NumberFormatException ex) {
//                System.err.println("Invalid Queue Number format: " + queueNumberObj);
//            }
//        }
//        return -1;
//    }
}
