package com.sowell.security.auth.spring;

import com.sowell.security.auth.LzAuthManager;
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
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 10:42
 */
public class AuthManagerBeanInject extends LzAuthManager {

	/**
	 * 自动注入<b>密码验证</b>
	 */
	@Autowired(required = false)
	public void injectPasswordEncoder(PasswordEncoder passwordEncoder) {
		if (LzAuthManager.passwordEncoder != null) {
			SpringUtil.destroyBean(passwordEncoder);
			return;
		}
		LzAuthManager.setPasswordEncoder(passwordEncoder);
	}

	/**
	 * 自动注入<b>校验认证状态处理器</b>
	 */
	@Autowired(required = false)
	public void injectCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		if (LzAuthManager.checkAccessAuthStatusHandler != null) {
			SpringUtil.destroyBean(checkAccessAuthStatusHandler);
			return;
		}
		LzAuthManager.setCheckAccessAuthStatusHandler(checkAccessAuthStatusHandler);
	}

	/**
	 * 自动注入<b>用户获取服务类</b>
	 */
	@Autowired(required = false)
	public void injectUserDetailsService(UserDetailsService userDetailsService) {
		if (LzAuthManager.userDetailsService != null) {
			SpringUtil.destroyBean(userDetailsService);
			return;
		}
		LzAuthManager.setUserDetailsService(userDetailsService);
	}

	/**
	 * 自动注入<b>验证码处理器</b>
	 */
	@Autowired(required = false)
	public void injectCaptchaHandler(CaptchaHandler captchaHandler) {
		if (LzAuthManager.captchaHandler != null) {
			SpringUtil.destroyBean(captchaHandler);
			return;
		}
		LzAuthManager.setCaptchaHandler(captchaHandler);
	}

	/**
	 * 自动注入<b>账号状态验证</b>
	 */
	@Autowired(required = false)
	public void injectAccessStatusHandler(AccessStatusHandler accessStatusHandler) {
		if (LzAuthManager.accessStatusHandler != null) {
			SpringUtil.destroyBean(accessStatusHandler);
			return;
		}
		LzAuthManager.setAccessStatusHandler(accessStatusHandler);
	}

	/**
	 * 自动注入<b>登录成功处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginSuccessHandler(AuthSuccessHandler authSuccessHandler) {
		if (LzAuthManager.authSuccessHandler != null) {
			SpringUtil.destroyBean(authSuccessHandler);
			return;
		}
		LzAuthManager.setLoginSuccessHandler(authSuccessHandler);
	}

	/**
	 * 自动注入<b>登录错误处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginErrorHandler(AuthErrorHandler authErrorHandler) {
		if (LzAuthManager.authErrorHandler != null) {
			SpringUtil.destroyBean(authErrorHandler);
			return;
		}
		LzAuthManager.setLoginErrorHandler(authErrorHandler);
	}
}
