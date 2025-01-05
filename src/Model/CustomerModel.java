package Model;

import java.util.Collection;
import java.util.Objects;

public class CustomerModel implements CSVParsable<CustomerModel> {
    private int QueueNumber;
    private String FirstName;
    private String SurName;
    private String ParcelID;

    public CustomerModel()
    {

    }

    public CustomerModel(int queueNumber, String firstName, String surName, String parcelID)
    {
        this.QueueNumber = queueNumber;
        this.FirstName = firstName;
        this.SurName = surName;
        this.ParcelID = parcelID;
    }

    public int getQueueNumber()
    {
        return QueueNumber;
    }

    public void setQueueNumber(int queueNumber)
    {
        this.QueueNumber = queueNumber;
    }

    public String getFirstName()
    {
        return  FirstName;
    }

    public void setFirstName(String firstName)
    {
        this.FirstName = firstName;
    }

    public  String getSurName()
    {
        return SurName;
    }

    public void setSurName(String surName)
    {
        this.SurName = surName;
    }

    public String getParcelID()
    {
        return ParcelID;
    }

    public void setParcelID(String parcelID)
    {
        this.ParcelID = parcelID;
    }

    @Override
    public String toString()
    {
        return  "{" +
                "QueueNumber=" + QueueNumber + ',' +
                "FirstName=" + FirstName + ',' +
                "SurName=" + SurName + ',' +
                "ParcelID=" + ParcelID +
                '}';

    }

    @Override
    public CustomerModel parseFromCSV(String[] values) {
        return new CustomerModel(
                Integer.parseInt(values[0]),
                values[1],
                values[2],
                values[3]
        );
    }

    @Override
    public Integer getId() {
        return QueueNumber;
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{
                String.valueOf(QueueNumber),
                FirstName,
                SurName,
                ParcelID};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return QueueNumber == that.QueueNumber &&
                Objects.equals(FirstName, that.FirstName) &&
                Objects.equals(SurName, that.SurName) &&
                Objects.equals(ParcelID, that.ParcelID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(QueueNumber, FirstName, SurName, ParcelID);
    }
}
