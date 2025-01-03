package Controller;

import Model.CustomerModel;
import Services.CustomerService.ICustomerService;

public class CustomerController {
    private final ICustomerService _customerService;

    public CustomerController(ICustomerService customerService)
    {
        this._customerService = customerService;
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
