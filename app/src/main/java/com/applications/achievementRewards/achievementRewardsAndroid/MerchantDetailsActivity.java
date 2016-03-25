package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.MerchantDetails_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.Merchants_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MerchantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);

        Intent intent = getIntent();

        MerchantModel merchantModel = new MerchantModel();
        merchantModel.setMerchantId(intent.getIntExtra("MERCHANTID", 0));
        merchantModel.setMerchantName(intent.getStringExtra("MERCHANTNAME"));

        new MerchantDetails_DatabaseTask().execute(merchantModel);

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setText(merchantModel.getMerchantName());
    }

    @Subscribe
    public void onDataLoadEvent(final MerchantModel merchantModel) {
        TextView merchantDescriptionTv = (TextView) findViewById(R.id.merchant_description_tv);
        merchantDescriptionTv.setText(merchantModel.getMerchantDescription());

        List<String> userAchievementsLabels = new ArrayList<>();
        for (UserAchievementModel userAchievementModel : merchantModel.getUserAchievementModels()) {
            userAchievementsLabels.add(userAchievementModel.getAchievementName());
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userAchievementsLabels);
        ListView merchantAchievementsLv = (ListView) findViewById(R.id.merchant_achievements_lv);
        merchantAchievementsLv.setAdapter(myAdapter);
        /*
        merchantAchievementsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                              Intent in = new Intent(MerchantDetailsActivity.this, AchievementDetailsActivity.class);
                                                              //in.putExtra("USERACHIEVEMENTMODEL", merchantModel.getUserAchievementModels().get(position));
                                                              startActivity(in);
                                                          }
                                                      }
        );
        */
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
