package com.sowell.security.config;

import com.sowell.security.IcpManager;
import com.sowell.security.filter.IcpServletFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/21 9:08
 */
public abstract class SecurityConfigurerAdapter implements EnvironmentAware {

    private FilterRegistrationBean<IcpServletFilter> registration;

    @Autowired
    public void setRegistration(
            FilterRegistrationBean<IcpServletFilter> registration
    ) {
        this.registration = registration;
    }

    /**
     * 初始化
     */
    private void init() {
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

    @Override
    public void setEnvironment(Environment environment) {
        IcpConfig icpConfig = Binder.get(environment).bind("sw.security", IcpConfig.class).get();
        IcpManager.setIcpConfig(icpConfig);
        this.init();
    }

    /*@Bean
    protected FilterRegistrationBean<IcpServletFilter> initFilterRegistrationBean() {
        if (registration == null) {
            registration = new FilterRegistrationBean<>();
            if (registration.getUrlPatterns().isEmpty()) {
                registration.addUrlPatterns("/*");
            }
            registration.setFilter(this.filterContainer);
            registration.setOrder(Integer.MIN_VALUE);
            registration.setName("filterContainer");
        }
        return registration;
    }*/
}
