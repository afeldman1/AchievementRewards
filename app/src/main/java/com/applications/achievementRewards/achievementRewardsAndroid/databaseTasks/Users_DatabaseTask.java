package com.applications.achievementRewards.achievementRewardsAndroid.databaseTasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.objects.CurrentUser;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.SqlServerJtdsDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.List;

public class Users_DatabaseTask extends AsyncTask<CurrentUser, Integer, List<CurrentUser>> {
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
        protected List<CurrentUser> doInBackground(CurrentUser... params) {
            List<CurrentUser> list = null;
            try {
                // query for all of the data objects in the database
                //list = currentUserDao.queryForAll();

                list = currentUserDao.queryBuilder().where().eq(CurrentUser.CURR_ID, params[0].getID()).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<CurrentUser> currentUsers) {
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
            EventBus.getDefault().post(currentUsers.get(0));
        }
}