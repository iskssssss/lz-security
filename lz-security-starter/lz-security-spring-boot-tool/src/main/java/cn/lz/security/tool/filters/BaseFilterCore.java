package cn.lz.security.tool.filters;

import cn.lz.security.LzConstant;
import cn.lz.security.LzCoreManager;
import cn.lz.security.config.EncryptConfig;
import cn.lz.security.exception.CorsException;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.fun.LzFilterAuthStrategy;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.tool.context.LzContextManager;
import cn.lz.security.tool.mode.LzRequest;
import cn.lz.security.tool.mode.LzResponse;
import cn.lz.security.tool.wrapper.HttpServletRequestWrapper;
import cn.lz.security.utils.ServletUtil;
import cn.lz.tool.cache.utils.GlobalScheduled;
import cn.lz.tool.core.bytes.ByteUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.enums.MediaType;

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
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 11:16
 */
public abstract class BaseFilterCore implements Filter {

	/**
	 * 被拦截后处理
	 */
	protected LzFilterAuthStrategy interceptHandler;

	/**
	 * 跨域信息处理
	 */
	protected LzFilterAuthStrategy corsHandler = params -> {
		if (params.length < 2 || !(params[0] instanceof LzRequest) || !(params[1] instanceof LzResponse)) {
			return;
		}
		LzCoreManager.getCorsConfig().initHeader((LzRequest) params[0], (LzResponse) params[1]);
	};

	/**
	 * 过滤
	 *
	 * @param lzRequest  请求流
	 * @param lzResponse 响应流
	 * @return 过滤结果
	 * @throws Exception 异常
	 */
	public abstract boolean doFilter(LzRequest lzRequest, LzResponse lzResponse) throws Exception;

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		LzContextManager.setContext(request, response, System.currentTimeMillis(), (swRequest, swResponse) -> {
			SecurityException securityException = null;
			try {
				if ("OPTIONS".equals(swRequest.getMethod())) {
					// 跨域处理
					corsHandler.run(swRequest, swResponse);
					swResponse.setStatus(200);
					swResponse.print(RCode.SUCCESS.toJson().getBytes(StandardCharsets.UTF_8));
					return;
				}
				final DataEncoder dataEncoder = LzCoreManager.getRequestDataEncryptHandler();
				// 解密处理
				this.decryptHandler(dataEncoder, swRequest);
				// 过滤处理
				if (this.doFilter(swRequest, swResponse)) {
					chain.doFilter(swRequest.getRequest(), swResponse.getResponse());
				} else {
					// 跨域处理
					corsHandler.run(swRequest, swResponse);
					// 被拦截后处理
					interceptHandler.run();
				}
				// 加密处理
				this.encryptHandler(dataEncoder, swResponse);
			} catch (Exception e) {
				// 错误处理
				securityException = exceptionHandler(e);
				if (!(e instanceof CorsException)) {
					// 跨域处理
					corsHandler.run(swRequest, swResponse);
				}
			} finally {
				final Object handlerData = LzFilterManager.getFilterDataHandler().handler(swRequest, swResponse, securityException);
				if (securityException != null && handlerData != null) {
					if (handlerData instanceof RCode) {
						ServletUtil.printResponse(LzContextManager.getResponse(), MediaType.APPLICATION_JSON_VALUE, (RCode) handlerData);
					} else {
						ServletUtil.printResponse(LzContextManager.getResponse(), MediaType.APPLICATION_JSON_VALUE, (byte[]) handlerData);
					}
				}
				final Object logSwitch = swRequest.getAttribute(LzConstant.LOG_SWITCH);
				if (logSwitch instanceof Boolean && ((Boolean) logSwitch)) {
					LzFilterManager.getFilterLogHandler().after(swRequest, swResponse, swRequest.getAttribute(LzConstant.LOG_ENTITY_CACHE_KEY), securityException);
				}
			}
		});
	}

	/**
	 * 解密处理
	 */
	private void decryptHandler(DataEncoder dataEncoder, LzRequest lzRequest) {
		if (!lzRequest.isDecrypt()) {
			return;
		}
		try {
			final HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) lzRequest.getRequest();
			final byte[] bodyBytes = httpServletRequestWrapper.getBody();
			EncryptConfig encryptConfig = LzCoreManager.getEncryptConfig();
			String cipherSaveKey = encryptConfig.getCipherSaveKey();
			byte[] bytes;
			if (StringUtil.isNotEmpty(cipherSaveKey)) {
				final Map requestData = ByteUtil.toObject(bodyBytes, Map.class);
				Object data = requestData.get(cipherSaveKey);
				bytes = ByteUtil.toBytes(data);
			} else {
				bytes = bodyBytes;
			}
			String requestContentType = lzRequest.getRequestContentType();
			Object decrypt;
			if (StringUtil.isEmpty(requestContentType) ||
					requestContentType.contains("json") ||
					requestContentType.contains("xml") ||
					requestContentType.contains("text")
			) {
				decrypt = ByteUtil.toObject(dataEncoder.decrypt(bytes));
			} else {
				decrypt = dataEncoder.decrypt(bytes);
			}
			httpServletRequestWrapper.setBody(ByteUtil.toBytes(decrypt));
		} catch (Exception e) {
			throw new SecurityException(RCode.DATA_DECRYPT_FAILED);
		}
	}

	/**
	 * 加密处理
	 */
	public void encryptHandler(DataEncoder dataEncoder, LzResponse lzResponse) {
		if (!lzResponse.isEncrypt()) {
			return;
		}
		try {
			byte[] encryptBytes = new byte[]{};
			final byte[] responseDataBytes = lzResponse.getResponseDataBytes();
			if (responseDataBytes != null) {
				encryptBytes = dataEncoder.encrypt(responseDataBytes);
			}
			ServletUtil.printResponse(lzResponse, lzResponse.getResponseContentType(), encryptBytes);
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
			LzLoggerUtil.error(getClass(), securityException.getMessage(), securityException);
		} else {
			LzLoggerUtil.error(getClass(), filterException.getMessage(), filterException);
		}
		return securityException;
	}

	@Override
	public void destroy() {
		if (GlobalScheduled.INSTANCE.shutdownNow().isEmpty()) {
			// TODO ...
		}
		if (LzLoggerUtil.removeLzLoggerMap()) {
			// TODO ...
		}
	}
}
