import Controller.CustomerController;
import Controller.ParcelController;
import Controller.StaffController;
import Model.CustomerModel;
import Model.ParcelModel;
import Model.SharedModel.QueueModel;
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
        QueueModel queueModel = new QueueModel();
        StaffController staffController = new StaffController(queueModel);

        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm(null, staffController);
            loginForm.setVisible(true);

            if (loginForm.isSucceeded()) {
                CustomerController customerController = new CustomerController();
                ParcelController parcelController = new ParcelController();

                Layout app = new Layout(customerController, parcelController, staffController);
                app.setVisible(true);

                JFrame queueFrame = new JFrame("Queue Panel");
                queueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                queueFrame.setSize(600, 400);
                queueFrame.add(new QueuePanel(staffController));
                queueFrame.setVisible(true);

            } else {
                System.exit(0);
            }
        });
    }
}