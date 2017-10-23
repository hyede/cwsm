package com.cwsm.customer.model.bean;

import com.cwsm.platfrom.model.bean.GenericObject;
import org.hibernate.validator.constraints.NotEmpty;

public class SaveCustomerBean extends GenericObject {
    private Long customerId;
    @NotEmpty(message="客户: 不能为空")
    private String customerName;
    @NotEmpty(message="微信号: 不能为空")
    private String openId;
//    @NotEmpty(message="手机: 不能为空")
    private String telephone;
    private  String address;
    private Long userId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}