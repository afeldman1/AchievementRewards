package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserAchievedAchievements_DatabaseTask extends AsyncTask<Long, Integer, List<String>> {

        public UserAchievedAchievements_DatabaseTask() {
        }

        @Override
        protected List<String> doInBackground(Long... params) {
            List<String> userAchievedAchievementsModels = new ArrayList<>();
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");

                String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
                Connection conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

                Statement statement = conn.createStatement();
                String queryString = "EXEC getAchievedAchievements " + params[0].toString();
                ResultSet rs = statement.executeQuery(queryString);

                while (rs.next()) {
                    userAchievedAchievementsModels.add(rs.getString(1) + ": " + rs.getString(2));
                }
            } catch (Exception e) {
                //Db_list.add("Error");
                e.printStackTrace();
            }

            return userAchievedAchievementsModels;

        }

        @Override
        protected void onPostExecute(List<String> userAchievedAchievementsModels) {
            //super.onPostExecute(currentUsers);

            EventBus.getDefault().post(userAchievedAchievementsModels);
        }
}