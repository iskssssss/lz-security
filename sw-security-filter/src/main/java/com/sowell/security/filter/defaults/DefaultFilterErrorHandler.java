package com.sowell.security.filter.defaults;

import com.sowell.security.handler.BaseFilterErrorHandler;
import com.sowell.security.filter.context.model.BaseRequest;
import com.sowell.security.filter.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.model.RequestResult;
import com.sowell.tool.json.JsonUtil;

/**
 * 默认过滤错误处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/09/16 14:51
 */
public class DefaultFilterErrorHandler implements BaseFilterErrorHandler<RequestResult> {

	@Override
	public RequestResult errorHandler(
			BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException
	) {
		final Object data = securityException.getResponseData();
		RequestResult requestResult = new RequestResult();
		requestResult.setCode(securityException.getCode());
		requestResult.setData(data == null ? null : JsonUtil.toJsonString(data));
		requestResult.setMessage(securityException.getMessage());

		if (securityException.getCode().equals(RCode.INTERNAL_SERVER_ERROR.getCode())) {
			response.setStatus(requestResult.getCode());
		}
		return requestResult;
	}
}
