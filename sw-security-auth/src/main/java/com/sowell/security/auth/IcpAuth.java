package com.sowell.security.auth;

import com.sowell.security.auth.config.AuthConfigurer;
import com.sowell.security.filter.IcpFilter;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 14:49
 */
public class IcpAuth extends IcpFilter {

	protected static final AuthConfigurer AUTH_CONFIGURER = new AuthConfigurer();

	public static AuthConfigurer getAuthConfigurer() {
		return AUTH_CONFIGURER;
	}
}
