//package com.sowell.security.tool.utils;
//
//import com.sowell.tool.core.enums.HttpStatus;
//import com.sowell.security.exception.base.SecurityException;
//import com.sowell.security.model.ResponseData;
//import com.sowell.tool.json.JsonUtil;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//
///**
// * @Version 版权 Copyright(c)2021 LZ
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/08/19 15:55
// */
//public class ErrorDataHandlerUtil {
//
//	public static ResponseData<ResultEntity> handlerErrorData(Exception error) {
//		ResponseData<ResultEntity> responseData = new ResponseData<>();
//		if (error instanceof SecurityException) {
//			final SecurityException securityException = (SecurityException) error;
//			final Object data = securityException.getResponseData();
//			ResultEntity resultEntity = new ResultEntity();
//			resultEntity.setCode(securityException.getResponseCode());
//			resultEntity.setData(data == null ? null : JsonUtil.toJsonString(data));
//			resultEntity.setMessage(securityException.getMessage());
//			responseData.setMsg("SecurityException错误");
//			responseData.setBodyData(resultEntity);
//			responseData.setHttpStatus(HttpStatus.UNAUTHORIZED);
//		} else if (error instanceof HttpMediaTypeNotSupportedException) {
//			responseData.setMsg("不支持当前媒体类型:");
//			responseData.setBodyData(getResultEntity(ResultCode.REQ_UNSUPPORTED_MEDIA_TYPE));
//			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
//		} else if (error instanceof HttpRequestMethodNotSupportedException) {
//			responseData.setMsg("不支持当前请求方法:");
//			responseData.setBodyData(getResultEntity(ResultCode.REQ_NOT_ALLOWED));
//			responseData.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
//		} else if (error instanceof HttpMessageNotReadableException) {
//			responseData.setMsg("请求解析失败，请检查传参方式或者ContentType:");
//			responseData.setBodyData(getResultEntity(ResultCode.REQ_BAD));
//			responseData.setHttpStatus(HttpStatus.BAD_REQUEST);
//		} else {
//			responseData.setMsg("运行时异常:");
//			responseData.setBodyData(getResultEntity(ResultCode.SYS_ERROR));
//			responseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return responseData;
//	}
//
//	private static ResultEntity getResultEntity(ResultCode resultCode) {
//		final ResultEntity resultEntity = new ResultEntity();
//		resultEntity.setCode(resultCode.getCode());
//		resultEntity.setData(null);
//		resultEntity.setMessage(resultCode.getMessage());
//		return resultEntity;
//	}
//}
