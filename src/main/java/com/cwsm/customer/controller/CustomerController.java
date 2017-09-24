package com.cwsm.customer.controller;

import com.cwsm.customer.model.bean.CustomerBean;
import com.cwsm.customer.model.bean.CustomerQueryBean;
import com.cwsm.customer.model.bean.SaveCustomerBean;
import com.cwsm.customer.service.CustomerService;
import com.cwsm.platfrom.exception.ServiceException;
import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.bean.UserDetails;
import com.cwsm.platfrom.service.AppSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //创建客户
    @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
    public ModelAndView saveCustomer(Map<String, Object> map, @Valid SaveCustomerBean customerBean, BindingResult result) {
        if (result.hasErrors()) {
            map.put("msg", result.getFieldError().getDefaultMessage());
            map.put("url", "/home");
            return new ModelAndView("fragments/error", map);
        } else {

            try {
                UserDetails userDetails = AppSec.getLoginUser();
                if (userDetails != null) {
                    customerBean.setUserId((Long) userDetails.getUserId());
                }
                customerService.saveCustomer(customerBean);
            } catch (ServiceException e) {
                map.put("msg", e.getMessage());
                map.put("url", "/home");
                return new ModelAndView("fragments/error", map);
            }

        }
        map.put("url", "/home");
        return new ModelAndView("fragments/success", map);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void listCustomers(Model model, CustomerQueryBean queryBean) {
        model.addAttribute("pageBean", customerService.listCustomers(queryBean));
    }





    @RequestMapping(value = "/checkOpenId", method = RequestMethod.GET)
    @ResponseBody
    public String checkOpenId(@RequestParam String openId,@RequestParam(required = false) Long additionalCustomerId) {
        boolean isExist = customerService.isExistCustomerByOpenId(openId, additionalCustomerId);
        if (isExist) {
            return "1";
        } else {
            return "-1";
        }
    }

    @RequestMapping(value = "/checkTel", method = RequestMethod.GET)
    @ResponseBody
    public String checkTel(String telephone,@RequestParam(required = false) Long additionalCustomerId) {
        boolean isExist = customerService.isExistCustomerByTel(telephone,additionalCustomerId);
        if (isExist) {
            return "1";
        } else {
            return "-1";
        }
    }


    @RequestMapping(value = "listCustomersByUserId", method = RequestMethod.GET)
    public ModelAndView listCustomersBy(CustomerQueryBean queryBean,Map<String, Object> map) {

        PageBean<CustomerBean> pageBean = customerService.listCustomers(queryBean);
        map.put("pageBean", pageBean);
        return new ModelAndView("myCustomerList", map);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteCustomerIdByCustomerId(long customerId,Map<String, Object> map) {
        customerService.deleteCustomerIdByCustomerId(customerId);
        map.put("url", "/customers/listCustomersByUserId?userId"+AppSec.getLoginUser().getUserId());
        return new ModelAndView("fragments/success", map);
    }

    @RequestMapping(value = "/getCustomerById", method = RequestMethod.GET)
    public ModelAndView getCustomerById(long customerId,Map<String, Object> map) {
        CustomerBean customerBean=customerService.getCustomerIdByCustomerId(customerId);
        map.put("customerBean", customerBean);
        return new ModelAndView("customerEdit", map);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateCustomerIdByCustomerId(Map<String, Object> map, @Valid SaveCustomerBean customerBean, BindingResult result) {
        if (result.hasErrors()) {
            map.put("msg", result.getFieldError().getDefaultMessage());
            map.put("url", "/home");
            return new ModelAndView("fragments/error", map);
        } else {
            try {

                customerService.updateCustomer(customerBean);
            } catch (ServiceException e) {
                map.put("msg", e.getMessage());
                map.put("url", "/home");
                return new ModelAndView("fragments/error", map);
            }
        }
        map.put("url", "/customers/listCustomersByUserId?userId"+AppSec.getLoginUser().getUserId());
        return new ModelAndView("fragments/success", map);
    }

}
