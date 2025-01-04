package Controller;

import Model.CustomerModel;
import Model.Dtos.LoginDto;
import Model.Dtos.ParcelStatus;
import Model.Dtos.UpdateStatusDto;
import Model.ParcelModel;
import Services.StaffService.IStaffService;
import Services.StaffService.StaffService;

import java.time.LocalDate;

public class StaffController {
    private final IStaffService _iStaffService;

    public StaffController()
    {
        this._iStaffService = new StaffService();
    }

    public boolean login(LoginDto dto)
    {
        return _iStaffService.login(dto);
    }

    public void logout(LoginDto dto)
    {
        _iStaffService.logout(dto);
    }

    public void updateParcelStatus(UpdateStatusDto dto)
    {
        _iStaffService.updateParcelStatus(dto);
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

    public void getDailyCustomerList(LocalDate date)
    {
        _iStaffService.getDailyCustomerList(date);
    }

    public void getDailyCollectedParcelList(LocalDate date)
    {
        _iStaffService.getDailyCollectedList(date);
    }

    public void getDailyCollectedFees(LocalDate date)
    {
        _iStaffService.getDailyCollectedFees(date);
    }
}
