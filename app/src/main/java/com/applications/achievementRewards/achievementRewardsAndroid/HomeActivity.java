package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.OnYourWayRewards_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievedAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.Adapter_OnWayRewards;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
    }

    @Subscribe
    public void onDataLoadEvent(UserAchievementModels userAchievementModels) {
        UserAchievementModels onYourWayRewards = new UserAchievementModels();
        List<String> achievedAchievements = new ArrayList<>();

        for (UserAchievementModel userAchievementModel : userAchievementModels ) {
            if (userAchievementModel.getProgress() < userAchievementModel.getTrackingMax())
            {
                onYourWayRewards.add(userAchievementModel);
            }
            if (userAchievementModel.getProgress() == userAchievementModel.getTrackingMax())
            {
                achievedAchievements.add(userAchievementModel.getMerchantName() + ": " + userAchievementModel.getAchievementName());
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

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, achievedAchievements);
        ListView achievedAchievementsLV = (ListView) findViewById(R.id.avail_list);
        achievedAchievementsLV.setAdapter(myAdapter);

        achievedAchievementsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                              Intent i = new Intent(HomeActivity.this, AchievementDetailsActivity.class);
                                                              //i.putExtra("TEXT", text);
                                                              //i.putExtra("IMAGE", img); // <-- Assumed you image is Parcelable
                                                              startActivity(i);
                                                          }
                                                      }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
