package com.cwsm.user.model.bean;

import com.cwsm.platfrom.model.bean.QueryBean;
import com.cwsm.user.model.entity.UserAccount;

import java.util.List;

public class UserQueryBean extends QueryBean {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}