package com.cwsm.platfrom.config;

import com.cwsm.platfrom.interceptor.UserSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

    public final static String SESSION_KEY = "user";
    @Autowired
    private UserSecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor =registry.addInterceptor(securityInterceptor);
        // 排除配置
        addInterceptor.excludePathPatterns("/users/regist");
        addInterceptor.excludePathPatterns("/users/registPage");
        addInterceptor.excludePathPatterns("/users/login");
        addInterceptor.excludePathPatterns("/users/loginPage");
        addInterceptor.excludePathPatterns("/webjarslocator/**");

        addInterceptor.addPathPatterns("/**");//配置登录拦截器拦截路径
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

        super.addResourceHandlers(registry);
    }
}