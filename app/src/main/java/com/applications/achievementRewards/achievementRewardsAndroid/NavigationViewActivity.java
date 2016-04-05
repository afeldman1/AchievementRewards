package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;


public class NavigationViewActivity extends Activity {

    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ListView navBar;
    int resID;

    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here

        super.setContentView(R.layout.activity_navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        navBar = (ListView) findViewById(R.id.left_drawer);


        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nav_list_strings));
        navBar.setAdapter(aa);
        navBar.setOnItemClickListener(new DrawerItemClickListener());


        // Setting the content of layout your provided to the act_content frame
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        resID = layoutResID;


    }

    private  class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            selectItem(arg2);
            // TODO Auto-generated method stub

        }

    }

    private void selectItem(int position) {
        switch(position) {
            case 0:
                if (resID == R.layout.activity_home) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                Intent a = new Intent(this, HomeActivity.class);
                startActivity(a);
                break;
            case 1:
                if (resID == R.layout.activity_settings)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                Intent b = new Intent(this, SettingsActivity.class);
                startActivity(b);
                break;
            case 2:
                Toast.makeText(this,"Achievement Rewards About Text", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}




