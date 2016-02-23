package com.applications.achievementRewards.achievementRewardsAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks.Users_DatabaseTask;

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

import java.sql.ResultSet;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignInActivityFBFragment extends Fragment {

    private TextView myTextView;

    private CallbackManager myCallbackManager;

    private AccessTokenTracker myTokenTracker;
    private ProfileTracker myProfileTracker;

    private CurrentUser currUser = new CurrentUser();

    public void dbConnect(String proc, String... params) {
        //DbInfo dbInfo = new DbInfo(proc);
        //Commented out to be moved to new activity
        //new ConnectDatabaseTask(this).execute(dbInfo);
    }

    public void dbConnecttemp() {
        //new ExecuteResultSetSP(this, this.getClass().getName(), "displayRealWelcomeMessage").execute(new DbInfo("getUser " + Long.toString(currUser.getID())));
    }

    private FacebookCallback<LoginResult> myCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            //displayWelcomeMessage(profile);
            //signInMatchFBProfileDB(profile);

            //currUser.setID(Integer.parseInt(profile.getId()));
            //new ExecuteResultSetSP(this.getClass().getName(), "displayRealWelcomeMessage").execute(new DbInfo("getUser" + Integer.toString(currUser.getID())));
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    private void signInMatchFBProfileDB(Profile profile){
        currUser.setID(Long.parseLong(profile.getId()));
        //Check if we have this person in our db.
        //If yes load profile and continue
        //Otherwise create new user entry and continue
        dbConnect("getUser", Long.toString(currUser.getID()));
    }

    private void displayWelcomeMessage(Profile profile) {
        if (profile != null) {
            myTextView.setText("hi " + profile.getName());
            Intent intent = new Intent(getActivity(), AllRewardsActivity.class);
            startActivity(intent);
        }
    }

    public void displayRealWelcomeMessage(ResultSet a) {

        //rs).findColumn("FirstName")
        currUser.setFirstName("jk");
        //currUser.setFirstName(rs.getString("FirstName"));
        //currUser.setLastName(rs.getString("LastName"));
        //currUser.setGender(rs.getString("Gender"));
        //currUser.setBirthday(rs.getDate("Birthday"));

        myTextView.setText("hi " + currUser.getName() +" "+ currUser.getGender() +" "+ currUser.getBirthday().toString());
    }

    public SignInActivityFBFragment() {
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

                if (currentProfile != null)
                {
                    currUser.setID(Long.parseLong(currentProfile.getId()));

                    currUser.setFirstName(currentProfile.getFirstName());
                    currUser.setLastName(currentProfile.getLastName());
                    //TODO: set currUser email, birthday, and gender

                    new Users_DatabaseTask(getActivity(), myTextView).execute(currUser);

                    Intent intent = new Intent(getActivity(), AllRewardsActivity.class);
                    //intent.putExtra("currentUsers", currentUsers.get(0));
                    getActivity().startActivity(intent);

                    //DatabaseHelper dbh = new DatabaseHelper();
                    //dbh.doSampleDatabaseStuff("Hi", myTextView);

                    /*
                    SessionFactory sessionFactory =  new Configuration().configure().buildSessionFactory();
                    Session session = sessionFactory.openSession();
                    try
                    {
                        session.beginTransaction();
                        session.save(currUser);
                        session.getTransaction().commit();
                    }
                    catch(Exception e)
                    {
                        session.getTransaction().rollback();
                    }*/

                    //dbConnecttemp();
                }
            }
        };
        myTokenTracker.startTracking();
        myProfileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
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
