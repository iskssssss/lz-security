package com.sowell.security.tool.context;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.config.EncryptConfig;
import com.sowell.security.context.IcpContextTheadLocal;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.fun.IcpFilterFunction;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.tool.mode.SwRequest;
import com.sowell.security.tool.mode.SwResponse;
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
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:56
 */
public class IcpContextManager {

	/**
	 * 设置上下文
	 *
	 * @param request  请求流
	 * @param response 响应流
	 */
	public static void setContext(ServletRequest request, ServletResponse response) {
		IcpContextManager.setContext(request, response, System.currentTimeMillis(), null);
	}

	/**
	 * 设置上下文
	 *
	 * @param request          请求流
	 * @param response         响应流
	 * @param startRequestTime 请求时间
	 */
	public static void setContext(ServletRequest request, ServletResponse response, long startRequestTime) {
		IcpContextManager.setContext(request, response, startRequestTime, null);
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
			IcpFilterFunction<SwRequest, SwResponse> function
	) {
		final long startRequestTime = System.currentTimeMillis();
		IcpContextManager.setContext(request, response, startRequestTime, function);
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
			IcpFilterFunction<SwRequest, SwResponse> function
	) {
		try {
			if (IcpSecurityContextThreadLocal.getIcpStorage() != null) {
				return;
			}
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			// 当前访问的接口
			final String requestPath = httpServletRequest.getServletPath();
			// 获取当前接口访问方法
			ControllerMethod controllerMethod = IcpCoreManager.getMethodByInterfaceUrl(requestPath);
			// 校验当前请求接口返回数据是否要加密
			final EncryptConfig encryptConfig = IcpCoreManager.getIcpConfig().getEncryptConfig();
			SwRequest swRequest = new SwRequest(httpServletRequest);
			SwResponse swResponse = new SwResponse(httpServletResponse);
			swRequest.setControllerMethod(controllerMethod);
			// 处理请求流和响应流信息
			if (encryptConfig.getEncrypt()) {
				IcpContextManager.handlerRequest(swRequest);
				IcpContextManager.handlerResponse(swRequest, swResponse);
			} else {
				swRequest = new SwRequest(httpServletRequest, false);
				swResponse = new SwResponse(httpServletResponse, false);
			}
			// 设置上下文
			IcpContextManager.setContext(swRequest, swResponse, startRequestTime);
			if (function == null) {
				return;
			}
			// 执行方法
			function.run(swRequest, swResponse);
		} catch (Exception exception) {
			IcpLoggerUtil.error(IcpContextManager.class, exception.getMessage(), exception);
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
				IcpContextManager.removeContext();
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
	private static void setContext(SwRequest request, SwResponse response, long startRequestTime) {
		final IcpSpringStorage icpSpringStorage = new IcpSpringStorage(request, startRequestTime);
		IcpSecurityContextThreadLocal.setBox(request, response, icpSpringStorage);
	}

	/**
	 * 包装处理请求流
	 * <p>根据是否请求加密来控制 响应流的包装类型</p>
	 *
	 * @param swRequest 请求流
	 * @return 包装后的请求流
	 */
	private static void handlerRequest(SwRequest swRequest) {
		try {
			final boolean isDecrypt = IcpCoreManager.getEncryptSwitchHandler().decrypt(swRequest);
			swRequest.setDecrypt(isDecrypt);
			if (isDecrypt) {
				final HttpServletRequest request = swRequest.getRequest();
				swRequest.setRequest(new HttpServletRequestWrapper(request));
			}
		} catch (Exception exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	/**
	 * 包装处理响应流
	 * <p>根据是否请求加密来控制 响应流的包装类型</p>
	 *
	 * @param swRequest  请求流
	 * @param swResponse 响应流
	 */
	private static void handlerResponse(SwRequest swRequest, SwResponse swResponse) {
		try {
			final boolean isEncrypt = IcpCoreManager.getEncryptSwitchHandler().encrypt(swRequest);
			swResponse.setEncrypt(isEncrypt);
			if (isEncrypt) {
				final HttpServletResponse response = swResponse.getResponse();
				swResponse.setResponse(new HttpServletResponseWrapper(response));
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
			BaseRequest<?> servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
			if (servletRequest == null) {
				return;
			}
			servletRequest.removeAllAttribute();
			servletRequest = null;
		} finally {
			IcpSecurityContextThreadLocal.remove();
		}
	}

	/**
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	private static IcpContextTheadLocal<?, ?> getIcpContext() {
		return ((IcpContextTheadLocal<?, ?>) IcpCoreManager.getIcpContext());
	}

	/**
	 * 获取本次请求的存储器
	 *
	 * @return 存储器
	 */
	public static IcpSpringStorage getStorage() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((IcpSpringStorage) icpContext.getStorage());
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public static SwRequest getRequest() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((SwRequest) icpContext.getRequest());
	}

	/**
	 * 获取响应流
	 *
	 * @return 响应流
	 */
	public static SwResponse getResponse() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((SwResponse) icpContext.getResponse());
	}
}