package View;

import javax.swing.*;
import java.awt.*;

public class ReportTable extends JPanel {
    private JTabbedPane reportTabBar;
    private JPanel customerPanel;
    private JPanel parcelPanel;
    private JTable customerTable;
    private JTable parcelTable;

    public ReportTable() {
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        reportTabBar = new JTabbedPane();

        String[] columnNames = {"Date", "Total Parcels", "Total Revenue", "Status"};

        Object[][] customerData = {
                {"2025-01-04", "15", "$150.00", "Completed"},
                {"2025-01-03", "12", "$120.00", "Completed"},
                {"2025-01-02", "18", "$180.00", "Completed"}
        };

        Object[][] parcelData = {
                {"Week 1 Jan", "105", "$1,050.00", "Completed"},
                {"Week 4 Dec", "95", "$950.00", "Completed"},
                {"Week 3 Dec", "88", "$880.00", "Completed"}
        };

        customerTable = new JTable(customerData, columnNames);
        parcelTable = new JTable(parcelData, columnNames);

        configureTable(customerTable);
        configureTable(parcelTable);

        customerPanel = new JPanel(new BorderLayout());
        parcelPanel = new JPanel(new BorderLayout());

        customerPanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        parcelPanel.add(new JScrollPane(parcelTable), BorderLayout.CENTER);
    }

    private void configureTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
    }

    private void layoutComponents() {
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        JScrollPane parcelScrollPane = new JScrollPane(parcelTable);

        reportTabBar.addTab("Daily Customer Report", customerScrollPane);
        reportTabBar.addTab("Daily Collected Parcel Report", parcelScrollPane);

        reportTabBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(reportTabBar, BorderLayout.CENTER);

        setPreferredSize(new Dimension(800, 500));
    }
}



