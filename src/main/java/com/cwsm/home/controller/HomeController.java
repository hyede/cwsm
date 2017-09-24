package com.cwsm.home.controller;

import com.cwsm.customer.model.bean.CustomerBean;
import com.cwsm.customer.model.bean.CustomerQueryBean;
import com.cwsm.customer.model.bean.SaveCustomerBean;
import com.cwsm.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页和登录基控制器
 *
 * @author zhangqing
 */
@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        prepareCustomerBeanModel();
        CustomerQueryBean customerQueryBean = new CustomerQueryBean();
        customerQueryBean.setOrderBy("createdTime");
        customerQueryBean.setPageSize(5);
        model.addAttribute("pageBean", customerService.listCustomers(customerQueryBean));
        return "home";
    }

    @ModelAttribute("saveCustomerBean")
    public SaveCustomerBean prepareCustomerBeanModel() {
        return new SaveCustomerBean();
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHome(Model model) {
        return index(model);

    }


}

