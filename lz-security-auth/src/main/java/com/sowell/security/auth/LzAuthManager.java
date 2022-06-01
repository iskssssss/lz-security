package com.sowell.security.auth;

import com.sowell.security.auth.config.AuthConfigurer;
import com.sowell.security.auth.defaults.DefaultCheckAccessAuthStatusHandler;
import com.sowell.security.auth.defaults.AuthErrorHandlerDefaultImpl;
import com.sowell.security.auth.defaults.AuthSuccessHandlerDefaultImpl;
import com.sowell.security.auth.defaults.DefaultPasswordEncoder;
import com.sowell.security.auth.handler.AbstractAuthorizationHandler;
import com.sowell.security.auth.handler.AccessStatusHandler;
import com.sowell.security.auth.handler.CaptchaHandler;
import com.sowell.security.auth.handler.ICheckAccessAuthStatusHandler;
import com.sowell.security.auth.handler.impl.AuthorizationHandler;
import com.sowell.security.auth.login.AuthErrorHandler;
import com.sowell.security.auth.login.AuthSuccessHandler;
import com.sowell.security.auth.logout.AbstractLogoutHandler;
import com.sowell.security.auth.logout.LogoutHandler;
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
public class LzAuthManager {

	//====================================================================================================================================

	protected static final AuthConfigurer AUTH_CONFIGURER = new AuthConfigurer();

	public static AuthConfigurer getAuthConfigurer() {
		return AUTH_CONFIGURER;
	}

	//====================================================================================================================================

	protected static AbstractAuthorizationHandler authorizationHandler;

	public static AbstractAuthorizationHandler getAuthorizationHandler() {
		if (LzAuthManager.authorizationHandler == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.authorizationHandler == null) {
					LzAuthManager.authorizationHandler = new AuthorizationHandler();
				}
			}
		}
		return LzAuthManager.authorizationHandler;
	}

	public static void setAuthorizationHandler(AbstractAuthorizationHandler authorizationHandler) {
		LzAuthManager.authorizationHandler = authorizationHandler;
	}

	//====================================================================================================================================

	protected static AbstractLogoutHandler logoutHandler;

	public static AbstractLogoutHandler getLogoutHandler() {
		if (LzAuthManager.logoutHandler == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.logoutHandler == null) {
					LzAuthManager.logoutHandler = new LogoutHandler();
				}
			}
		}
		return LzAuthManager.logoutHandler;
	}

	public static void setLogoutHandler(AbstractLogoutHandler logoutHandler) {
		LzAuthManager.logoutHandler = logoutHandler;
	}

	//====================================================================================================================================

	/**
	 * 密码验证
	 */
	protected static volatile PasswordEncoder passwordEncoder;

	public static PasswordEncoder getPasswordEncoder() {
		if (LzAuthManager.passwordEncoder == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.passwordEncoder == null) {
					LzAuthManager.passwordEncoder = new DefaultPasswordEncoder();
				}
			}
		}
		return LzAuthManager.passwordEncoder;
	}

	public static void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		LzAuthManager.passwordEncoder = passwordEncoder;
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
		LzAuthManager.userDetailsService = userDetailsService;
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
		LzAuthManager.captchaHandler = captchaHandler;
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
		LzAuthManager.accessStatusHandler = accessStatusHandler;
	}

	//====================================================================================================================================

	/**
	 * 登录成功处理器
	 */
	protected static volatile AuthSuccessHandler authSuccessHandler;

	public static AuthSuccessHandler getLoginSuccessHandler() {
		if (LzAuthManager.authSuccessHandler == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.authSuccessHandler == null) {
					LzAuthManager.authSuccessHandler = new AuthSuccessHandlerDefaultImpl();
				}
			}
		}
		return LzAuthManager.authSuccessHandler;
	}

	public static void setLoginSuccessHandler(AuthSuccessHandler authSuccessHandler) {
		LzAuthManager.authSuccessHandler = authSuccessHandler;
	}

	//====================================================================================================================================

	/**
	 * 登录错误处理器
	 */
	protected static volatile AuthErrorHandler authErrorHandler;

	public static AuthErrorHandler getLoginErrorHandler() {
		if (LzAuthManager.authErrorHandler == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.authErrorHandler == null) {
					LzAuthManager.authErrorHandler = new AuthErrorHandlerDefaultImpl();
				}
			}
		}
		return authErrorHandler;
	}

	public static void setLoginErrorHandler(AuthErrorHandler authErrorHandler) {
		LzAuthManager.authErrorHandler = authErrorHandler;
	}
	//====================================================================================================================================

	/**
	 * 校验认证状态处理器
	 */
	protected static volatile ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

	public static ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
		if (LzAuthManager.checkAccessAuthStatusHandler == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.checkAccessAuthStatusHandler == null) {
					LzAuthManager.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
				}
			}
		}
		return LzAuthManager.checkAccessAuthStatusHandler;
	}

	public static void setCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		LzAuthManager.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
	}

	//====================================================================================================================================

	protected static LogoutService logoutService;

	public static LogoutService getLogoutService() {
		return logoutService;
	}

	public static void setLogoutService(LogoutService logoutService) {
		LzAuthManager.logoutService = logoutService;
	}
}
