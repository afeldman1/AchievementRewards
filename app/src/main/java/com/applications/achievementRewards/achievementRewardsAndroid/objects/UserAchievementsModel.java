package com.applications.achievementRewards.achievementRewardsAndroid.objects;

public class UserAchievementsModel {

    private String achievementName;
    private String achievementDescription;
    private String merchantLogoURL;

    public UserAchievementsModel(String achievementName, String achievementDescription, String merchantLogoURL) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.merchantLogoURL = merchantLogoURL;
    }

    public String getMerchantLogoURL() {
        return merchantLogoURL;
    }

    public void setMerchantLogoURL(String merchantLogoURL) {
        this.merchantLogoURL = merchantLogoURL;
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
}
