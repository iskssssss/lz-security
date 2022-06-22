package cn.lz.security.auth.config;

import cn.lz.security.fun.LzFilterAuthStrategy;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	/**
	 * 获取认证前处理方法
	 *
	 * @return 认证前处理方法
	 */
	public LzFilterAuthStrategy getAuthBeforeHandler() {
		return super.authBeforeHandler;
	}

	/**
	 * 获取认证后处理方法
	 *
	 * @return 认证后处理方法
	 */
	public LzFilterAuthStrategy getAuthAfterHandler() {
		return super.authAfterHandler;
	}

	public String getLoginUrl() {
		return this.loginHandlerInfo.loginUrl;
	}

	public String getIdentifierKey() {
		return this.loginHandlerInfo.identifierKey;
	}

	public String getCredentialKey() {
		return this.loginHandlerInfo.credentialKey;
	}

	public String getCodeKey() {
		return this.loginHandlerInfo.codeKey;
	}

	public String getRememberMeKey() {
		return this.loginHandlerInfo.rememberMeKey;
	}

	public String getLogoutUrl() {
		return this.logoutHandlerInfo.logoutUrl;
	}
}