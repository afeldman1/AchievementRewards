package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.NavDrawerAdapter;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.NavDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class NavigationViewActivity extends Activity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;

    ListView listView;


    int resID;
    String[] navTitles;
    int icons[] = {R.mipmap.home, R.mipmap.merchants, R.mipmap.settings, R.mipmap.about};
    String userName = "username";
    int profilePicture = 1;


    NavDrawerItem[] navDrawerItems = new NavDrawerItem[icons.length];

    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here
        super.setContentView(R.layout.activity_navigation_view);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        listView = (ListView) findViewById(R.id.left_drawer);

        navTitles = getResources().getStringArray(R.array.nav_list_strings);

        for (int i = 0; i < navTitles.length; i++)
        {
            navDrawerItems[i] = new NavDrawerItem(icons[i], navTitles[i]);
        }

        //user ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userID = sharedPreferences.getLong("currUserID", 0);
        Map names = sharedPreferences.getAll();

        String lastName = (String)names.get("LastName");
        String firstName = (String)names.get("FirstName");

        String full_name = firstName + " " + lastName;
        userName = full_name;








        LayoutInflater inflater = getLayoutInflater();
        ViewGroup mTop = (ViewGroup)inflater.inflate(R.layout.nav_header, null);

        listView.addHeaderView(mTop, null, false);

        TextView fb_name = (TextView) listView.findViewById (R.id.FB_name);
        fb_name.setText(userName);


        String prof_url = "https://graph.facebook.com/" + String.valueOf(userID)+ "/picture?type=large";
        ImageView profile_pic = (ImageView) listView.findViewById(R.id.profile_image);

        Picasso.with(this).load(prof_url).into(profile_pic);

        listView.setAdapter(new NavDrawerAdapter(this, R.layout.nav_view_item, navDrawerItems));

        listView.setOnItemClickListener(new DrawerItemClickListener());



        // Setting the content of layout your provided to the act_content frame
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        resID = layoutResID;


    }

    private  class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //add 1 since now we have a header
            selectItem(arg2-1);
            // TODO Auto-generated method stub

        }

    }

    private void selectItem(int position) {
        Intent intent;

        switch(position) {
            case 0:
                if (resID == R.layout.activity_home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case 1:
                if (resID == R.layout.activity_merchants) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                intent = new Intent(this, MerchantsActivity.class);
                startActivity(intent);
                break;
            case 2:
                if (resID == R.layout.activity_settings)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case 3:
                Toast.makeText(this,"Achievement Rewards About Text", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}




