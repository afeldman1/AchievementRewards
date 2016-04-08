package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import org.greenrobot.eventbus.EventBus;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAchievementDetails_DatabaseTask extends AsyncTask<UserAchievementModel, Integer, UserAchievementModel> {

    @Override
    protected UserAchievementModel doInBackground(UserAchievementModel... params) {
        UserAchievementModel userAchievementModel = params[0];
        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            if(userAchievementModel.getUserAchievementId() == null)
            {
                String queryString = "EXEC getAchievementDetails @aID=?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setInt(1, userAchievementModel.getAchievementId());

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    userAchievementModel.setAchievementDescription(rs.getString("AchievementDescription"));
                    userAchievementModel.setRewardName(rs.getString("RewardName"));
                    userAchievementModel.setRewardDescription(rs.getString("RewardDescription"));
                    userAchievementModel.setMerchantId(rs.getInt("MerchantId"));
                }
            }
            else
            {
                String queryString = "EXEC getAchievementDetails @uaID=?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setInt(1, userAchievementModel.getUserAchievementId());

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    userAchievementModel.setAchievementId(rs.getInt("AchievementId"));
                    userAchievementModel.setAchievementDescription(rs.getString("AchievementDescription"));
                    userAchievementModel.setRewardName(rs.getString("RewardName"));
                    userAchievementModel.setRewardDescription(rs.getString("RewardDescription"));
                    userAchievementModel.setMerchantId(rs.getInt("MerchantId"));
                    //userAchievementModel.setLogoUrl(rs.getString("LogoUrl") == null ? null : new URL(rs.getString("LogoUrl")));
                }
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

        return userAchievementModel;
    }

    @Override
    protected void onPostExecute(UserAchievementModel userAchievementModel) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(userAchievementModel);
    }
}
