package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievementDetails_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AchievementDetailsActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_details);

        Intent intent = getIntent();
        final UserAchievementModel userAchievementModel = intent.getParcelableExtra("USERACHIEVEMENTMODEL");

        new UserAchievementDetails_DatabaseTask().execute(userAchievementModel);

        TextView achievementNameTv = (TextView) findViewById(R.id.achievement_name_tv);
        achievementNameTv.setText(userAchievementModel.getAchievementName());

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setText(userAchievementModel.getMerchantName());
        merchantNameTv.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(AchievementDetailsActivity.this, MerchantDetailsActivity.class);
                in.putExtra("MERCHANTID", userAchievementModel.getMerchantId());
                in.putExtra("MERCHANTNAME", userAchievementModel.getMerchantName());
                startActivity(in);
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button redeemBtn = (Button) findViewById(R.id.redeem_btn);
        TextView redeemedAtTv = (TextView) findViewById(R.id.redeemed_at_tv);
        if(userAchievementModel.getProgress() != userAchievementModel.getTrackingMax())
        {
            redeemBtn.setVisibility(View.GONE);
            redeemedAtTv.setVisibility(View.GONE);
            progressBar.setMax(userAchievementModel.getTrackingMax());
            progressBar.setProgress((int) Math.round(userAchievementModel.getProgress()));
            progressBar.setVisibility(View.VISIBLE);
        }
        else if(userAchievementModel.getProgress() != null && userAchievementModel.getProgress() == userAchievementModel.getTrackingMax() && userAchievementModel.getRedeemedAt() == null)
        {
            progressBar.setVisibility(View.GONE);
            redeemedAtTv.setVisibility(View.GONE);
            redeemBtn.setVisibility(View.VISIBLE);
        }
        else if(userAchievementModel.getProgress() == userAchievementModel.getTrackingMax() && userAchievementModel.getRedeemedAt() != null)
        {
            redeemBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            redeemedAtTv.setText("Redeemed on: " + userAchievementModel.getRedeemedAt());
            redeemedAtTv.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onDataLoadEvent(UserAchievementModel userAchievementModel) {
        TextView achievementDescTv = (TextView) findViewById(R.id.achievement_desc_tv);
        achievementDescTv.setText(userAchievementModel.getAchievementDescription());

        TextView rewardNameTv = (TextView) findViewById(R.id.reward_name_tv);
        rewardNameTv.setText(userAchievementModel.getRewardName());

        TextView rewardDescTv = (TextView) findViewById(R.id.reward_desc_tv);
        rewardDescTv.setText(userAchievementModel.getRewardDescription());
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
        } else if (id == R.id.action_test) {
            Intent intent = new Intent(this, DisplayLocation.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
