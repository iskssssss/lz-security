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
public class IcpAuthManager {

	//====================================================================================================================================

	protected static final AuthConfigurer AUTH_CONFIGURER = new AuthConfigurer();

	public static AuthConfigurer getAuthConfigurer() {
		return AUTH_CONFIGURER;
	}

	//====================================================================================================================================

	protected static AbstractAuthorizationHandler authorizationHandler;

	public static AbstractAuthorizationHandler getAuthorizationHandler() {
		if (IcpAuthManager.authorizationHandler == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.authorizationHandler == null) {
					IcpAuthManager.authorizationHandler = new AuthorizationHandler();
				}
			}
		}
		return IcpAuthManager.authorizationHandler;
	}

	public static void setAuthorizationHandler(AbstractAuthorizationHandler authorizationHandler) {
		IcpAuthManager.authorizationHandler = authorizationHandler;
	}

	//====================================================================================================================================

	protected static AbstractLogoutHandler logoutHandler;

	public static AbstractLogoutHandler getLogoutHandler() {
		if (IcpAuthManager.logoutHandler == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.logoutHandler == null) {
					IcpAuthManager.logoutHandler = new LogoutHandler();
				}
			}
		}
		return IcpAuthManager.logoutHandler;
	}

	public static void setLogoutHandler(AbstractLogoutHandler logoutHandler) {
		IcpAuthManager.logoutHandler = logoutHandler;
	}

	//====================================================================================================================================

	/**
	 * 密码验证
	 */
	protected static volatile PasswordEncoder passwordEncoder;

	public static PasswordEncoder getPasswordEncoder() {
		if (IcpAuthManager.passwordEncoder == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.passwordEncoder == null) {
					IcpAuthManager.passwordEncoder = new DefaultPasswordEncoder();
				}
			}
		}
		return IcpAuthManager.passwordEncoder;
	}

	public static void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		IcpAuthManager.passwordEncoder = passwordEncoder;
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
		IcpAuthManager.userDetailsService = userDetailsService;
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
		IcpAuthManager.captchaHandler = captchaHandler;
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
		IcpAuthManager.accessStatusHandler = accessStatusHandler;
	}

	//====================================================================================================================================

	/**
	 * 登录成功处理器
	 */
	protected static volatile AuthSuccessHandler authSuccessHandler;

	public static AuthSuccessHandler getLoginSuccessHandler() {
		if (IcpAuthManager.authSuccessHandler == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.authSuccessHandler == null) {
					IcpAuthManager.authSuccessHandler = new AuthSuccessHandlerDefaultImpl();
				}
			}
		}
		return IcpAuthManager.authSuccessHandler;
	}

	public static void setLoginSuccessHandler(AuthSuccessHandler authSuccessHandler) {
		IcpAuthManager.authSuccessHandler = authSuccessHandler;
	}

	//====================================================================================================================================

	/**
	 * 登录错误处理器
	 */
	protected static volatile AuthErrorHandler authErrorHandler;

	public static AuthErrorHandler getLoginErrorHandler() {
		if (IcpAuthManager.authErrorHandler == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.authErrorHandler == null) {
					IcpAuthManager.authErrorHandler = new AuthErrorHandlerDefaultImpl();
				}
			}
		}
		return authErrorHandler;
	}

	public static void setLoginErrorHandler(AuthErrorHandler authErrorHandler) {
		IcpAuthManager.authErrorHandler = authErrorHandler;
	}
	//====================================================================================================================================

	/**
	 * 校验认证状态处理器
	 */
	protected static volatile ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

	public static ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
		if (IcpAuthManager.checkAccessAuthStatusHandler == null) {
			synchronized (IcpAuthManager.class) {
				if (IcpAuthManager.checkAccessAuthStatusHandler == null) {
					IcpAuthManager.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
				}
			}
		}
		return IcpAuthManager.checkAccessAuthStatusHandler;
	}

	public static void setCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		IcpAuthManager.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
	}

	//====================================================================================================================================

	protected static LogoutService logoutService;

	public static LogoutService getLogoutService() {
		return logoutService;
	}

	public static void setLogoutService(LogoutService logoutService) {
		IcpAuthManager.logoutService = logoutService;
	}
}
