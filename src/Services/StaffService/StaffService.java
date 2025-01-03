package Services.StaffService;

import Infra.Extension;
import Infra.UnitOfWork.UnitOfWork;
import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.Dtos.QueueOfCustomer;
import Model.ParcelModel;
import Model.StaffModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;
import Services.ParcelService.IParcelService;
import Services.ParcelService.ParcelService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StaffService implements IStaffService
{
    private final UnitOfWork _uow;
    private final IParcelService _iparcelService;
    private final ICustomerService _iCustomerService;
    private final  QueueOfCustomer queueList;
    private final LocalDate now;
    private StaffModel loggedInStaff = null;

    public StaffService()
    {
        this._uow = new UnitOfWork();
        this._iparcelService = new ParcelService();
        this._iCustomerService = new CustomerService();
        this.queueList = new QueueOfCustomer();
        this.now = Extension.getLocalTime();
    }

    @Override
    public Boolean login(String userName, String password) {
        Optional<StaffModel> staff = _uow._staffRepository().Search(s -> s.UserName.equals(userName));
        if(staff.isPresent() && staff.get().getPassword().equals(password))
        {
            loggedInStaff = staff.get();
            return true;
        }

        return false;
    }

    @Override
    public Boolean logout(String userName, String password) {
        loggedInStaff = null;
        return true;
    }

    @Override
    public ParcelModel updateParcelStatus(String ParcelID, ParcelStatus parcelStatus, double discount, double totalFee) {
        ParcelModel result = new ParcelModel();
        Optional<ParcelModel> parcel = _iparcelService.getParcelDetail(ParcelID);
        if(parcel.isPresent())
        {
            ParcelModel parcelModel = parcel.get();

            parcelModel.setParcelStatus(parcelStatus);
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
                    discount,
                    totalFee
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
    public QueueOfCustomer addCustomerToQueue(CustomerModel data) {
        queueList.getCustomerList().add(data);
        return  queueList;
    }

    @Override
    public QueueOfCustomer removeCustomerFromQueue(int queueNumber) {
        queueList.CustomerList.removeIf(d -> d.getQueueNumber() == queueNumber);
        return queueList;
    }

    @Override
    public List<CollectedParcelModel> getDailyCollectedList(LocalDate date) {
        List<CollectedParcelModel> resultList = new ArrayList<>();
        if(loggedInStaff != null)
        {
            resultList = _uow._collectedParcelRepository().whereAsList(d -> d.getCollectedDate().equals(date));
        }

        return  resultList;
    }

    @Override
    public List<CustomerModel> getDailyCustomerList(LocalDate date) {
        List<CustomerModel> customerList = new ArrayList<>();
        if(loggedInStaff != null)
        {
            List<String> parcelIDList = _uow._collectedParcelRepository()
                    .whereAsList(d -> d.getCollectedDate().equals(date))
                    .stream().map(CollectedParcelModel::getParcelID)
                    .toList();

            customerList = _uow._customerRepository().getList().stream()
                    .filter(customer -> parcelIDList.contains(customer.getParcelID()))
                    .toList();
        }

        return customerList;
    }

    @Override
    public double getDailyCollectedFees(LocalDate date) {
        double totalFees = 0;
        if(loggedInStaff != null)
        {
            totalFees = _uow._collectedParcelRepository()
                    .whereAsList(d -> d.getCollectedDate().equals(date))
                    .stream().mapToDouble(CollectedParcelModel:: getTotalFee)
                    .sum();
        }

        return  totalFees;
    }
}
