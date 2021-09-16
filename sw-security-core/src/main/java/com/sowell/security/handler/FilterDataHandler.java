package com.sowell.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.utils.ByteUtil;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.context.IcpContextHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
			HttpServletRequest request,
			HttpServletResponse response,
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
			return null;
		} finally {
			if (responseBytes != null) {
				response.resetBuffer();
				ServletUtil.printResponse(response, responseBytes);
			}
		}
	}

	private void handlerAfterLog(
			HttpServletRequest request,
			HttpServletResponse response,
			byte[] errorBytes,
			Exception ex
	) {
		byte[] responseDataBytes = ServletUtil.getResponseDataBytes(response, errorBytes);
		if (!IcpContextHandler.isSaveRequestLog()) {
			return;
		}
		final Object logEntity = IcpContextHandler.getAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY);
		final Optional<Long> timeOptional = Optional.ofNullable(IcpContextHandler.getAttribute(IcpConstant.REQUEST_TIME_CACHE_KEY, Long.class));
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
			HttpServletRequest requestWrapper,
			HttpServletResponse responseWrapper,
			Exception e
	) throws IOException {
		Object errorHandler;
		// 异常数据处理
		errorHandler = this.getFilterErrorHandler().errorHandler(requestWrapper, responseWrapper, e);
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
		return ByteUtil.object2bytes(errorHandler);
	}
}