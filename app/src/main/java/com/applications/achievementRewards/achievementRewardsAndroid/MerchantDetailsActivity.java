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
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MerchantDetailsActivity extends NavigationViewActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();

        MerchantModel merchantModel = new MerchantModel();
        merchantModel.setMerchantId(intent.getIntExtra("MERCHANTID", 0));
        merchantModel.setMerchantName(intent.getStringExtra("MERCHANTNAME"));

        new MerchantDetails_DatabaseTask().execute(merchantModel);

        TextView merchantNameTv = (TextView) findViewById(R.id.merchant_name_tv);
        merchantNameTv.setText(merchantModel.getMerchantName());

        TextView merchantAchievementsHeaderTv = (TextView) findViewById(R.id.merchant_achievements_header_tv);
        merchantAchievementsHeaderTv.setText("More achievements from " + merchantModel.getMerchantName());
    }

    @Subscribe
    public void onDataLoadEvent(final MerchantModel merchantModel) {
        TextView merchantDescriptionTv = (TextView) findViewById(R.id.merchant_description_tv);
        merchantDescriptionTv.setText(merchantModel.getMerchantDescription());

        ImageView merchantImage = (ImageView) findViewById(R.id.merchant_logo_iv);
        Picasso.with(this).load(merchantModel.getLogoUrl().toString()).into(merchantImage);

        List<String> userAchievementsLabels = new ArrayList<>();
        for (UserAchievementModel userAchievementModel : merchantModel.getUserAchievementModels()) {
            userAchievementsLabels.add(userAchievementModel.getAchievementName());
        }
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

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        LatLng boardwalk = new LatLng(40.74399, -74.03);
        //map.addMarker(new MarkerOptions().position(boardwalk).title("Boardwalk Fresh Burgers and Fries"));
        map.moveCamera(CameraUpdateFactory.newLatLng(boardwalk));
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
