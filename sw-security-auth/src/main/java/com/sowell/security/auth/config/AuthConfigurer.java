package com.sowell.security.auth.config;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 15:18
 */
public class AuthConfigurer extends AuthConfigurerBuilder<AuthConfigurer> {

	public String getLoginUrl() {
		return this.authUrlConfig.loginUrl;
	}

	public String getLogoutUrl() {
		return this.authUrlConfig.logoutUrl;
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
}
