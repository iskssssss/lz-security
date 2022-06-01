package com.sowell.security.auth.config;

import com.sowell.security.auth.LzAuthManager;
import com.sowell.security.auth.filters.LzAuthFilterCore;
import com.sowell.security.filter.LzFilterManager;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.filter.config.FilterConfigurerBuilder;
import com.sowell.security.tool.config.BaseSecurityConfigurerAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 10:16
 */
public abstract class SecurityAuthConfigurerAdapter extends BaseSecurityConfigurerAdapter {

	protected FilterRegistrationBean<LzAuthFilterCore> registration;
	private LzAuthFilterCore lzAuthFilter;

	@Override
	protected void init() {
		this.lzAuthFilter = new LzAuthFilterCore();
		this.auth(LzAuthManager.getAuthConfigurer());
		this.filter(LzFilterManager.getFilterConfigurer().filterUrl());
	}

	/**
	 * 配置认证信息
	 *
	 * @param authConfigurer 配置信息
	 */
	protected abstract void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer);

	/**
	 * 配置过滤信息
	 *
	 * @param filterConfigurer 过滤信息
	 */
	protected abstract void filter(FilterConfigurerBuilder<FilterConfigurer>.FilterUrl filterConfigurer);

	@Bean
	protected FilterRegistrationBean<LzAuthFilterCore> initFilterRegistrationBean() {
		if (this.registration == null) {
			this.registration = new FilterRegistrationBean<>();
			if (this.registration.getUrlPatterns().isEmpty()) {
				this.registration.addUrlPatterns("/*");
			}
			this.registration.setFilter(this.lzAuthFilter);
			this.registration.setOrder(Integer.MIN_VALUE);
			this.registration.setName("authFilter");
		}
		return this.registration;
	}
}
