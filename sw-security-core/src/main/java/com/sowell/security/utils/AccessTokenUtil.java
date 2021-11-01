package com.sowell.security.utils;

import com.sowell.security.IcpManager;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.tool.jwt.model.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 14:41
 */
public final class AccessTokenUtil {

	private static final IAccessTokenHandler ACCESS_TOKEN_HANDLER;

	static {
		ACCESS_TOKEN_HANDLER = IcpManager.getAccessTokenHandler();
	}

	/**
	 * 生成AccessToken
	 *
	 * @param t 数据
	 * @return AccessToken
	 */
	public static Object generateAccessToken(AuthDetails t) {
		return ACCESS_TOKEN_HANDLER.generateAccessToken(t);
	}

	/**
	 * 获取当前请求客户端携带的AccessToken
	 * <p>注1：若客户端未携带的AccessToken，此类会抛出异常。</p>
	 *
	 * @return AccessToken
	 */
	public static Object getAccessToken() {
		return ACCESS_TOKEN_HANDLER.getAccessTokenInfo();
	}

	/**
	 * 获取当前请求客户端的信息
	 * <p>注1：此类会抛出异常。</p>
	 *
	 * @return 客户端信息
	 */
	public static AuthDetails<?> getAuthDetails() {
		return AccessTokenUtil.getAuthDetails(true);
	}

	/**
	 * 获取当前请求客户端的信息
	 *
	 * @param throwException 是否抛出异常
	 * @return 客户端信息
	 */
	public static AuthDetails<?> getAuthDetails(boolean throwException) {
		try {
			final AuthDetails authDetails = ACCESS_TOKEN_HANDLER.getAuthDetails();
			if (authDetails == null) {
				throw new AccountNotExistException();
			}
			return authDetails;
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	/**
	 * 获取当前请求客户端的信息
	 * <p>注1：此类会抛出异常。</p>
	 *
	 * @param authDetailsClass 目标类型
	 * @return 客户端信息
	 */
	public static <T extends AuthDetails<T>> T getAuthDetails(Class<T> authDetailsClass) {
		return AccessTokenUtil.getAuthDetails(authDetailsClass, true);
	}

	/**
	 * 获取当前请求客户端的信息
	 *
	 * @param authDetailsClass 目标类型
	 * @param throwException   是否抛出异常
	 * @return 客户端信息
	 */
	public static <T extends AuthDetails<T>> T getAuthDetails(Class<T> authDetailsClass, boolean throwException) {
		try {
			final AuthDetails<?> authDetails = ACCESS_TOKEN_HANDLER.getAuthDetails();
			if (authDetails == null) {
				throw new AccountNotExistException();
			}
			if (authDetails.getClass() == authDetailsClass) {
				return ((T) authDetails);
			}
			throw new RuntimeException("目标类型不匹配！");
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	/**
	 * 通过AccessToken获取客户端信息
	 * <p>注1：此类会抛出异常。</p>
	 *
	 * @param accessToken AccessToken
	 * @return 客户端信息
	 */
	public static AuthDetails<?> getAuthDetails(String accessToken) {
		return getAuthDetails(accessToken, true);
	}

	/**
	 * 通过AccessToken获取客户端信息
	 *
	 * @param accessToken    AccessToken
	 * @param throwException 是否抛出异常
	 * @return 客户端信息
	 */
	public static AuthDetails<?> getAuthDetails(String accessToken, boolean throwException) {
		try {
			final AuthDetails authDetails = ACCESS_TOKEN_HANDLER.getAuthDetails(accessToken);
			if (authDetails == null) {
				throw new AccountNotExistException();
			}
			return authDetails;
		} catch (Throwable throwable) {
			if (throwException) {
				throw throwable;
			}
			return null;
		}
	}

	private static <T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails) {
		ACCESS_TOKEN_HANDLER.setAuthDetails(authDetails);
	}

	public static Object refreshAccessToken() {
		return ACCESS_TOKEN_HANDLER.refreshAccessToken();
	}

	public static Object refreshAccessToken(Object accessTokenInfo) {
		return ACCESS_TOKEN_HANDLER.refreshAccessToken(accessTokenInfo);
	}
}

