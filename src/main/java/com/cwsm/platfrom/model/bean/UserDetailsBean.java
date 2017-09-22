package com.cwsm.platfrom.model.bean;

/**
 * Created by Ming on 6/25/17.
 */
public class UserDetailsBean implements UserDetails {

    private String UUID;
    private String organizationCode;
    private Object userId;
    private String userName;
    private String password;

    public UserDetailsBean() {
    }

    public UserDetailsBean(String organizationCode, Object userId, String userName, String password) {
        this.organizationCode = organizationCode;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.UUID = this.userId + "@" + this.organizationCode;
    }

    @Override
    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String UUID() {
        return UUID;
    }

    @Override
    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }
}
