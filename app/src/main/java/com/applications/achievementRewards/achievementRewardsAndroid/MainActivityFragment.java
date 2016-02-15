package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView myTextView;

    private CallbackManager myCallbackManager;

    private AccessTokenTracker myTokenTracker;
    private ProfileTracker myProfileTracker;

    private CurrentUser currUser;

    public void dbConnect(String host, String port, String db_userid, String db_password) {
        DbInfo dbInfo = new DbInfo(host, port, db_userid, db_password);
        //Commented out to be moved to new activity
        //new ConnectDatabaseTask(this).execute(dbInfo);
    }

    private FacebookCallback<LoginResult> myCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            displayWelcomeMessage(profile);
            signInMatchFBProfileDB(profile);

            dbConnect("dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com", "1433", "awsUser", "awsPassword");
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    private void signInMatchFBProfileDB(Profile profile){
        //currUser.setID(Integer.parseInt(profile.getId()));
        //Check if we have this person in our db.
        //If yes load profile and continue
        //Otherwise create new user entry and continue
    }

    private void displayWelcomeMessage(Profile profile) {
        if (profile != null) {
            myTextView.setText("hi " + profile.getName());
            Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
            startActivity(intent);
        }
    }

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        myCallbackManager = CallbackManager.Factory.create();
        myTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        myProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                displayWelcomeMessage(currentProfile);
            }
        };
        myTokenTracker.startTracking();
        myProfileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "read_insights"));
        loginButton.setFragment(this);
        loginButton.registerCallback(myCallbackManager, myCallback);

        myTextView = (TextView) view.findViewById(R.id.ha);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayWelcomeMessage(profile);
    }

    @Override
    public void onStop() {
        super.onStop();

        myTokenTracker.stopTracking();
        myProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
