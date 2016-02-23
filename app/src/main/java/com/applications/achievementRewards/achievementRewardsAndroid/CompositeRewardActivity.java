package com.applications.achievementRewards.achievementRewardsAndroid;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.Override;import java.lang.String;


public class CompositeRewardActivity extends LinearLayout {
    public CompositeRewardActivity(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //override the onClickListener for this object
            }
        });

        LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.composite_reward, this);
    }
    //This is the constructor using the parents context
    public CompositeRewardActivity(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.composite_reward, this, true);
    }

    //set the company name at the top
    public void setCompanyName(String text)
    {
        TextView tx = (TextView) findViewById(R.id.merchant_name);
        tx.setText(text);
    }

    public void setDesc(String text)
    {
        TextView tx = (TextView) findViewById(R.id.merchant_desc);
        tx.setText(text);
    }

    //Need a set ivMerchantLogo function as well

}
