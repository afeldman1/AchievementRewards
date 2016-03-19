package com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserAchievementModels implements Iterable<UserAchievementModel> {
    private List<UserAchievementModel> userAchievementModels;

    public UserAchievementModels() {
        this.userAchievementModels = new ArrayList<>();
    }

    public List<UserAchievementModel> getUserAchievementModels() {
        return userAchievementModels;
    }

    public void setUserAchievementModels(List<UserAchievementModel> userAchievementModels) {
        this.userAchievementModels = userAchievementModels;
    }

    public void add(UserAchievementModel userAchievementModel) {
        this.userAchievementModels.add(userAchievementModel);
    }

    @Override
    public Iterator<UserAchievementModel> iterator() {
        return userAchievementModels.iterator();
    }
}
