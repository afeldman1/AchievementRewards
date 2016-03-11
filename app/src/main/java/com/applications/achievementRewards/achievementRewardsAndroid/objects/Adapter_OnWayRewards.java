package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.R;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.OnYourWayRewardsModels;

public class Adapter_OnWayRewards extends BaseAdapter{
    Context context;
    OnYourWayRewardsModels onYourWayRewardsModels;
    private static LayoutInflater inflater = null;

    public Adapter_OnWayRewards(Context context, OnYourWayRewardsModels onYourWayRewardsModels) {
        this.context = context;
        this.onYourWayRewardsModels = onYourWayRewardsModels;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return onYourWayRewardsModels.getOnYourWayRewardsModels().size();
    }

    @Override
    public Object getItem(int position) {
        return  onYourWayRewardsModels.getOnYourWayRewardsModels().get(position);
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
        merchant_tv.setText(((OnYourWayRewardsModel)getItem(position)).getMerchant());

        TextView achievement_tv = (TextView) vi.findViewById(R.id.achievement_tv);
        achievement_tv.setText(((OnYourWayRewardsModel) getItem(position)).getAchievement());

        ProgressBar progress_bar = (ProgressBar) vi.findViewById(R.id.progress_bar);
        progress_bar.setMax(((OnYourWayRewardsModel)getItem(position)).getTrackingMax());
        progress_bar.setProgress((int) Math.round((((OnYourWayRewardsModel) getItem(position)).getProgress())));

        return vi;
    }

}
