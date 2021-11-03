package com.sowell.security.auth.config;

import com.sowell.security.auth.IcpAuth;
import com.sowell.security.tool.config.SecurityConfigurerAdapter;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 10:16
 */
public abstract class SecurityAuthConfigurerAdapter extends SecurityConfigurerAdapter {

	@Override
	protected void init() {
		this.auth(IcpAuth.getAuthConfigurer());
	}

	/**
	 * 配置认证信息
	 *
	 * @param authConfigurer 配置信息
	 */
	protected abstract void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer);
}
