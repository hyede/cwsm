package com.cwsm.customer.service;

import com.cwsm.customer.model.bean.CustomerBean;
import com.cwsm.customer.model.bean.CustomerQueryBean;
import com.cwsm.customer.model.bean.SaveCustomerBean;
import com.cwsm.customer.model.entity.Customer;
import com.cwsm.customer.model.entity.QCustomer;
import com.cwsm.platfrom.exception.ErrorCode;
import com.cwsm.platfrom.exception.ServiceException;
import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.dao.IRepositoryService;
import com.cwsm.platfrom.model.dao.QueryCriteria;
import com.cwsm.platfrom.model.dao.impl.QueryCriteriaBuilder;
import com.cwsm.user.model.entity.UserAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private IRepositoryService repositoryService;

    //创建客户
    @Transactional
    public void saveCustomer(SaveCustomerBean customerBean) {

        boolean isExist=isExistCustomer(customerBean.getOpenId(),customerBean.getTelephone());
        if (!isExist){
            Customer customer=new Customer();
//        customer.setCustomerName(customerBean.getCustomerName());
//        customer.setOpenId(customerBean.getOpenId());
//        customer.setTelephone(customerBean.getTelephone());
//        customer.setAddress(customerBean.getAddress());
            BeanUtils.copyProperties(customerBean,customer);
            customer.setCreatedTime(Calendar.getInstance().getTime());
            if (customerBean.getUserId()!=null){
                 UserAccount userAccount= repositoryService.getById(UserAccount.class,customerBean.getUserId());
                customer.setUserAccount(userAccount);
            }
            repositoryService.save(customer);
        }else {
            throw new ServiceException(ErrorCode.duplicate_customer, "duplicate customer");
        }
    }


    @Transactional
    public PageBean<CustomerBean> listCustomers(CustomerQueryBean queryBean) {
        PageBean<CustomerBean> resultBean = new PageBean<CustomerBean> ();
        QueryCriteriaBuilder queryCriteriaBuilder=new QueryCriteriaBuilder(Customer.class);
        QCustomer qCustomer=QCustomer.customer;
//

        queryCriteriaBuilder.setOrder(queryBean.getOrder());
        queryCriteriaBuilder.setPageStart(queryBean.getPageStart());
        queryCriteriaBuilder.setPageSize(queryBean.getPageSize());

        if(queryBean.getOpenId() !=null) {
            queryCriteriaBuilder.addCondition(qCustomer.openId.eq(queryBean.getOpenId()));
        }
        if(queryBean.getTelephone()!=null){
            queryCriteriaBuilder.addCondition(qCustomer.telephone.like("%" + queryBean.getTelephone() + "%"));
        }

        QueryCriteria criteria = queryCriteriaBuilder.build();
        PageBean<Customer> pageBean = repositoryService.query(criteria);
        List<CustomerBean> customerBeans=new ArrayList<>();
        for(Customer customer : pageBean.getResult()) {
            customerBeans.add(CustomerBean.toCustomerBean(customer));
        }

        resultBean=PageBean.copy(resultBean,customerBeans);

        return resultBean;
    }


    // 查询客户是否存在
    @Transactional
    public boolean isExistCustomer(String openId,String tel) {
        if (openId==null&&tel==null){
            return false;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);
        if (openId!=null){
            queryCriteriaBuilder.addCondition(qCustomer.openId.eq(openId));
        }
        if (tel!=null){
            queryCriteriaBuilder.addCondition(qCustomer.telephone.eq(tel));
        }
        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        return repositoryService.exist(queryCriteria);
    }
}
