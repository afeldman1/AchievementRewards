package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantLocModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementModel;

import org.greenrobot.eventbus.EventBus;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adam Feldman on 3/24/2016.
 */
public class MerchantDetails_DatabaseTask extends AsyncTask<MerchantModel, Integer, MerchantModel> {

    @Override
    protected MerchantModel doInBackground(MerchantModel... params) {
        MerchantModel merchantModel = params[0];
        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            String queryString = "EXEC getMerchantDetails ?";
            preparedStatement = conn.prepareStatement(queryString);
            preparedStatement.setInt(1, merchantModel.getMerchantId());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            while (rs.next()) {
                merchantModel.setMerchantDescription(rs.getString("MerchantDescription"));
                merchantModel.setLogoUrl(rs.getString("LogoUrl") == null ? null : new URL(rs.getString("LogoUrl")));
            }

            preparedStatement.getMoreResults();
            rs = preparedStatement.getResultSet();
            while (rs.next()) {
                MerchantLocModel merchantLocModel = new MerchantLocModel();

                merchantLocModel.setAddress(rs.getString("Address"));
                merchantLocModel.setLat(rs.getDouble("Lat"));
                merchantLocModel.setLon(rs.getDouble("Long"));
                merchantLocModel.setPhoneNum(rs.getInt("PhoneNum"));
                merchantLocModel.setRad(rs.getDouble("Rad"));

                merchantModel.addMerchantLocModel(merchantLocModel);
            }

            preparedStatement.getMoreResults();
            rs = preparedStatement.getResultSet();
            while (rs.next()) {
                UserAchievementModel userAchievementModel = new UserAchievementModel();

                userAchievementModel.setAchievementId(rs.getInt("AchievementId"));
                userAchievementModel.setAchievementName(rs.getString("AchievementName"));
                userAchievementModel.setMerchantName(merchantModel.getMerchantName());

                merchantModel.addUserAchievementModel(userAchievementModel);
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

        return merchantModel;
    }

    @Override
    protected void onPostExecute(MerchantModel merchantsModel) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(merchantsModel);
    }
}
