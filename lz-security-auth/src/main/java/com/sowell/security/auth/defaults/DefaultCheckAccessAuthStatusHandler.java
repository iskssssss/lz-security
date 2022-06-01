package com.sowell.security.auth.defaults;

import com.sowell.security.LzCoreManager;
import com.sowell.security.auth.handler.ICheckAccessAuthStatusHandler;
import com.sowell.security.config.LzConfig;
import com.sowell.security.context.LzSecurityContextThreadLocal;
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
		final BaseRequest servletRequest = LzSecurityContextThreadLocal.getServletRequest();
		final LzConfig lzConfig = LzCoreManager.getLzConfig();
		final String saveName = lzConfig.getTokenConfig().getName();
		final String authorizationToken = servletRequest.getHeader(saveName);
		return StringUtil.isNotEmpty(authorizationToken);
	}
}
