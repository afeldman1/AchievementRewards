package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantLocModel;
import com.applications.achievementRewards.achievementRewardsAndroid.objects.MerchantModel;

import org.greenrobot.eventbus.EventBus;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Merchants_DatabaseTask extends AsyncTask<Integer, Integer, List<MerchantModel>> {

    @Override
    protected List<MerchantModel> doInBackground(Integer... params) {
        List<MerchantModel> merchantsModels = new ArrayList<>();
        //List<MerchantLocModel> merchantsLocsModel = null;
        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            String queryString = "EXEC getMerchants";
            preparedStatement = conn.prepareStatement(queryString);

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            while (rs.next()) {
                MerchantModel merchantModel = new MerchantModel();

                merchantModel.setMerchantId(rs.getInt("MerchantId"));
                merchantModel.setMerchantName(rs.getString("MerchantName"));
                merchantModel.setLogoUrl(rs.getString("LogoUrl") == null ? null : new URL(rs.getString("LogoUrl")));

                merchantsModels.add(merchantModel);
            }

            /*
            preparedStatement.getMoreResults();
            rs = preparedStatement.getResultSet();
            while (rs.next()) {
                MerchantLocModel merchantLocModel = new MerchantLocModel();

                merchantLocModel.setLat(rs.getDouble("Lat"));
                merchantLocModel.setLon(rs.getDouble("Long"));

                merchantsLocsModel.add(merchantLocModel);
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return merchantsModels;
    }

    @Override
    protected void onPostExecute(List<MerchantModel> merchantsModels) {
        //super.onPostExecute(currentUsers);

        EventBus.getDefault().post(merchantsModels);
    }
}
