package com.cwsm.user.model.entity;

import com.cwsm.platfrom.model.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


/**
 * Created by yede on 2017/9/21.
 */
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount  extends AbstractEntity<Long> {

    public enum Status {
        active,
        inactive
    }
    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PWD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
    private Status accountStatus;

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

    public Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }
}
