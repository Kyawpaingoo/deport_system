package Controller;

import Model.CollectedParcelModel;
import Model.ParcelModel;
import Services.ParcelService.IParcelService;
import Services.ParcelService.ParcelService;

import java.util.Map;
import java.util.Optional;

public class ParcelController {
    private final IParcelService _parcelService;

    public ParcelController()
    {
        this._parcelService = new ParcelService();
    }

    public boolean addParcel(ParcelModel obj)
    {
        return _parcelService.addParcel(obj);
    }

    public Optional<ParcelModel> getParcelDetail(String parcelID)
    {
        return _parcelService.getParcelDetail(parcelID);
    }

    public boolean removeParcel(int ID)
    {
        return _parcelService.removeParcel(ID);
    }

    public Map<Integer, CollectedParcelModel> getCollectedParcel()
    {
        return  _parcelService.getCollectedParcel();
    }

    public Map<Integer, ParcelModel> getUncollectedParcel()
    {
        return _parcelService.getUncollectedParcel();
    }

    public Optional<ParcelModel> searchParcel(String q)
    {
        return _parcelService.searchParcel(q);
    }

    public Map<Integer, ParcelModel> sortBySurname()
    {
        return _parcelService.sortBySurname();
    }

    public double calculatedParcelFee(String dimensions, double weight, int days, double discount)
    {
        return _parcelService.calculateParcelFee(dimensions, weight, days, discount);
    }

}
