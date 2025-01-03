package Model.Dtos;

import Model.ParcelModel;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    public Map<Integer, ParcelModel> ParcelMap;

    public ParcelMap()
    {
        this.ParcelMap = new HashMap<>();
    }

    public Map<Integer, ParcelModel> getPercelMap()
    {
        return ParcelMap;
    }

    public void setParcelMap(Map<Integer, ParcelModel> parcelMap)
    {
        this.ParcelMap = parcelMap;
    }

    @Override
    public String toString() {
        return "ParcelMap{" +
                "ParcelMap" + ParcelMap +
                "}";
    }
}

