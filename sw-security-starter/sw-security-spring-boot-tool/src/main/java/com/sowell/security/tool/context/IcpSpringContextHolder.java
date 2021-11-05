package com.sowell.security.tool.context;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.annotation.ResponseDataEncrypt;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.context.IcpContextTheadLocal;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.config.IcpConfig;
import com.sowell.security.fun.IcpFilterFunction;
import com.sowell.security.tool.mode.SwRequest;
import com.sowell.security.tool.mode.SwResponse;
import com.sowell.security.tool.wrapper.HttpServletRequestWrapper;
import com.sowell.security.tool.wrapper.HttpServletResponseWrapper;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.reflect.model.ControllerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:56
 */
public class IcpSpringContextHolder {

	/**
	 * 设置上下文
	 *
	 * @param request  请求流
	 * @param response 响应流
	 */
	public static void setContext(ServletRequest request, ServletResponse response) {
		IcpSpringContextHolder.setContext(request, response, System.currentTimeMillis(), null);
	}

	/**
	 * 设置上下文
	 *
	 * @param request          请求流
	 * @param response         响应流
	 * @param startRequestTime 请求时间
	 */
	public static void setContext(ServletRequest request, ServletResponse response, long startRequestTime) {
		IcpSpringContextHolder.setContext(request, response, startRequestTime, null);
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
		IcpSpringContextHolder.setContext(request, response, startRequestTime, function);
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
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			// 当前访问的接口
			final String requestPath = httpServletRequest.getServletPath();
			// 获取当前接口访问方法
			ControllerMethod method = IcpCoreManager.getMethodByInterfaceUrl(requestPath);
			// 校验当前请求接口返回数据是否要加密
			final IcpConfig.EncryptConfig encryptConfig = IcpCoreManager.getIcpConfig().getEncryptConfig();
			// 获取当前访问接口方法/类的加密注解
			ResponseDataEncrypt responseDataEncrypt = method == null ? null : method.getMethodAndControllerAnnotation(ResponseDataEncrypt.class);
			// 处理请求流和响应流信息
			SwRequest swRequest = handlerRequest(encryptConfig, responseDataEncrypt, requestPath, httpServletRequest);
			SwResponse swResponse = handlerResponse(encryptConfig, responseDataEncrypt, requestPath, (HttpServletResponse) response);
			// 设置上下文
			IcpSpringContextHolder.setContext(swRequest, swResponse, startRequestTime);
			if (function == null) {
				return;
			}
			// 执行方法
			function.run(swRequest, swResponse);
		} catch (UnsupportedEncodingException ignored) {
		} finally {
			if (function != null) {
				IcpSpringContextHolder.removeContext();
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
	 * @param encryptConfig       加密配置信息
	 * @param responseDataEncrypt 当前请求方法/类的加密注解
	 * @param requestPath         当前访问接口地址
	 * @param request             请求流
	 * @return 包装后的请求流
	 */
	private static SwRequest handlerRequest(
			IcpConfig.EncryptConfig encryptConfig,
			ResponseDataEncrypt responseDataEncrypt,
			String requestPath,
			HttpServletRequest request
	) {
		boolean isEncrypt = false;
		if (encryptConfig.getEncrypt()) {
			if (responseDataEncrypt == null) {
				final UrlHashSet encryptUrlList = encryptConfig.getEncryptUrlList();
				isEncrypt = encryptUrlList.containsPath(requestPath);
			} else {
				isEncrypt = responseDataEncrypt.requestEncrypt();
			}
		}
		try {
			// 设置响应流包装方式
			SwRequest swRequest;
			if (isEncrypt) {
				swRequest = new SwRequest(new HttpServletRequestWrapper(request));
			} else {
				swRequest = new SwRequest(request);
			}
			swRequest.setEncrypt(isEncrypt);
			return swRequest;
		} catch (Exception exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	/**
	 * 包装处理响应流
	 * <p>根据是否请求加密来控制 响应流的包装类型</p>
	 *
	 * @param encryptConfig       加密配置信息
	 * @param responseDataEncrypt 当前请求方法/类的加密注解
	 * @param requestPath         当前访问接口地址
	 * @param response            响应流
	 * @return 包装后的响应流
	 */
	private static SwResponse handlerResponse(
			IcpConfig.EncryptConfig encryptConfig,
			ResponseDataEncrypt responseDataEncrypt,
			String requestPath,
			HttpServletResponse response
	) {
		boolean isEncrypt = false;
		if (encryptConfig.getEncrypt()) {
			if (responseDataEncrypt == null) {
				final UrlHashSet encryptUrlList = encryptConfig.getEncryptUrlList();
				isEncrypt = encryptUrlList.containsPath(requestPath);
			} else {
				isEncrypt = responseDataEncrypt.responseEncrypt();
			}
		}
		try {
			SwResponse swResponse;
			if (isEncrypt) {
				swResponse = new SwResponse(new HttpServletResponseWrapper(response));
			} else {
				swResponse = new SwResponse(response);
			}
			swResponse.setEncrypt(isEncrypt);
			return swResponse;
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
