import Controller.CustomerController;
import Controller.ParcelController;
import Controller.StaffController;
import Model.CustomerModel;
import Model.ParcelModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;
import View.CustomerList;
import View.Layout;
import View.LoginForm;
import View.QueuePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm(null);
            loginForm.setVisible(true);

            if (loginForm.isSucceeded()) {
                CustomerController customerController = new CustomerController();
                ParcelController parcelController = new ParcelController();
                StaffController staffController = new StaffController();

                Layout app = new Layout(customerController, parcelController, staffController);
                app.setVisible(true);

                JFrame queueFrame = new JFrame("Queue Panel");
                queueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                queueFrame.setSize(600, 400);
                queueFrame.add(new QueuePanel());
                queueFrame.setVisible(true);

            } else {
                System.exit(0);
            }
        });
    }
}