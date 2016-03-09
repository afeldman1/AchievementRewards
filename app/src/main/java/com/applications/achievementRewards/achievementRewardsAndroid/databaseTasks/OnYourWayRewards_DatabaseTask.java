package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.OnYourWayRewardsModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.OnYourWayRewardsModels;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OnYourWayRewards_DatabaseTask extends AsyncTask<Long, Integer, OnYourWayRewardsModels> {

    @Override
    protected OnYourWayRewardsModels doInBackground(Long... params) {
        OnYourWayRewardsModels onYourWayRewardsModels = new OnYourWayRewardsModels();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            Connection conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            Statement statement = conn.createStatement();
            String queryString = "EXEC getUserInProgressAchievements " + params[0].toString();
            ResultSet rs = statement.executeQuery(queryString);

            while (rs.next()) {
                onYourWayRewardsModels.add(new OnYourWayRewardsModel(rs.getString("Merchant"), rs.getString("Achievement"), rs.getDouble("Progress"), rs.getInt("TrackingMax")));
            }
        } catch (Exception e) {
            //Db_list.add("Error");
            e.printStackTrace();
        }

        return onYourWayRewardsModels;
    }

    @Override
    protected void onPostExecute(OnYourWayRewardsModels userAchievementModels) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(userAchievementModels);
    }
}
