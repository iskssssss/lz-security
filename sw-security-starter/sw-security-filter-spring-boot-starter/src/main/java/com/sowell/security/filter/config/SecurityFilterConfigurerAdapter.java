package com.sowell.security.filter.config;

import com.sowell.security.filter.IcpFilterManager;
import com.sowell.security.filter.filters.IcpInterfaceFilterCore;
import com.sowell.security.tool.config.BaseSecurityConfigurerAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 过滤配置适配器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 10:12
 */
public abstract class SecurityFilterConfigurerAdapter extends BaseSecurityConfigurerAdapter {

	protected FilterRegistrationBean<IcpInterfaceFilterCore> registration;
	private IcpInterfaceFilterCore filterContainer;

	/**
	 * 初始化
	 */
	@Override
	protected void init() {
		this.filterContainer = new IcpInterfaceFilterCore();
		this.filter(IcpFilterManager.getFilterConfigurer());
	}

	/**
	 * 设置过滤范围
	 *
	 * @param urlPatterns 接口列表
	 */
	protected final void addUrlPatterns(String... urlPatterns) {
		registration.addUrlPatterns(urlPatterns);
	}

	/**
	 * 配置过滤信息
	 *
	 * @param filterConfigurer 配置信息
	 */
	protected abstract void filter(FilterConfigurerBuilder<FilterConfigurer> filterConfigurer);

	@Bean
	protected FilterRegistrationBean<IcpInterfaceFilterCore> initFilterRegistrationBean() {
		if (this.registration == null) {
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
