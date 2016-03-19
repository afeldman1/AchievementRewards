package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

public class AchievementDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_details);

        Intent intent = getIntent();
        UserAchievementModel userAchievementModel = intent.getParcelableExtra("USERACHIEVEMENTMODEL");

        TextView achievementNameTv = (TextView) findViewById(R.id.achievement_name_tv);
        achievementNameTv.setText(userAchievementModel.getAchievementName());

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setText(userAchievementModel.getMerchantName());

        TextView achievementDescTv = (TextView) findViewById(R.id.achievement_desc_tv);
        achievementDescTv.setText(userAchievementModel.getAchievementDescription());

        TextView rewardNameTv = (TextView) findViewById(R.id.reward_name_tv);
        rewardNameTv.setText(userAchievementModel.getRewardName());

        TextView rewardDescTv = (TextView) findViewById(R.id.reward_desc_tv);
        rewardDescTv.setText(userAchievementModel.getRewardDescription());

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
        else if(userAchievementModel.getProgress() == userAchievementModel.getTrackingMax() && userAchievementModel.getRedeemedAt() == null)
        {
            progressBar.setVisibility(View.GONE);
            redeemedAtTv.setVisibility(View.GONE);
            redeemBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            redeemBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            redeemedAtTv.setText("Redeemed on: " + userAchievementModel.getRedeemedAt());
            redeemedAtTv.setVisibility(View.VISIBLE);
        }
    }
}
