package com.sowell.security.utils;

import com.sowell.security.IcpManager;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.tool.jwt.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.jwt.JwtUtil;

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

	public static <T extends AuthDetails<T>> String generateAccessToken(T t) {
		return ACCESS_TOKEN_HANDLER.generateAccessToken(t);
	}

	public static String getAccessToken() {
		return ACCESS_TOKEN_HANDLER.getAccessToken();
	}

	public static AuthDetails<?> getAuthDetails() {
		return SecurityUtil.getAuthDetails(true);
	}

	public static AuthDetails<?> getAuthDetails(boolean throwException) {
		try {
			return ACCESS_TOKEN_HANDLER.getAuthDetails();
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	public static AuthDetails<?> getAuthDetails(String accessToken) {
		return getAuthDetails(accessToken, true);
	}

	public static AuthDetails<?> getAuthDetails(String accessToken, boolean throwException) {
		try {
			return ACCESS_TOKEN_HANDLER.getAuthDetails(accessToken);
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	public static <T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails) {
		ACCESS_TOKEN_HANDLER.setAuthDetails(authDetails);
	}

	public static AuthDetails<?> jwtAccessTokenToAuthDetails(String jwtAccessToken) {
		if (jwtAccessToken == null) {
			return null;
		}
		final AuthDetails<?> authDetails = JwtUtil.toBean(jwtAccessToken);
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return authDetails;
	}
}
