package Services.ParcelService;

import Infra.Extension;
import Infra.UnitOfWork.UnitOfWork;
import Logger.Logger;
import Model.CollectedParcelModel;
import Model.CustomerModel;
import Model.Dtos.ParcelStatus;
import Model.ParcelModel;
import Services.CustomerService.CustomerService;
import Services.CustomerService.ICustomerService;

import java.time.LocalDate;
import java.util.*;

public class ParcelService implements IParcelService {
    private final UnitOfWork _uow;;
    private final LocalDate now;

    public ParcelService()
    {
        this._uow = new UnitOfWork();
        this.now = Extension.getLocalTime();
    }

    @Override
    public boolean addParcel(ParcelModel obj) {
        obj.setReceivedDate(now);
        boolean result = _uow._parcelRepository().insert(obj);
        Logger.getInstance().log("Parcel added: " + obj.toString());
        return  result;
    }

    @Override
    public Optional<ParcelModel> getParcelDetail(String parcelID) {
        Optional<ParcelModel> result = _uow._parcelRepository()
                                        .FirstOrDefault(d -> Objects.equals(d.getParcelID(), parcelID));
        return result;
    }

    @Override
    public boolean removeParcel(int ID) {
        ParcelModel obj = _uow._parcelRepository().getById(ID).get();
        Logger.getInstance().log("Parcel added: " + obj.toString());
        boolean result = _uow._parcelRepository().delete(ID);
        return result;
    }

    @Override
    public Map<Integer, CollectedParcelModel> getCollectedParcel() {
        Map<Integer, CollectedParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._collectedParcelRepository().whereAsMap(d -> d.getParcelStatus() == ParcelStatus.Collected);
        return  resultMap;
    }

    @Override
    public Map<Integer, ParcelModel> getUncollectedParcel() {
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._parcelRepository().whereAsMap(d -> d.getParcelStatus() == ParcelStatus.WaitingForCollection);
        return  resultMap;
    }

    @Override
    public Map<Integer,ParcelModel> searchParcel(String q) {
        List<ParcelModel> result = _uow._parcelRepository()
                .Search(d -> Objects.equals(d.getCustomerSurname(), q) ||
                        Objects.equals(d.getParcelID(), q));
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        for (ParcelModel parcel : result) {
            resultMap.put(parcel.getNo(), parcel);
        }
        return resultMap;
    }

    @Override
    public Map<Integer, ParcelModel> sortBySurname(){
        Map<Integer, ParcelModel> resultMap = new HashMap<>();
        resultMap = _uow._parcelRepository().sortAsMap(d -> d.getCustomerSurname() != null);
        return  resultMap;
    }

    @Override
    public Double calculateParcelFee(String dimensions, double weight, int days, double discount) {
        double[] dimensionList = ConvertDimensionToDouble(dimensions);

        double length = dimensionList[0];
        double width = dimensionList[1];
        double height = dimensionList[2];

        double volumeRate = 0.01;
        double weightRate = 0.05;
        double dailyRate = 1.1;

        double volume = length * width * height;

        double baseFee = (volume * volumeRate) + (weight * weightRate);

        double feeWithDays = baseFee * Math.pow(dailyRate, days);

        double totalFee = feeWithDays - discount;

        if (totalFee < 0) {
            totalFee = 0;
        }

        return totalFee;
    }

    double[] ConvertDimensionToDouble(String dimensions)
    {
        String[] parts = dimensions.split("x");

        double length = Double.parseDouble(parts[0]);
        double width = Double.parseDouble(parts[1]);
        double height = Double.parseDouble(parts[2]);

        return new double[]{length, width, height};
    }
}
