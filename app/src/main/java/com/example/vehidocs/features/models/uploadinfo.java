package com.example.vehidocs.features.models;

public class uploadinfo {

    public String vehicleNo;
    public String renewDate;
    public String expireDate;
    public String imageURLOne;
    public String imageURLTwo;

    public uploadinfo(String vehicleNo, String renewDate, String expireDate, String imageURLOne, String imageURLTwo) {
        this.vehicleNo = vehicleNo;
        this.renewDate = renewDate;
        this.expireDate = expireDate;
        this.imageURLOne = imageURLOne;
        this.imageURLTwo = imageURLTwo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(String renewDate) {
        this.renewDate = renewDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getImageURLOne() {
        return imageURLOne;
    }

    public void setImageURLOne(String imageURLOne) {
        this.imageURLOne = imageURLOne;
    }

    public String getImageURLTwo() {
        return imageURLTwo;
    }

    public void setImageURLTwo(String imageURLTwo) {
        this.imageURLTwo = imageURLTwo;
    }
}
