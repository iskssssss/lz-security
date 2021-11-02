package com.sowell.security.config;

import com.sowell.security.IcpManager;
import com.sowell.security.filter.IcpServletFilter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
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
    private IcpServletFilter filterContainer;

    /**
     * 初始化
     */
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

    @Override
    public void setEnvironment(Environment environment) {
        IcpConfig icpConfig = Binder.get(environment).bind("sw.security", IcpConfig.class).get();
        IcpManager.setIcpConfig(icpConfig);
        this.init();
    }

    @Bean
    protected FilterRegistrationBean<IcpServletFilter> initFilterRegistrationBean() {
        if (this.registration == null){
            this.registration = new FilterRegistrationBean<>();
            if (this.registration.getUrlPatterns().isEmpty()) {
                this.registration.addUrlPatterns("/*");
            }
            this.registration.setFilter(this.filterContainer);
            this.registration.setOrder(Integer.MIN_VALUE);
            this.registration.setName("filterContainer");
        }
        return this.registration;
    }
}
