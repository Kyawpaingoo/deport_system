package Services.CustomerService;

import Infra.UnitOfWork.UnitOfWork;
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
    public QueueOfCustomer getAll() {
        QueueOfCustomer queueList = new QueueOfCustomer();
        List<CustomerModel> customerList = _uow._customerRepository().getList();
        queueList.CustomerList.addAll(customerList);
       // System.out.println(queueList.CustomerList);
        return queueList;
    }

    @Override
    public Optional<CustomerModel> getByQueue(int Id) {
        Optional<CustomerModel> result = _uow._customerRepository().getById(Id);
//        System.out.println(result);
        return result;
    }

    @Override
    public String addCustomer(CustomerModel obj) {
        boolean result = _uow._customerRepository().insert(obj);
//        System.out.println(result);
        return result ? "Success" : "Fail";
    }

    @Override
    public String removeCustomer(int QueueNumber) {
        boolean result = _uow._customerRepository().delete(QueueNumber);
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
