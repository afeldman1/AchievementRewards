package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OnYourWayRewards_DatabaseTask extends AsyncTask<Long, Integer, UserAchievementModels> {

    @Override
    protected UserAchievementModels doInBackground(Long... params) {
        UserAchievementModels userAchievementModels = new UserAchievementModels();
        Connection conn = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            Statement statement = conn.createStatement();
            String queryString = "EXEC getUserInProgressAchievements " + params[0].toString();
            ResultSet rs = statement.executeQuery(queryString);

            while (rs.next()) {
                userAchievementModels.add(new UserAchievementModel(rs.getInt("userAchievementsId"), rs.getString("Merchant"), rs.getString("Achievement"), rs.getDouble("Progress"), rs.getInt("TrackingMax")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userAchievementModels;
    }

    @Override
    protected void onPostExecute(UserAchievementModels userAchievementModels) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(userAchievementModels);
    }
}
