package com.azilen.training.config;

import com.azilen.training.filter.RegisterLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RegisterLoginFilter> loggingFilter() {
        FilterRegistrationBean<RegisterLoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RegisterLoginFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
        //hello
    }
}
