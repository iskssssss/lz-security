package com.sowell.security.auth.config;

import com.sowell.security.auth.LzAuthManager;
import com.sowell.security.auth.handler.*;
import com.sowell.security.auth.login.AuthErrorHandler;
import com.sowell.security.auth.login.AuthSuccessHandler;
import com.sowell.security.auth.logout.LogoutService;
import com.sowell.security.auth.service.PasswordEncoder;
import com.sowell.security.auth.service.UserDetailsService;
import com.sowell.security.fun.LzFilterAuthStrategy;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 15:18
 */
public class AuthConfigurerBuilder<T extends AuthConfigurer> {

	protected final LoginHandlerInfo loginHandlerInfo;
	protected final LogoutHandlerInfo logoutHandlerInfo;
	protected LzFilterAuthStrategy authAfterHandler = params -> { };
	protected LzFilterAuthStrategy authBeforeHandler = params -> { };

	public AuthConfigurerBuilder() {
		this.loginHandlerInfo = new LoginHandlerInfo();
		this.logoutHandlerInfo = new LogoutHandlerInfo();
	}

	public AuthConfigurerBuilder<T> userDetailsService(UserDetailsService userDetailsService) {
		LzAuthManager.setUserDetailsService(userDetailsService);
		return this;
	}

	public AuthConfigurerBuilder<T> captchaHandler(CaptchaHandler captchaHandler) {
		LzAuthManager.setCaptchaHandler(captchaHandler);
		return this;
	}

	public AuthConfigurerBuilder<T> passwordEncoder(PasswordEncoder passwordEncoder) {
		LzAuthManager.setPasswordEncoder(passwordEncoder);
		return this;
	}

	public AuthConfigurerBuilder<T> accessStatusHandler(AccessStatusHandler accessStatusHandler) {
		LzAuthManager.setAccessStatusHandler(accessStatusHandler);
		return this;
	}

	public AuthConfigurerBuilder<T> checkAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		LzAuthManager.setCheckAccessAuthStatusHandler(checkAccessAuthStatusHandler);
		return this;
	}

	/**
	 * 获取登录处理信息设置器
	 *
	 * @return 登录处理信息设置器
	 */
	public LoginHandlerInfo login() {
		return loginHandlerInfo;
	}

	/**
	 * 获取登出处理信息设置器
	 *
	 * @return 登出处理信息设置器
	 */
	public LogoutHandlerInfo logout() {
		return logoutHandlerInfo;
	}

	/**
	 * 认证前处理
	 *
	 * @param authBeforeHandler 认证后处理器
	 * @return this
	 */
	public AuthConfigurerBuilder<T> setAuthBeforeHandler(LzFilterAuthStrategy authBeforeHandler) {
		this.authBeforeHandler = authBeforeHandler;
		return this;
	}

	/**
	 * 认证后处理
	 *
	 * @param authAfterHandler 认证后处理器
	 * @return this
	 */
	public AuthConfigurerBuilder<T> setAuthAfterHandler(LzFilterAuthStrategy authAfterHandler) {
		this.authAfterHandler = authAfterHandler;
		return this;
	}

	public class LoginHandlerInfo {

		protected String loginUrl = "/api/login/login.do";

		protected String identifierKey = "username";
		protected String credentialKey = "password";
		protected String codeKey = "code";
		protected String rememberMeKey = "rememberMe";

		public LoginHandlerInfo loginErrorHandler(AuthErrorHandler authErrorHandler) {
			LzAuthManager.setLoginErrorHandler(authErrorHandler);
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo loginSuccessHandler(AuthSuccessHandler authSuccessHandler) {
			LzAuthManager.setLoginSuccessHandler(authSuccessHandler);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置登录地址
		 *
		 * @param loginUrl 登录地址
		 */
		public LoginHandlerInfo loginUrl(String loginUrl) {
			this.loginUrl = loginUrl;
			return this;
		}

		public LoginHandlerInfo identifierKey(String identifierKey) {
			this.identifierKey = identifierKey;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo credentialKey(String credentialKey) {
			this.credentialKey = credentialKey;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo codeKey(String codeKey) {
			this.codeKey = codeKey;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo rememberMeKey(String rememberMeKey) {
			this.rememberMeKey = rememberMeKey;
			return LoginHandlerInfo.this;
		}

		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}

	public class LogoutHandlerInfo {
		protected String logoutUrl = "/api/logout/logout.do";

		/**
		 * 设置登出地址
		 *
		 * @param logoutUrl 登出地址
		 */
		public LogoutHandlerInfo logoutUrl(String logoutUrl) {
			this.logoutUrl = logoutUrl;
			return this;
		}

		public LogoutHandlerInfo logoutService(LogoutService logoutService) {
			LzAuthManager.setLogoutService(logoutService);
			return this;
		}

		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}
}
