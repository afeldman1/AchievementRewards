package com.applications.achievementRewards.achievementRewardsAndroid;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.applications.achievementRewards.achievementRewardsAndroid.adaptors.Adapter_OnWayRewards;
import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.UserAchievements_DatabaseTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Permission;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationTracking extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation= null;
    Location mCurrentLocation = null;

    double total_distance = 0;
    String mLastUpdateTime = null;
    int total_count = 0;

    LocationRequest mLocationRequest = null;

    UserAchievementModels userAchievementModels = new UserAchievementModels();

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    public LocationTracking() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(LocationTracking.this)
                    .addConnectionCallbacks(LocationTracking.this)
                    .addOnConnectionFailedListener(LocationTracking.this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();

        createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        new UserAchievements_DatabaseTask().execute(sharedPreferences.getLong("currUserID", 0));

        super.onCreate();
    }

    @Subscribe
    public void onDataLoadEvent(UserAchievementModels userAchievementModelsGet) {
        userAchievementModels = userAchievementModelsGet;
    }

    @Override
    public void onDestroy() {


        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        startLocationUpdates();

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = mCurrentLocation;
        mCurrentLocation = location;

        if (mLastLocation != null) {
            total_distance += mCurrentLocation.distanceTo(mLastLocation); //Distance in meters
            //TOTAL DISTANCE is essentially all the distance gathered in this session. Preferably in "ON DESTROY"
            total_count ++;
        }

        if (total_count >= 10) {
            total_count = 0;

            total_distance = total_distance * 0.000621371; //CONVERSION FROM METERS TO MILES.

            for (UserAchievementModel userAchievementModel : userAchievementModels) {
                if (userAchievementModel.getProgress() < userAchievementModel.getTrackingMax())
                {
                    double curProg = userAchievementModel.getProgress();
                    curProg += total_distance;
                    if (curProg >= userAchievementModel.getTrackingMax()) {
                        curProg = userAchievementModel.getTrackingMax();
                    }

                    userAchievementModel.setProgress(curProg);

                }
            }
            total_distance = 0;

        }
    }
}
