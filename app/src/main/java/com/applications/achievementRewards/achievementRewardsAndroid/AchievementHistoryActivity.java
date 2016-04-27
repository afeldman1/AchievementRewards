package com.applications.achievementRewards.achievementRewardsAndroid;

import android.os.Bundle;

public class AchievementHistoryActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_history);
        super.setHeaderText("Achieved Achievement History");
    }
}
