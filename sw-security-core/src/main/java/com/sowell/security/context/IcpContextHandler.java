package com.sowell.security.context;

import com.sowell.security.IcpManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.fun.IcpFunction;
import com.sowell.security.utils.BeanUtil;
import com.sowell.security.IcpConstant;
import com.sowell.security.context.model.IcpAbstractStorage;
import com.sowell.security.utils.ServletUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 16:29
 */
public class IcpContextHandler {

	public static void setContext(
			HttpServletRequest request,
			HttpServletResponse response,
			IcpAbstractStorage icpAbstractStorage
	) {
		IcpSecurityContext.setBox(request, response, icpAbstractStorage);
	}

	public static void setContext(
			ServletRequest request,
			ServletResponse response,
			IcpAbstractStorage icpAbstractStorage,
			IcpFunction function
	) {
		try {
			IcpContextHandler.setContext(((HttpServletRequest) request), ((HttpServletResponse) response), icpAbstractStorage);
			IcpContextHandler.setAttribute(IcpConstant.REQUEST_TIME_CACHE_KEY, System.currentTimeMillis());
			function.run();
		} finally {
			IcpContextHandler.removeContext();
		}
	}

	public static HttpServletRequest getServletRequest() {
		return IcpSecurityContext.getServletRequest();
	}

	public static HttpServletResponse getServletResponse() {
		return IcpSecurityContext.getServletResponse();
	}

	public static void removeContext() {
		IcpSecurityContext.remove();
	}

	public static void removeAllAttribute() {
		final HttpServletRequest servletRequest = getServletRequest();
		final Enumeration<String> attributeNames = servletRequest.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			final String attributeName = attributeNames.nextElement();
			servletRequest.removeAttribute(attributeName);
		}
	}

	public static boolean setSaveRequestLog(boolean is) {
		final IcpAbstractStorage icpAbstractStorage = IcpSecurityContext.getIcpAbstractStorage();
		if (icpAbstractStorage == null) {
			return false;
		}
		icpAbstractStorage.setSaveRequestLog(is);
		return is;
	}

	public static boolean isUrl(String url) {
		final HttpServletRequest servletRequest = getServletRequest();
		String requestUrl = ServletUtil.getLookupPathForRequest(servletRequest);
		return ServletUtil.urlMatch(url, requestUrl);
	}

	public static boolean isSaveRequestLog() {
		final IcpAbstractStorage icpAbstractStorage = IcpSecurityContext.getIcpAbstractStorage();
		if (icpAbstractStorage == null) {
			return false;
		}
		return icpAbstractStorage.getSaveRequestLog();
	}

	public static void setAttribute(String key, Object value) {
		final HttpServletRequest request = getServletRequest();
		request.setAttribute(key, value);
	}

	public static <T> T getAttribute(String key, Class<T> tClass) {
		final HttpServletRequest request = getServletRequest();
		final Object attribute = request.getAttribute(key);
		if (attribute == null) {
			return null;
		}
		try {
			return (T) attribute;
		} catch (Exception e) {
			if (attribute instanceof CharSequence) {
				return BeanUtil.toBean(attribute.toString(), tClass);
			}
		}
		return null;
	}

	public static Object getAttribute(String key) {
		final HttpServletRequest request = getServletRequest();
		return request.getAttribute(key);
	}
}
