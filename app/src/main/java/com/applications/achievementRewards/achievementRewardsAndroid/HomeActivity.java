package com.applications.achievementRewards.achievementRewardsAndroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.Adapter_OnWayRewards;

public class HomeActivity extends AppCompatActivity {

    String[] avail_rewards = {"availReward1", "availReward2"};
    String[] on_way_rewards = {"reward1", "reward2"};
    //String[] secondStringArray = {"Here", "Is", "My", "second", "layout", "woo!"};

    //test to add in one of the composite views

    //CompositeRewardActivity[] onWayRewards = {temp};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, avail_rewards);
        ListView myList=(ListView)
                findViewById(R.id.avail_list);
        myList.setAdapter(myAdapter);


        ListView myList2 = (ListView) findViewById(R.id.on_way_list);
        myList2.setAdapter(new Adapter_OnWayRewards(this,on_way_rewards));

    }
}
