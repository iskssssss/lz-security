package com.sowell.security.filter.handler;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.IcpFilterManager;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.string.StringUtil;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 数据处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/08/02 09:31
 */
public final class FilterDataHandler {

	public Object handler(BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException) {
		byte[] errorBytes = null;
		try {
			if (securityException != null) {
				errorBytes = errorHandler(request, response, securityException);
			}
			return errorBytes;
		} catch (Exception exception) {
			return RCode.UNKNOWN_MISTAKE;
		}
	}

	private byte[] errorHandler(BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException) {
		// 异常数据处理
		Object errorHandler = IcpFilterManager.getFilterErrorHandler().errorHandler(request, response, securityException);
		if (StringUtil.isEmpty(errorHandler)) {
			return null;
		}
		// 处理字节数组
		if (errorHandler instanceof byte[]) {
			return ((byte[]) errorHandler);
		}
		// 处理map类型
		if (errorHandler instanceof Map) {
			return JSONObject.toJSONString(errorHandler).getBytes(StandardCharsets.UTF_8);
		}
		// 处理可序列化数据
		if (errorHandler instanceof Serializable) {
			// 处理字符数据
			if (errorHandler instanceof CharSequence) {
				return ((String) errorHandler).getBytes(StandardCharsets.UTF_8);
			}
			// 处理其它数据
			return JSONObject.toJSONString(errorHandler).getBytes(StandardCharsets.UTF_8);
		}
		// 处理其它数据
		return ByteUtil.toBytes(errorHandler);
	}
}