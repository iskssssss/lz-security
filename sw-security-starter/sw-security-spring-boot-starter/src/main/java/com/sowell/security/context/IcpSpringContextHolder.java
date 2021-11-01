package com.sowell.security.context;

import com.sowell.security.IcpManager;
import com.sowell.security.annotation.RecordRequestData;
import com.sowell.security.annotation.RecordResponseData;
import com.sowell.security.annotation.ResponseDataEncrypt;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.fun.IcpFilterFunction;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.wrapper.HttpServletRequestWrapper;
import com.sowell.security.wrapper.HttpServletResponseWrapper;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.reflect.model.ControllerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

	public static void setContext(
			ServletRequest request, ServletResponse response, long startRequestTime
	) {
		IcpSpringContextHolder.setContext(request, response, startRequestTime, null);
	}

	public static void setContext(
			ServletRequest request, ServletResponse response, long startRequestTime, IcpFilterFunction<SwRequest, SwResponse> function
	) {
		try {
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			// 获取当前接口访问方法
			ControllerMethod method = IcpManager.getMethodByInterfaceUrl(httpServletRequest.getServletPath());
			// 处理请求流和响应流信息
			SwRequest swRequest = handlerRequest(method, httpServletRequest);
			SwResponse swResponse = handlerResponse(method, swRequest, httpServletResponse);
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

	public static void setContext(
			SwRequest request, SwResponse response, long startRequestTime
	) {
		final IcpSpringStorage icpSpringStorage = new IcpSpringStorage(request, startRequestTime);
		IcpSecurityContextThreadLocal.setBox(request, response, icpSpringStorage);
	}

	private static SwResponse handlerResponse(
			ControllerMethod method, SwRequest swRequest, HttpServletResponse response
	) {
		// 是否要记录响应数据信息
		RecordResponseData recordResponseData = null;
		// 是否需要加密
		ResponseDataEncrypt responseDataEncrypt = null;
		if (method != null) {
			recordResponseData = method.getMethodAndControllerAnnotation(RecordResponseData.class);
			responseDataEncrypt = method.getMethodAndControllerAnnotation(ResponseDataEncrypt.class);
		}
		// 当前访问的接口
		final String requestPath = swRequest.getRequestPath();
		// 校验当前请求接口返回数据是否要加密
		final IcpConfig.EncryptConfig encryptConfig = IcpManager.getIcpConfig().getEncryptConfig();
		final UrlHashSet encryptUrlList = encryptConfig.getEncryptUrlList();
		final boolean isEncrypt = encryptConfig.getEncrypt() && (responseDataEncrypt != null || encryptUrlList.containsUrl(requestPath));
		try {
			// 设置响应流包装方式
			SwResponse swResponse;
			if (recordResponseData != null || isEncrypt) {
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

	public static SwRequest handlerRequest(
			ControllerMethod method, HttpServletRequest request
	) {
		RecordRequestData recordRequestData = null;
		if (method != null) {
			recordRequestData = method.getMethodAndControllerAnnotation(RecordRequestData.class);
		}
		SwRequest swRequest;
		try {
			if (recordRequestData != null) {
				return new SwRequest(new HttpServletRequestWrapper(request));
			} else {
				swRequest = new SwRequest(request);
			}
			return swRequest;
		} catch (IOException exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	public static void removeContext() {
		try {
			BaseRequest servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
			servletRequest.removeAllAttribute();
			servletRequest = null;
		} finally {
			IcpSecurityContextThreadLocal.remove();
		}
	}

	private static IcpContextTheadLocal<?, ?> getIcpContext() {
		return ((IcpContextTheadLocal<?, ?>) IcpManager.getIcpContext());
	}

	public static IcpSpringStorage getStorage() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((IcpSpringStorage) icpContext.getStorage());
	}

	public static SwRequest getRequest() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((SwRequest) icpContext.getRequest());
	}

	public static SwResponse getResponse() {
		IcpContextTheadLocal<?, ?> icpContext = getIcpContext();
		return ((SwResponse) icpContext.getResponse());
	}
}
