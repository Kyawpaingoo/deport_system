package Controller;

import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.Dtos.LoginDto;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.Dtos.UpdateStatusDto;
import Model.ParcelModel;
import Model.SharedModel.QueueModel;
import Model.StaffModel;
import Services.StaffService.IStaffService;
import Services.StaffService.StaffService;

import java.time.LocalDate;
import java.util.List;

public class StaffController {
    private final IStaffService _iStaffService;
    private QueueModel queueModel;

    public StaffController()
    {
        this._iStaffService = new StaffService();
    }

    public StaffController(QueueModel queueModel)
    {
        this._iStaffService = new StaffService();
        this.queueModel = queueModel;
    }

    public QueueModel getQueueModel() {
        return queueModel;
    }

    public boolean login(LoginDto dto)
    {
        return _iStaffService.login(dto);
    }

    public boolean logout()
    {
        return _iStaffService.logout();
    }

    public StaffModel getLoggedInStaff() {
        return _iStaffService.getLoggedInStaff();
    }

    public ParcelModel updateParcelStatus(UpdateStatusDto dto)
    {
        return _iStaffService.updateParcelStatus(dto);
    }

    public String addParcel(ParcelModel obj)
    {
        return _iStaffService.addParcel(obj);
    }

    public CustomerModel processCustomer(int queueNumber)
    {
        return _iStaffService.processCustomer(queueNumber);
    }

    public void addCustomerToQueue(CustomerModel data)
    {
        queueModel.addCustomer(data);
    }

    public void removeCustomerFromQueue(CustomerModel customer)
    {
        queueModel.removeCustomer(customer);
    }

    public List<CustomerModel> getDailyCustomerList(LocalDate date)
    {
        return _iStaffService.getDailyCustomerList(date);
    }

    public List<CollectedParcelModel> getDailyCollectedParcelList(LocalDate date)
    {
        return _iStaffService.getDailyCollectedList(date);
    }

    public double getDailyCollectedFees(LocalDate date)
    {
        return _iStaffService.getDailyCollectedFees(date);
    }

    public QueueOfCustomer getQueueList()
    {
        QueueOfCustomer queueOfCustomer = new QueueOfCustomer();
        queueOfCustomer.setCustomerList(queueModel.getCustomerList());
        return queueOfCustomer;
    }
}
