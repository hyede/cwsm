package com.cwsm.customer.model.entity;

import com.cwsm.platfrom.model.entity.AbstractEntity;
import com.cwsm.user.model.entity.UserAccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER")
public class Customer  extends AbstractEntity<Long> {

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_OPEN_ID")
    private String openId;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "ADDRESS")
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;

    @ManyToOne
    @JoinColumn(name = "USER_ACCOUNT_ID")
    private UserAccount userAccount;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
