package com.applications.achievementRewards.achievementRewardsAndroid;

import android.os.AsyncTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteResultSetSP extends AsyncTask<Object, Integer, ResultSet> {
    String postExecuteClass;
    String postExecuteMethod;
    String postExecuteSecondaryClass;
    String postExecuteSecondaryMethod;

    MainActivityFragment myMainActivityFragment;

    public ExecuteResultSetSP(MainActivityFragment mainActivityFragment, String... postExecutionData) {
        myMainActivityFragment = mainActivityFragment;

        if (postExecutionData.length >= 2)
        {
            postExecuteClass = postExecutionData[0];
            postExecuteMethod = postExecutionData[1];
        }
        if (postExecutionData.length == 4)
        {
            postExecuteSecondaryClass = postExecutionData[2];
            postExecuteSecondaryMethod = postExecutionData[3];
        }
    }

    protected ResultSet doInBackground(Object... params) {

        String ConnectionString = "jdbc:jtds:sqlserver://" + ((DbInfo)params[0]).getHost() + ":" + ((DbInfo)params[0]).getPort() + "/" + ((DbInfo)params[0]).getDatabase();
        ResultSet rs = null;
        PreparedStatement cs=null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection conn = DriverManager.getConnection(ConnectionString, ((DbInfo)params[0]).getDb_userid(), ((DbInfo)params[0]).getDb_password());

            cs = conn.prepareStatement("EXECUTE " + ((DbInfo)params[0]).getCurrQuery());
            cs.setEscapeProcessing(true);
            cs.setQueryTimeout(90);

            rs = cs.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    /*public List resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    public Object convertResultSetToList(ResultSet rs) throws SQLException {

        rs.last();
        Object a[] =  new Object[rs.getMetaData().getColumnCount()];;
        rs.beforeFirst();

        int rw = 0;
        while (rs.next()) {
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                a[rw][i-1] = rs.getObject(i);
            }
            rw++;
        }

        return a;
    }*/

    @Override
    protected void onPostExecute(ResultSet rs) {
        //super.onPostExecute(strings);

        int a = 1;

        try {
            if ((postExecuteClass != null && postExecuteSecondaryClass == null) || (postExecuteClass != null && rs.getBoolean(0)))
            {
                //Object a = convertResultSetToList(rs);
                //myMainActivityFragment.displayRealWelcomeMessage(rs);


                //Class<?> c = Class.forName(postExecuteClass);
                //Class<?> c = Class.forName(myMainActivityFragment);
                //Method method = c.getClass().getMethod(postExecuteMethod);
                Method method = myMainActivityFragment.getClass().getMethod(postExecuteMethod);
                method.invoke(myMainActivityFragment, rs);

                //Class<?>c = Class.forName(.getClass());
                //Method m = myMainActivityFragment.g.getMethod(postExecuteMethod, ResultSet.class);
                //m.invoke(null, rs);


                //Method method = c.getClass().getDeclaredMethod(postExecuteMethod);
                //method.invoke(c, rs);
            }
            else if (postExecuteClass != null && postExecuteSecondaryClass != null && !rs.getBoolean(0))
            {
                Class<?> c = Class.forName(postExecuteSecondaryClass);
                Method method = c.getClass().getMethod(postExecuteSecondaryMethod);
                method.invoke(c, rs);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}