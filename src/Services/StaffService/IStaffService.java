package Services.StaffService;

import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.ParcelModel;
import Model.StaffModel;

public interface IStaffService {
    StaffModel login(String userName, String password);
    Boolean logout(String userName, String password);
    ParcelModel updateParcelStatus(String ParcelID, ParcelStatus parcelStatus);
    String addParcel(ParcelModel obj);
    CustomerModel processCustomer(int queueNumber);
    QueueOfCustomer addCustomerToQueue(CustomerModel data);
    QueueOfCustomer removeCustomerFromQueue(int queueNumber);
}
