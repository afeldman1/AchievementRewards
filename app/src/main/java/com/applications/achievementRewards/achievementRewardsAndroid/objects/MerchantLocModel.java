package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class MerchantLocModel implements Parcelable {
    private int merchantLocId;
    private double lat;
    private double lon;
    private String address;
    private int phoneNum;
    private double rad;

    public MerchantLocModel() {
    }

    public int getMerchantLocId() {
        return merchantLocId;
    }

    public void setMerchantLocId(int merchantLocId) {
        this.merchantLocId = merchantLocId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(merchantLocId);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(address);
        dest.writeInt(phoneNum);
        dest.writeDouble(rad);
    }

    protected MerchantLocModel(Parcel in) {
        merchantLocId = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        address = in.readString();
        phoneNum = in.readInt();
        rad = in.readDouble();
    }

    public static final Creator<MerchantLocModel> CREATOR = new Creator<MerchantLocModel>() {
        @Override
        public MerchantLocModel createFromParcel(Parcel in) {
            return new MerchantLocModel(in);
        }

        @Override
        public MerchantLocModel[] newArray(int size) {
            return new MerchantLocModel[size];
        }
    };
}
