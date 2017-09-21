package com.cwsm.user.service;

import com.cwsm.platfrom.model.dao.IRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yede on 2017/9/21.
 */
@Service
public class UserAccountService {
    @Autowired
    private IRepositoryService repositoryService;
//    @Transactional(readOnly = true)
//    public PageBean<UserAccount> searchUsers(UserQueryBean queryBean) {
//
//    }
}
