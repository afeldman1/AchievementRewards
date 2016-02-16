package com.applications.achievementRewards.achievementRewardsAndroid;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "tbl_Users")

public class CurrentUser {
    public static final String CURR_ID = "FBid";

    @DatabaseField(columnName = "FBid", canBeNull = false)
    private Long currID;
    @DatabaseField(columnName = "FirstName")
    private String currFirstName;
    @DatabaseField(columnName = "LastName")
    private String currLastName;
    @DatabaseField(columnName = "Email")
    private String currEmail;
    @DatabaseField(columnName = "Gender")
    private String currGender;
    @DatabaseField(columnName = "Birthday")
    private Date currBirthday;

    public CurrentUser() {
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "currID=" + currID +
                ", currFirstName='" + currFirstName + '\'' +
                ", currLastName='" + currLastName + '\'' +
                ", currEmail='" + currEmail + '\'' +
                ", currGender='" + currGender + '\'' +
                ", currBirthday=" + currBirthday +
                '}';
    }

    public void setID(Long id){
        currID = id;
    }
    public Long getID(){
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

    public void setEmail(String email){
        currEmail = email;
    }
    public String getEmail(){
        return currEmail;
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
