package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;

import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.NavDrawerAdapter;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.NavDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class NavigationViewActivity extends AppCompatActivity {

    //layouts
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;

    //list of items for navigation view
    ListView listView;
    String[] navTitles;
    int icons[] = {R.mipmap.home, R.mipmap.merchants, R.mipmap.settings, R.mipmap.about};
    NavDrawerItem[] navDrawerItems = new NavDrawerItem[icons.length];

    //user values
    int resID;
    String userName = "username";
    int profilePicture = 1;

    //action bar toggle
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here
        super.setContentView(R.layout.activity_navigation_view);

        //toolbar code
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //get all the layouts/widgets and assign to variable
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        listView = (ListView) findViewById(R.id.left_drawer);

        //get navigation bar strings and assign
        navTitles = getResources().getStringArray(R.array.nav_list_strings);
        for (int i = 0; i < navTitles.length; i++) {
            navDrawerItems[i] = new NavDrawerItem(icons[i], navTitles[i]);
        }

        //user ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userID = sharedPreferences.getLong("currUserID", 0);
        Map names = sharedPreferences.getAll();

        String lastName = (String) names.get("LastName");
        String firstName = (String) names.get("FirstName");

        String full_name = firstName + " " + lastName;
        userName = full_name;


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup mTop = (ViewGroup) inflater.inflate(R.layout.nav_header, null);

        listView.addHeaderView(mTop, null, false);

        TextView fb_name = (TextView) listView.findViewById(R.id.FB_name);
        fb_name.setText(userName);


        String prof_url = "https://graph.facebook.com/" + String.valueOf(userID) + "/picture?type=large";
        ImageView profile_pic = (ImageView) listView.findViewById(R.id.profile_image);

        Picasso.with(this).load(prof_url).into(profile_pic);

        listView.setAdapter(new NavDrawerAdapter(this, R.layout.nav_view_item, navDrawerItems));

        listView.setOnItemClickListener(new DrawerItemClickListener());

        setupDrawer();

        // Setting the content of layout your provided to the act_content frame
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        resID = layoutResID;


    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //add 1 since now we have a header
            selectItem(arg2 - 1);
            // TODO Auto-generated method stub

        }

    }

    private void selectItem(int position) {
        Intent intent;

        switch (position) {
            case 0:
                if (resID == R.layout.activity_home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case 1:
                if (resID == R.layout.activity_merchants) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                intent = new Intent(this, MerchantsActivity.class);
                startActivity(intent);
                break;
            case 2:
                if (resID == R.layout.activity_settings) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case 3:
                Toast.makeText(this, "Achievement Rewards About Text", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



