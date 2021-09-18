package com.sowell.security.utils;

import com.sowell.common.core.web.result.ResultCode;
import com.sowell.common.core.web.result.ResultEntity;
import com.sowell.security.enums.HttpStatus;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.ResponseData;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 15:55
 */
public class ErrorDataHandlerUtil {

	public static ResponseData<ResultEntity> handlerErrorData(Exception error) {
		ResponseData<ResultEntity> responseData = new ResponseData<>();
		if (error instanceof SecurityException) {
			final SecurityException securityException = (SecurityException) error;
			final Object data = securityException.getResponseData();
			ResultEntity resultEntity = new ResultEntity() {{
				setCode(securityException.getCode());
				setData(data == null ? null : JsonUtil.toJsonObject(data));
				setMessage(securityException.getMessage());
			}};
			responseData.setMsg("SecurityException错误");
			responseData.setBodyData(resultEntity);
			responseData.setHttpStatus(HttpStatus.UNAUTHORIZED);
		} else if (error instanceof HttpMediaTypeNotSupportedException) {
			responseData.setMsg("不支持当前媒体类型:");
			responseData.setBodyData(getResultEntity(ResultCode.REQ_UNSUPPORTED_MEDIA_TYPE));
			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		} else if (error instanceof HttpRequestMethodNotSupportedException) {
			responseData.setMsg("不支持当前请求方法:");
			responseData.setBodyData(getResultEntity(ResultCode.REQ_NOT_ALLOWED));
			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		} else if (error instanceof HttpMessageNotReadableException) {
			responseData.setMsg("请求解析失败，请检查传参方式或者ContentType:");
			responseData.setBodyData(getResultEntity(ResultCode.REQ_BAD));
			responseData.setHttpStatus(HttpStatus.BAD_REQUEST);
		} else {
			responseData.setMsg("运行时异常:");
			responseData.setBodyData(getResultEntity(ResultCode.SYS_ERROR));
			responseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseData;
	}

	private static ResultEntity getResultEntity(ResultCode resultCode) {
		return new ResultEntity() {{
			setCode(resultCode.getCode());
			setData(null);
			setMessage(resultCode.getMessage());
		}};
	}
}
