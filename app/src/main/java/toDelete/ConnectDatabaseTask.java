package toDelete;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.applications.achievementRewards.achievementRewardsAndroid.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectDatabaseTask extends AsyncTask<DbInfo, Integer, List<String>> {
    Activity myActivity;

    public ConnectDatabaseTask(Activity activity) {
        myActivity = activity;
    }

    protected List<String> doInBackground(DbInfo... params) {

        List<String> Db_list = new ArrayList<>();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String ConnectionString = "jdbc:jtds:sqlserver://" + params[0].getHost() + ":" + params[0].getPort() + "/achievmentRewardsDB";
            Connection conn = DriverManager.getConnection(ConnectionString, params[0].getDb_userid(), params[0].getDb_password());

            System.out.println("connected");
            Statement statement = conn.createStatement();
            String queryString = "select firstName, lastName from tbl_Users";
            ResultSet rs = statement.executeQuery(queryString);

            while (rs.next()) {
                Db_list.add(rs.getString(1));
            }
        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }

        // Escape early if cancel() is called
        //if (isCancelled()) break;

        return Db_list;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(List<String> result) {
        //super.onPostExecute(strings);
        TextView myTextView = (TextView) myActivity.findViewById(R.id.ha);
        myTextView.setText("Test: " + result.toString());


    }
}
