package Services.ParcelService;

import Model.Dtos.ParcelStatus;
import Model.ParcelModel;

import java.util.Map;
import java.util.Optional;

public interface IParcelService {
    boolean addParcel(ParcelModel obj);
    Optional<ParcelModel> getParcelDetail(String parcelID);
    boolean removeParcel(int ID);
    Map<Integer, ParcelModel> getCollectedParcel();
    Map<Integer, ParcelModel> getUncollectedParcel();
    Optional<ParcelModel> searchParcel(String q);
    Map<Integer, ParcelModel> sortBySurname();
}
