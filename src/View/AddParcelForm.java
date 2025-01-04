package View;

import Model.Dtos.ParcelStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddParcelForm extends JDialog {
    private JTextField ParcelIDField;
    private JTextField DaysInDeportField;
    private JTextField WeightField;
    private JTextField DimensionField;
    private JComboBox<ParcelStatus> StatusField;
    private JTextField ReceivedDateField;
    private JTextField CollectedField;
    private JTextField CustomerSurnameField;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel contentPanel;
    private boolean succeeded;

    public AddParcelForm(Frame parent) {
        super(parent, "Add New Customer", true);
        initializeComponents();
        layoutComponents();

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        ParcelIDField = new JTextField(20);
        DaysInDeportField = new JTextField(20);
        WeightField = new JTextField(20);
        DimensionField = new JTextField(20);
        StatusField = new JComboBox<>(ParcelStatus.values());
        ReceivedDateField = new JTextField(20);
        CollectedField = new JTextField(20);
        CustomerSurnameField = new JTextField(20);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ParcelIDField.getText().isEmpty() &&
                        !DaysInDeportField.getText().isEmpty() &&
                        !WeightField.getText().isEmpty() &&
                        !DimensionField.getText().isEmpty() &&
                        StatusField.getSelectedItem() != null &&
                        !ReceivedDateField.getText().isEmpty() &&
                        !CustomerSurnameField.getText().isEmpty()) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddParcelForm.this, "Please enter fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                succeeded = false;
                dispose();
            }
        });
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());

        contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Parcel ID:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(ParcelIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("Days in Deport:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(DaysInDeportField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(WeightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(new JLabel("Dimensions:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(DimensionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(StatusField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(new JLabel("Received Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        contentPanel.add(ReceivedDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(new JLabel("Collected Date (yyyy-MM-dd) optional*:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(CollectedField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPanel.add(new JLabel("Customer Surname:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(CustomerSurnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPanel.add(addButton, gbc);

        gbc.gridx = 1;
        contentPanel.add(cancelButton, gbc);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getParcelID() {
        return ParcelIDField.getText();
    }

    public int getDaysInDeport() {
        try {
            return Integer.parseInt(DaysInDeportField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid format for Days in Deport. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public double getWeight() {
        try {
            return Double.parseDouble(WeightField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid format for Weight. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }

    public String getDimension() {
        return DimensionField.getText();
    }

    public ParcelStatus getStatus() {
        return (ParcelStatus) StatusField.getSelectedItem();
    }

    public LocalDate getReceivedDate() {
        try {
            return LocalDate.parse(ReceivedDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format for Received Date. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public LocalDate getCollected() {
        if (CollectedField.getText().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(CollectedField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format for Collected Date. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getCustomerSurname() {
        return CustomerSurnameField.getText();
    }
}
