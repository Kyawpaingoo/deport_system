package View;

import Controller.StaffController;
import Model.CollectedParcelModel;
import Model.CustomerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReportTable extends JPanel {
    private JTabbedPane reportTabBar;
    private JPanel customerPanel;
    private JPanel parcelPanel;
    private JTable customerTable;
    private JTable parcelTable;
    private StaffController _staffController;

    public ReportTable(StaffController staffController) {
        this._staffController = staffController;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
        loadReportData();
    }

    private void initializeComponents() {
        reportTabBar = new JTabbedPane();

        String[] customerColumnNames = {"Customer ID", "Queue Number", "Status"};
        String[] parcelColumnNames = {"Date", "Parcel ID", "Status", "Fee"};

        customerTable = new JTable(new DefaultTableModel(customerColumnNames, 0));
        parcelTable = new JTable(new DefaultTableModel(parcelColumnNames, 0));

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

    private void loadReportData() {
        java.util.List<CustomerModel> dailyCustomerData = _staffController.getDailyCustomerList(LocalDate.now());
        List<CollectedParcelModel> dailyParcelData = _staffController.getDailyCollectedParcelList(LocalDate.now());

        updateTableData(customerTable, dailyCustomerData);
        updateTableData(parcelTable, dailyParcelData);
    }

    private void updateTableData(JTable table, List<?> data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Object row : data) {
            if (row instanceof CustomerModel) {
                CustomerModel customer = (CustomerModel) row;
                model.addRow(new Object[]{
                        customer.getParcelID(),
                        "$" + customer.getQueueNumber(),
                        "Completed"
                });
            } else if (row instanceof CollectedParcelModel) {
                CollectedParcelModel parcel = (CollectedParcelModel) row;
                model.addRow(new Object[]{
                        LocalDate.now().toString(),
                        parcel.getParcelID(),
                        parcel.getParcelStatus(),
                        "$" + parcel.getTotalFee(),
                });
            }
        }
    }
}



