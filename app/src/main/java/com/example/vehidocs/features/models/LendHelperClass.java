package com.example.vehidocs.features.models;

public class LendHelperClass {
    String lendName, mobileNumber, licenseNo, lendStatus;

    public LendHelperClass(String lendName, String mobileNumber, String licenseNo, String lendStatus) {
        this.lendName = lendName;
        this.mobileNumber = mobileNumber;
        this.licenseNo = licenseNo;
        this.lendStatus = lendStatus;
    }

    public String getFullName() {
        return lendName;
    }

    public void setFullName(String fullName) {
        this.lendName = fullName;
    }

    public String getMobileNo() {
        return mobileNumber;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNumber = mobileNo;
    }

    public String getLicense() {
        return licenseNo;
    }

    public void setLicense(String license) {
        this.licenseNo = license;
    }

    public String getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(String lendStatus) {
        this.lendStatus = lendStatus;
    }
}
