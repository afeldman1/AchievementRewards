package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.OnYourWayRewards_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievedAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.Adapter_OnWayRewards;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.OnYourWayRewardsModels;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievedAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
        new OnYourWayRewards_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));
    }

    @Subscribe
    public void onAchievedAchievementsModelEvent(List<String> userAchievedAchievementsModels){
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userAchievedAchievementsModels);
        ListView myList=(ListView)
                findViewById(R.id.avail_list);
        myList.setAdapter(myAdapter);
    }

    @Subscribe
    public void onOnYourWayRewardsModelEvent(OnYourWayRewardsModels onYourWayRewardsModels) {
        ListView myList2 = (ListView) findViewById(R.id.on_way_list);
        myList2.setAdapter(new Adapter_OnWayRewards(this, onYourWayRewardsModels));
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
