package Controller;

import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Services.StaffService.IStaffService;
import Services.StaffService.StaffService;

public class StaffController {
    private final IStaffService _iStaffService;

    public StaffController()
    {
        this._iStaffService = new StaffService();
    }

    public void login(String userName, String password)
    {
        _iStaffService.login(userName, password);
    }

    public void logout(String userName, String password)
    {
        _iStaffService.logout(userName, password);
    }

    public void updateParcelStatus(String ParcelID, ParcelStatus parcelStatus)
    {
        _iStaffService.updateParcelStatus(ParcelID, parcelStatus);
    }

    public void addParcel(ParcelModel obj)
    {
        _iStaffService.addParcel(obj);
    }

    public void processCustomer(int queueNumber)
    {
        _iStaffService.processCustomer(queueNumber);
    }

    public void addCustomerToQueue(CustomerModel data)
    {
        _iStaffService.addCustomerToQueue(data);
    }

    public void removeCustomerFromQueue(int queueNumber)
    {
        _iStaffService.removeCustomerFromQueue(queueNumber);
    }
}
