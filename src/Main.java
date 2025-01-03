import Controller.CustomerController;
import Model.CustomerModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;

public class Main {
    public static void main(String[] args) {
        ICustomerService customerService = new CustomerService();
        CustomerController customerController = new CustomerController(customerService);

        customerController.getAll();
        customerController.getCurrentCustomer(4);

        CustomerModel customer = new CustomerModel();
        customer.setQueueNumber(23);
        customer.setFirstName("Luke");
        customer.setSurName("Skywalker");
        customer.setParcelID("C1023");
        customerController.addCustomer(customer);
    }
}