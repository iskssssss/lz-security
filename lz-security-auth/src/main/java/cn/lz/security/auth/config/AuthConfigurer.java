package cn.lz.security.auth.config;

/**
 * 认证相关配置信息获取类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	/**
	 * 获取登录地址
	 *
	 * @return 登录地址
	 */
	public String getLoginUrl() {
		return this.loginHandlerInfo.loginUrl;
	}

	/**
	 * 获取登出地址
	 *
	 * @return 登出地址
	 */
	public String getLogoutUrl() {
		return this.logoutHandlerInfo.logoutUrl;
	}

	/**
	 * 获取存放标识的键值
	 *
	 * @return 存放标识的键值
	 */
	public String getIdentifierKey() {
		return this.loginHandlerInfo.identifierKey;
	}

	/**
	 * 获取存放凭据的键值
	 *
	 * @return 存放凭据的键值
	 */
	public String getCredentialKey() {
		return this.loginHandlerInfo.credentialKey;
	}

	/**
	 * 获取存放验证码的键值
	 *
	 * @return 存放验证码的键值
	 */
	public String getCodeKey() {
		return this.loginHandlerInfo.codeKey;
	}

	/**
	 * 获取存放记住我的键值
	 *
	 * @return 存放记住我的键值
	 */
	public String getRememberMeKey() {
		return this.loginHandlerInfo.rememberMeKey;
	}
}
