package com.cwsm.customer.controller;

import com.cwsm.customer.model.bean.CustomerBean;
import com.cwsm.customer.model.bean.CustomerQueryBean;
import com.cwsm.customer.model.bean.SaveCustomerBean;
import com.cwsm.customer.service.CustomerService;
import com.cwsm.platfrom.model.bean.UserDetails;
import com.cwsm.platfrom.service.AppSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //创建客户
    @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
    public String saveCustomer(Model model, @Valid SaveCustomerBean customerBean, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("MSG", "出错啦！");
        } else {
            UserDetails userDetails = AppSec.getLoginUser();
            if (userDetails != null) {
                customerBean.setUserId((Long) userDetails.getUserId());
            }
            customerService.saveCustomer(customerBean);
            model.addAttribute("MSG", "提交成功！");
        }
        return "home";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void list(Model model, CustomerQueryBean queryBean) {
        model.addAttribute("pageBean", customerService.listCustomers(queryBean));
    }

}
