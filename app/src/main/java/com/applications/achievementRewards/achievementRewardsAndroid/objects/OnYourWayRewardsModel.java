package com.applications.achievementRewards.achievementRewardsAndroid.objects;

public class OnYourWayRewardsModel {
    private String merchant;
    private String achievement;
    private double progress;
    private int trackingMax;

    public OnYourWayRewardsModel(String merchant, String achievement, double progress, int trackingMax) {
        this.merchant = merchant;
        this.achievement = achievement;
        this.progress = progress;
        this.trackingMax = trackingMax;
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
