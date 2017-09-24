package com.cwsm.user.model.bean;

import com.cwsm.platfrom.model.bean.GenericObject;

public class LoginUserBean extends GenericObject {

    private String userName;
    private String userPwd;

    public LoginUserBean() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
