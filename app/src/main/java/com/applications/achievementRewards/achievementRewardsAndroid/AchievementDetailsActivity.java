package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.RedeemUserAchievement_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievementDetails_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.util.Date;

public class AchievementDetailsActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_details);

        Intent intent = getIntent();
        final UserAchievementModel userAchievementModel = intent.getParcelableExtra("USERACHIEVEMENTMODEL");

        new UserAchievementDetails_DatabaseTask().execute(userAchievementModel);

        super.setHeaderText(userAchievementModel.getAchievementName());

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setText(userAchievementModel.getMerchantName());

        if (userAchievementModel.getTrackingMax() != null) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            Button redeemBtn = (Button) findViewById(R.id.redeem_btn);
            TextView redeemedAtTv = (TextView) findViewById(R.id.redeemed_at_tv);
            if (userAchievementModel.getProgress() != (double) userAchievementModel.getTrackingMax().intValue()) {
                redeemBtn.setVisibility(View.GONE);
                redeemedAtTv.setVisibility(View.GONE);
                progressBar.setMax(userAchievementModel.getTrackingMax());
                progressBar.setProgress((int) Math.round(userAchievementModel.getProgress()));
                progressBar.setVisibility(View.VISIBLE);
            }
            else if (userAchievementModel.getProgress() != null && userAchievementModel.getProgress() == (double) userAchievementModel.getTrackingMax().intValue() && userAchievementModel.getRedeemedAt() == null) {
                progressBar.setVisibility(View.GONE);
                redeemedAtTv.setVisibility(View.GONE);
                redeemBtn.setVisibility(View.VISIBLE);
                redeemBtn.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        new RedeemUserAchievement_DatabaseTask().execute(userAchievementModel.getUserAchievementId());
                    }
                });
            }
            else if (userAchievementModel.getProgress() == (double) userAchievementModel.getTrackingMax().intValue() && userAchievementModel.getRedeemedAt() != null) {
                redeemBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
                redeemedAtTv.setText("Redeemed on: " + dateFormat.format(userAchievementModel.getRedeemedAt()) + " " + timeFormat.format(userAchievementModel.getRedeemedAt()));
                redeemedAtTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe
    public void onDataLoadEvent(final UserAchievementModel userAchievementModel) {
        TextView achievementDescTv = (TextView) findViewById(R.id.achievement_desc_tv);
        achievementDescTv.setText(userAchievementModel.getAchievementDescription());

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(AchievementDetailsActivity.this, MerchantDetailsActivity.class);
                in.putExtra("MERCHANTID", userAchievementModel.getMerchantId());
                in.putExtra("MERCHANTNAME", userAchievementModel.getMerchantName());
                startActivity(in);
            }
        });

        TextView rewardNameTv = (TextView) findViewById(R.id.reward_name_tv);
        rewardNameTv.setText(userAchievementModel.getRewardName());

        TextView rewardDescTv = (TextView) findViewById(R.id.reward_desc_tv);
        rewardDescTv.setText(userAchievementModel.getRewardDescription());

        ImageView merchantImage = (ImageView) findViewById(R.id.merchant_logo_iv);
        Picasso.with(this).load(userAchievementModel.getLogoUrl().toString()).into(merchantImage);
    }

    @Subscribe
    public void onUserAchievementRedeemedEvent(Date redeemedAt) {
        if (redeemedAt != null) {
            Button redeemBtn = (Button) findViewById(R.id.redeem_btn);
            redeemBtn.setVisibility(View.GONE);

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

            TextView redeemedAtTv = (TextView) findViewById(R.id.redeemed_at_tv);
            redeemedAtTv.setText("Redeemed on: " + dateFormat.format(redeemedAt) + " " + timeFormat.format(redeemedAt));
            redeemedAtTv.setVisibility(View.VISIBLE);
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
