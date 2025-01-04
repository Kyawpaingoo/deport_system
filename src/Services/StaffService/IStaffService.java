package Services.StaffService;

import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.Dtos.UpdateStatusDto;
import Model.Dtos.LoginDto;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.ParcelModel;
import Model.StaffModel;

import java.time.LocalDate;
import java.util.List;

public interface IStaffService {
    Boolean login(LoginDto dto);
    Boolean logout(LoginDto dto);
    ParcelModel updateParcelStatus(UpdateStatusDto dto);
    String addParcel(ParcelModel obj);
    CustomerModel processCustomer(int queueNumber);
    QueueOfCustomer addCustomerToQueue(CustomerModel data);
    QueueOfCustomer removeCustomerFromQueue(int queueNumber);
    List<CollectedParcelModel> getDailyCollectedList(LocalDate date);
    List<CustomerModel> getDailyCustomerList(LocalDate date);
    double getDailyCollectedFees(LocalDate date);
}
