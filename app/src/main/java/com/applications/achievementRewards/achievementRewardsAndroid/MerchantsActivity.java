package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.Adaptor_Merchants;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.Merchants_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MerchantsActivity extends NavigationViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants);
        super.setHeaderText("All Merchants");

        new Merchants_DatabaseTask().execute();
    }

    @Subscribe
    public void onDataLoadEvent(final List<MerchantModel> merchantModels) {
        ListView merchantsLV = (ListView) findViewById(R.id.merchants_lv);
        final Adaptor_Merchants adaptorMerchants = new Adaptor_Merchants(this, merchantModels);
        merchantsLV.setAdapter(adaptorMerchants);

        merchantsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                   Intent in = new Intent(MerchantsActivity.this, MerchantDetailsActivity.class);
                                                   in.putExtra("MERCHANTID", merchantModels.get(position).getMerchantId());
                                                   in.putExtra("MERCHANTNAME", merchantModels.get(position).getMerchantName());
                                                   startActivity(in);
                                               }
                                           }
        );

        EditText filterET = (EditText) findViewById(R.id.filter_et);
        filterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptorMerchants.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
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
