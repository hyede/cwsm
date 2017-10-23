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
import org.springframework.util.StringUtils;

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
        boolean isExist = isExistCustomer(customerBean.getOpenId(), customerBean.getTelephone());
        if (!isExist) {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerBean, customer);
            customer.setCreatedTime(Calendar.getInstance().getTime());
            if (customerBean.getUserId() != null) {
                UserAccount userAccount = repositoryService.getById(UserAccount.class, customerBean.getUserId());
                customer.setUserAccount(userAccount);
            }
            repositoryService.save(customer);
        } else {
            throw new ServiceException(ErrorCode.duplicate_customer, "重复的客户");
        }
    }

    //更新客户
    @Transactional
    public CustomerBean updateCustomer(SaveCustomerBean customerBean) {
        Customer customer = repositoryService.getById(Customer.class, customerBean.getCustomerId());
        if (customer == null) {
            throw new ServiceException(ErrorCode.invalid_customer, "无效的客户");
        }

        //先判断是否微信号重复
        boolean isOpenId = isExistCustomerByOpenId(customerBean.getOpenId(), customerBean.getCustomerId());
        if (isOpenId) {
            throw new ServiceException(ErrorCode.duplicate_openId, "重复的微信号");
        }
//        boolean isTel = isExistCustomerByTel(customerBean.getTelephone(), customerBean.getCustomerId());
//        if (isTel) {
//            throw new ServiceException(ErrorCode.duplicate_tel, "重复的手机号");
//        }

        if (customerBean.getAddress() != null) {
            customer.setAddress(customerBean.getAddress());
        }

        if (customerBean.getOpenId() != null) {
            customer.setOpenId(customerBean.getOpenId());
        }
        if (customerBean.getTelephone() != null) {
            customer.setTelephone(customerBean.getTelephone());
        }
        if (customerBean.getCustomerName() != null) {
            customer.setCustomerName(customerBean.getCustomerName());
        }
        repositoryService.save(customer);
        return CustomerBean.toCustomerBean(customer);
    }


    @Transactional
    public PageBean<CustomerBean> listCustomers(CustomerQueryBean queryBean) {
        PageBean<CustomerBean> resultBean = new PageBean<CustomerBean>();
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);
        QCustomer qCustomer = QCustomer.customer;

        if (queryBean.getOrderBy().equals("createdTime")) {
            queryCriteriaBuilder.setOrderBy(qCustomer.createdTime);
        }

        queryCriteriaBuilder.setOrder(queryBean.getOrder());
        queryCriteriaBuilder.setPageStart(queryBean.getPageStart());
        queryCriteriaBuilder.setPageSize(queryBean.getPageSize());

        if (!StringUtils.isEmpty(queryBean.getOpenId())) {
            queryCriteriaBuilder.addCondition(qCustomer.openId.eq(queryBean.getOpenId()));
        }
        if (!StringUtils.isEmpty(queryBean.getTelephone())) {
            queryCriteriaBuilder.addCondition(qCustomer.telephone.like("%" + queryBean.getTelephone() + "%"));
        }
        if (queryBean.getUserId() != null) {
            queryCriteriaBuilder.addCondition(qCustomer.userAccount.id.eq(queryBean.getUserId()));
        }
        if (!StringUtils.isEmpty(queryBean.getCustomerName())) {
            queryCriteriaBuilder.addCondition(qCustomer.customerName.like("%" + queryBean.getCustomerName() + "%"));
        }
        QueryCriteria criteria = queryCriteriaBuilder.build();
        PageBean<Customer> pageBean = repositoryService.query(criteria);
        List<CustomerBean> customerBeans = new ArrayList<>();
        for (Customer customer : pageBean.getResult()) {
            customerBeans.add(CustomerBean.toCustomerBean(customer));
        }

        resultBean = PageBean.copy(pageBean, customerBeans);

        return resultBean;
    }


    // 查询客户是否存在
    @Transactional
    public boolean isExistCustomer(String openId, String tel) {
        if (openId == null && tel == null) {
            return false;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);
        if (openId != null) {
            queryCriteriaBuilder.addCondition(qCustomer.openId.eq(openId));
        }
//        if (tel != null) {
//            queryCriteriaBuilder.addCondition(qCustomer.telephone.eq(tel));
//        }
        QueryCriteria queryCriteria = queryCriteriaBuilder.buildOR();
        return repositoryService.exist(queryCriteria);
    }

    // 查询客户是否存在 通过手机
    @Transactional
    public boolean isExistCustomerByTel(String tel, Long additionalCustomerId) {
        if (tel == null) {
            return false;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);

        queryCriteriaBuilder.addCondition(qCustomer.telephone.eq(tel));

        if (additionalCustomerId != null) {
            queryCriteriaBuilder.addCondition(qCustomer.id.ne(additionalCustomerId));
        }

        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        return repositoryService.exist(queryCriteria);
    }


    // 查询客户 通过手机
    @Transactional
    public Customer getCustomerByTel(String tel, Long additionalCustomerId) {
        if (tel == null) {
            return null;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);

        if (additionalCustomerId != null) {
            queryCriteriaBuilder.addCondition(qCustomer.id.ne(additionalCustomerId));
        }
        queryCriteriaBuilder.setPageSize(Integer.MAX_VALUE);
        queryCriteriaBuilder.addCondition(qCustomer.telephone.eq(tel));
        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        PageBean<Customer> pageBean = repositoryService.query(queryCriteria);
        if (pageBean.getResult() != null && !pageBean.getResult().isEmpty()) {
            return pageBean.getResult().get(0);
        }
        return null;
    }


    // 查询客户是否存在 通过微信号
    @Transactional
    public boolean isExistCustomerByOpenId(String openId, Long additionalCustomerId) {
        if (openId == null) {
            return false;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);

        queryCriteriaBuilder.addCondition(qCustomer.openId.eq(openId));
        if (additionalCustomerId != null) {
            queryCriteriaBuilder.addCondition(qCustomer.id.ne(additionalCustomerId));
        }

        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        return repositoryService.exist(queryCriteria);
    }

    // 查询客户是否存在 通过微信号
    @Transactional
    public Customer getCustomerByOpenId(String openId, Long additionalCustomerId) {
        if (openId == null) {
            return null;
        }
        QCustomer qCustomer = QCustomer.customer;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(Customer.class);

        queryCriteriaBuilder.addCondition(qCustomer.openId.eq(openId));
        if (additionalCustomerId != null) {
            queryCriteriaBuilder.addCondition(qCustomer.id.ne(additionalCustomerId));
        }
        queryCriteriaBuilder.setPageSize(Integer.MAX_VALUE);
        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        PageBean<Customer> pageBean = repositoryService.query(queryCriteria);
        if (pageBean.getResult() != null && !pageBean.getResult().isEmpty()) {
            return pageBean.getResult().get(0);
        }
        return null;
    }

    // 通过id删除客户
    @Transactional
    public void deleteCustomerIdByCustomerId(Long customerId) {
        Customer customer = repositoryService.getById(Customer.class, customerId);
        if (customer != null) {
            repositoryService.delete(customer);
        }
    }


    // 通过id查询客户
    @Transactional
    public CustomerBean getCustomerIdByCustomerId(Long customerId) {
        Customer customer = repositoryService.getById(Customer.class, customerId);
        if (customer == null) {
            throw new ServiceException(ErrorCode.invalid_customer, "无效的客户");
        }
        return CustomerBean.toCustomerBean(customer);
    }

}
