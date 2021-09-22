package com.sowell.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.IcpManager;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.utils.ByteUtil;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.utils.StringUtil;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/02 09:31
 */
public class FilterDataHandler {
	private BaseFilterErrorHandler<?> filterErrorHandler;

	public BaseFilterErrorHandler<?> getFilterErrorHandler() {
		if (this.filterErrorHandler == null) {
			this.filterErrorHandler = IcpManager.getFilterErrorHandler();
		}
		return this.filterErrorHandler;
	}

	public boolean handler(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Exception ex
	) {
		byte[] errorBytes = null;
		try {
			if (ex != null) {
				errorBytes = errorHandler(request, response, ex);
			}
			return errorBytes != null;
		} catch (Exception exception) {
			ServletUtil.printResponse(response, RCode.UNKNOWN_MISTAKE);
			return true;
		} finally {
			if (errorBytes != null) {
				ServletUtil.printResponse(response, errorBytes);
			}
		}
	}

	private byte[] errorHandler(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Exception e
	) throws IOException {
		Object errorHandler;
		// 异常数据处理
		errorHandler = this.getFilterErrorHandler().errorHandler(request, response, e);
		if (StringUtil.isEmpty(errorHandler)) {
			return null;
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