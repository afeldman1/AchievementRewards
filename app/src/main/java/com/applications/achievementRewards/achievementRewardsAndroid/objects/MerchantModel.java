package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MerchantModel implements Parcelable {
    private int merchantId;
    private String merchantName;
    private String merchantDescription;
    private URL logoUrl;
    private List<MerchantLocModel> merchantLocModels = new ArrayList<>();
    private List<UserAchievementModel> userAchievementModels = new ArrayList<>();

    public MerchantModel() {
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantDescription() {
        return merchantDescription;
    }

    public void setMerchantDescription(String merchantDescription) {
        this.merchantDescription = merchantDescription;
    }

    public URL getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(URL logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<MerchantLocModel> getMerchantLocModels() {
        return merchantLocModels;
    }

    public void setMerchantLocModels(List<MerchantLocModel> merchantLocModels) {
        this.merchantLocModels = merchantLocModels;
    }

    public void addMerchantLocModel(MerchantLocModel merchantLocModel) {
        this.merchantLocModels.add(merchantLocModel);
    }

    public List<UserAchievementModel> getUserAchievementModels() {
        return userAchievementModels;
    }

    public void setUserAchievementModels(List<UserAchievementModel> userAchievementModels) {
        this.userAchievementModels = userAchievementModels;
    }

    public void addUserAchievementModel(UserAchievementModel userAchievementModel) {
        this.userAchievementModels.add(userAchievementModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(merchantId);
        dest.writeValue(merchantName);
        dest.writeValue(merchantDescription);
        dest.writeValue(logoUrl == null ? null : logoUrl.toString());
        dest.writeValue(merchantLocModels);
        dest.writeValue(userAchievementModels);
    }

    protected MerchantModel(Parcel in) {
        merchantId = in.readInt();
        merchantName = in.readString();
        merchantDescription = in.readString();
        try {
            String url = (String) in.readValue(String.class.getClassLoader());
            this.logoUrl = url == null ? null : new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        merchantLocModels = in.createTypedArrayList(MerchantLocModel.CREATOR);
        userAchievementModels = in.createTypedArrayList(UserAchievementModel.CREATOR);
    }

    public static final Creator<MerchantModel> CREATOR = new Creator<MerchantModel>() {
        @Override
        public MerchantModel createFromParcel(Parcel in) {
            return new MerchantModel(in);
        }

        @Override
        public MerchantModel[] newArray(int size) {
            return new MerchantModel[size];
        }
    };
}
