package Services.ParcelService;

import Infra.UnitOfWork.UnitOfWork;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ParcelService implements IParcelService {
    private final UnitOfWork _uow;;

    public ParcelService()
    {
        this._uow = new UnitOfWork();
    }

    @Override
    public boolean addParcel(ParcelModel obj) {
        boolean result = _uow._parcelRepository().insert(obj);
        return  result;
    }

    @Override
    public Optional<ParcelModel> getParcelDetail(String parcelID) {
        Optional<ParcelModel> result = _uow._parcelRepository()
                                        .Search(d -> Objects.equals(d.getParcelID(), parcelID));
        return  result;
    }

    @Override
    public boolean removeParcel(int ID) {
        boolean result = _uow._parcelRepository().delete(ID);
        return result;
    }

    @Override
    public Map<Integer, ParcelModel> getCollectedParcel() {
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._parcelRepository().whereAsMap(d -> d.getParcelStatus() == ParcelStatus.Collected);
        return  resultMap;
    }

    @Override
    public Map<Integer, ParcelModel> getUncollectedParcel() {
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._parcelRepository().whereAsMap(d -> d.getParcelStatus() == ParcelStatus.WaitingForCollection);
        return  resultMap;
    }

    @Override
    public Optional<ParcelModel> searchParcel(String q) {
        Optional<ParcelModel> result = _uow._parcelRepository()
                                .Search(d -> Objects.equals(d.getCustomerSurname(), q) ||
                                        Objects.equals(d.getParcelID(), q));
        return result;
    }

    @Override
    public Map<Integer, ParcelModel> sortBySurname(){
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._parcelRepository().sortAsMap(d -> d.getCustomerSurname() != null);
        return  resultMap;
    }
}
