package com.cwsm.user.controller;

import com.cwsm.platfrom.config.WebMvcConf;
import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.bean.UserDetailsBean;
import com.cwsm.user.model.bean.SaveUserBean;
import com.cwsm.user.model.bean.UserAccountBean;
import com.cwsm.user.model.bean.UserQueryBean;
import com.cwsm.user.model.entity.UserAccount;
import com.cwsm.user.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by yede on 2017/9/22.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/registPage", method = RequestMethod.GET)
    public String toRegistPage() {
        return "registPage";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String toLoginPage() {
        return "loginPage";
    }


    @RequestMapping(value = "/doregist", method = RequestMethod.POST)
    @ResponseBody
    public String doregist(SaveUserBean saveUserBean, HttpSession session) {
        UserAccountBean userAccountBean = userAccountService.isExistUserNameAndPassword(saveUserBean.getUserName(), saveUserBean.getPassword());
        if (userAccountBean != null) {
            return "-1";
        } else {
            UserAccount userAccount = userAccountService.saveUserAccount(saveUserBean);
            if (userAccount != null) {
                // 设置session
                UserDetailsBean userDetailsBean = new UserDetailsBean();
                userDetailsBean.setUserId(userAccount.getId());
                userDetailsBean.setUserName(userAccount.getUserName());
                userDetailsBean.setPassword(userAccount.getPassword());
                session.setAttribute(WebMvcConf.SESSION_KEY, userDetailsBean);
                return "1";
            }
            return "3";

        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(SaveUserBean user, HttpSession session) {
        try {
            UserAccountBean userAccountBean = userAccountService.isExistUserNameAndPassword(user.getUserName(), user.getPassword());
            if (userAccountBean != null) {
                // 设置session
                UserDetailsBean userDetailsBean = new UserDetailsBean();
                userDetailsBean.setUserId(userAccountBean.getUserId());
                userDetailsBean.setUserName(userAccountBean.getUserName());
                userDetailsBean.setPassword(userAccountBean.getPassword());
                session.setAttribute(WebMvcConf.SESSION_KEY, userDetailsBean);
                return "1";
            }else {
                return "-1";
            }
        } catch (Exception e) {
            logger.error("登陆失败：" + e, e);
        }
        return "-2";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebMvcConf.SESSION_KEY);
        return "redirect:/loginPage";
    }


    //查询所有用户
    @GetMapping("")
    public ModelAndView list(UserQueryBean queryBean,Map<String, Object> map) {
        PageBean<UserAccountBean> pageBean = userAccountService.searchUsers(queryBean);
        map.put("pageBean", pageBean);
        return new ModelAndView("userList", map);
    }

}
