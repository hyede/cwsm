package com.cwsm.home.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 首页和登录基控制器
 * @author zhangqing
 */
@Controller
public class HomeController {


    private Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("now", new Date());
        return "index0";
    }

    @RequestMapping("/login")
    String index() {
        return "login";
    }



}

