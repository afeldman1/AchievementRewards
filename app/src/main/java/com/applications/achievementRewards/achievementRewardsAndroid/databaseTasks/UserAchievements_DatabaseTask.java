package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAchievements_DatabaseTask extends AsyncTask<Long, Integer, UserAchievementModels> {

    @Override
    protected UserAchievementModels doInBackground(Long... params) {
        UserAchievementModels userAchievementModels = new UserAchievementModels();
        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            String queryString = "EXEC getUserAchievements ?";
            preparedStatement = conn.prepareStatement(queryString);
            preparedStatement.setLong(1, params[0]);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UserAchievementModel userAchievementModel = new UserAchievementModel();

                userAchievementModel.setUserAchievementId(rs.getInt("UserAchievementId"));
                userAchievementModel.setAchievementName(rs.getString("AchievementName"));
                userAchievementModel.setMerchantName(rs.getString("MerchantName"));
                userAchievementModel.setProgress(rs.getDouble("Progress"));
                if (rs.wasNull()) {
                    userAchievementModel.setProgress(null);
                }
                userAchievementModel.setTrackingMax(rs.getInt("TrackingMax"));
                if (rs.wasNull()) {
                    userAchievementModel.setTrackingMax(null);
                }
                userAchievementModel.setRedeemedAt(rs.getDate("RedeemedAt"));

                userAchievementModels.add(userAchievementModel);
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
