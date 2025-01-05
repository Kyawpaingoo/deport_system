package Model;

import Model.Dtos.ParcelStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CollectedParcelModel implements CSVParsable<CollectedParcelModel> {
    private  int No;
    private String ParcelID;
    private int DaysInDepot;
    private double Weight;
    private String Dimensions;
    private ParcelStatus ParcelStatus;
    private LocalDate ReceivedDate;
    private LocalDate CollectedDate;
    private String CustomerSurname;
    private double Discount;
    private double TotalFee;

    public CollectedParcelModel(int no, String parcelID, int daysInDepot, double weight, String dimensions,
                       ParcelStatus parcelStatus, LocalDate receivedDate, LocalDate collectedDate, String customerSurname,
                       double discount, double totalFee)
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
        this.Discount = discount;
        this.TotalFee = totalFee;
    }

    public CollectedParcelModel()
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

    public double getDiscount()
    {
        return Discount;
    }

    public void setDiscount(double discount)
    {
        this.Discount = discount;
    }

    public double getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(double totalFee) {
        this.TotalFee = totalFee;
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String toString() {
        return String.format(String.format("{" +
                "No=" + No + "," +
                "ParcelID=" + ParcelID + ',' +
                "DaysInDepot=" + DaysInDepot + ',' +
                "Weight=" + Weight + ',' +
                "Dimensions=" + Dimensions + ',' +
                "ParcelStatus=" + ParcelStatus + ',' +
                "ReceivedDate=" + DATE_FORMAT.format(ReceivedDate)  + ',' +
                "CollectedDate=" +  DATE_FORMAT.format(CollectedDate) + ',' +
                "CustomerSurname=" + CustomerSurname + ',' +
                "Discount=" + Discount + ',' +
                "TotalFee=" + TotalFee + ',' +
                '}'));
    }

    @Override
    public CollectedParcelModel parseFromCSV(String[] values) {
        LocalDate receivedDate = LocalDate.parse(values[6], DATE_FORMAT);
        LocalDate collectedDate = LocalDate.parse(values[7], DATE_FORMAT);

        return new CollectedParcelModel(
                Integer.parseInt(values[0]),
                values[1],
                Integer.parseInt(values[2]),
                Double.parseDouble(values[3]),
                values[4],
                ParcelStatus.valueOf(values[5]),
                receivedDate,
                collectedDate,
                values[8],
                Double.parseDouble(values[9]),
                Double.parseDouble(values[10])
        );
    }

    @Override
    public String[] toCSVRow() {
        return new String[] {
                String.valueOf(No),
                ParcelID,
                String.valueOf(DaysInDepot),
                String.valueOf(Weight),
                Dimensions,
                ParcelStatus.name(),
                DATE_FORMAT.format(ReceivedDate),
                DATE_FORMAT.format(CollectedDate),
                CustomerSurname,
                String.valueOf(Discount),
                String.valueOf(TotalFee)
        };
    }

    @Override
    public Integer getId() {
        return No;
    }
}
