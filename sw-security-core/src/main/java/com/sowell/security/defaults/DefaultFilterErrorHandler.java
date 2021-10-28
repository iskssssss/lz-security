package com.sowell.security.defaults;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.core.enums.HttpStatus;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.model.ResponseData;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.json.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:51
 */
public class DefaultFilterErrorHandler implements BaseFilterErrorHandler<String> {

	@Override
	public String errorHandler(BaseRequest<?> request, BaseResponse<?> response, Exception error) {
		Map<String, Object> resultEntity = new LinkedHashMap<>();
		if (error instanceof SecurityException) {
			final SecurityException securityException = (SecurityException) error;
			final Object data = securityException.getResponseData();
			resultEntity.put("code", securityException.getCode());
			resultEntity.put("data", data == null ? null : JsonUtil.toJsonString(data));
			resultEntity.put("message", securityException.getMessage());
		} else {
			resultEntity.put("code", RCode.UNKNOWN_MISTAKE.getCode());
			resultEntity.put("message", RCode.UNKNOWN_MISTAKE.getMessage());
		}
		final ResponseData<Map<String, Object>> responseData = new ResponseData<>();
		responseData.setMsg("SecurityException错误");
		responseData.setBodyData(resultEntity);
		responseData.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setStatus(responseData.getHttpStatus().value());
		return JSONObject.toJSONString(responseData.getBodyData());
	}
}
