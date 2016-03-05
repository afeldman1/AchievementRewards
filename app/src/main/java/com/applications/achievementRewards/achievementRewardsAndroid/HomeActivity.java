package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.OnYourWayRewards_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievedAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.Adapter_OnWayRewards;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.OnYourWayRewardsModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //String[] avail_rewards = {"availReward1", "availReward2"};
    String[] on_way_rewards = {"reward1", "reward2"};
    //String[] secondStringArray = {"Here", "Is", "My", "second", "layout", "woo!"};

    //test to add in one of the composite views

    //CompositeRewardActivity[] onWayRewards = {temp};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievedAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
        new OnYourWayRewards_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
    }

    @Subscribe
    public void onEvent(List<String> userAchievedAchievementsModels){
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userAchievedAchievementsModels);
        ListView myList=(ListView)
                findViewById(R.id.avail_list);
        myList.setAdapter(myAdapter);
    }

    @Subscribe
    public void onOnYourWayRewardsModelEvent(List<OnYourWayRewardsModel> onYourWayRewardsModel) {
        ListView myList2 = (ListView) findViewById(R.id.on_way_list);
        myList2.setAdapter(new Adapter_OnWayRewards(this, on_way_rewards));
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
