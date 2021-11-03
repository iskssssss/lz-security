package com.sowell.security.auth;

import com.sowell.security.auth.config.AuthConfigurer;
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
 * @date 2021/11/02 14:49
 */
public class IcpAuth {

	//====================================================================================================================================

	protected static final AuthConfigurer AUTH_CONFIGURER = new AuthConfigurer();

	public static AuthConfigurer getAuthConfigurer() {
		return AUTH_CONFIGURER;
	}

	//====================================================================================================================================


	/**
	 * 密码验证
	 */
	protected static PasswordEncoder passwordEncoder;

	public static PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public static void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		IcpAuth.passwordEncoder = passwordEncoder;
	}
	//====================================================================================================================================

	/**
	 * 用户获取服务类
	 */
	protected static UserDetailsService userDetailsService;

	public static UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public static void setUserDetailsService(UserDetailsService userDetailsService) {
		IcpAuth.userDetailsService = userDetailsService;
	}
	//====================================================================================================================================

	/**
	 * 验证码处理器
	 */
	protected static CaptchaHandler captchaHandler;

	public static CaptchaHandler getCaptchaHandler() {
		return captchaHandler;
	}

	public static void setCaptchaHandler(CaptchaHandler captchaHandler) {
		IcpAuth.captchaHandler = captchaHandler;
	}
	//====================================================================================================================================

	/**
	 * 账号状态验证
	 */
	protected static AccessStatusHandler accessStatusHandler;

	public static AccessStatusHandler getAccessStatusHandler() {
		return accessStatusHandler;
	}

	public static void setAccessStatusHandler(AccessStatusHandler accessStatusHandler) {
		IcpAuth.accessStatusHandler = accessStatusHandler;
	}
	//====================================================================================================================================

	/**
	 * 登录成功处理器
	 */
	protected static LoginSuccessHandler loginSuccessHandler;

	public static LoginSuccessHandler getLoginSuccessHandler() {
		return loginSuccessHandler;
	}

	public static void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
		IcpAuth.loginSuccessHandler = loginSuccessHandler;
	}
	//====================================================================================================================================

	/**
	 * 登录错误处理器
	 */
	protected static LoginErrorHandler loginErrorHandler;

	public static LoginErrorHandler getLoginErrorHandler() {
		return loginErrorHandler;
	}

	public static void setLoginErrorHandler(LoginErrorHandler loginErrorHandler) {
		IcpAuth.loginErrorHandler = loginErrorHandler;
	}
	//====================================================================================================================================

	/**
	 * 校验认证状态处理器
	 */
	protected static ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

	public static ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
		return checkAccessAuthStatusHandler;
	}

	public static void setCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		IcpAuth.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
	}
	//====================================================================================================================================

	protected static LogoutService logoutService;

	public static LogoutService getLogoutService() {
		return logoutService;
	}

	public static void setLogoutService(LogoutService logoutService) {
		IcpAuth.logoutService = logoutService;
	}
}
