package toDelete;

public class DbInfo {
    private String currHost;
    private String currPort;
    private String currDatabase;
    private String currDb_userid;
    private String currDb_password;
    private String currQuery;

    public DbInfo(String query){
        setHost("dbinstance.clj6bmyeizyc.us-east-1.rds.amazonaws.com");
        setPort("1433");
        setDatabase("achievmentRewardsDB");
        setDb_userid("awsUser");
        setDb_password("awsPassword");
        setCurrQuery(query);
    }
    public void setHost(String host){
        currHost = host;
    }
    public String getHost(){
        return currHost;
    }

    public void setPort(String port){
        currPort = port;
    }
    public String getPort(){
        return currPort;
    }

    public void setDatabase(String database){
        currDatabase = database;
    }
    public String getDatabase(){
        return currDatabase;
    }

    public void setDb_userid(String db_userid){
        currDb_userid = db_userid;
    }
    public String getDb_userid(){
        return currDb_userid;
    }

    public void setDb_password(String db_password){
        currDb_password = db_password;
    }
    public String getDb_password(){
        return currDb_password;
    }

    public void setCurrQuery(String query){
        currQuery = query;
    }
    public String getCurrQuery(){
        return currQuery;
    }
}
