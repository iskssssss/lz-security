package com.sowell.security.token;

import com.sowell.security.IcpManager;
import com.sowell.security.filter.config.IcpConfig;
import com.sowell.security.filter.context.IcpSecurityContextThreadLocal;
import com.sowell.security.filter.context.model.BaseRequest;
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
	 * 获取获取客户端的AccessToken信息
	 *
	 * @return AccessToken信息
	 */
	default Object getAccessTokenInfo() {
		final BaseRequest<?> servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		final String accessToken = servletRequest.getHeader(headerName);
		if (StringUtil.isEmpty(accessToken)) {
			throw new HeaderNotAccessTokenException();
		}
		return accessToken;
	}

	/**
	 * 生成AccessToken信息
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 * @return AccessToken信息
	 */
	Object generateAccessToken(T authDetails);

	/**
	 * 校验AccessToken信息是否过期
	 *
	 * @return true：过期 反之未过期
	 */
	default boolean checkExpiration() {
		return this.checkExpiration(this.getAccessTokenInfo());
	}

	/**
	 * 校验AccessToken信息是否过期
	 *
	 * @param accessTokenInfo AccessToken信息
	 * @return true：过期 反之未过期
	 */
	boolean checkExpiration(Object accessTokenInfo);

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
	 * @param accessTokenInfo AccessToken信息
	 * @return 认证信息
	 */
	T getAuthDetails(Object accessTokenInfo);

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 */
	void setAuthDetails(T authDetails);

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @return 刷新后的AccessToken信息
	 */
	default Object refreshAccessToken() {
		return this.refreshAccessToken(this.getAccessTokenInfo());
	}

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @param accessTokenInfo AccessToken信息
	 * @return 刷新后的AccessToken信息
	 */
	Object refreshAccessToken(Object accessTokenInfo);

}
