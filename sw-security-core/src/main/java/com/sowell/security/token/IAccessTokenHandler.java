package com.sowell.security.token;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.utils.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:07
 */
public interface IAccessTokenHandler {

	/**
	 * 生成AccessToken
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 * @return AccessToken
	 */
	<T extends AuthDetails<T>> String generateAccessToken(T authDetails);

	/**
	 * 获取获取客户端的AccessToken
	 *
	 * @return AccessToken
	 */
	default String getAccessToken() {
		final BaseRequest servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		final String accessToken = servletRequest.getHeader(headerName);
		return accessToken;
	}

	/**
	 * 获取认证信息
	 *
	 * @param accessToken AccessToken
	 * @param <T>         类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> T getAuthDetails(String accessToken);

	/**
	 * 获取认证信息
	 *
	 * @param <T> 类型
	 * @return 认证信息
	 */
	<T extends AuthDetails<T>> T getAuthDetails();

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 * @param <T>         类型
	 */
	<T extends AuthDetails<T>> void setAuthDetails(T authDetails);

}
