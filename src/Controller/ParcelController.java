package Controller;

import Model.ParcelModel;
import Services.ParcelService.IParcelService;
import Services.ParcelService.ParcelService;

public class ParcelController {
    private final IParcelService _parcelService;

    public ParcelController()
    {
        this._parcelService = new ParcelService();
    }

    public void addParcel(ParcelModel obj)
    {
        _parcelService.addParcel(obj);
    }

    public void getParcelDetail(String parcelID)
    {
        _parcelService.getParcelDetail(parcelID);
    }

    public void removeParcel(int ID)
    {
        _parcelService.removeParcel(ID);
    }

    public void getCollectedParcel()
    {
        _parcelService.getCollectedParcel();
    }

    public void getUncollectedParcel()
    {
        _parcelService.getUncollectedParcel();
    }

    public void searchParcel(String q)
    {
        _parcelService.searchParcel(q);
    }

    public void sortBySurname()
    {
        _parcelService.sortBySurname();
    }

    public void calculatedParcelFee(String dimensions, double weight, int days, double discount)
    {
        _parcelService.calculateParcelFee(dimensions, weight, days, discount);
    }

}
