package com.example.vehidocs.features.models;

public class ImageUploadInfo {
    String fullVehicleNo;
    String mobileNumber;
    String vehicleType;
    public String imageURL;
    public String imageURL1;
    String LendTo;
    String LendToMobileNo;
    String LendToLicenseNumber;
    String LendStatus;
    String TheftStatus;
    String TaxStatus;
    String TaxRenewedDate;
    String TaxExpireDate;
    String RequestStatus;

    public ImageUploadInfo(String fullVehicleNo, String mobileNumber, String vehicleType, String imageURL, String imageURL1, String lendTo, String lendToMobileNo, String lendToLicenseNumber, String lendStatus, String theftStatus, String taxStatus, String taxRenewedDate, String taxExpireDate, String requestStatus) {
        this.fullVehicleNo = fullVehicleNo;
        this.mobileNumber = mobileNumber;
        this.vehicleType = vehicleType;
        this.imageURL = imageURL;
        this.imageURL1 = imageURL1;
        this.LendTo = lendTo;
        this.LendToMobileNo = lendToMobileNo;
        this.LendToLicenseNumber = lendToLicenseNumber;
        this.LendStatus = lendStatus;
        this.TheftStatus = theftStatus;
        this.TaxStatus = taxStatus;
        this.TaxRenewedDate = taxRenewedDate;
        this.TaxExpireDate = taxExpireDate;
        this.RequestStatus = requestStatus;
    }


    public String getVehicleNo() {
        return fullVehicleNo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageURL1() {
        return imageURL1;
    }

    public String getLendTo() {
        return LendTo;
    }

    public String getLendToMobileNo() {
        return LendToMobileNo;
    }

    public String getLendToLicenseNumber() {
        return LendToLicenseNumber;
    }

    public String getLendStatus() {
        return LendStatus;
    }

    public String getTheftStatus() {
        return TheftStatus;
    }

    public String getTaxStatus() {
        return TaxStatus;
    }

    public String getTaxRenewedDate() {
        return TaxRenewedDate;
    }

    public String getTaxExpireDate() {
        return TaxExpireDate;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }
}
