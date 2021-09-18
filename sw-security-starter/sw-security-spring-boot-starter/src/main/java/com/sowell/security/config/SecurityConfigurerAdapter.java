package com.sowell.security.config;

import com.sowell.security.IcpManager;
import com.sowell.security.auth.ICheckAccessAuthStatusHandler;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.defaults.*;
import com.sowell.security.filter.IcpServletFilter;
import com.sowell.security.handler.*;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.spring.BeanInject;
import com.sowell.security.spring.BeanRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
@Import({BeanRegister.class, BeanInject.class, ControllerExceptionHandler.class})
public abstract class SecurityConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(SecurityConfigurerAdapter.class);

    private FilterRegistrationBean<IcpServletFilter> registration;
    private IcpServletFilter filterContainer;

    private void init() {
        this.filterContainer = new IcpServletFilter();
        this.config(IcpManager.getFilterConfigurer());
    }

    /**
     * 配置认证和过滤信息
     *
     * @param filterConfigurer 配置信息
     */
    protected abstract void config(FilterConfigurer filterConfigurer);

    /**
     * 设置过滤范围
     *
     * @param urlPatterns 接口列表
     */
    protected final void addUrlPatterns(String... urlPatterns) {
        registration.addUrlPatterns(urlPatterns);
    }

    @Bean
    protected FilterRegistrationBean<IcpServletFilter> initFilterRegistrationBean() {
        if (registration == null) {
            registration = new FilterRegistrationBean<>();
            this.init();
            if (registration.getUrlPatterns().isEmpty()) {
                registration.addUrlPatterns("/api/*");
            }
            registration.setFilter(this.filterContainer);
            registration.setOrder(Integer.MIN_VALUE);
            registration.setName("filterContainer");
        }
        return registration;
    }
}
