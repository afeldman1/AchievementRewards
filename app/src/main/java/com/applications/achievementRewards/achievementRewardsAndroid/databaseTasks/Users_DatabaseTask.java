package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.CurrentUserModel;
import com.applications.achievementRewards.achievementRewardsAndroid.HomeActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users_DatabaseTask extends AsyncTask<CurrentUserModel, Integer, CurrentUserModel> {
    private FragmentActivity myFragmentActivity;

    public Users_DatabaseTask(FragmentActivity fragmentActivity) {
        myFragmentActivity = fragmentActivity;
    }

    @Override
    protected CurrentUserModel doInBackground(CurrentUserModel... params) {
        CurrentUserModel currentUserModel = new CurrentUserModel();
        Connection conn = null;
        PreparedStatement preparedStatement;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB";
            conn = DriverManager.getConnection(ConnectionString, "awsUser", "awsPassword");

            String queryString = "EXEC getUser ?";
            preparedStatement = conn.prepareStatement(queryString);
            preparedStatement.setLong(1, params[0].getID());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                currentUserModel.setCurrentUserModel(rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email"), rs.getString("Gender"), rs.getDate("Birthday"));
                currentUserModel.setID(params[0].getID());
            }

            if (currentUserModel.getID() == null)
            {
                queryString = "EXEC createUser ?, ?, ?, ?, ?, ?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setLong(1, params[0].getID());
                preparedStatement.setString(2, params[0].getFirstName());
                preparedStatement.setString(3, params[0].getLastName());
                if (params[0].getEmail() != null)
                {
                    preparedStatement.setString(4, params[0].getEmail());
                }
                else
                {
                    preparedStatement.setNull(4, java.sql.Types.VARCHAR);
                }
                if (params[0].getGender() != null)
                {
                    preparedStatement.setString(5, params[0].getGender());
                }
                else
                {
                    preparedStatement.setNull(5, java.sql.Types.VARCHAR);
                }
                if (params[0].getBirthday() != null)
                {
                    preparedStatement.setDate(6, new java.sql.Date(params[0].getBirthday().getTime()));
                }
                else
                {
                    preparedStatement.setNull(6, java.sql.Types.DATE);
                }

                preparedStatement.executeUpdate();

                currentUserModel.setCurrentUserModel(params[0].getID(), params[0].getFirstName(), params[0].getLastName(), params[0].getEmail(), params[0].getGender(), params[0].getBirthday());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
               //preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return currentUserModel;
    }

    @Override
    protected void onPostExecute(CurrentUserModel currentUsersModel) {
        //super.onPostExecute(currentUsersModel);
/*
        // our string builder for building the content-view
        StringBuilder sb = new StringBuilder();
        sb.append("got ").append(currentUsersModel.size()).append(" entries").append('\n');

        // if we already have items in the database
        int simpleC = 0;
        for (CurrentUserModel currentUser : currentUsersModel) {
            sb.append("------------------------------------------\n");
            sb.append("[" + simpleC + "] = ").append(currentUser).append('\n');
            simpleC++;
        }
        sb.append("------------------------------------------\n");

        for (CurrentUserModel currentUser : list) {
            currentUserDao.delete(currentUser);
            sb.append("deleted id ").append(currentUser.getID()).append('\n');
            simpleC++;
        }

        tv.setText(sb.toString());
*/
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(myFragmentActivity).edit();

        editor.remove("currUserID");
        editor.putLong("currUserID", currentUsersModel.getID());

        editor.remove("FirstName");
        editor.putString("FirstName", currentUsersModel.getFirstName());

        editor.remove("LastName");
        editor.putString("LastName", currentUsersModel.getLastName());

        editor.remove("Gender");
        editor.putString("Gender", currentUsersModel.getGender());

        editor.remove("Birthday");
        editor.putLong("Birthday", currentUsersModel.getBirthday() != null ? currentUsersModel.getBirthday().getTime() : 0);

        editor.remove("Email");
        editor.putString("Email", currentUsersModel.getEmail());

        editor.commit();

        Intent intent = new Intent(myFragmentActivity, HomeActivity.class);
        myFragmentActivity.startActivity(intent);
    }
}