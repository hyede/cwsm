package com.cwsm.customer.model.bean;

import com.cwsm.customer.model.entity.Customer;
import com.cwsm.platfrom.model.bean.GenericObject;

import java.util.Date;

public class CustomerBean extends GenericObject {
    private Long customerId;
    private String customerName;
    private String openId;
    private String telephone;
    private  Long userId;
    private  String userName;
    private  String address;
    private Date createdTime;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public  static  CustomerBean toCustomerBean(Customer customer){
        if (customer==null){
            return  null;
        }
        CustomerBean customerBean=new CustomerBean();
        customerBean.setCustomerId(customer.getId());
        customerBean.setCustomerName(customer.getCustomerName());
        customerBean.setOpenId(customer.getOpenId());
        customerBean.setTelephone(customer.getTelephone());
        customerBean.setUserName(customer.getUserAccount()!=null?customer.getUserAccount().getUserName():null);
        customerBean.setUserId(customer.getUserAccount()!=null?customer.getUserAccount().getId():null);
        customerBean.setCreatedTime(customer.getCreatedTime());
        customerBean.setAddress(customer.getAddress());
        return  customerBean;
    }
}
