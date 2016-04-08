package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MerchantModel {
    private int merchantId;
    private String merchantName;
    private String merchantDescription;
    private URL logoUrl;
    private List<MerchantLocModel> merchantLocModels = new ArrayList<>();
    private List<UserAchievementModel> userAchievementModels = new ArrayList<>();

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
}
