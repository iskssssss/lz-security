package com.sowell.security.utils;

import cn.hutool.json.JSONUtil;
import com.sowell.common.core.web.result.ICode;
import com.sowell.common.core.web.result.ResultCode;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.ResponseData;
import org.springframework.http.HttpStatus;
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

	public static ResponseData<ICode> handlerErrorData(Exception error) {
		ResponseData<ICode> responseData = new ResponseData<>();
		if (error instanceof SecurityException) {
			final SecurityException securityException = (SecurityException) error;
			if (error instanceof AccountNotExistException) {
				final String message = securityException.getMessage();
				final Object rd = securityException.getResponseData();
				ICode rc;
				if (rd instanceof ICode) {
					rc = ((ICode) rd);
				} else {
					rc = new ICode() {
						@Override
						public Integer getCode() {
							return RCode.TOKEN_EXPIRE.getCode();
						}

						@Override
						public String getMessage() {
							if (StringUtil.isEmpty(rd)) {
								return message;
							}
							return JSONUtil.toJsonStr(rd);
						}
					};
				}
				responseData.setMsg(message);
				responseData.setBodyData(rc);
				responseData.setHttpStatus(HttpStatus.UNAUTHORIZED);
			}
		} else if (error instanceof HttpMediaTypeNotSupportedException) {
			responseData.setMsg("不支持当前媒体类型:");
			responseData.setBodyData(ResultCode.REQ_UNSUPPORTED_MEDIA_TYPE);
			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		} else if (error instanceof HttpRequestMethodNotSupportedException) {
			responseData.setMsg("不支持当前请求方法:");
			responseData.setBodyData(ResultCode.REQ_NOT_ALLOWED);
			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		} else if (error instanceof HttpMessageNotReadableException) {
			responseData.setMsg("请求解析失败，请检查传参方式或者ContentType:");
			responseData.setBodyData(ResultCode.REQ_BAD);
			responseData.setHttpStatus(HttpStatus.BAD_REQUEST);
		} else {
			responseData.setMsg("运行时异常:");
			responseData.setBodyData(ResultCode.SYS_ERROR);
			responseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseData;
	}
}
