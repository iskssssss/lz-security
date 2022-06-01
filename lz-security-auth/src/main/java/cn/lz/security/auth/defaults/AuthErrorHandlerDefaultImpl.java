package cn.lz.security.auth.defaults;

import cn.lz.security.auth.login.AuthErrorHandler;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.utils.ServletUtil;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.tool.http.enums.MediaType;

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
		ServletUtil.printResponse(response, MediaType.APPLICATION_JSON_VALUE, securityException);
	}
}
