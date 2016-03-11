package com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.OnYourWayRewardsModel;

import java.util.ArrayList;
import java.util.List;

public class OnYourWayRewardsModels {
    private List<OnYourWayRewardsModel> onYourWayRewardsModels;

    public OnYourWayRewardsModels() {
        this.onYourWayRewardsModels = new ArrayList<>();
    }

    public List<OnYourWayRewardsModel> getOnYourWayRewardsModels() {
        return onYourWayRewardsModels;
    }

    public void setOnYourWayRewardsModels(List<OnYourWayRewardsModel> onYourWayRewardsModels) {
        this.onYourWayRewardsModels = onYourWayRewardsModels;
    }

    public void add(OnYourWayRewardsModel onYourWayRewardsModel) {
        this.onYourWayRewardsModels.add(onYourWayRewardsModel);
    }
}
