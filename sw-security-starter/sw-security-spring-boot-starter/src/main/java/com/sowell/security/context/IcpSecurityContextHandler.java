package com.sowell.security.context;

import com.sowell.security.fun.IcpFunction;
import com.sowell.security.model.UserAgentInfo;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.IcpManager;
import com.sowell.security.context.IcpContextHandler;
import com.sowell.security.context.IcpSecurityContext;
import com.sowell.security.context.model.IcpAbstractStorage;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:35
 */
public class IcpSecurityContextHandler extends IcpContextHandler {

	public static void setContext(
			ServletRequest request,
			ServletResponse response,
			IcpFunction function
	) {
		IcpContextHandler.setContext(request, response, new IcpStorage(request, response), function);
	}

	public static void removeContext() {
		IcpContextHandler.removeContext();
	}

	public static IcpStorage getIcpStorage() {
		final IcpAbstractStorage icpAbstractStorage = IcpSecurityContext.getIcpAbstractStorage();
		return ((IcpStorage) icpAbstractStorage);
	}

	public static UserAgentInfo getUserAgentInfo() {
		final IcpStorage icpAbstractStorage = getIcpStorage();
		if (icpAbstractStorage == null) {
			return new UserAgentInfo();
		}
		final UserAgentInfo userAgentInfo = icpAbstractStorage.getUserAgentInfo();
		if (userAgentInfo == null) {
			return new UserAgentInfo();
		}
		return userAgentInfo;
	}

	public static Method getMethod() {
		final IcpStorage icpAbstractStorage = getIcpStorage();
		if (icpAbstractStorage == null) {
			return null;
		}
		final String requestUrl = icpAbstractStorage.getRequestUrl();
		if (StringUtil.isEmpty(requestUrl)) {
			return null;
		}
		return IcpManager.getMethodByInterfaceUrl(requestUrl);
	}

	public static String getAuthorizationToken() {
		final HttpServletRequest servletRequest = getServletRequest();
		return servletRequest.getHeader("Authorization");
	}
}