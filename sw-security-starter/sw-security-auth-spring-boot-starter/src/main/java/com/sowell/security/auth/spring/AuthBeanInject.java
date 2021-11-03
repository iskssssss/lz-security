package com.sowell.security.auth.spring;

import com.sowell.security.auth.IcpAuth;
import com.sowell.security.auth.handler.AccessStatusHandler;
import com.sowell.security.auth.handler.CaptchaHandler;
import com.sowell.security.auth.handler.ICheckAccessAuthStatusHandler;
import com.sowell.security.auth.login.LoginErrorHandler;
import com.sowell.security.auth.login.LoginSuccessHandler;
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
public class AuthBeanInject extends IcpAuth {

	/**
	 * 自动注入<b>密码验证</b>
	 */
	@Autowired(required = false)
	public void injectPasswordEncoder(PasswordEncoder passwordEncoder) {
		if (IcpAuth.passwordEncoder != null) {
			SpringUtil.destroyBean(passwordEncoder);
			return;
		}
		IcpAuth.setPasswordEncoder(passwordEncoder);
	}

	/**
	 * 自动注入<b>校验认证状态处理器</b>
	 */
	@Autowired(required = false)
	public void injectCheckAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
		if (IcpAuth.checkAccessAuthStatusHandler != null) {
			SpringUtil.destroyBean(checkAccessAuthStatusHandler);
			return;
		}
		IcpAuth.setCheckAccessAuthStatusHandler(checkAccessAuthStatusHandler);
	}

	/**
	 * 自动注入<b>用户获取服务类</b>
	 */
	@Autowired(required = false)
	public void injectUserDetailsService(UserDetailsService userDetailsService) {
		if (IcpAuth.userDetailsService != null) {
			SpringUtil.destroyBean(userDetailsService);
			return;
		}
		IcpAuth.setUserDetailsService(userDetailsService);
	}

	/**
	 * 自动注入<b>验证码处理器</b>
	 */
	@Autowired(required = false)
	public void injectCaptchaHandler(CaptchaHandler captchaHandler) {
		if (IcpAuth.captchaHandler != null) {
			SpringUtil.destroyBean(captchaHandler);
			return;
		}
		IcpAuth.setCaptchaHandler(captchaHandler);
	}

	/**
	 * 自动注入<b>账号状态验证</b>
	 */
	@Autowired(required = false)
	public void injectAccessStatusHandler(AccessStatusHandler accessStatusHandler) {
		if (IcpAuth.accessStatusHandler != null) {
			SpringUtil.destroyBean(accessStatusHandler);
			return;
		}
		IcpAuth.setAccessStatusHandler(accessStatusHandler);
	}

	/**
	 * 自动注入<b>登录成功处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
		if (IcpAuth.loginSuccessHandler != null) {
			SpringUtil.destroyBean(loginSuccessHandler);
			return;
		}
		IcpAuth.setLoginSuccessHandler(loginSuccessHandler);
	}

	/**
	 * 自动注入<b>登录错误处理器</b>
	 */
	@Autowired(required = false)
	public void injectLoginErrorHandler(LoginErrorHandler loginErrorHandler) {
		if (IcpAuth.loginErrorHandler != null) {
			SpringUtil.destroyBean(loginErrorHandler);
			return;
		}
		IcpAuth.setLoginErrorHandler(loginErrorHandler);
	}
}
