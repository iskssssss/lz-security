package com.sowell.security;

import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.security.utils.JwtUtil;

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

	public static <T extends AuthDetails<T>> T getAuthDetails() {
		return SecurityUtil.getAuthDetails(true);
	}

	public static <T extends AuthDetails<T>> T getAuthDetails(boolean throwException) {
		try {
			return ACCESS_TOKEN_HANDLER.getAuthDetails();
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	public static <T extends AuthDetails<T>> T getAuthDetails(String accessToken) {
		return getAuthDetails(accessToken, true);
	}

	public static <T extends AuthDetails<T>> T getAuthDetails(String accessToken, boolean throwException) {
		try {
			return ACCESS_TOKEN_HANDLER.getAuthDetails(accessToken);
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	public static <T extends AuthDetails<T>> void setAuthDetails(T t) {
		ACCESS_TOKEN_HANDLER.setAuthDetails(t);
	}

	public static <T extends AuthDetails<T>> T jwtAccessTokenToAuthDetails(String jwtAccessToken) {
		if (jwtAccessToken == null) {
			return null;
		}
		final T authDetails = JwtUtil.toBean(jwtAccessToken);
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return authDetails;
	}
}
