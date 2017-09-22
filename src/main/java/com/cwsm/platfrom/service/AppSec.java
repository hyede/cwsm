package com.cwsm.platfrom.service;


import com.cwsm.platfrom.model.bean.UserDetails;

/**
 * Created by Ming on 4/16/16.
 */
public class AppSec {

    private static final ThreadLocal<UserDetails> LOGIN_USER = new ThreadLocal();

    public static UserDetails getLoginUser() {
        return LOGIN_USER.get();
    }

    public static void setLoginUser(UserDetails loginUser) {
        LOGIN_USER.set(loginUser);
    }

    public static void clearLoginUser() {
        LOGIN_USER.remove();
    }
}
