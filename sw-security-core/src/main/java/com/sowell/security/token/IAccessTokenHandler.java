package com.sowell.security.token;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
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
public interface IAccessTokenHandler {

	/**
	 * 获取获取客户端的AccessToken
	 *
	 * @return AccessToken
	 */
	default String getAccessToken() {
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
	 * 生成AccessToken
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 * @return AccessToken
	 */
	<T extends AuthDetails<T>> String generateAccessToken(AuthDetails<T> authDetails);

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
	 * @param accessToken AccessToken
	 * @param <T>         类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> AuthDetails<T> getAuthDetails(String accessToken);

	/**
	 * 获取认证信息
	 *
	 * @param <T> 类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> AuthDetails<T> getAuthDetails();

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 */
	<T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails);
}
