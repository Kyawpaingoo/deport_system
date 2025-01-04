package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomerForm extends JDialog {
    private JTextField firstNameField;
    private JTextField surNameField;
    private JTextField ParcelIDField;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel contentPanel;
    private boolean succeeded;

    public AddCustomerForm(Frame parent) {
        super(parent, "Add New Customer", true);
        initializeComponents();
        layoutComponents();

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        firstNameField = new JTextField(20);
        surNameField = new JTextField(20);
        ParcelIDField = new JTextField(20);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstNameField.getText().isEmpty() && !surNameField.getText().isEmpty() && !ParcelIDField.getText().isEmpty()) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddCustomerForm.this, "Please enter both first name and last name.", "Error", JOptionPane.ERROR_MESSAGE);
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
        contentPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("SurName:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(surNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(new JLabel("Parcel ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(ParcelIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(cancelButton, gbc);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getSurName() {
        return surNameField.getText();
    }

    public String getParcelID() {
        return ParcelIDField.getText();
    }
}
