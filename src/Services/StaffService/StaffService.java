package Services.StaffService;

import Infra.Extension;
import Infra.UnitOfWork.UnitOfWork;
import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.Dtos.LoginDto;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.Dtos.UpdateStatusDto;
import Model.ParcelModel;
import Model.SharedModel.QueueModel;
import Model.StaffModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;
import Services.ParcelService.IParcelService;
import Services.ParcelService.ParcelService;
import Services.QueueObserver;
import View.CustomerQueueList;
import Services.QueueObserver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StaffService implements IStaffService
{
    private final UnitOfWork _uow;
    private final IParcelService _iparcelService;
    private final ICustomerService _iCustomerService;
    private final LocalDate now;
    private StaffModel loggedInStaff = null;

    public StaffService()
    {
        this._uow = new UnitOfWork();
        this._iparcelService = new ParcelService();
        this._iCustomerService = new CustomerService();
        this.now = Extension.getLocalTime();
    }

    @Override
    public Boolean login(LoginDto dto) {
        Optional<StaffModel> staff = _uow._staffRepository().Search(s -> s.UserName.equals(dto.getUserName()));
        if(staff.isPresent() && staff.get().getPassword().equals(dto.getPassword()))
        {
            loggedInStaff = staff.get();
            return true;
        }

        return false;
    }

    @Override
    public Boolean logout() {
        loggedInStaff = null;
        return true;
    }

    @Override
    public StaffModel getLoggedInStaff() {
        System.out.println(loggedInStaff);
        return loggedInStaff;
    }

    @Override
    public ParcelModel updateParcelStatus(UpdateStatusDto dto) {
        ParcelModel result = new ParcelModel();
        Optional<ParcelModel> parcel = _iparcelService.getParcelDetail(dto.getParcelId());
        if(parcel.isPresent())
        {
            ParcelModel parcelModel = parcel.get();

            parcelModel.setParcelStatus(dto.getParcelStatus());
            parcelModel.setCollectedDate(now);

            long daysInDepot = java.time.Duration.between(
                    parcelModel.getReceivedDate().atStartOfDay(),
                    parcelModel.getCollectedDate().atStartOfDay()
            ).toDays();
            parcelModel.setDaysInDepot((int) daysInDepot);

            ParcelModel data = _uow._parcelRepository().update(parcelModel);

            CollectedParcelModel collectedParcel = new CollectedParcelModel(
                    data.getNo(),
                    data.getParcelID(),
                    data.getDaysInDepot(),
                    data.getWeight(),
                    data.getDimensions(),
                    data.getParcelStatus(),
                    data.getReceivedDate(),
                    data.getCollectedDate(),
                    data.getCustomerSurname(),
                    dto.getDiscount(),
                    dto.getTotalFee()
            );

            _uow._collectedParcelRepository().insert(collectedParcel);

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
        obj.setReceivedDate(now);
        boolean result = _uow._parcelRepository().insert(obj);
        return result ? "Success" : "Fail";
    }

    @Override
    public CustomerModel processCustomer(int queueNumber) {
        Optional<CustomerModel> result = _iCustomerService.getByQueue(queueNumber);
        return result.orElse(null);
    }

    @Override
    public List<CollectedParcelModel> getDailyCollectedList(LocalDate date) {
        List<CollectedParcelModel> resultList = new ArrayList<>();

        resultList = _uow._collectedParcelRepository().whereAsList(d -> d.getCollectedDate().equals(date));


        return resultList;
    }

    @Override
    public List<CustomerModel> getDailyCustomerList(LocalDate date) {
        List<CustomerModel> customerList = new ArrayList<>();

            List<String> parcelIDList = _uow._collectedParcelRepository()
                    .whereAsList(d -> d.getCollectedDate().equals(date))
                    .stream().map(CollectedParcelModel::getParcelID)
                    .toList();

            customerList = _uow._customerRepository().getList().stream()
                    .filter(customer -> parcelIDList.contains(customer.getParcelID()))
                    .toList();


        return customerList;
    }

    @Override
    public double getDailyCollectedFees(LocalDate date) {
        double totalFees = 0;

            totalFees = _uow._collectedParcelRepository()
                    .whereAsList(d -> d.getCollectedDate().equals(date))
                    .stream().mapToDouble(CollectedParcelModel:: getTotalFee)
                    .sum();


        return totalFees;
    }
}
