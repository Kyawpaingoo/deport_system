package View;

import Controller.ParcelController;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Infra.Extension;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static Infra.Extension.generateNumber;

public class ParcelTable extends JPanel{
    private JTabbedPane tabbedPane;
    private JTable allParcelTable;
    private JTable collectedParcelTable;
    private JTable uncollectedParcelTable;
    private DefaultTableModel allTableModel;
    private DefaultTableModel collectedTableModel;
    private DefaultTableModel uncollectedTableModel;
    private JTextArea parcelDetails;
    private JPanel parcelPanel;
    private JButton addNewParcelButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JButton searchButton;
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
        tabbedPane = new JTabbedPane();

        allTableModel = createTableModel();
        collectedTableModel = createTableModel();
        uncollectedTableModel = createTableModel();

        allParcelTable = new JTable(allTableModel);
        collectedParcelTable = new JTable(collectedTableModel);
        uncollectedParcelTable = new JTable(uncollectedTableModel);

        parcelDetails = new JTextArea();
        parcelDetails.setEditable(false);

        addNewParcelButton = new JButton("Add New Parcel");
        deleteButton = new JButton("Delete");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = allParcelTable.getSelectedRow();
                if (selectedRow != -1) {
                    int parcelNumber = (int) allParcelTable.getValueAt(selectedRow, 0);

                    if (parcelNumber != -1) {
                        _parcelController.removeParcel(parcelNumber);
                        allTableModel.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Parcel Number. Unable to delete parcel.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No parcel selected.");
                }
            }
        });

        allParcelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = allParcelTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int parcelNumber = (int) allParcelTable.getValueAt(selectedRow, 0);
                        String parcelId = (String) allParcelTable.getValueAt(selectedRow, 1);
                        int daysInDeport = (int) allParcelTable.getValueAt(selectedRow, 2);
                        double weight = (double) allParcelTable.getValueAt(selectedRow, 3);
                        String dimension = (String) allParcelTable.getValueAt(selectedRow, 4);
                        ParcelStatus status = (ParcelStatus) allParcelTable.getValueAt(selectedRow, 5);
                        LocalDate receivedDate = (LocalDate) allParcelTable.getValueAt(selectedRow, 6);
                        LocalDate collectedDate = (LocalDate) allParcelTable.getValueAt(selectedRow, 7);
                        String customerSurname = (String) allParcelTable.getValueAt(selectedRow, 8);
                        parcelDetails.setText("Parcel Number: " + parcelNumber + "\nParcel ID: " + parcelId +
                                "\nDays in Deport: " + daysInDeport + "\nWeight:" + weight +
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim();
                searchParcels(query);
            }
        });
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(new Object[]{
                "No", "Parcel ID", "Days in Deport", "Weight", "Dimensions",
                "Status", "Received Date", "Collected Date", "Customer Surname"}, 0) {
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
    }

    private void showAddNewParcelDialog() {
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

            allTableModel.addRow(new Object[]{
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

            if (status == ParcelStatus.Collected) {
                collectedTableModel.addRow(new Object[]{
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
            } else {
                uncollectedTableModel.addRow(new Object[]{
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
    }
    private void layoutComponents() {
        JScrollPane allScrollPane = new JScrollPane(allParcelTable);
        JScrollPane collectedScrollPane = new JScrollPane(collectedParcelTable);
        JScrollPane uncollectedScrollPane = new JScrollPane(uncollectedParcelTable);

        JSplitPane allSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, allScrollPane, new JScrollPane(parcelDetails));
        allSplitPane.setDividerLocation(0.7);

        parcelPanel = new JPanel(new BorderLayout());
        parcelPanel.add(allSplitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(addNewParcelButton);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        parcelPanel.add(topPanel, BorderLayout.NORTH);

        tabbedPane.addTab("All", parcelPanel);
        tabbedPane.addTab("Collected", collectedScrollPane);
        tabbedPane.addTab("Uncollected", uncollectedScrollPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadParcels() {
        Map<Integer, ParcelModel> parcels = _parcelController.sortBySurname();
        for (Map.Entry<Integer, ParcelModel> entry : parcels.entrySet()) {
            ParcelModel parcel = entry.getValue();
            Object[] rowData = new Object[]{
                    parcel.getNo(),
                    parcel.getParcelID(),
                    parcel.getDaysInDepot(),
                    parcel.getWeight(),
                    parcel.getDimensions(),
                    parcel.getParcelStatus(),
                    parcel.getReceivedDate(),
                    parcel.getCollectedDate(),
                    parcel.getCustomerSurname()
            };
            allTableModel.addRow(rowData);
            if (parcel.getParcelStatus() == ParcelStatus.Collected) {
                collectedTableModel.addRow(rowData);
            } else {
                uncollectedTableModel.addRow(rowData);
            }
        }
    }

    private void searchParcels(String query) {
        Map<Integer, ParcelModel> filteredParcels;
        if (query.isEmpty()) {
            filteredParcels = _parcelController.sortBySurname();
        } else {
            filteredParcels = _parcelController.searchParcel(query);
        }
        allTableModel.setRowCount(0);

        for (ParcelModel parcel : filteredParcels.values()) {
            Object[] rowData = new Object[]{
                    parcel.getNo(),
                    parcel.getParcelID(),
                    parcel.getDaysInDepot(),
                    parcel.getWeight(),
                    parcel.getDimensions(),
                    parcel.getParcelStatus(),
                    parcel.getReceivedDate(),
                    parcel.getCollectedDate(),
                    parcel.getCustomerSurname()
            };
            allTableModel.addRow(rowData);
        }
    }
}
