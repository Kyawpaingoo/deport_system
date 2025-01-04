package View;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParcelTable extends JPanel{
    private JTable parcelTable;
    private DefaultTableModel tableModel;
    private JTextArea parcelDetails;
    private JPanel parcelPanel;
    private JButton addNewParcelButton;
    private JButton deleteButton;

    public ParcelTable()
    {
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        parcelPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Parcel ID", "Customer ID", "Status"}, 0);
        parcelTable = new JTable(tableModel);
        parcelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel.addRow(new Object[]{"1", "101", "Uncollected"});
        tableModel.addRow(new Object[]{"2", "102", "Collected"});

        parcelDetails = new JTextArea();
        parcelDetails.setEditable(false);

        addNewParcelButton = new JButton("Add New Parcel");
        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = parcelTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        parcelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = parcelTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String parcelId = (String) parcelTable.getValueAt(selectedRow, 0);
                        String customerId = (String) parcelTable.getValueAt(selectedRow, 1);
                        String status = (String) parcelTable.getValueAt(selectedRow, 2);
                        parcelDetails.setText("Parcel ID: " + parcelId + "\nCustomer ID: " + customerId + "\nStatus: " + status);
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
            tableModel.addRow(new Object[]{
                    addParcelForm.getParcelID(),
                    addParcelForm.getDaysInDeport(),
                    addParcelForm.getWeight(),
                    addParcelForm.getDimension(),
                    addParcelForm.getStatus(),
                    addParcelForm.getReceivedDate(),
                    addParcelForm.getCollected(),
                    addParcelForm.getCustomerSurname()
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
}
