package View;

import javax.swing.*;
import java.awt.*;

public class Layout extends JFrame {
    private JPanel parentPanel;
    private JPanel sidebar;
    private JPanel contentPanel;
    private JButton customerListButton;
    private JButton parcelListButton;
    private JButton customerQueueButton;
    private JButton reportsButton;
    private JButton logoutButton;

    public Layout() {
        setTitle("Deport Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        parentPanel = new JPanel(new BorderLayout());

        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.LIGHT_GRAY);

        customerListButton = new JButton("Customer List");
        parcelListButton = new JButton("Parcel List");
        customerQueueButton = new JButton("Customer Queue");
        reportsButton = new JButton("Reports");
        logoutButton = new JButton("Logout"); // Logout button

        customerListButton.addActionListener(e -> showContent(new CustomerList()));
        parcelListButton.addActionListener(e -> showContent(new ParcelTable()));
        customerQueueButton.addActionListener(e -> showContent(new CustomerQueueList()));
        reportsButton.addActionListener(e -> showContent(new ReportTable()));
        logoutButton.addActionListener(e -> handleLogout());

        sidebar.add(customerListButton);
        sidebar.add(parcelListButton);
        sidebar.add(customerQueueButton);
        sidebar.add(reportsButton);
        sidebar.add(logoutButton);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new CustomerList(), BorderLayout.CENTER);

        parentPanel.add(sidebar, BorderLayout.WEST);
        parentPanel.add(contentPanel, BorderLayout.CENTER);

        add(parentPanel);

        setLocationRelativeTo(null);
    }

    private void showContent(JPanel component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
