package com.applications.achievementRewards.achievementRewardsAndroid;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Permission;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.text.DateFormat;
import java.util.Date;

public class LocationTracking extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation= null;
    Location mCurrentLocation = null;

    int total_distance = 0;
    String mLastUpdateTime = null;

    LocationRequest mLocationRequest = null;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
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

        super.onCreate();
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
            //
        }

    }
}
