package com.sowell.security.auth.config;

import com.sowell.security.auth.defaults.DefaultCheckAccessAuthStatusHandler;
import com.sowell.security.auth.defaults.DefaultLoginErrorHandler;
import com.sowell.security.auth.defaults.DefaultLoginSuccessHandler;
import com.sowell.security.auth.defaults.DefaultPasswordEncoder;
import com.sowell.security.auth.handler.AccessStatusHandler;
import com.sowell.security.auth.handler.CaptchaHandler;
import com.sowell.security.auth.handler.ICheckAccessAuthStatusHandler;
import com.sowell.security.auth.login.LoginErrorHandler;
import com.sowell.security.auth.login.LoginSuccessHandler;
import com.sowell.security.auth.logout.LogoutService;
import com.sowell.security.auth.service.PasswordEncoder;
import com.sowell.security.auth.service.UserDetailsService;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	public String getLoginUrl() {
		return this.authUrlConfig.loginUrl;
	}

	public String getLogoutUrl() {
		return this.authUrlConfig.logoutUrl;
	}

	public PasswordEncoder getPasswordEncoder() {
		if (this.loginHandlerInfo.passwordEncoder == null) {
			this.loginHandlerInfo.passwordEncoder = new DefaultPasswordEncoder();
		}
		return this.loginHandlerInfo.passwordEncoder;
	}

	public UserDetailsService getUserDetailsService() {
		return this.loginHandlerInfo.userDetailsService;
	}

	public CaptchaHandler getCaptchaHandler() {
		return this.loginHandlerInfo.captchaHandler;
	}

	public AccessStatusHandler getAccessStatusHandler() {
		return this.loginHandlerInfo.accessStatusHandler;
	}

	public LoginSuccessHandler getLoginSuccessHandler() {
		if (this.loginHandlerInfo.loginSuccessHandler == null) {
			this.loginHandlerInfo.loginSuccessHandler = new DefaultLoginSuccessHandler();
		}
		return this.loginHandlerInfo.loginSuccessHandler;
	}

	public LoginErrorHandler getLoginErrorHandler() {
		if (this.loginHandlerInfo.loginErrorHandler == null) {
			this.loginHandlerInfo.loginErrorHandler = new DefaultLoginErrorHandler();
		}
		return this.loginHandlerInfo.loginErrorHandler;
	}

	public ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
		if (this.loginHandlerInfo.checkAccessAuthStatusHandler == null) {
			this.loginHandlerInfo.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
		}
		return this.loginHandlerInfo.checkAccessAuthStatusHandler;
	}

	public String getIdentifierKey() {
		return this.loginHandlerInfo.identifierKey;
	}

	public String getCredentialKey() {
		return this.loginHandlerInfo.credentialKey;
	}

	public String getCodeKey() {
		return this.loginHandlerInfo.codeKey;
	}

	public String getRememberMeKey() {
		return this.loginHandlerInfo.rememberMeKey;
	}

	public LogoutService getLogoutService() {
		return this.logoutHandlerInfo.logoutService;
	}
}
