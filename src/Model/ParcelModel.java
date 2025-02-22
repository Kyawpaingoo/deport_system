package Model;
import Model.Dtos.ParcelStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ParcelModel implements CSVParsable<ParcelModel> {
    private  int No;
    private String ParcelID;
    private int DaysInDepot;
    private double Weight;
    private String Dimensions;
    private ParcelStatus ParcelStatus;
    private LocalDate ReceivedDate;
    private LocalDate CollectedDate;
    private String CustomerSurname;

    public ParcelModel(int no, String parcelID, int daysInDepot, double weight, String dimensions,
                       ParcelStatus parcelStatus, LocalDate receivedDate, LocalDate collectedDate, String customerSurname)
    {
        this.No = no;
        this.ParcelID = parcelID;
        this.DaysInDepot = daysInDepot;
        this.Weight = weight;
        this.Dimensions = dimensions;
        this.ParcelStatus = parcelStatus;
        this.ReceivedDate = receivedDate;
        this.CollectedDate = collectedDate;
        this.CustomerSurname = customerSurname;
    }

    public ParcelModel()
    {

    }

    public int getNo()
    {
        return No;
    }

    public void setNo(int no)
    {
        this.No = no;
    }
    public String getParcelID()
    {
        return ParcelID;
    }

    public void setParcelID(String parcelID)
    {
        this.ParcelID = parcelID;
    }

    public int getDaysInDepot()
    {
        return DaysInDepot;
    }

    public void setDaysInDepot(int daysInDepot)
    {
        this.DaysInDepot = daysInDepot;
    }

    public double getWeight()
    {
        return Weight;
    }

    public void setWeight(double weight)
    {
        this.Weight = weight;
    }

    public String getDimensions()
    {
        return Dimensions;
    }

    public void setDimensions(String dimensions)
    {
        this.Dimensions = dimensions;
    }

    public ParcelStatus getParcelStatus()
    {
        return ParcelStatus;
    }

    public void setParcelStatus(ParcelStatus parcelStatus)
    {
        this.ParcelStatus = parcelStatus;
    }

    public LocalDate getReceivedDate()
    {
        return ReceivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate)
    {
        this.ReceivedDate = receivedDate;
    }

    public LocalDate getCollectedDate()
    {
        return CollectedDate;
    }

    public void setCollectedDate(LocalDate collectedDate)
    {
        this.CollectedDate = collectedDate;
    }

    public String getCustomerSurname()
    {
        return CustomerSurname;
    }

    public void setCustomerSurname(String customerSurname)
    {
        this.CustomerSurname = customerSurname;
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public ParcelModel parseFromCSV(String[] values) {
        LocalDate receivedDate = LocalDate.parse(values[6], DATE_FORMAT);
        LocalDate collectedDate = "NULL".equals(values[7]) ? null : LocalDate.parse(values[7], DATE_FORMAT);

        return new ParcelModel(
                Integer.parseInt(values[0]),
                values[1],
                Integer.parseInt(values[2]),
                Double.parseDouble(values[3]),
                values[4],
                ParcelStatus.valueOf(values[5]),
                receivedDate,
                collectedDate,
                values[8]
        );
    }

    @Override
    public Integer getId()
    {
        return  No;
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{
                String.valueOf(No),
                ParcelID,
                String.valueOf(DaysInDepot),
                String.valueOf(Weight),
                Dimensions,
                ParcelStatus.name(),
                DATE_FORMAT.format(ReceivedDate),
                CollectedDate != null ? DATE_FORMAT.format(CollectedDate) : "NULL",
                CustomerSurname
        };
    }

    @Override
    public String toString() {
        return String.format("{" +
                "No=" + No + "," +
                "ParcelID=" + ParcelID + ',' +
                "DaysInDepot=" + DaysInDepot + ',' +
                "Weight=" + Weight + ',' +
                "Dimensions=" + Dimensions + ',' +
                "ParcelStatus=" + ParcelStatus + ',' +
                "ReceivedDate=" + DATE_FORMAT.format(ReceivedDate)  + ',' +
                "CollectedDate=" +  (CollectedDate != null ? DATE_FORMAT.format(CollectedDate) : "N/A") + ',' +
                "CustomerSurname=" + CustomerSurname +
                '}');
    }
}


