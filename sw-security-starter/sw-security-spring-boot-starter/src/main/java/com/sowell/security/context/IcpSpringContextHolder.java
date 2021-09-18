package com.sowell.security.context;

import com.sowell.security.IcpManager;
import com.sowell.security.annotation.RecordRequestData;
import com.sowell.security.annotation.RecordResponseData;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.fun.IcpFilterFunction;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.model.UserAgentInfo;
import com.sowell.security.wrapper.HttpServletRequestWrapper;
import com.sowell.security.wrapper.HttpServletResponseWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 11:56
 */
public class IcpSpringContextHolder {

	public static void setContext(
			HttpServletRequest request,
			HttpServletResponse response
	) {
		IcpSpringContextHolder.setContext(request, response, null);
	}

	public static void setContext(
			ServletRequest request,
			ServletResponse response,
			IcpFilterFunction<SwRequest, SwResponse> function
	) {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			Method method = IcpManager.getMethodByInterfaceUrl(httpServletRequest.getServletPath());

			SwRequest swRequest = handlerRequest(method, httpServletRequest);
			SwResponse swResponse = handlerResponse(method, httpServletResponse);

			IcpSpringContextHolder.setContext(swRequest, swResponse);
			if (function == null) {
				return;
			}
			function.run(swRequest, swResponse);
		} finally {
			if (function != null) {
				IcpSpringContextHolder.removeContext();
			}
		}
	}

	public static void setContext(
			SwRequest request,
			SwResponse response
	) {
		IcpSecurityContextThreadLocal.setBox(
				request,
				response,
				new IcpStorage(request)
		);
	}

	private static SwResponse handlerResponse(Method method, HttpServletResponse response) {
		RecordResponseData recordResponseData = null;
		if (method != null) {
			recordResponseData = method.getAnnotation(RecordResponseData.class);
		}
		if (recordResponseData == null) {
			return new SwResponse(response);
		}
		try {
			return new SwResponse(new HttpServletResponseWrapper(response));
		} catch (Exception exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	public static SwRequest handlerRequest(Method method, HttpServletRequest request) {
		RecordRequestData recordRequestData = null;
		if (method != null) {
			recordRequestData = method.getAnnotation(RecordRequestData.class);
		}
		if (recordRequestData == null) {
			return new SwRequest(request);
		}
		try {
			return new SwRequest(new HttpServletRequestWrapper(request));
		} catch (IOException exception) {
			throw new SecurityException(RCode.REQUEST_ERROR.getCode(), RCode.REQUEST_ERROR.getMessage(), exception);
		}
	}

	public static void removeContext() {
		BaseRequest servletRequest = IcpSecurityContextThreadLocal.getServletRequest();
		servletRequest.removeAllAttribute();
		servletRequest = null;
		IcpSecurityContextThreadLocal.remove();
	}

	public static IcpContextTheadLocal getIcpContext() {
		return ((IcpContextTheadLocal) IcpManager.getIcpContext());
	}

	public static IcpStorage getStorage() {
		IcpContextTheadLocal icpContext = getIcpContext();
		return ((IcpStorage) icpContext.getStorage());
	}

	public static SwRequest getRequest() {
		IcpContextTheadLocal icpContext = getIcpContext();
		return ((SwRequest) icpContext.getRequest());
	}

	public static SwResponse getResponse() {
		IcpContextTheadLocal icpContext = getIcpContext();
		return ((SwResponse) icpContext.getResponse());
	}

	public static UserAgentInfo getUserAgentInfo() {
		final IcpStorage storage = getStorage();
		return storage.getUserAgentInfo();
	}

	public static Method getControllerMethod() {
		SwRequest request = getRequest();
		return request.getControllerMethod();
	}

	public static Long getRequestTime() {
		final IcpStorage storage = getStorage();
		return storage.getRequestTime();
	}
}
