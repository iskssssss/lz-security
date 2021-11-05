package com.sowell.demo.filter.auth.config;

import com.sowell.security.auth.config.AuthConfigurer;
import com.sowell.security.auth.config.AuthConfigurerBuilder;
import com.sowell.security.auth.config.SecurityAuthConfigurerAdapter;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.CoreConfigurerBuilder;
import com.sowell.security.defaults.DefaultAuthDetails;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.filter.config.FilterConfigurerBuilder;
import com.sowell.security.log.IcpLoggerUtil;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 16:04
 */
@Configuration
public class AuthSecurityConfig extends SecurityAuthConfigurerAdapter {

	@Override
	protected void config(CoreConfigurerBuilder<CoreConfigurer> coreConfigurer) {
		IcpLoggerUtil.info(AuthSecurityConfig.class, "基础信息配置");
	}

	@Override
	protected void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer) {
		authConfigurer
				.userDetailsService(username -> {
					DefaultAuthDetails defaultAuthDetails = new DefaultAuthDetails();
					if ("admin".equals(username)) {
						defaultAuthDetails.setId("admin");
						defaultAuthDetails.setIdentifier(username);
						defaultAuthDetails.setCredential("123456");
					} else {
						defaultAuthDetails.setId("user" + System.currentTimeMillis());
						defaultAuthDetails.setIdentifier(username);
						defaultAuthDetails.setCredential("123");
					}
					return defaultAuthDetails;
				})
				.accessStatusHandler(authDetails -> {

				})
				.setAuthBeforeHandler(params -> System.out.println("认证前"))
				.setAuthAfterHandler(params -> System.out.println("认证后"))
				.login()
				.loginUrl("/api/login/login.do")
				.identifierKey("username")
				.credentialKey("password")
				.and()
				.logout()
				.logoutUrl("/api/logout/logout.do");
		IcpLoggerUtil.info(AuthSecurityConfig.class, "认证信息配置");
	}

	@Override
	protected void filter(FilterConfigurerBuilder<FilterConfigurer> filterConfigurer) {
		IcpLoggerUtil.info(AuthSecurityConfig.class, "过滤信息配置");
		filterConfigurer.filterUrl().addIncludeUrls("/**");
	}
}