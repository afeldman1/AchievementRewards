package com.applications.achievementRewards.achievementRewardsAndroid.objects;

public class MerchantLocModel {
    private int merchantLocId;
    private int merchantId;
    private double lat;
    private double lon;
    private String address;
    private int phoneNum;
    private double rad;

    public int getMerchantLocId() {
        return merchantLocId;
    }

    public void setMerchantLocId(int merchantLocId) {
        this.merchantLocId = merchantLocId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }
}
