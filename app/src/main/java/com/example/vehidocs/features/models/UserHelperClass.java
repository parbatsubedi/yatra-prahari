package com.example.vehidocs.features.models;

public class UserHelperClass {
    String fullName, email, mobileNumber, license;

    public UserHelperClass(String fullName, String email, String mobileNumber, String license) {
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.license = license;
        //this.bikeNumber = bikeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNumber;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNumber = mobileNo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    /*public String getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(String bikeNumber) {
        this.bikeNumber = bikeNumber;
    }*/

}
