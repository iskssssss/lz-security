package com.sowell.security.token;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.auth.AccountNotExistException;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 14:41
 */
public final class AccessTokenUtil {

	/**
	 * 生成AccessToken
	 *
	 * @param t 数据
	 * @return AccessToken
	 */
	public static String generateAccessToken(AuthDetails<?> t) {
		return generateAccessToken(t, false);
	}

	/**
	 * 生成AccessToken
	 *
	 * @param t           数据
	 * @param writeCookie 是否写入Cookie
	 * @return AccessToken
	 */
	public static String generateAccessToken(AuthDetails<?> t, boolean writeCookie) {
		final String token = IcpCoreManager.getAccessTokenHandler().generateAccessToken(t);
		final IcpConfig.TokenConfig tokenConfig = IcpCoreManager.getIcpConfig().getTokenConfig();
		final String prefix = tokenConfig.getPrefix();
		final boolean isOpenPrefix = StringUtil.isNotEmpty(prefix) && StringUtil.notAllSpace(prefix);
		if (writeCookie) {
			final IcpContext<?, ?> icpContext = IcpCoreManager.getIcpContext();
			final BaseResponse<?> response = icpContext.getResponse();
			response.addCookie(tokenConfig.getName(), prefix + IcpConstant.PREFIX_TOKEN_SPLIT_FOR_COOKIE + token, null, null, ((int) tokenConfig.getTimeout()));
		}
		if (isOpenPrefix) {
			return prefix + IcpConstant.PREFIX_TOKEN_SPLIT + token;
		}
		return token;
	}

	/**
	 * 获取当前请求客户端携带的AccessToken
	 * <p>注1：若客户端未携带的AccessToken，此类会抛出异常。</p>
	 *
	 * @return AccessToken
	 */
	public static String getAccessToken() {
		return IcpCoreManager.getAccessTokenHandler().getAccessTokenInfo();
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
			final AuthDetails<?> authDetails = IcpCoreManager.getAccessTokenHandler().getAuthDetails();
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
			final AuthDetails<?> authDetails = IcpCoreManager.getAccessTokenHandler().getAuthDetails();
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
			final AuthDetails<?> authDetails = IcpCoreManager.getAccessTokenHandler().getAuthDetails(accessToken);
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

	private static <T extends AuthDetails<?>> void setAuthDetails(T authDetails) {
		IcpCoreManager.getAccessTokenHandler().setAuthDetails(authDetails);
	}

	public static Object refreshAccessToken() {
		return IcpCoreManager.getAccessTokenHandler().refreshAccessToken();
	}

	public static String refreshAccessToken(String accessToken) {
		return IcpCoreManager.getAccessTokenHandler().refreshAccessToken(accessToken);
	}
}

