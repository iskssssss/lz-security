package com.sowell.security.auth.config;

import com.sowell.security.fun.IcpFilterAuthStrategy;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	/**
	 * 获取认证前处理方法
	 *
	 * @return 认证前处理方法
	 */
	public IcpFilterAuthStrategy getAuthBeforeHandler() {
		return super.authBeforeHandler;
	}

	/**
	 * 获取认证后处理方法
	 *
	 * @return 认证后处理方法
	 */
	public IcpFilterAuthStrategy getAuthAfterHandler() {
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
