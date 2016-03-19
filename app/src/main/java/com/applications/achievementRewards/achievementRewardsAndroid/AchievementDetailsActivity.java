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

        TextView merchantTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantTv.setText(userAchievementModel.getMerchantName());

        TextView achievementNameTv = (TextView) findViewById(R.id.achievement_name_tv);
        achievementNameTv.setText(userAchievementModel.getMerchantName());

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button redeemBtn = (Button) findViewById(R.id.redeem_btn);
        if(userAchievementModel.getProgress() != userAchievementModel.getTrackingMax())
        {
            redeemBtn.setVisibility(View.GONE);
            progressBar.setMax(userAchievementModel.getTrackingMax());
            progressBar.setProgress((int) Math.round(userAchievementModel.getProgress()));
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            redeemBtn.setVisibility(View.VISIBLE);
        }
    }
}
