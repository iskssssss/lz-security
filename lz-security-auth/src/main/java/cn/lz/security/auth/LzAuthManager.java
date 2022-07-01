package cn.lz.security.auth;

import cn.lz.security.auth.config.AuthConfigurer;
import cn.lz.security.auth.defaults.*;
import cn.lz.security.auth.login.AccessStatusHandler;
import cn.lz.security.auth.login.LoginService;
import cn.lz.security.auth.login.CaptchaHandler;
import cn.lz.security.auth.login.ICheckAccessAuthStatusHandler;
import cn.lz.security.auth.login.AuthErrorHandler;
import cn.lz.security.auth.login.AuthSuccessHandler;
import cn.lz.security.auth.defaults.LoginServiceDefault;
import cn.lz.security.auth.logout.LogoutService;
import cn.lz.security.auth.logout.LogoutServiceDefault;
import cn.lz.security.auth.service.CredentialEncoder;
import cn.lz.security.auth.service.UserDetailsService;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 14:49
 */
public class LzAuthManager {

	//====================================================================================================================================

	protected static final AuthConfigurer AUTH_CONFIGURER = new AuthConfigurer();

	public static AuthConfigurer getAuthConfigurer() {
		return AUTH_CONFIGURER;
	}

	//====================================================================================================================================

	protected static LoginService loginService;

	public static LoginService getLoginService() {
		if (LzAuthManager.loginService == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.loginService == null) {
					LzAuthManager.loginService = new LoginServiceDefault();
				}
			}
		}
		return LzAuthManager.loginService;
	}

	public static void setLoginService(LoginService loginService) {
		LzAuthManager.loginService = loginService;
	}

	//====================================================================================================================================

	protected static LogoutService logoutService;

	public static LogoutService getLogoutService() {
		if (LzAuthManager.logoutService == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.logoutService == null) {
					LzAuthManager.logoutService = new LogoutServiceDefault();
				}
			}
		}
		return LzAuthManager.logoutService;
	}

	public static void setLogoutService(LogoutService logoutService) {
		LzAuthManager.logoutService = logoutService;
	}

	//====================================================================================================================================

	/**
	 * 密码验证
	 */
	protected static volatile CredentialEncoder credentialEncoder;

	public static CredentialEncoder getCredentialEncoder() {
		if (LzAuthManager.credentialEncoder == null) {
			synchronized (LzAuthManager.class) {
				if (LzAuthManager.credentialEncoder == null) {
					LzAuthManager.credentialEncoder = new CredentialEncoderDefault();
				}
			}
		}
		return LzAuthManager.credentialEncoder;
	}

	public static void setCredentialEncoder(CredentialEncoder credentialEncoder) {
		LzAuthManager.credentialEncoder = credentialEncoder;
	}

	//====================================================================================================================================

	/**
	 * 用户获取服务类
	 */
	protected static UserDetailsService userDetailsService;

	public static UserDetailsService getUserDetailsService() {
		if (userDetailsService == null) {
			synchronized (LzAuthManager.class) {
				if (userDetailsService == null) {
					userDetailsService = new UserDetailsServiceDefault();
				}
			}
		}
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
					LzAuthManager.authSuccessHandler = new AuthSuccessHandlerDefault();
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
					LzAuthManager.authErrorHandler = new AuthErrorHandlerDefault();
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
					LzAuthManager.checkAccessAuthStatusHandler = new CheckAccessAuthStatusHandlerDefault();
				}
			}
		}
		return LzAuthManager.checkAccessAuthStatusHandler;
	}

	public static void setCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		LzAuthManager.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
	}
}
