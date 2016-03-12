package com.applications.achievementRewards.achievementRewardsAndroid.objects;

import java.util.Date;

public class CurrentUserModel {
    public static final String CURR_ID = "FBid";
    private Long fbId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private Date birthday;

    public void setCurrentUserModel(Long fbId, String firstName, String lastName, String email, String gender, Date birthday) {
        this.fbId = fbId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public void setCurrentUserModel(String firstName, String lastName, String email, String gender, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public void setID(Long id){
        fbId = id;
    }
    public Long getID(){
        return fbId;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getName(){
        return firstName + ' ' + lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setGender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return gender;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    public Date getBirthday(){
        return birthday;
    }

}
