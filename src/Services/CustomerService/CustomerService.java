package Services.CustomerService;

import Infra.UnitOfWork.UnitOfWork;
import Logger.Logger;
import Model.CustomerModel;
import Model.Dtos.QueueOfCustomer;

import java.util.List;
import java.util.Optional;

public class CustomerService implements ICustomerService{

    private final UnitOfWork _uow;

    public CustomerService()
    {
        this._uow = new UnitOfWork();
    }

    @Override
    public List<CustomerModel> getAll() {

        List<CustomerModel> customerList = _uow._customerRepository().getList();
        return customerList;
    }

    @Override
    public Optional<CustomerModel> getByQueue(int Id) {
        Optional<CustomerModel> result = _uow._customerRepository().getById(Id);
        return result;
    }

    @Override
    public String addCustomer(CustomerModel obj) {
        boolean result = _uow._customerRepository().insert(obj);
        Logger.getInstance().log("Customer added: " + obj.toString());
        return result ? "Success" : "Fail";
    }

    @Override
    public String removeCustomer(int QueueNumber) {
        CustomerModel obj = _uow._customerRepository().getById(QueueNumber).get();
        boolean result = _uow._customerRepository().delete(QueueNumber);

        Logger.getInstance().log("Customer remove: " + obj.toString());
        return result ? "Success" : "Fail";
    }

    @Override
    public String getCurrentCustomer(int QueueNumber)
    {
        Optional<CustomerModel> data = getByQueue(QueueNumber);
        String result = "Customer Number: " + data.get().getQueueNumber();
        return  result;
    }
}
