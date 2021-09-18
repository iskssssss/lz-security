package com.sowell.security.defaults;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.HttpStatus;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.ResponseData;
import com.sowell.security.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:51
 */
public class DefaultFilterErrorHandler
		implements BaseFilterErrorHandler<String> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String errorHandler(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Exception error) {
		Map<String, Object> resultEntity = new LinkedHashMap<>();
		if (error instanceof SecurityException) {
			final SecurityException securityException = (SecurityException) error;
			final Object data = securityException.getResponseData();
			resultEntity.put("code", securityException.getCode());
			resultEntity.put("data", data == null ? null : JsonUtil.toJsonObject(data));
			resultEntity.put("message", securityException.getMessage());
		} else {
			final String message = error.getMessage();
			resultEntity.put("code", 500);
			resultEntity.put("data", null);
			resultEntity.put("message", message);
		}
		final ResponseData<Map<String, Object>> responseData = new ResponseData<Map<String, Object>>() {{
			setMsg("SecurityException错误");
			setBodyData(resultEntity);
			setHttpStatus(HttpStatus.UNAUTHORIZED);
		}};
		response.setStatus(responseData.getHttpStatus().value());
		log.error(responseData.getMsg(), error);
		return JSONObject.toJSONString(responseData.getBodyData());
	}
}
