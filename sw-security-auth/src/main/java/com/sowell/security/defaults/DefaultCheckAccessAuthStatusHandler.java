package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.auth.ICheckAccessAuthStatusHandler;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.core.string.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:50
 */
public class DefaultCheckAccessAuthStatusHandler implements ICheckAccessAuthStatusHandler {

	@Override
	public boolean check(
			BaseRequest<?> request,
			BaseResponse<?> response
	) {
		final BaseRequest servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final String headerName = icpConfig.getHeaderName();
		final String authorizationToken = servletRequest.getHeader(headerName);
		return StringUtil.isNotEmpty(authorizationToken);
	}
}
