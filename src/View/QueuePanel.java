package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QueuePanel extends JPanel {
    private JTable currentProcessingTable;
    private JTable upcomingQueueTable;
    private DefaultTableModel currentProcessingTableModel;
    private DefaultTableModel upcomingQueueTableModel;

    public QueuePanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        currentProcessingTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Status"}, 0);
        upcomingQueueTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Status"}, 0);

        currentProcessingTable = new JTable(currentProcessingTableModel);
        upcomingQueueTable = new JTable(upcomingQueueTableModel);

        currentProcessingTableModel.addRow(new Object[]{"1", "John Doe", "Processing"});
        currentProcessingTableModel.addRow(new Object[]{"2", "Jane Smith", "Processing"});

        upcomingQueueTableModel.addRow(new Object[]{"3", "Alice Johnson", "Upcoming"});
        upcomingQueueTableModel.addRow(new Object[]{"4", "Bob Brown", "Upcoming"});
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
}
