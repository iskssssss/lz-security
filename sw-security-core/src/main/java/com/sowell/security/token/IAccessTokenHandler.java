package com.sowell.security.token;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.filter.config.IcpConfig;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.HeaderNotAccessTokenException;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * AccessToken处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/26 17:13
 */
public interface IAccessTokenHandler<T extends AuthDetails<T>> {

	/**
	 * 获取获取客户端的AccessToken
	 *
	 * @return AccessToken
	 */
	default String getAccessTokenInfo() {
		final BaseRequest<?> servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpCoreManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		String accessToken = servletRequest.getHeader(headerName);
		if (StringUtil.isEmpty(accessToken)) {
			accessToken = servletRequest.getCookieValue(headerName);
		}
		if (StringUtil.isEmpty(accessToken)) {
			throw new HeaderNotAccessTokenException();
		}
		return accessToken;
	}

	/**
	 * 生成AccessToken
	 *
	 * @param authDetails 认证信息
	 * @return AccessToken
	 */
	String generateAccessToken(T authDetails);

	/**
	 * 校验AccessToken是否过期
	 *
	 * @return true：过期 反之未过期
	 */
	default boolean checkExpiration() {
		return this.checkExpiration(this.getAccessTokenInfo());
	}

	/**
	 * 校验AccessToken是否过期
	 *
	 * @param accessToken AccessToken
	 * @return true：过期 反之未过期
	 */
	boolean checkExpiration(String accessToken);

	/**
	 * 获取认证信息
	 *
	 * @return 认证信息
	 */
	default T getAuthDetails() {
		return this.getAuthDetails(this.getAccessTokenInfo());
	}

	/**
	 * 获取认证信息
	 *
	 * @param accessToken AccessToken
	 * @return 认证信息
	 */
	T getAuthDetails(String accessToken);

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 */
	void setAuthDetails(T authDetails);

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @return 刷新后的AccessToken
	 */
	default String refreshAccessToken() {
		return this.refreshAccessToken(this.getAccessTokenInfo());
	}

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @param accessToken AccessToken
	 * @return 刷新后的AccessToken
	 */
	String refreshAccessToken(String accessToken);

}
