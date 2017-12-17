package com.mathheals.finans_bt;

/**
 * Created by user on 14.10.2017.
 */

public class User {
    private String userID;
    private String user_name;
    private String user_email;
    private String password;

    public String userID(){return userID;};
    public String getUser_name(){
        return user_name;
}
    public String getUser_email(){
    return user_email;
}
    public String getPassword(){
    return password;
}
   // public String getResim(){return Resim;}

    public User(){}
    public void setUserID(String userID){this.userID=userID;}
    public void setUser_name(String user_name){this.user_name=user_name;}
    public void setUser_email(String user_email){this.user_email=user_email;}
    public void setPassword(String password){this.password=password;}
    //public void setResim(String Resim){this.Resim=Resim;}
    public User(String userID,String user_name, String user_email, String password) {

        this.userID = userID;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
    }
}