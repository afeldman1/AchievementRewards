package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.Adapter_OnWayRewards;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends NavigationViewActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.setHeaderText("Home");
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
    }

    @Subscribe
    public void onDataLoadEvent(UserAchievementModels userAchievementModels) {
        UserAchievementModels onYourWayRewards = new UserAchievementModels();
        final UserAchievementModels achievedAchievements = new UserAchievementModels();
        List<String> achievedAchievementLabels = new ArrayList<>();

        for (UserAchievementModel userAchievementModel : userAchievementModels) {
            if (userAchievementModel.getProgress() < userAchievementModel.getTrackingMax())
            {
                onYourWayRewards.add(userAchievementModel);
            }
            if (userAchievementModel.getProgress() == (double)userAchievementModel.getTrackingMax().intValue() && userAchievementModel.getRedeemedAt() == null)
            {
                achievedAchievements.add(userAchievementModel);
                achievedAchievementLabels.add(userAchievementModel.getMerchantName() + ": " + userAchievementModel.getAchievementName());
            }
        }

        ListView onYourWayLV = (ListView) findViewById(R.id.on_way_list);
        onYourWayLV.setAdapter(new Adapter_OnWayRewards(this, onYourWayRewards));

        onYourWayLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                     Intent in = new Intent(HomeActivity.this, AchievementDetailsActivity.class);
                                                     in.putExtra("USERACHIEVEMENTMODEL", ((UserAchievementModel) parent.getItemAtPosition(position)));
                                                     startActivity(in);
                                                 }
                                             }
        );

        ListView achievedAchievementsLV = (ListView) findViewById(R.id.avail_list);
        if (achievedAchievementLabels.size() > 0) {
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, achievedAchievementLabels);
            achievedAchievementsLV.setAdapter(myAdapter);

            achievedAchievementsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                              public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                                  Intent in = new Intent(HomeActivity.this, AchievementDetailsActivity.class);
                                                                  in.putExtra("USERACHIEVEMENTMODEL", achievedAchievements.getUserAchievementModel(position));
                                                                  //i.putExtra("TEXT", text);
                                                                  //i.putExtra("IMAGE", img); // <-- Assumed you image is Parcelable
                                                                  startActivity(in);
                                                              }
                                                          }
            );
        }
        else {
            achievedAchievementsLV.setVisibility(View.GONE);

            TextView achievedAchievementsTV = (TextView) findViewById(R.id.avail_text);
            achievedAchievementsTV.setVisibility(View.GONE);
        }
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
