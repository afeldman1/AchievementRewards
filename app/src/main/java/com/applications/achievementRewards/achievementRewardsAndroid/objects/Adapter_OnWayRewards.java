package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.R;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

public class Adapter_OnWayRewards extends BaseAdapter{
    Context context;
    UserAchievementModels userAchievementModels;
    private static LayoutInflater inflater = null;

    public Adapter_OnWayRewards(Context context, UserAchievementModels userAchievementModels) {
        this.context = context;
        this.userAchievementModels = userAchievementModels;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userAchievementModels.getUserAchievementModels().size();
    }

    @Override
    public Object getItem(int position) {
        return  userAchievementModels.getUserAchievementModels().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
            if (vi == null)
            vi = inflater.inflate(R.layout.composite_onway_rewards, null);

        TextView merchant_tv = (TextView) vi.findViewById(R.id.merchant_tv);
        merchant_tv.setText(((UserAchievementModel)getItem(position)).getMerchantName());

        TextView achievement_tv = (TextView) vi.findViewById(R.id.achievement_tv);
        achievement_tv.setText(((UserAchievementModel) getItem(position)).getAchievementName());

        ProgressBar progress_bar = (ProgressBar) vi.findViewById(R.id.progress_bar);
        progress_bar.setMax(((UserAchievementModel)getItem(position)).getTrackingMax());
        progress_bar.setProgress((int) Math.round((((UserAchievementModel) getItem(position)).getProgress())));

        return vi;
    }

}
