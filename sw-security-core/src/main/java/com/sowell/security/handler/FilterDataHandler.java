package com.sowell.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.utils.ByteUtil;
import com.sowell.security.utils.StringUtil;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/02 09:31
 */
public class FilterDataHandler {

	private BaseFilterLogHandler filterLogHandler;
	private BaseFilterErrorHandler<?> filterErrorHandler;

	public BaseFilterLogHandler getFilterLogHandler() {
		if (this.filterLogHandler == null) {
			this.filterLogHandler = IcpManager.getFilterLogHandler();
		}
		return this.filterLogHandler;
	}

	public BaseFilterErrorHandler<?> getFilterErrorHandler() {
		if (this.filterErrorHandler == null) {
			this.filterErrorHandler = IcpManager.getFilterErrorHandler();
		}
		return this.filterErrorHandler;
	}

	public byte[] handler(
			BaseRequest<?> request,
			BaseResponse<?> response,
			byte[] responseBytes,
			Exception ex
	) {
		try {
			byte[] errorBytes = null;
			if (ex != null) {
				errorBytes = errorHandler(request, response, ex);
			}
			this.handlerAfterLog(request, response, errorBytes, ex);
			if (ex != null && errorBytes != null) {
				responseBytes = errorBytes;
			}
			return errorBytes;
		} catch (Exception exception) {
			ServletUtil.printResponse(response, RCode.UNKNOWN_MISTAKE);
			return null;
		} finally {
			if (responseBytes != null) {
				ServletUtil.printResponse(response, responseBytes);
			}
		}
	}

	private void handlerAfterLog(
			BaseRequest<?> request,
			BaseResponse<?> response,
			byte[] errorBytes,
			Exception ex
	) {
		byte[] responseDataBytes = null;
		if (errorBytes == null) {
			responseDataBytes = response.getResponseDataBytes();
		}
		final Object logEntity = request.getAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY);
		final Optional<Long> timeOptional = Optional.ofNullable(request.getAttribute(IcpConstant.REQUEST_TIME_CACHE_KEY, Long.class));
		if (logEntity == null) {
			return;
		}
		this.getFilterLogHandler().afterHandler(
				logEntity,
				System.currentTimeMillis() - timeOptional.orElse(System.currentTimeMillis()),
				request,
				response.getStatus(),
				responseDataBytes,
				ex
		);
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