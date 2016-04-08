package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class UserAchievementModel implements Parcelable {
    private Integer userAchievementId;
    private Integer achievementId;
    private String achievementName;
    private String achievementDescription;
    private String rewardName;
    private String rewardDescription;
    private int merchantId;
    private String merchantName;
    private URL logoUrl;
    private Double progress;
    private Integer trackingMax;
    private Date redeemedAt;

    public UserAchievementModel(Integer userAchievementId, Integer achievementId, String achievementName, String achievementDescription, String rewardName, String rewardDescription, int merchantId, String merchantName, URL logoUrl, double progress, Integer trackingMax, Date redeemedAt) {
        this.userAchievementId = userAchievementId;
        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.logoUrl = logoUrl;
        this.progress = progress;
        this.trackingMax = trackingMax;
        this.redeemedAt = redeemedAt;
    }

    public UserAchievementModel(Integer userAchievementId, String achievementName, String merchantName, double progress, int trackingMax) {
        this.userAchievementId = userAchievementId;
        this.achievementName = achievementName;
        this.merchantName = merchantName;
        this.progress = progress;
        this.trackingMax = trackingMax;
    }

    public UserAchievementModel() {
    }

    public Integer getUserAchievementId() {
        return userAchievementId;
    }

    public void setUserAchievementId(Integer userAchievementId) {
        this.userAchievementId = userAchievementId;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
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

    public URL getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(URL logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Integer getTrackingMax() {
        return trackingMax;
    }

    public void setTrackingMax(Integer trackingMax) {
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
        pc.writeValue(userAchievementId);
        pc.writeValue(achievementId);
        pc.writeValue(achievementName);
        pc.writeValue(achievementDescription);
        pc.writeValue(rewardName);
        pc.writeValue(rewardDescription);
        pc.writeValue(merchantName);
        pc.writeValue(merchantId);
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
        this.userAchievementId = (Integer) pc.readValue(Integer.class.getClassLoader());
        this.achievementId = (Integer) pc.readValue(Integer.class.getClassLoader());
        this.achievementName = (String) pc.readValue(String.class.getClassLoader());
        this.achievementDescription = (String) pc.readValue(String.class.getClassLoader());
        this.rewardName = (String) pc.readValue(String.class.getClassLoader());
        this.rewardDescription = (String) pc.readValue(String.class.getClassLoader());
        this.merchantName = (String) pc.readValue(String.class.getClassLoader());
        this.merchantId = (Integer) pc.readValue(Integer.class.getClassLoader());
        try {
            String url = (String) pc.readValue(String.class.getClassLoader());
            this.logoUrl = url == null ? null : new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.progress = (Double) pc.readValue(Double.class.getClassLoader());
        this.trackingMax = (Integer) pc.readValue(Integer.class.getClassLoader());
        Long t = (Long) pc.readValue(Long.class.getClassLoader());
        this.redeemedAt = t == null ? null : new Date(t);
    }
}
