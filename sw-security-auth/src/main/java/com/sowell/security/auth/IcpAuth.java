package com.sowell.security.auth;

import com.sowell.security.auth.config.AuthConfigurer;
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
	protected static volatile PasswordEncoder passwordEncoder;

	public static PasswordEncoder getPasswordEncoder() {
		if (IcpAuth.passwordEncoder == null) {
			synchronized (IcpAuth.class) {
				if (IcpAuth.passwordEncoder == null) {
					IcpAuth.passwordEncoder = new DefaultPasswordEncoder();
				}
			}
		}
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
	protected static volatile LoginSuccessHandler loginSuccessHandler;

	public static LoginSuccessHandler getLoginSuccessHandler() {
		if (IcpAuth.loginSuccessHandler == null) {
			synchronized (IcpAuth.class) {
				if (IcpAuth.loginSuccessHandler == null) {
					IcpAuth.loginSuccessHandler = new DefaultLoginSuccessHandler();
				}
			}
		}
		return loginSuccessHandler;
	}

	public static void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
		IcpAuth.loginSuccessHandler = loginSuccessHandler;
	}

	//====================================================================================================================================

	/**
	 * 登录错误处理器
	 */
	protected static volatile LoginErrorHandler loginErrorHandler;

	public static LoginErrorHandler getLoginErrorHandler() {
		if (IcpAuth.loginErrorHandler == null) {
			synchronized (IcpAuth.class) {
				if (IcpAuth.loginErrorHandler == null) {
					IcpAuth.loginErrorHandler = new DefaultLoginErrorHandler();
				}
			}
		}
		return loginErrorHandler;
	}

	public static void setLoginErrorHandler(LoginErrorHandler loginErrorHandler) {
		IcpAuth.loginErrorHandler = loginErrorHandler;
	}
	//====================================================================================================================================

	/**
	 * 校验认证状态处理器
	 */
	protected static volatile ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

	public static ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
		if (IcpAuth.checkAccessAuthStatusHandler == null) {
			synchronized (IcpAuth.class) {
				if (IcpAuth.checkAccessAuthStatusHandler == null) {
					IcpAuth.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
				}
			}
		}
		return IcpAuth.checkAccessAuthStatusHandler;
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
