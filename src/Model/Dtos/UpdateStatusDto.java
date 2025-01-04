package Model.Dtos;

public class UpdateStatusDto {
    private String parcelId;
    private ParcelStatus parcelStatus;
    private double discount;
    private double totalFee;

    public UpdateStatusDto() {
    }

    public UpdateStatusDto(String parcelId, ParcelStatus parcelStatus, double discount, double totalFee) {
        this.parcelId = parcelId;
        this.parcelStatus = parcelStatus;
        this.discount = discount;
        this.totalFee = totalFee;
    }

    public String getParcelId() {
        return parcelId;
    }

    public void setParcelId(String parcelId) {
        this.parcelId = parcelId;
    }

    public ParcelStatus getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(ParcelStatus parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }
}
