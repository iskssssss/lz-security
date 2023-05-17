package cn.lz.security.filter;

import cn.lz.security.LzConstant;
import cn.lz.security.LzCoreManager;
import cn.lz.security.config.encrypt.EncryptConfig;
import cn.lz.security.exception.CorsException;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.context.LzSpringBootContextManager;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.security.filter.wrapper.HttpServletRequestWrapper;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.utils.ServletUtil;
import cn.lz.tool.cache.utils.GlobalScheduled;
import cn.lz.tool.core.bytes.ByteUtil;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.enums.MediaType;
import org.springframework.web.cors.CorsUtils;

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
public abstract class BaseFilterCore extends LzSpringBootContextManager implements Filter {

	/**
	 * 过滤
	 *
	 * @param lzRequest  请求流
	 * @param lzResponse 响应流
	 * @return 过滤结果
	 * @throws Exception 异常
	 */
	public abstract boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context) throws Exception;

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		LzSpringBootContextManager.setContext(request, response, System.currentTimeMillis(), (swRequest, swResponse) -> {
			SecurityException securityException = null;
			boolean preFlightRequest = CorsUtils.isPreFlightRequest(swRequest.getRequest());
			try {
				// 过滤前处理
				FilterConfigurer filterConfigurer = LzFilterManager.getFilterConfigurer();
				filterConfigurer.getFilterBeforeHandler().run();
				if (preFlightRequest) {
					// 跨域处理
					filterConfigurer.getCorsHandler().handler(swRequest, swResponse, true);
					swResponse.setStatus(200);
					swResponse.print(RCode.SUCCESS.toJson().getBytes(StandardCharsets.UTF_8));
					return;
				}
				final DataEncoder dataEncoder = LzCoreManager.getDataEncryptHandler();
				// 解密处理
				this.decryptHandler(dataEncoder, swRequest);
				// 过滤处理
				if (this.doFilter(swRequest, swResponse, (SpringBootContextTheadLocal) LzCoreManager.getLzContext())) {
					chain.doFilter(swRequest.getRequest(), swResponse.getResponse());
				} else {
					// 跨域处理
					filterConfigurer.getCorsHandler().handler(swRequest, swResponse, false);
					// 被拦截后处理
					filterConfigurer.getInterceptHandler().run();
				}
				// 加密处理
				this.encryptHandler(dataEncoder, swResponse);
				// 过滤后处理
				filterConfigurer.getFilterAfterHandler().run();
			} catch (Exception e) {
				// 错误处理
				securityException = exceptionHandler(e);
				if (!(e instanceof CorsException)) {
					// 跨域处理
					LzFilterManager.getFilterConfigurer().getCorsHandler().handler(swRequest, swResponse, preFlightRequest);
				}
			} finally {
				final Object handlerData = LzFilterManager.getFilterDataHandler().handler(swRequest, swResponse, securityException);
				if (securityException != null && handlerData != null) {
					if (handlerData instanceof RCode) {
						ServletUtil.printResponse(LzSpringBootContextManager.getResponse(), MediaType.APPLICATION_JSON_VALUE, (RCode) handlerData);
					} else {
						ServletUtil.printResponse(LzSpringBootContextManager.getResponse(), MediaType.APPLICATION_JSON_VALUE, (byte[]) handlerData);
					}
				}
				BaseFilterLogHandler filterLogHandler = LzFilterManager.getFilterLogHandler();
				if (filterLogHandler != null) {
					final Object logSwitch = swRequest.getAttribute(LzConstant.LOG_SWITCH);
					if (logSwitch instanceof Boolean && ((Boolean) logSwitch)) {
						filterLogHandler.after(swRequest, swResponse, swRequest.getAttribute(LzConstant.LOG_ENTITY_CACHE_KEY), securityException);
					}
				}
			}
		});
	}

	/**
	 * 解密处理
	 */
	private void decryptHandler(DataEncoder dataEncoder, LzRequest lzRequest) {
		if (dataEncoder == null || !lzRequest.isDecrypt()) {
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
			throw new SecurityException(RCode.DATA_DECRYPT_FAILED, e);
		}
	}

	/**
	 * 加密处理
	 */
	public void encryptHandler(DataEncoder dataEncoder, LzResponse lzResponse) {
		if (dataEncoder == null || !lzResponse.isEncrypt()) {
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
			throw new SecurityException(RCode.DATA_ENCRYPT_FAILED, e);
		}
	}

	/**
	 * 异常处理
	 */
	public SecurityException exceptionHandler(Exception filterException) {
		SecurityException securityException;
		if (filterException instanceof SecurityException) {
			securityException = (SecurityException) filterException;
		} else {
			Throwable cause = filterException.getCause();
			if (cause instanceof SecurityException) {
				securityException = (SecurityException) cause;
			} else {
				securityException = new SecurityException(RCode.INTERNAL_SERVER_ERROR, filterException);
			}
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
