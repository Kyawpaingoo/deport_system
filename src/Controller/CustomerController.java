package Controller;

import Model.CustomerModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;

public class CustomerController {
    private final ICustomerService _customerService;

    public CustomerController()
    {
        this._customerService = new CustomerService();
    }

    public void getAll()
    {
        _customerService.getAll();
    }

    public void addCustomer(CustomerModel obj)
    {
        _customerService.addCustomer(obj);
    }

    public void removeCustomer(int QueueNumber)
    {
        _customerService.removeCustomer(QueueNumber);
    }

    public void getCurrentCustomer(int QueueNumber)
    {
        _customerService.getCurrentCustomer(QueueNumber);
    }
}
