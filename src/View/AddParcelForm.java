package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddParcelForm extends JDialog {
    private JTextField ParcelIDField;
    private JTextField DaysInDeportField;
    private JTextField WeightField;
    private JTextField DimensionField;
    private JTextField StatusField;
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
        StatusField = new JTextField(20);
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
                        !StatusField.getText().isEmpty() &&
                        !ReceivedDateField.getText().isEmpty() &&
                        !CollectedField.getText().isEmpty() &&
                        !CustomerSurnameField.getText().isEmpty()) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddParcelForm.this, "Please enter both first name and last name.", "Error", JOptionPane.ERROR_MESSAGE);
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
        contentPanel.add(new JLabel("Received Date:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(ReceivedDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(new JLabel("Collected Date:"), gbc);
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

    public String getDaysInDeport() {
        return DaysInDeportField.getText();
    }

    public String getWeight() {
        return WeightField.getText();
    }

    public String getDimension() {
        return DimensionField.getText();
    }

    public String getStatus() {
        return StatusField.getText();
    }

    public String getReceivedDate() {
        return ReceivedDateField.getText();
    }

    public String getCollected() {
        return CollectedField.getText();
    }

    public String getCustomerSurname() {
        return CustomerSurnameField.getText();
    }
}
