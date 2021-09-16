package com.sowell.security.spring;

import com.sowell.security.IcpManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.filter.IcpServletFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
public abstract class SecurityConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(SecurityConfigurerAdapter.class);

    private IcpServletFilter filterContainer;

    public void init() {
        this.filterContainer = new IcpServletFilter();
        this.config(IcpManager.getFilterConfigurer());
    }

    protected abstract void config(FilterConfigurer filterConfigurer);

    @Bean
    @DependsOn({"beanRegisterInit"})
    protected FilterRegistrationBean<IcpServletFilter> initFilterRegistrationBean() {
        this.init();
        FilterRegistrationBean<IcpServletFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(this.filterContainer);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Integer.MIN_VALUE);
        registration.setName("filterContainer");
        return registration;
    }
}
