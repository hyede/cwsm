package com.cwsm.user.model.bean;

/**
 * Created by yede on 2017/9/22.
 */
public class SaveUserBean {
    private  String  userName;
    private  String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}