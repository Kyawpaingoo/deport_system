import Controller.CustomerController;
import Model.CustomerModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;
import View.CustomerList;
import View.Layout;
import View.LoginForm;
import View.QueuePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm(null);
            loginForm.setVisible(true);

            if (loginForm.isSucceeded()) {

                Layout app = new Layout();
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