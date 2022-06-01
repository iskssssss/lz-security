package cn.lz.security.auth.config;

import cn.lz.security.auth.filters.LzAuthFilterCore;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.config.FilterConfigurerBuilder;
import cn.lz.security.tool.config.BaseSecurityConfigurerAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
