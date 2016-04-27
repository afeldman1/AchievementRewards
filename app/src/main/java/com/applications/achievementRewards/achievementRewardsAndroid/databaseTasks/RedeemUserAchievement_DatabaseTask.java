package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RedeemUserAchievement_DatabaseTask extends AsyncTask<Integer, Integer, Date> {

    @Override
    protected Date doInBackground(Integer... params) {
        Connection conn = null;
        PreparedStatement preparedStatement;
        Date redeemedAt = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            String queryString = "EXEC setAchievementRedeemed @uaID=?";
            preparedStatement = conn.prepareStatement(queryString);
            preparedStatement.setInt(1, params[0]);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                redeemedAt = rs.getTimestamp("RedeemedAt");
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

        return redeemedAt;
    }

    @Override
    protected void onPostExecute(Date redeemedAt) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(redeemedAt);
    }
}
