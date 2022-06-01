package com.sowell.security.auth.defaults;

import com.sowell.security.auth.login.AuthErrorHandler;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.log.LzLoggerUtil;
import com.sowell.tool.http.enums.ContentTypeEnum;

/**
 * 认证失败处理器 默认实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/6/23 17:22
 */
public class AuthErrorHandlerDefaultImpl implements AuthErrorHandler {

	@Override
	public void error(BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException) {
		response.setStatus(200);
		LzLoggerUtil.error(getClass(), securityException.toJson());
		ServletUtil.printResponse(response, ContentTypeEnum.JSON.name, securityException);
	}
}
