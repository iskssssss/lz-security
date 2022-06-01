package com.sowell.security.tool.context;

import com.sowell.security.LzCoreManager;
import com.sowell.security.config.EncryptConfig;
import com.sowell.security.context.LzContextTheadLocal;
import com.sowell.security.context.LzSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.fun.LzFilterFunction;
import com.sowell.security.log.LzLoggerUtil;
import com.sowell.security.tool.mode.LzRequest;
import com.sowell.security.tool.mode.LzResponse;
import com.sowell.security.tool.wrapper.HttpServletRequestWrapper;
import com.sowell.security.tool.wrapper.HttpServletResponseWrapper;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.reflect.model.ControllerMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:56
 */
public class LzContextManager {

	/**
	 * 设置上下文
	 *
	 * @param request  请求流
	 * @param response 响应流
	 */
	public static void setContext(ServletRequest request, ServletResponse response) {
		LzContextManager.setContext(request, response, System.currentTimeMillis(), null);
	}

	/**
	 * 设置上下文
	 *
	 * @param request          请求流
	 * @param response         响应流
	 * @param startRequestTime 请求时间
	 */
	public static void setContext(ServletRequest request, ServletResponse response, long startRequestTime) {
		LzContextManager.setContext(request, response, startRequestTime, null);
	}

	/**
	 * 设置上下文
	 *
	 * @param request  请求流
	 * @param response 响应流
	 * @param function 回调方法
	 */
	public static void setContext(
			ServletRequest request,
			ServletResponse response,
			LzFilterFunction<LzRequest, LzResponse> function
	) {
		final long startRequestTime = System.currentTimeMillis();
		LzContextManager.setContext(request, response, startRequestTime, function);
	}

	/**
	 * 设置上下文
	 *
	 * @param request          请求流
	 * @param response         响应流
	 * @param startRequestTime 请求时间
	 * @param function         回调方法
	 */
	public static void setContext(
			ServletRequest request,
			ServletResponse response,
			long startRequestTime,
			LzFilterFunction<LzRequest, LzResponse> function
	) {
		try {
			if (LzSecurityContextThreadLocal.getLzStorage() != null) {
				return;
			}
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			// 当前访问的接口
			final String requestPath = httpServletRequest.getServletPath();
			// 获取当前接口访问方法
			ControllerMethod controllerMethod = LzCoreManager.getMethodByInterfaceUrl(requestPath);
			// 校验当前请求接口返回数据是否要加密
			final EncryptConfig encryptConfig = LzCoreManager.getLzConfig().getEncryptConfig();
			LzRequest lzRequest = new LzRequest(httpServletRequest);
			LzResponse lzResponse = new LzResponse(httpServletResponse);
			lzRequest.setControllerMethod(controllerMethod);
			// 处理请求流和响应流信息
			if (encryptConfig.getEncrypt()) {
				LzContextManager.handlerRequest(lzRequest);
				LzContextManager.handlerResponse(lzRequest, lzResponse);
			} else {
				lzRequest = new LzRequest(httpServletRequest, false);
				lzResponse = new LzResponse(httpServletResponse, false);
			}
			// 设置上下文
			LzContextManager.setContext(lzRequest, lzResponse, startRequestTime);
			if (function == null) {
				return;
			}
			// 执行方法
			function.run(lzRequest, lzResponse);
		} catch (Exception exception) {
			LzLoggerUtil.error(LzContextManager.class, exception.getMessage(), exception);
			final byte[] bytes = RCode.INTERNAL_SERVER_ERROR.toJson().getBytes(StandardCharsets.UTF_8);
			final int length = bytes.length;
			try (ServletOutputStream outputStream = response instanceof HttpServletResponseWrapper ?
					((HttpServletResponseWrapper) response).getResponseOutputStream() : response.getOutputStream()) {
				response.setContentLength(length);
				outputStream.write(bytes, 0, length);
				outputStream.flush();
			} catch (IOException ioException) {
				throw new SecurityException(RCode.UNKNOWN_MISTAKE.getCode(), RCode.UNKNOWN_MISTAKE.getMessage(), ioException);
			}
		} finally {
			if (function != null) {
				LzContextManager.removeContext();
			}
		}
	}

	/**
	 * 设置上下文
	 *
	 * @param request          请求流
	 * @param response         响应流
	 * @param startRequestTime 请求时间
	 */
	private static void setContext(LzRequest request, LzResponse response, long startRequestTime) {
		final LzSpringStorage lzSpringStorage = new LzSpringStorage(request, startRequestTime);
		LzSecurityContextThreadLocal.setBox(request, response, lzSpringStorage);
	}

	/**
	 * 包装处理请求流
	 * <p>根据是否请求加密来控制 响应流的包装类型</p>
	 *
	 * @param lzRequest 请求流
	 * @return 包装后的请求流
	 */
	private static void handlerRequest(LzRequest lzRequest) {
		try {
			final boolean isDecrypt = LzCoreManager.getEncryptSwitchHandler().decrypt(lzRequest);
			lzRequest.setDecrypt(isDecrypt);
			if (isDecrypt) {
				final HttpServletRequest request = lzRequest.getRequest();
				lzRequest.setRequest(new HttpServletRequestWrapper(request));
			}
		} catch (Exception exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	/**
	 * 包装处理响应流
	 * <p>根据是否请求加密来控制 响应流的包装类型</p>
	 *
	 * @param lzRequest  请求流
	 * @param lzResponse 响应流
	 */
	private static void handlerResponse(LzRequest lzRequest, LzResponse lzResponse) {
		try {
			final boolean isEncrypt = LzCoreManager.getEncryptSwitchHandler().encrypt(lzRequest);
			lzResponse.setEncrypt(isEncrypt);
			if (isEncrypt) {
				final HttpServletResponse response = lzResponse.getResponse();
				lzResponse.setResponse(new HttpServletResponseWrapper(response));
			}
		} catch (Exception exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	/**
	 * 移除上下文
	 */
	public static void removeContext() {
		try {
			BaseRequest<?> servletRequest = LzSecurityContextThreadLocal.getServletRequest();
			if (servletRequest == null) {
				return;
			}
			servletRequest.removeAllAttribute();
			servletRequest = null;
		} finally {
			LzSecurityContextThreadLocal.remove();
		}
	}

	/**
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	private static LzContextTheadLocal<?, ?> getLzContext() {
		return ((LzContextTheadLocal<?, ?>) LzCoreManager.getLzContext());
	}

	/**
	 * 获取本次请求的存储器
	 *
	 * @return 存储器
	 */
	public static LzSpringStorage getStorage() {
		LzContextTheadLocal<?, ?> lzContext = getLzContext();
		return ((LzSpringStorage) lzContext.getStorage());
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public static LzRequest getRequest() {
		LzContextTheadLocal<?, ?> lzContext = getLzContext();
		return ((LzRequest) lzContext.getRequest());
	}

	/**
	 * 获取响应流
	 *
	 * @return 响应流
	 */
	public static LzResponse getResponse() {
		LzContextTheadLocal<?, ?> lzContext = getLzContext();
		return ((LzResponse) lzContext.getResponse());
	}
}
