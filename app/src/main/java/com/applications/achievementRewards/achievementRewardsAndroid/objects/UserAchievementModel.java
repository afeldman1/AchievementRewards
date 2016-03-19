package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class UserAchievementModel implements Parcelable {
    private int userAchievementId;
    private String achievementName;
    private String achievementDescription;
    private String rewardName;
    private String rewardDescription;
    private String merchantName;
    private String merchantDescription;
    private URL logoUrl;
    private double progress;
    private int trackingMax;
    private Date redeemedAt;

    public UserAchievementModel(int userAchievementId, String achievementName, String achievementDescription, String rewardName, String rewardDescription, String merchantName, String merchantDescription, URL logoUrl, double progress, int trackingMax, Date redeemedAt) {
        this.userAchievementId = userAchievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.merchantName = merchantName;
        this.merchantDescription = merchantDescription;
        this.logoUrl = logoUrl;
        this.progress = progress;
        this.trackingMax = trackingMax;
        this.redeemedAt = redeemedAt;
    }

    public UserAchievementModel(int userAchievementId, String achievementName, String merchantName, double progress, int trackingMax) {
        this.userAchievementId = userAchievementId;
        this.achievementName = achievementName;
        this.merchantName = merchantName;
        this.progress = progress;
        this.trackingMax = trackingMax;
    }

    public UserAchievementModel() {
    }

    public int getUserAchievementId() {
        return userAchievementId;
    }

    public void setUserAchievementId(int userAchievementId) {
        this.userAchievementId = userAchievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getTrackingMax() {
        return trackingMax;
    }

    public void setTrackingMax(int trackingMax) {
        this.trackingMax = trackingMax;
    }

    public Date getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(Date redeemedAt) {
        this.redeemedAt = redeemedAt;
    }

    /** Used to give additional hints on how to process the received parcel.*/
    @Override
    public int describeContents() {
        // ignore for now
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeInt(userAchievementId);
        pc.writeValue(achievementName);
        pc.writeValue(achievementDescription);
        pc.writeValue(rewardName);
        pc.writeValue(rewardDescription);
        pc.writeValue(merchantName);
        pc.writeValue(merchantDescription);
        pc.writeValue(logoUrl == null ? null : logoUrl.toString());
        pc.writeValue(progress);
        pc.writeValue(trackingMax);
        pc.writeValue(redeemedAt == null ? null : redeemedAt.getTime());
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<UserAchievementModel> CREATOR = new Parcelable.Creator<UserAchievementModel>() {
        public UserAchievementModel createFromParcel(Parcel pc) {
            return new UserAchievementModel(pc);
        }
        public UserAchievementModel[] newArray(int size) {
            return new UserAchievementModel[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public UserAchievementModel(Parcel pc){
        this.userAchievementId =  pc.readInt();
        this.achievementName = (String) pc.readValue(String.class.getClassLoader());
        this.achievementDescription = (String) pc.readValue(String.class.getClassLoader());
        this.rewardName = (String) pc.readValue(String.class.getClassLoader());
        this.rewardDescription = (String) pc.readValue(String.class.getClassLoader());
        this.merchantName = (String) pc.readValue(String.class.getClassLoader());
        this.merchantDescription = (String) pc.readValue(String.class.getClassLoader());
        try {
            String url = (String) pc.readValue(String.class.getClassLoader());
            this.logoUrl = url == null ? null : new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.progress = (double) pc.readValue(Double.class.getClassLoader());
        this.trackingMax = (int) pc.readValue(Integer.class.getClassLoader());
        Long t = (Long) pc.readValue(Long.class.getClassLoader());
        this.redeemedAt = t == null ? null : new Date(t);
    }
}
