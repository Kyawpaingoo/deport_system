package Services.CustomerService;

import Model.CustomerModel;
import Model.Dtos.QueueOfCustomer;
import Model.StaffModel;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
     List<CustomerModel> getAll();
     Optional<CustomerModel> getByQueue(int Id);
     String addCustomer(CustomerModel obj);
     String removeCustomer(int QueueNumber);
     String getCurrentCustomer(int QueueNumber);

}
