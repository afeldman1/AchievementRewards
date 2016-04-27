package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.wrapper.UserAchievementModels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Adam Feldman on 4/26/2016.
 */
public class UpdateProgress_DatabaseTask extends AsyncTask<UserAchievementModels, Integer, Void> {

    @Override
    protected Void doInBackground(UserAchievementModels... params) {

        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            for (UserAchievementModel userAchievementModel : params[0]) {
                String queryString = "EXEC updateUserAchievementProgress @uaID=?, @progress=?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setInt(0, userAchievementModel.getUserAchievementId());
                preparedStatement.setDouble(1, userAchievementModel.getProgress());
                preparedStatement.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
