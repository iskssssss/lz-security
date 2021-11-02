package com.sowell.security.auth.config;

import com.sowell.security.auth.handler.*;
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
public class AuthConfigurerBuilder<T extends AuthConfigurer> {
	protected final AuthUrlConfig authUrlConfig;
	protected final LoginHandlerInfo loginHandlerInfo;
	protected final LogoutHandlerInfo logoutHandlerInfo;

	public AuthConfigurerBuilder() {
		this.authUrlConfig = new AuthUrlConfig();
		this.loginHandlerInfo = new LoginHandlerInfo();
		this.logoutHandlerInfo = new LogoutHandlerInfo();
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

	static class AuthUrlConfig {

		protected String loginUrl = "/api/login/login.do";
		protected String logoutUrl = "/api/logout/logout.do";

		/**
		 * 设置登录地址
		 *
		 * @param loginUrl 登录地址
		 */
		public AuthUrlConfig loginUrl(String loginUrl) {
			this.loginUrl = loginUrl;
			return this;
		}

		/**
		 * 设置登出地址
		 *
		 * @param logoutUrl 登出地址
		 */
		public AuthUrlConfig logoutUrl(String logoutUrl) {
			this.logoutUrl = logoutUrl;
			return this;
		}
	}

	public class LoginHandlerInfo {

		/**
		 * 密码验证
		 */
		protected PasswordEncoder passwordEncoder;
		/**
		 * 用户获取服务类
		 */
		protected UserDetailsService userDetailsService;
		/**
		 * 验证码处理器
		 */
		protected CaptchaHandler captchaHandler;
		/**
		 * 账号状态验证
		 */
		protected AccessStatusHandler accessStatusHandler;
		/**
		 * 登录成功处理器
		 */
		protected LoginSuccessHandler loginSuccessHandler;
		/**
		 * 登录错误处理器
		 */
		protected LoginErrorHandler loginErrorHandler;
		/**
		 * 校验认证状态处理器
		 */
		protected ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

		protected String identifierKey = "identifier";
		protected String credentialKey = "credential";
		protected String codeKey = "code";
		protected String rememberMeKey = "rememberMe";

		public LoginHandlerInfo userDetailsService(UserDetailsService userDetailsService) {
			this.userDetailsService = userDetailsService;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo captchaHandler(CaptchaHandler captchaHandler) {
			this.captchaHandler = captchaHandler;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo passwordEncoder(PasswordEncoder passwordEncoder) {
			this.passwordEncoder = passwordEncoder;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo accessStatusHandler(AccessStatusHandler accessStatusHandler) {
			this.accessStatusHandler = accessStatusHandler;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo loginErrorHandler(LoginErrorHandler loginErrorHandler) {
			this.loginErrorHandler = loginErrorHandler;
			return LoginHandlerInfo.this;
		}

		public LoginHandlerInfo loginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
			this.loginSuccessHandler = loginSuccessHandler;
			return LoginHandlerInfo.this;
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

		public LoginHandlerInfo checkAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
			this.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
			return LoginHandlerInfo.this;
		}

		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}

	public class LogoutHandlerInfo {
		protected LogoutService logoutService;

		public LogoutHandlerInfo logoutService(LogoutService logoutService) {
			this.logoutService = logoutService;
			return this;
		}

		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}
}
