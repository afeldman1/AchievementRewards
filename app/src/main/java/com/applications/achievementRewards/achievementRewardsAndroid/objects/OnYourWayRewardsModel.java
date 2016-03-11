package com.applications.achievementRewards.achievementRewardsAndroid.objects;

public class OnYourWayRewardsModel {
    private int userAchievementId;
    private String merchant;
    private String achievement;
    private double progress;
    private int trackingMax;

    public OnYourWayRewardsModel(int userAchievementId, String merchant, String achievement, double progress, int trackingMax) {
        this.userAchievementId = userAchievementId;
        this.merchant = merchant;
        this.achievement = achievement;
        this.progress = progress;
        this.trackingMax = trackingMax;
    }

    public int getUserAchievementId() {
        return userAchievementId;
    }

    public void setUserAchievementId(int userAchievementId) {
        this.userAchievementId = userAchievementId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
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
}
