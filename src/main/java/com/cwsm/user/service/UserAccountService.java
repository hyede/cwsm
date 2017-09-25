package com.cwsm.user.service;

import com.cwsm.customer.model.bean.CustomerBean;
import com.cwsm.customer.model.entity.Customer;
import com.cwsm.customer.model.entity.QCustomer;
import com.cwsm.platfrom.exception.ErrorCode;
import com.cwsm.platfrom.exception.ServiceException;
import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.dao.IRepositoryService;
import com.cwsm.platfrom.model.dao.QueryCriteria;
import com.cwsm.platfrom.model.dao.impl.QueryCriteriaBuilder;
import com.cwsm.user.model.bean.LoginUserBean;
import com.cwsm.user.model.bean.SaveUserBean;
import com.cwsm.user.model.bean.UserAccountBean;
import com.cwsm.user.model.bean.UserQueryBean;
import com.cwsm.user.model.entity.QUserAccount;
import com.cwsm.user.model.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yede on 2017/9/21.
 */
@Service
public class UserAccountService {
    @Autowired
    private IRepositoryService repositoryService;

    @Transactional(readOnly = true)
    public PageBean<UserAccountBean> searchUsers(UserQueryBean queryBean) {
        PageBean<UserAccountBean> resultBean = new PageBean<UserAccountBean>();
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(UserAccount.class);
        QUserAccount qUserAccount = QUserAccount.userAccount;

        queryCriteriaBuilder.setOrder(queryBean.getOrder());
        queryCriteriaBuilder.setPageStart(queryBean.getPageStart());
        queryCriteriaBuilder.setPageSize(queryBean.getPageSize());

        if (!StringUtils.isEmpty(queryBean.getUserName())) {
            queryCriteriaBuilder.addCondition(qUserAccount.userName.like("%" + queryBean.getUserName() + "%"));
        }


        QueryCriteria criteria = queryCriteriaBuilder.build();
        PageBean<UserAccount> pageBean = repositoryService.query(criteria);
        List<UserAccountBean> userAccountBeans = new ArrayList<>();
        for (UserAccount userAccount : pageBean.getResult()) {
            userAccountBeans.add(UserAccountBean.toUserAccountBean(userAccount));
        }

        resultBean = PageBean.copy(pageBean, userAccountBeans);

        return resultBean;
    }

    @Transactional
    public UserAccountBean getUserAccountById(Long userId) {
        UserAccount userAccount = repositoryService.getById(UserAccount.class, userId);
        if (userAccount == null) {
            throw new ServiceException(ErrorCode.invalid_user_account, "无效的账号");
        }
        return UserAccountBean.toUserAccountBean(userAccount);
    }

    @Transactional
    public UserAccount saveUserAccount(SaveUserBean saveBean) {
        if (isExistUserName(saveBean.getUserName(),null)) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountStatus(UserAccount.Status.active);
        userAccount.setUserName(saveBean.getUserName());
        userAccount.setPassword(saveBean.getPassword());
        userAccount = repositoryService.save(userAccount);
        return userAccount;
    }

    @Transactional(readOnly = true)
    public boolean isExistUserName(String userName,Long userId) {
        QUserAccount qUserAccount = QUserAccount.userAccount;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(UserAccount.class);
        queryCriteriaBuilder.addCondition(qUserAccount.userName.eq(userName));
        if (userId!=null){
            queryCriteriaBuilder.addCondition(qUserAccount.id.ne(userId));
        }
        QueryCriteria queryCriteria = queryCriteriaBuilder.build();
        return repositoryService.exist(queryCriteria);
    }

    @Transactional
    public UserAccountBean login(LoginUserBean loginBean) {
        QUserAccount qUserAccount = QUserAccount.userAccount;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(UserAccount.class);
        if (loginBean.getUserName() != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.userName.eq(loginBean.getUserName()));
        }
        if (loginBean.getUserPwd() != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.password.eq(loginBean.getUserPwd()));
        }
        QueryCriteria<UserAccount> criteria = queryCriteriaBuilder.build();
        PageBean<UserAccount> results = repositoryService.query(criteria);
        UserAccount userAccount = results.getResult().get(0);


        return UserAccountBean.toUserAccountBean(userAccount);
    }


    @Transactional(readOnly = true)
    public PageBean<UserAccountBean> searchUserAccounts(UserQueryBean queryBean) {

        QUserAccount qUserAccount = QUserAccount.userAccount;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(UserAccount.class);
        if (queryBean.getUserName() != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.userName.contains(queryBean.getUserName()));
        }
        if (queryBean.getAccountStatus() != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.accountStatus.eq(queryBean.getAccountStatus()));
        }
        queryCriteriaBuilder.setOrder(queryBean.getOrder());
        queryCriteriaBuilder.setPageStart(queryBean.getPageStart());
        queryCriteriaBuilder.setPageSize(queryBean.getPageSize());
        QueryCriteria<UserAccount> criteria = queryCriteriaBuilder.build();

        PageBean<UserAccount> pageBean = repositoryService.query(criteria);
        List<UserAccountBean> userAccountBeans = new ArrayList<>();
        for (UserAccount userAccount : pageBean.getResult()) {
            userAccountBeans.add(UserAccountBean.toUserAccountBean(userAccount));
        }
        return PageBean.copy(pageBean, userAccountBeans);

    }

    @Transactional(readOnly = true)
    public UserAccountBean isExistUserNameAndPassword(String userName, String password) {
        QUserAccount qUserAccount = QUserAccount.userAccount;
        QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder(UserAccount.class);
        if (userName != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.userName.eq(userName));
        }
        queryCriteriaBuilder.setPageSize(Integer.MAX_VALUE);
        if (password != null) {
            queryCriteriaBuilder.addCondition(qUserAccount.password.eq(password));
        }
        QueryCriteria<UserAccount> criteria = queryCriteriaBuilder.build();
        PageBean<UserAccount> pageBean = repositoryService.query(criteria);
        if (pageBean.getResult() == null || pageBean.getResult().isEmpty()) {
            return null;
        }
        UserAccountBean userAccountBean = UserAccountBean.toUserAccountBean(pageBean.getResult().get(0));
        return userAccountBean;
    }

    @Transactional
    public UserAccountBean updateUserAccount(SaveUserBean saveUserBean) {
        UserAccount userAccount = repositoryService.getById(UserAccount.class, saveUserBean.getUserId());
        if (!userAccount.getUserName().equals(saveUserBean.getUserName())) {
            boolean isExist = isExistUserName(saveUserBean.getUserName(),null);
            if (isExist) {
                throw new ServiceException(ErrorCode.duplicate_user_name, "重复的账号名");
            }
        }

        if (saveUserBean.getPassword() != null) {
            userAccount.setPassword(saveUserBean.getPassword());
        }

        if (saveUserBean.getUserName() != null) {
            userAccount.setUserName(saveUserBean.getUserName());
        }

        repositoryService.save(userAccount);

        return UserAccountBean.toUserAccountBean(userAccount);
    }

}
