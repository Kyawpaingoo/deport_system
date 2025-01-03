package Services.StaffService;

import Infra.UnitOfWork.UnitOfWork;
import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Model.StaffModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;
import Services.ParcelService.IParcelService;
import Services.ParcelService.ParcelService;

import java.util.Optional;

public class StaffService implements IStaffService{

    private final UnitOfWork _uow;
    private final IParcelService _iparcelService;
    private  final ICustomerService _iCustomerService;

    public StaffService()
    {
        this._uow = new UnitOfWork();
        this._iparcelService = new ParcelService();
        this._iCustomerService = new CustomerService();
    }

    @Override
    public StaffModel login(String userName, String password) {
        return null;
    }

    @Override
    public Boolean logout(String userName, String password) {
        return null;
    }

    @Override
    public ParcelModel updateParcelStatus(String ParcelID, ParcelStatus parcelStatus) {
        ParcelModel result = new ParcelModel();
        Optional<ParcelModel> parcel = _iparcelService.getParcelDetail(ParcelID);
        if(parcel.isPresent())
        {
            parcel.get().setParcelStatus(parcelStatus);
            ParcelModel data = _uow._parcelRepository().update(parcel.get());
            if(data != null)
            {
                result = data;
            }
        }
        return result;
    }

    @Override
    public String addParcel(ParcelModel obj)
    {
        boolean result = _uow._parcelRepository().insert(obj);
        return result ? "Success" : "Fail";
    }

    @Override
    public CustomerModel processCustomer(int queueNumber) {
        Optional<CustomerModel> result = _iCustomerService.getByQueue(queueNumber);
        return result.orElse(null);
    }
}
