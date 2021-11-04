package com.sowell.security.auth.spring;

import com.sowell.security.auth.IcpAuthManager;
import com.sowell.security.auth.handler.AccessStatusHandler;
import com.sowell.security.auth.handler.CaptchaHandler;
import com.sowell.security.auth.handler.ICheckAccessAuthStatusHandler;
import com.sowell.security.auth.login.AuthErrorHandler;
import com.sowell.security.auth.login.AuthSuccessHandler;
import com.sowell.security.auth.service.PasswordEncoder;
import com.sowell.security.auth.service.UserDetailsService;
import com.sowell.security.tool.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 10:42
 */
public class AuthManagerBeanInject extends IcpAuthManager {

	/**
	 * 自动注入<b>密码验证</b>
	 */
	@Autowired(required = false)
	public void injectPasswordEncoder(PasswordEncoder passwordEncoder) {
		if (IcpAuthManager.passwordEncoder != null) {
			SpringUtil.destroyBean(passwordEncoder);
			return;
		}
		IcpAuthManager.setPasswordEncoder(passwordEncoder);
	}

	/**
	 * 自动注入<b>校验认证状态处理器</b>
	 */
	@Autowired(required = false)
	public void injectCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		if (IcpAuthManager.checkAccessAuthStatusHandler != null) {
			SpringUtil.destroyBean(checkAccessAuthStatusHandler);
			return;
		}
		IcpAuthManager.setCheckAccessAuthStatusHandler(checkAccessAuthStatusHandler);
	}

	/**
	 * 自动注入<b>用户获取服务类</b>
	 */
	@Autowired(required = false)
	public void injectUserDetailsService(UserDetailsService userDetailsService) {
		if (IcpAuthManager.userDetailsService != null) {
			SpringUtil.destroyBean(userDetailsService);
			return;
		}
		IcpAuthManager.setUserDetailsService(userDetailsService);
	}

	/**
	 * 自动注入<b>验证码处理器</b>
	 */
	@Autowired(required = false)
	public void injectCaptchaHandler(CaptchaHandler captchaHandler) {
		if (IcpAuthManager.captchaHandler != null) {
			SpringUtil.destroyBean(captchaHandler);
			return;
		}
		IcpAuthManager.setCaptchaHandler(captchaHandler);
	}

	/**
	 * 自动注入<b>账号状态验证</b>
	 */
	@Autowired(required = false)
	public void injectAccessStatusHandler(AccessStatusHandler accessStatusHandler) {
		if (IcpAuthManager.accessStatusHandler != null) {
			SpringUtil.destroyBean(accessStatusHandler);
			return;
		}
		IcpAuthManager.setAccessStatusHandler(accessStatusHandler);
	}

	/**
	 * 自动注入<b>登录成功处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginSuccessHandler(AuthSuccessHandler authSuccessHandler) {
		if (IcpAuthManager.authSuccessHandler != null) {
			SpringUtil.destroyBean(authSuccessHandler);
			return;
		}
		IcpAuthManager.setLoginSuccessHandler(authSuccessHandler);
	}

	/**
	 * 自动注入<b>登录错误处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginErrorHandler(AuthErrorHandler authErrorHandler) {
		if (IcpAuthManager.authErrorHandler != null) {
			SpringUtil.destroyBean(authErrorHandler);
			return;
		}
		IcpAuthManager.setLoginErrorHandler(authErrorHandler);
	}
}
