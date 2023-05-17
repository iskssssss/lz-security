package cn.lz.security.auth.config;

import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.auth.LzAuthManager;

/**
 * 认证相关配置信息获取类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	/**
	 * 获取匿名的接口列表
	 *
	 * @return 匿名的接口列表
	 */
	public UrlHashSet getAnonymousUrlList() {
		return LzAuthManager.getAuthConfig().getAnonymousUrlList();
	}

	/**
	 * 获取需认证的接口列表
	 *
	 * @return 需认证的接口列表
	 */
	public UrlHashSet getAuthUrlList() {
		return LzAuthManager.getAuthConfig().getAuthUrlList();
	}

	/**
	 * 获取登录地址
	 *
	 * @return 登录地址
	 */
	public String getLoginUrl() {
		return LzAuthManager.getLoginConfig().getLoginUrl();
	}

	/**
	 * 获取登出地址
	 *
	 * @return 登出地址
	 */
	public String getLogoutUrl() {
		return LzAuthManager.getLogoutConfig().getLogoutUrl();
	}

	/**
	 * 获取存放标识的键值
	 *
	 * @return 存放标识的键值
	 */
	public String getIdentifierKey() {
		return LzAuthManager.getLoginConfig().getIdentifierKey();
	}

	/**
	 * 获取存放凭据的键值
	 *
	 * @return 存放凭据的键值
	 */
	public String getCredentialKey() {
		return LzAuthManager.getLoginConfig().getCredentialKey();
	}

	/**
	 * 获取存放验证码的键值
	 *
	 * @return 存放验证码的键值
	 */
	public String getCodeKey() {
		return LzAuthManager.getLoginConfig().getCodeKey();
	}

	/**
	 * 获取存放键值的键值
	 *
	 * @return 存放键值的键值
	 */
	public String getKeyKey() {
		return LzAuthManager.getLoginConfig().getKeyKey();
	}

	/**
	 * 获取存放记住我的键值
	 *
	 * @return 存放记住我的键值
	 */
	public String getRememberMeKey() {
		return LzAuthManager.getLoginConfig().getRememberMeKey();
	}
}
