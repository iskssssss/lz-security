package com.sowell.security.token;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.AccountNotExistException;
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

	default String getAccessToken() {
		final BaseRequest servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		final String accessToken = servletRequest.getHeader(headerName);
		if (StringUtil.isEmpty(accessToken)) {
			throw new AccountNotExistException(RCode.NOT_Authorization);
		}
		return accessToken;
	}

	<T extends AuthDetails<T>> T getAuthDetails(String accessToken);

	<T extends AuthDetails<T>> T getAuthDetails();

	<T extends AuthDetails<T>> void setAuthDetails(T authDetails);

}
