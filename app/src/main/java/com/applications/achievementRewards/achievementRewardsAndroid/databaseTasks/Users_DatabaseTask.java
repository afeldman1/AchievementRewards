package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.AllRewardsActivity;
import com.applications.achievementRewards.achievementRewardsAndroid.CurrentUser;
import com.applications.achievementRewards.achievementRewardsAndroid.HomeActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.SqlServerJtdsDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class Users_DatabaseTask extends AsyncTask<CurrentUser, Integer, CurrentUser> {
        //private final String LOG_TAG = getClass().getSimpleName();
        private ConnectionSource connectionSource;
        private Dao<CurrentUser, Integer> currentUserDao;
        private FragmentActivity myFragmentActivity;
        private TextView tv;

        public Users_DatabaseTask(FragmentActivity fragmentActivity, TextView intv) {
            tv = intv;
            myFragmentActivity = fragmentActivity;

            if (connectionSource == null) {
                try {
                    connectionSource = new JdbcConnectionSource("jdbc:jtds:sqlserver://dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com:1433/achievmentRewardsDB", "awsUser", "awsPassword", new SqlServerJtdsDatabaseType());
                    currentUserDao = DaoManager.createDao(connectionSource, CurrentUser.class);
                } catch (SQLException e) {
                    throw new RuntimeException("Problems initializing database objects", e);
                }
                //try {
                //    TableUtils.createTable(connectionSource, CurrentUser.class);
                //} catch (SQLException e) {
                //    // ignored
                //}
            }
        }

        @Override
        protected CurrentUser doInBackground(CurrentUser... params) {
            List<CurrentUser> list = null;
            try {
                // query for all of the data objects in the database
                //list = currentUserDao.queryForAll();

                list = currentUserDao.queryBuilder().where().eq(CurrentUser.CURR_ID, params[0].getID()).query();

                if (list.size() == 0)
                {
                    currentUserDao.create(params[0]);
                    list.add(params[0]);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return list.get(0);

        }

        @Override
        protected void onPostExecute(CurrentUser currentUsers) {
            //super.onPostExecute(currentUsers);
/*
            // our string builder for building the content-view
            StringBuilder sb = new StringBuilder();
            sb.append("got ").append(currentUsers.size()).append(" entries").append('\n');

            // if we already have items in the database
            int simpleC = 0;
            for (CurrentUser currentUser : currentUsers) {
                sb.append("------------------------------------------\n");
                sb.append("[" + simpleC + "] = ").append(currentUser).append('\n');
                simpleC++;
            }
            sb.append("------------------------------------------\n");

            for (CurrentUser currentUser : list) {
                currentUserDao.delete(currentUser);
                sb.append("deleted id ").append(currentUser.getID()).append('\n');
                simpleC++;
            }

            tv.setText(sb.toString());
*/
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(myFragmentActivity).edit();
            //editor = myFragmentActivity.getPreferences(Context.MODE_PRIVATE).edit();

            editor.remove("currUserID");
            editor.putLong("currUserID", currentUsers.getID());

            editor.remove("FirstName");
            editor.putString("FirstName", currentUsers.getCurrFirstName());

            editor.remove("LastName");
            editor.putString("LastName", currentUsers.getCurrLastName());

            editor.remove("Gender");
            editor.putString("Gender", currentUsers.getGender());

            editor.remove("Birthday");
            editor.putLong("Birthday", currentUsers.getBirthday() != null ? currentUsers.getBirthday().getTime() : 0);

            editor.remove("Email");
            editor.putString("Email", currentUsers.getEmail());

            editor.commit();

            Intent intent = new Intent(myFragmentActivity, HomeActivity.class);
            //intent.putExtra("currentUsers", currentUsers.get(0));
            myFragmentActivity.startActivity(intent);

            //EventBus.getDefault().post(currentUsers);
        }
}