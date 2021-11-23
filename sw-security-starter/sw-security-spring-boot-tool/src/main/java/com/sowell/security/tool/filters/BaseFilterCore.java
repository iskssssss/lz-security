package com.sowell.security.tool.filters;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.IcpFilterManager;
import com.sowell.security.handler.DataEncoder;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.tool.context.IcpContextManager;
import com.sowell.security.tool.mode.SwRequest;
import com.sowell.security.tool.mode.SwResponse;
import com.sowell.security.tool.wrapper.HttpServletRequestWrapper;
import com.sowell.security.utils.ServletUtil;
import com.sowell.tool.cache.utils.GlobalScheduled;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.model.RequestResult;
import com.sowell.tool.http.enums.ContentTypeEnum;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 基础过滤中心
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 11:16
 */
public abstract class BaseFilterCore implements Filter {

	/**
	 * 过滤
	 *
	 * @param swRequest  请求流
	 * @param swResponse 响应流
	 * @return 过滤结果
	 * @throws Exception 异常
	 */
	public abstract boolean doFilter(SwRequest swRequest, SwResponse swResponse) throws Exception;

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		IcpContextManager.setContext(request, response, System.currentTimeMillis(), (swRequest, swResponse) -> {
			SecurityException securityException = null;
			try {
				final DataEncoder dataEncoder = IcpCoreManager.getRequestDataEncryptHandler();
				// 解密处理
				this.decryptHandler(dataEncoder, swRequest);
				// 过滤处理
				if (this.doFilter(swRequest, swResponse)) {
					chain.doFilter(swRequest.getRequest(), swResponse.getResponse());
				}
				// 加密处理
				this.encryptHandler(dataEncoder, swResponse);
			} catch (Exception e) {
				// 错误处理
				securityException = exceptionHandler(e);
			} finally {
				final Object handlerData = IcpFilterManager.getFilterDataHandler().handler(swRequest, swResponse, securityException);
				if (securityException != null && handlerData != null) {
					if (handlerData instanceof RCode) {
						ServletUtil.printResponse(IcpContextManager.getResponse(), ContentTypeEnum.JSON.name, (RCode) handlerData);
					} else {
						ServletUtil.printResponse(IcpContextManager.getResponse(), ContentTypeEnum.JSON.name, (byte[]) handlerData);
					}
				}
				final Object logSwitch = swRequest.getAttribute(IcpConstant.LOG_SWITCH);
				if (logSwitch instanceof Boolean && ((Boolean) logSwitch)) {
					IcpFilterManager.getFilterLogHandler().after(swRequest, swResponse, swRequest.getAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY), securityException);
				}
			}
		});
	}

	/**
	 * 解密处理
	 */
	private void decryptHandler(DataEncoder dataEncoder, SwRequest swRequest) {
		if (!swRequest.isDecrypt()) {
			return;
		}
		try {
			final HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) swRequest.getRequest();
			final byte[] bodyBytes = httpServletRequestWrapper.getBody();
			final Map requestData = ByteUtil.toObject(bodyBytes, Map.class);
			final Object decrypt = dataEncoder.decrypt(ByteUtil.toBytes(requestData.get("data")));
			httpServletRequestWrapper.setBody(ByteUtil.toBytes(decrypt));
		} catch (Exception e) {
			throw new SecurityException(RCode.DATA_DECRYPT_FAILED);
		}
	}

	/**
	 * 加密处理
	 */
	public void encryptHandler(DataEncoder dataEncoder, SwResponse swResponse) {
		if (!swResponse.isEncrypt()) {
			return;
		}
		try {
			byte[] encryptBytes;
			final byte[] responseDataBytes = swResponse.getResponseDataBytes();
			final RequestResult resultObject = ByteUtil.toObject(responseDataBytes, RequestResult.class);
			if (resultObject != null) {
				resultObject.setData(dataEncoder.encrypt(ByteUtil.toBytes(resultObject.getData())));
				encryptBytes = ByteUtil.toBytes(resultObject.toJson());
			} else {
				RequestResult requestResult = new RequestResult();
				requestResult.setData(dataEncoder.encrypt(responseDataBytes));
				encryptBytes = ByteUtil.toBytes(requestResult.toJson());
			}
			ServletUtil.printResponse(swResponse, ContentTypeEnum.JSON.name, encryptBytes);
		} catch (Exception e) {
			throw new SecurityException(RCode.DATA_ENCRYPT_FAILED);
		}
	}

	/**
	 * 异常处理
	 */
	public SecurityException exceptionHandler(Exception filterException) {
		SecurityException securityException;
		if (filterException instanceof SecurityException) {
			securityException = (SecurityException) filterException;
		} else if (filterException.getCause() instanceof SecurityException) {
			securityException = (SecurityException) filterException.getCause();
		} else {
			securityException = new SecurityException(RCode.INTERNAL_SERVER_ERROR, filterException);
		}
		if (securityException.getCause() != null) {
			IcpLoggerUtil.error(getClass(), securityException.getMessage(), securityException);
		} else {
			IcpLoggerUtil.error(getClass(), filterException.getMessage(), filterException);
		}
		return securityException;
	}

	@Override
	public void destroy() {
		if (GlobalScheduled.INSTANCE.shutdownNow().isEmpty()) {
			// TODO ...
		}
		if (IcpLoggerUtil.removeIcpLoggerMap()) {
			// TODO ...
		}
	}
}
