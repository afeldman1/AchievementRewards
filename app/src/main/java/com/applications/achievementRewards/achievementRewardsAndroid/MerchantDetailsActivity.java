package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.MerchantDetails_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantLocModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MerchantDetailsActivity extends NavigationViewActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Double> longs = new ArrayList<>();
    private List<Double> lats = new ArrayList<>();
    private boolean location = false;
    private List<String> addresses = new ArrayList<>();
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);



        Intent intent = getIntent();

        MerchantModel merchantModel = new MerchantModel();
        merchantModel.setMerchantId(intent.getIntExtra("MERCHANTID", 0));
        merchantModel.setMerchantName(intent.getStringExtra("MERCHANTNAME"));

        new MerchantDetails_DatabaseTask().execute(merchantModel);

        super.setHeaderText(merchantModel.getMerchantName());

        title = merchantModel.getMerchantName();

        TextView merchantAchievementsHeaderTv = (TextView) findViewById(R.id.merchant_achievements_header_tv);
        merchantAchievementsHeaderTv.setText("More achievements from " + merchantModel.getMerchantName());
    }

    @Subscribe
    public void onDataLoadEvent(final MerchantModel merchantModel) {
        TextView merchantDescriptionTv = (TextView) findViewById(R.id.merchant_description_tv);
        merchantDescriptionTv.setText(merchantModel.getMerchantDescription());

        if (merchantModel.getLogoUrl() != null) {
            ImageView merchantImage = (ImageView) findViewById(R.id.merchant_logo_iv);
            Picasso.with(this).load(merchantModel.getLogoUrl().toString()).into(merchantImage);
        }

        List<String> userAchievementsLabels = new ArrayList<>();
        for (UserAchievementModel userAchievementModel : merchantModel.getUserAchievementModels()) {
            userAchievementsLabels.add(userAchievementModel.getAchievementName());
        }

        for (MerchantLocModel merchantLocModel : merchantModel.getMerchantLocModels()) {
            longs.add(merchantLocModel.getLon());
            lats.add(merchantLocModel.getLat());
            addresses.add(merchantLocModel.getAddress());
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        location = true;

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userAchievementsLabels);
        ListView merchantAchievementsLv = (ListView) findViewById(R.id.merchant_achievements_lv);
        merchantAchievementsLv.setAdapter(myAdapter);

        merchantAchievementsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                              Intent in = new Intent(MerchantDetailsActivity.this, AchievementDetailsActivity.class);
                                                              in.putExtra("USERACHIEVEMENTMODEL", merchantModel.getUserAchievementModels().get(position));
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

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        int count = 0;

        //MAYBE WAIT HERE FOR A SECOND?

        for (int i = 0; i < lats.size(); ++i) {
            if (lats.get(i) != null && lats.get(i) != 0 && longs.get(i) != null && longs.get(i) != 0) {
                LatLng position = new LatLng(lats.get(i),longs.get(i));
                if (addresses.get(i) != null && addresses.get(i) != ""){
                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(addresses.get(i))
                            .icon(BitmapDescriptorFactory.defaultMarker(135)));
                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(title)
                            .icon(BitmapDescriptorFactory.defaultMarker(135)));

                }
                if (count == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                }
                count++;
            }
        }

        if (count == 0) {
            LatLng position = new LatLng(40.744337, -74.025749);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }
}
