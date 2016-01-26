package com.example.adamfeldman.achievmentrewards;

public class DbInfo {
    private String currHost;
    private String currPort;
    private String currDb_userid;
    private String currDb_password;

    public DbInfo(String host, String port, String db_userid, String db_password){
        setHost(host);
        setPort(port);
        setDb_userid(db_userid);
        setDb_password(db_password);
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
}
