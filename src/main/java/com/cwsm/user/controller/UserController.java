package com.cwsm.user.controller;

import com.cwsm.home.controller.HomeController;
import com.cwsm.user.model.bean.SaveUserBean;
import com.cwsm.user.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yede on 2017/9/22.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private  UserAccountService userAccountService;

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(SaveUserBean user){
        try{
            System.out.println(user.toString());
//            if(userServices.hasUser(user)){
//                return "redirect:/user/index";
//            }else{
//                return "redirect:/";
//            }
        }catch (Exception e){
            logger.error("登陆失败："+e,e);
        }
        return  "redirect:/";
    }

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        try{

        }catch (Exception e){
            logger.error("登陆失败："+e,e);
        }
        return "page/index/index";
    }
}
