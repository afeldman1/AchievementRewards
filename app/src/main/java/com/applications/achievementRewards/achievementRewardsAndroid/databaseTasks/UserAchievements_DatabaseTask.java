package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.UserAchievementsModel;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserAchievements_DatabaseTask extends AsyncTask<Long, Integer, List<UserAchievementsModel>> {

        public UserAchievements_DatabaseTask() {
        }

        @Override
        protected List<UserAchievementsModel> doInBackground(Long... params) {
            List<UserAchievementsModel> userAchievementModels = new ArrayList<>();
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");

                String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
                Connection conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

                Statement statement = conn.createStatement();
                String queryString = "EXEC getUserAchievements " + params[0].toString();
                ResultSet rs = statement.executeQuery(queryString);

                while (rs.next()) {
                    userAchievementModels.add(new UserAchievementsModel(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            } catch (Exception e) {
                //Db_list.add("Error");
                e.printStackTrace();
            }

            return userAchievementModels;

        }

        @Override
        protected void onPostExecute(List<UserAchievementsModel> userAchievementModels) {
            //super.onPostExecute(currentUsers);

            EventBus.getDefault().post(userAchievementModels);
        }
}