package Controller;

import Model.CustomerModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerController {
    private final ICustomerService _customerService;

    public CustomerController()
    {
        this._customerService = new CustomerService();
    }

    public List<CustomerModel> getAll()
    {
        return  _customerService.getAll();
    }

    public CustomerModel getByQueueNumber(int QueueNumber)
    {
        return _customerService.getByQueue(QueueNumber).get();
    }

    public String addCustomer(CustomerModel obj)
    {
        return _customerService.addCustomer(obj);
    }

    public String removeCustomer(int QueueNumber)
    {
        return _customerService.removeCustomer(QueueNumber);
    }

    public String getCurrentCustomer(int QueueNumber)
    {
        return  _customerService.getCurrentCustomer(QueueNumber);
    }
}
