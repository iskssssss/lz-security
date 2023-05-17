package cn.lz.security.auth.config;

import cn.lz.security.auth.LzAuthFilterCore;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.filters.AnonymousFilter;
import cn.lz.security.auth.filters.AuthFilter;
import cn.lz.security.auth.filters.ResourceFilter;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.config.FilterConfigurerBuilder;
import cn.lz.security.filter.config.SecurityFilterConfigurerAdapter;
import cn.lz.security.filter.filters.ExcludeUrlFilter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 10:16
 */
public abstract class SecurityAuthConfigurerAdapter extends SecurityFilterConfigurerAdapter {

	@Override
	protected final void init() {
		super.filterContainer = new LzAuthFilterCore();
		this.auth(LzAuthManager.getAuthConfigurer());
		this.filter(LzFilterManager.getFilterConfigurer());
	}

	/**
	 * 配置认证信息
	 *
	 * @param authConfigurer 配置信息
	 */
	protected abstract void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer);

	@Override
	protected void filter(FilterConfigurerBuilder<FilterConfigurer> filterConfigurer) {
		ExcludeUrlFilter excludeUrlFilter = new ExcludeUrlFilter();
		AnonymousFilter anonymousFilter = new AnonymousFilter();
		AuthFilter authFilter = new AuthFilter();
		ResourceFilter resourceFilter = new ResourceFilter();
		filterConfigurer.filterConfig()
				.linkInterfacesFilter(excludeUrlFilter, anonymousFilter, authFilter, resourceFilter);
	}

	@Override
	public final void setEnvironment(Environment environment) {
		AuthConfig authConfig = Binder.get(environment).bind("lz.security.auth-config", AuthConfig.class).orElse(new AuthConfig());
		LoginConfig loginConfig = Binder.get(environment).bind("lz.security.auth-config.login-config", LoginConfig.class).orElse(new LoginConfig());
		LogoutConfig logoutConfig = Binder.get(environment).bind("lz.security.auth-config.logout-config", LogoutConfig.class).orElse(new LogoutConfig());
		LzAuthManager.setAuthConfig(authConfig);
		LzAuthManager.setLoginConfig(loginConfig);
		LzAuthManager.setLogoutConfig(logoutConfig);
		super.setEnvironment(environment);
	}
}
