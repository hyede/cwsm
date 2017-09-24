package com.cwsm.platfrom.interceptor;

import com.cwsm.platfrom.config.WebMvcConf;
import com.cwsm.platfrom.model.bean.UserDetailsBean;
import com.cwsm.platfrom.service.AppSec;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserSecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute(WebMvcConf.SESSION_KEY);
        if (obj == null || !(obj instanceof UserDetailsBean)) {
            response.sendRedirect(request.getContextPath() + "/users/loginPage");
            return false;
        }

        AppSec.setLoginUser((UserDetailsBean) obj);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (AppSec.getLoginUser() != null) {
            request.getSession().setAttribute(WebMvcConf.SESSION_KEY, AppSec.getLoginUser());
        }
        AppSec.clearLoginUser();
    }
}