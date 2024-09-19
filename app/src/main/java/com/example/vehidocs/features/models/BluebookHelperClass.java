package com.example.vehidocs.features.models;

public class BluebookHelperClass {
    String fullVehicleNo;
    String mobileNumber;
    String FilePathUri;


    public BluebookHelperClass(String fullVehicleNo, String mobileNumber, String FilePathUri, String FilePathUri1) {
        this.fullVehicleNo = fullVehicleNo;
        this.mobileNumber = mobileNumber;
        this.FilePathUri = FilePathUri;
    }

    public BluebookHelperClass(String fullVehicleNo, String mobileNumber, String filePathUri) {
    }

    public String getVehicleNo() {
        return fullVehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.fullVehicleNo = fullVehicleNo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFilePathUri() {
        return FilePathUri;
    }

    public void setFilePathUri(String filePathUri) {
        FilePathUri = filePathUri;
    }
}
