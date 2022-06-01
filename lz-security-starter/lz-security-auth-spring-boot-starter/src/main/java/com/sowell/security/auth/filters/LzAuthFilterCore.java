package com.sowell.security.auth.filters;

import com.sowell.security.auth.LzAuthManager;
import com.sowell.security.auth.annotation.AnonymousCheck;
import com.sowell.security.auth.annotation.AuthCheck;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.LzFilterManager;
import com.sowell.security.annotation.ExcludeInterface;
import com.sowell.security.annotation.IncludeInterface;
import com.sowell.security.fun.LzFilterAuthStrategy;
import com.sowell.security.token.AccessTokenUtil;
import com.sowell.security.tool.filters.BaseFilterCore;
import com.sowell.security.tool.mode.LzRequest;
import com.sowell.security.tool.mode.LzResponse;
import com.sowell.tool.core.enums.AuthCode;
import com.sowell.tool.reflect.model.ControllerMethod;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 11:22
 */
public class LzAuthFilterCore extends BaseFilterCore {

	/**
	 * 过滤前处理
	 */
	private LzFilterAuthStrategy authBeforeHandler;
	/**
	 * 过滤后处理
	 */
	private LzFilterAuthStrategy authAfterHandler;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.authBeforeHandler = LzAuthManager.getAuthConfigurer().getAuthBeforeHandler();
		this.authAfterHandler = LzAuthManager.getAuthConfigurer().getAuthAfterHandler();
	}

	@Override
	public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse) {
		try {
			this.authBeforeHandler.run();
			final ControllerMethod controllerMethod = lzRequest.getControllerMethod();
			final String requestPath = lzRequest.getRequestPath();
			// 判断当前访问地址 (是否是开放地址 or 是否在拦截地址中)
			if (!includeHandler(controllerMethod, requestPath)) {
				return true;
			}
			// 匿名接口处理
			final AnonymousCheck anonymousCheck = controllerMethod.getMethodAndControllerAnnotation(AnonymousCheck.class);
			if (anonymousCheck != null && anonymousCheck.open()) {
				if (AccessTokenUtil.checkExpiration()) {
					return true;
				}
				throw new SecurityException(AuthCode.ANONYMOUS);
			}
			// 认证接口处理
			final AuthCheck authCheck = controllerMethod.getMethodAndControllerAnnotation(AuthCheck.class);
			if (authCheck == null) {
				return true;
			}
			final boolean authCheckOpen = authCheck.open();
			if (authCheckOpen) {
				if (!AccessTokenUtil.checkExpiration()) {
					return true;
				}
				throw new SecurityException(AuthCode.AUTHORIZATION);
			}
			return true;
		} finally {
			this.authAfterHandler.run();
		}
	}

	/**
	 * 是否拦截接口
	 *
	 * @param controllerMethod 当前请求方法
	 * @param requestPath      当前请求地址
	 * @return true 拦截 false 不拦截
	 */
	public boolean includeHandler(ControllerMethod controllerMethod, String requestPath) {
		ExcludeInterface excludeInterface = null;
		IncludeInterface includeInterface = null;
		if (controllerMethod != null) {
			excludeInterface = controllerMethod.getMethodAndControllerAnnotation(ExcludeInterface.class);
			includeInterface = controllerMethod.getMethodAndControllerAnnotation(IncludeInterface.class);
		}
		if (excludeInterface != null) {
			return excludeInterface.open();
		}
		if (LzFilterManager.getFilterConfigurer().getExcludeUrls().containsPath(requestPath)) {
			return false;
		}
		if (includeInterface != null) {
			return includeInterface.open();
		}
		return LzFilterManager.getFilterConfigurer().getIncludeUrls().containsPath(requestPath);
	}

	@Override
	public void destroy() {

	}
}
