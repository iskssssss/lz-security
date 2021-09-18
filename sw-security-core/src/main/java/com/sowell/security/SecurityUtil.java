package com.sowell.security;

import com.sowell.security.model.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 14:41
 */
public final class SecurityUtil {

	private static final IAccessTokenHandler ACCESS_TOKEN_HANDLER;

	static {
		ACCESS_TOKEN_HANDLER = IcpManager.getAccessTokenHandler();
	}

	public static String getAccessToken() {
		return ACCESS_TOKEN_HANDLER.getAccessToken();
	}

	public static <T extends AuthDetails<T>> T getAuthDetails() {
		return ACCESS_TOKEN_HANDLER.getAuthDetails();
	}

	public static <T extends AuthDetails<T>> T getAuthDetails(String accessToken) {
		return ACCESS_TOKEN_HANDLER.getAuthDetails(accessToken);
	}
}
