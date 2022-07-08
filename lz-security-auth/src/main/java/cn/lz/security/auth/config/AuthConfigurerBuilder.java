package cn.lz.security.auth.config;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.login.*;
import cn.lz.security.auth.logout.LogoutService;
import cn.lz.security.auth.service.CredentialEncoder;
import cn.lz.security.auth.service.UserDetailsService;

import java.util.Arrays;

/**
 * 认证相关配置信息设置类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 15:18
 */
public class AuthConfigurerBuilder<T extends AuthConfigurer> {

	private final AuthUrl authUrl = new AuthUrl();
	/**
	 * 认证登录信息
	 */
	protected final LoginHandlerInfo loginHandlerInfo = new LoginHandlerInfo();
	/**
	 * 认证登出信息
	 */
	protected final LogoutHandlerInfo logoutHandlerInfo = new LogoutHandlerInfo();

	/**
	 * 获取认证接口过滤设置器
	 *
	 * @return 接口过滤设置器
	 */
	public AuthUrl authUrl() {
		return authUrl;
	}

	/**
	 * 设置用户认证信息获取服务类
	 *
	 * @param userDetailsService 用户认证信息获取服务类
	 * @return this
	 */
	public AuthConfigurerBuilder<T> userDetailsService(UserDetailsService userDetailsService) {
		LzAuthManager.setUserDetailsService(userDetailsService);
		return this;
	}

	/**
	 * 设置验证码处理器
	 *
	 * @param captchaHandler 验证码处理器
	 * @return this
	 */
	public AuthConfigurerBuilder<T> captchaHandler(CaptchaHandler captchaHandler) {
		LzAuthManager.setCaptchaHandler(captchaHandler);
		return this;
	}

	/**
	 * 设置密码加解密处理器
	 *
	 * @param credentialEncoder 密码加解密处理器
	 * @return this
	 */
	public AuthConfigurerBuilder<T> passwordEncoder(CredentialEncoder credentialEncoder) {
		LzAuthManager.setCredentialEncoder(credentialEncoder);
		return this;
	}

	/**
	 * 设置账号状态验证处理器
	 *
	 * @param accessStatusHandler 账号状态验证处理器
	 * @return this
	 */
	public AuthConfigurerBuilder<T> accessStatusHandler(AccessStatusHandler accessStatusHandler) {
		LzAuthManager.setAccessStatusHandler(accessStatusHandler);
		return this;
	}

	/**
	 * 设置校验认证状态处理器
	 *
	 * @param checkAccessAuthStatusHandler 校验认证状态处理器
	 * @return this
	 */
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

	public class LoginHandlerInfo {

		/**
		 * 设置认证失败处理器
		 *
		 * @param authErrorHandler 认证失败处理器
		 * @return this
		 */
		public LoginHandlerInfo loginErrorHandler(AuthErrorHandler authErrorHandler) {
			LzAuthManager.setLoginErrorHandler(authErrorHandler);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置认证成功处理器
		 *
		 * @param authSuccessHandler 认证成功处理器
		 * @return this
		 */
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
			LzAuthManager.getLoginConfig().setLoginUrl(loginUrl);
			return this;
		}

		/**
		 * 设置存放标识的键值
		 *
		 * @param identifierKey 存放标识的键值
		 * @return this
		 */
		public LoginHandlerInfo identifierKey(String identifierKey) {
			LzAuthManager.getLoginConfig().setIdentifierKey(identifierKey);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置存放凭据的键值
		 *
		 * @param credentialKey 存放凭据的键值
		 * @return this
		 */
		public LoginHandlerInfo credentialKey(String credentialKey) {
			LzAuthManager.getLoginConfig().setCredentialKey(credentialKey);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置存放验证码的键值
		 *
		 * @param codeKey 存放验证码的键值
		 * @return this
		 */
		public LoginHandlerInfo codeKey(String codeKey) {
			LzAuthManager.getLoginConfig().setCodeKey(codeKey);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置存放键值的键值
		 *
		 * @param keyKey 存放键值的键值
		 * @return this
		 */
		public LoginHandlerInfo keyKey(String keyKey) {
			LzAuthManager.getLoginConfig().setKeyKey(keyKey);
			return LoginHandlerInfo.this;
		}

		/**
		 * 设置存放记住我的键值
		 *
		 * @param rememberMeKey 存放记住我的键值
		 * @return this
		 */
		public LoginHandlerInfo rememberMeKey(String rememberMeKey) {
			LzAuthManager.getLoginConfig().setRememberMeKey(rememberMeKey);
			return LoginHandlerInfo.this;
		}

		/**
		 * and
		 *
		 * @return this
		 */
		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}

	public class LogoutHandlerInfo {

		/**
		 * 设置登出地址
		 *
		 * @param logoutUrl 登出地址
		 */
		public LogoutHandlerInfo logoutUrl(String logoutUrl) {
			LzAuthManager.getLogoutConfig().setLogoutUrl(logoutUrl);
			return this;
		}

		/**
		 * 设置登出处理器
		 *
		 * @param logoutService 登出处理器
		 * @return this
		 */
		public LogoutHandlerInfo logoutService(LogoutService logoutService) {
			LzAuthManager.setLogoutService(logoutService);
			return this;
		}

		/**
		 * and
		 *
		 * @return this
		 */
		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}
	}

	public class AuthUrl {

		/**
		 * 设置匿名接口
		 *
		 * @param anonymousUrls 匿名接口列表
		 */
		public AuthUrl addAnonymousUrls(String... anonymousUrls) {
			addUrlHashSet(LzAuthManager.getAuthConfig().getAnonymousUrlList(), anonymousUrls);
			return this;
		}

		/**
		 * 设置需认证接口
		 *
		 * @param authUrls 需认证接口列表
		 */
		public AuthUrl addAuthUrls(String... authUrls) {
			addUrlHashSet(LzAuthManager.getAuthConfig().getAuthUrlList(), authUrls);
			return this;
		}

		public AuthConfigurerBuilder<T> and() {
			return AuthConfigurerBuilder.this;
		}


		private void addUrlHashSet(UrlHashSet urlHashSet, String... urls) {
			//urlHashSet.clear();
			if (urls == null || urls.length < 1) {
				return;
			}
			if (urls.length < 2) {
				urlHashSet.add(urls[0]);
			}
			urlHashSet.addAll(Arrays.asList(urls));
		}
	}
}
