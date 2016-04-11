package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.Adaptor_Merchants;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.Merchants_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MerchantsActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants);

        new Merchants_DatabaseTask().execute();
    }

    @Subscribe
    public void onDataLoadEvent(final List<MerchantModel> merchantModels) {
        ListView merchantsLV = (ListView) findViewById(R.id.merchants_lv);
        merchantsLV.setAdapter(new Adaptor_Merchants(this, merchantModels));

        merchantsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                   Intent in = new Intent(MerchantsActivity.this, MerchantDetailsActivity.class);
                                                   in.putExtra("MERCHANTID", merchantModels.get(position).getMerchantId());
                                                   in.putExtra("MERCHANTNAME", merchantModels.get(position).getMerchantName());
                                                   startActivity(in);
                                               }
                                           }
        );
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
