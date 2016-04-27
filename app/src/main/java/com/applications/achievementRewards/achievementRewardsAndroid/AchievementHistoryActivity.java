package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class AchievementHistoryActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_history);
        super.setHeaderText("Redeemed Achievements");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievements_DatabaseTask().execute(new Long[] {sharedPreferences.getLong("currUserID", 0), (long) 1});
    }

    @Subscribe
    public void onDataLoadEvent(final UserAchievementModels userAchievementModels) {
        final List<String> achievedAchievementLabels = new ArrayList<>();

        for (UserAchievementModel userAchievementModel : userAchievementModels) {
            achievedAchievementLabels.add(userAchievementModel.getMerchantName() + ": " + userAchievementModel.getAchievementName());
        }

        ListView achievementsLV = (ListView) findViewById(R.id.achievements_lv);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, achievedAchievementLabels);
        achievementsLV.setAdapter(myAdapter);

        achievementsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                              Intent in = new Intent(AchievementHistoryActivity.this, AchievementDetailsActivity.class);
                                                              in.putExtra("USERACHIEVEMENTMODEL", userAchievementModels.getUserAchievementModel(position));
                                                              startActivity(in);
                                                          }
                                                      }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
