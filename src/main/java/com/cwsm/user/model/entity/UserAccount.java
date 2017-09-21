package com.cwsm.user.model.entity;

import com.cwsm.platfrom.model.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by yede on 2017/9/21.
 */
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount  extends AbstractEntity<Long> {

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PWD")
    private String password;

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
