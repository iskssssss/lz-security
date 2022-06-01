package cn.lz.demo.filter.auth.config;

import cn.lz.demo.filter.auth.config.handler.TestEncodeSwitchHandler;
import cn.lz.security.auth.config.AuthConfigurer;
import cn.lz.security.auth.config.AuthConfigurerBuilder;
import cn.lz.security.auth.config.SecurityAuthConfigurerAdapter;
import cn.lz.security.config.CoreConfigurer;
import cn.lz.security.config.CoreConfigurerBuilder;
import cn.lz.security.defaults.DefaultAuthDetails;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.config.FilterConfigurerBuilder;
import cn.lz.security.log.LzLoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 16:04
 */
@Configuration
public class AuthSecurityConfig extends SecurityAuthConfigurerAdapter {

	private final TestEncodeSwitchHandler testEncodeSwitchHandler;

	@Autowired
	public AuthSecurityConfig(
			TestEncodeSwitchHandler testEncodeSwitchHandler
	) {
		this.testEncodeSwitchHandler = testEncodeSwitchHandler;
	}

	@Override
	protected void config(CoreConfigurerBuilder<CoreConfigurer> coreConfigurer) {
		coreConfigurer
				.setEncryptSwitchHandler(testEncodeSwitchHandler);
		LzLoggerUtil.info(AuthSecurityConfig.class, "基础信息配置");
	}

	@Override
	protected void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer) {
		authConfigurer
				.userDetailsService(username -> {
					// 用户信息获取
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
					// 用户状态验证
				})
				// 登录信息配置
				.login().loginUrl("/api/login/login.do")
				.identifierKey("username").credentialKey("password")
				.and()
				// 登出信息配置
				.logout().logoutUrl("/api/logout/logout.do");
		LzLoggerUtil.info(AuthSecurityConfig.class, "认证信息配置");
	}

	@Override
	protected void filter(FilterConfigurerBuilder<FilterConfigurer>.FilterUrl filterConfigurer) {
		LzLoggerUtil.info(AuthSecurityConfig.class, "过滤信息配置");
		filterConfigurer
				.addExcludeUrls(
						"/favicon.ico", "/webjars/**", "/doc.html",
						"/swagger-resources", "/v2/api-docs", "/v2/api-docs-ext"
				)
				.addIncludeUrls("/**");
	}
}
