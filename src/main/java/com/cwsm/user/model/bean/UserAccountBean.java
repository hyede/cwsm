package com.cwsm.user.model.bean;

import com.cwsm.platfrom.model.bean.GenericObject;
import com.cwsm.user.model.entity.UserAccount;

import java.util.Date;

public class UserAccountBean extends GenericObject {

    private Long userId;
    private String userName;
    private String password;
    private UserAccount.Status accountStatus;//账户状态，分为激活、未激活以及禁用
    private Date registeredTime;//账号注册时间
    private Date lastLoginTime;//记录用户上次登陆时间
    private Date pwdLastModifiedTime;
    private Date expirationDate;
    private Date loginTime; //账号登陆时间，更新于每次用户登录时

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public UserAccount.Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(UserAccount.Status accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getPwdLastModifiedTime() {
        return pwdLastModifiedTime;
    }

    public void setPwdLastModifiedTime(Date pwdLastModifiedTime) {
        this.pwdLastModifiedTime = pwdLastModifiedTime;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
    public static UserAccountBean toUserAccountBean(UserAccount account) {
        if(account == null) {
            return null;
        }
        UserAccountBean accountBean = new UserAccountBean();
        accountBean.setUserName(account.getUserName());
        accountBean.setAccountStatus(account.getAccountStatus());
        accountBean.setPassword(account.getPassword());
        accountBean.setUserId(account.getId());

        return accountBean;
    }
}
