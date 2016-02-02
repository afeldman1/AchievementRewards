package com.applications.achievementRewards.achievementRewardsAndroid;

import java.util.Date;

public class CurrentUser {
    private int currID;
    private String currFirstName;
    private String currLastName;
    private String currGender;
    private Date currBirthday;

    public void setID(int id){
        currID = id;
    }
    public int getID(){
        return currID;
    }

    public void setFirstName(String firstName){
        currFirstName = firstName;
    }
    public void setLastName(String lastName){
        currLastName = lastName;
    }
    public String getCurrFirstName(){
        return currFirstName;
    }
    public String getCurrLastName(){
        return currLastName;
    }
    public String getName(){
        return currFirstName + ' ' + currLastName;
    }

    public void setGender(String gender){
        currGender = gender;
    }
    public String getGender(){
        return currGender;
    }

    public void setBirthday(Date birthday){
        currBirthday = birthday;
    }
    public Date getBirthday(){
        return currBirthday;
    }

}
