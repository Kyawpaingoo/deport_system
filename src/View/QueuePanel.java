package View;

import Controller.StaffController;
import Model.CustomerModel;
import Model.Dtos.QueueOfCustomer;
import Model.SharedModel.QueueModel;
import Services.QueueObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class QueuePanel extends JPanel implements Observer {
    private JTable currentProcessingTable;
    private JTable upcomingQueueTable;
    private DefaultTableModel currentProcessingTableModel;
    private DefaultTableModel upcomingQueueTableModel;
    private StaffController _staffController;

    public QueuePanel(StaffController staffController) {
        this._staffController = staffController;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
        loadQueueData();

        staffController.getQueueModel().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        currentProcessingTableModel.setRowCount(0);
        upcomingQueueTableModel.setRowCount(0);
        loadQueueData();
    }

    private void initializeComponents() {
        currentProcessingTableModel = new DefaultTableModel(new Object[]{"Queue Number", "Surname", "Parcel ID"}, 0);
        upcomingQueueTableModel = new DefaultTableModel(new Object[]{"Queue Number", "Surname", "Parcel ID"}, 0);

        currentProcessingTable = new JTable(currentProcessingTableModel);
        upcomingQueueTable = new JTable(upcomingQueueTableModel);
    }

    private void layoutComponents() {
        JScrollPane currentProcessingScrollPane = new JScrollPane(currentProcessingTable);
        JScrollPane upcomingQueueScrollPane = new JScrollPane(upcomingQueueTable);

        JPanel currentProcessingPanel = new JPanel(new BorderLayout());
        currentProcessingPanel.setBorder(BorderFactory.createTitledBorder("Current Processing Queue"));
        currentProcessingPanel.add(currentProcessingScrollPane, BorderLayout.CENTER);

        JPanel upcomingQueuePanel = new JPanel(new BorderLayout());
        upcomingQueuePanel.setBorder(BorderFactory.createTitledBorder("Upcoming Queue"));
        upcomingQueuePanel.add(upcomingQueueScrollPane, BorderLayout.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        add(currentProcessingPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        add(upcomingQueuePanel, gbc);
    }

    private void loadQueueData()
    {
        QueueModel customerQueue = _staffController.getQueueModel();
        int index = 0;
        for (CustomerModel customer : customerQueue.getCustomerList()) {

            if (index < 3) {
                currentProcessingTableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getSurName(), customer.getParcelID()});
            } else {
                upcomingQueueTableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getSurName(), customer.getParcelID()});
            }
            index++;
        }

        currentProcessingTable.revalidate();
        currentProcessingTable.repaint();
        upcomingQueueTable.revalidate();
        upcomingQueueTable.repaint();
    }
}
