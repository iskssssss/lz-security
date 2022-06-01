package com.sowell.security.filter.filters;

import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.LzFilterManager;
import com.sowell.security.annotation.ExcludeInterface;
import com.sowell.security.annotation.IncludeInterface;
import com.sowell.security.fun.LzFilterAuthStrategy;
import com.sowell.security.tool.filters.BaseFilterCore;
import com.sowell.security.tool.mode.LzRequest;
import com.sowell.security.tool.mode.LzResponse;
import com.sowell.security.tool.utils.SpringUtil;
import com.sowell.tool.core.enums.HttpStatus;
import com.sowell.tool.reflect.model.ControllerMethod;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:25
 */
public final class LzInterfaceFilterCore extends BaseFilterCore {

	/**
	 * 过滤前处理
	 */
	private LzFilterAuthStrategy filterBeforeHandler;
	/**
	 * 过滤后处理
	 */
	private LzFilterAuthStrategy filterAfterHandler;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (SpringUtil.getBeansWithAnnotationCount(LogBeforeFilter.class) > 1) {
			throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
		}
		this.filterBeforeHandler = LzFilterManager.getFilterConfigurer().getFilterBeforeHandler();
		this.filterAfterHandler = LzFilterManager.getFilterConfigurer().getFilterAfterHandler();
		LzFilterManager.init();
	}

	@Override
	public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse) {
		try {
			// 过滤前处理
			if (this.filterBeforeHandler != null) {
				this.filterBeforeHandler.run();
			}
			final ControllerMethod controllerMethod = lzRequest.getControllerMethod();
			final String requestPath = lzRequest.getRequestPath();
			// 判断当前访问地址 (是否是开放地址 or 是否在拦截地址中)
			if (!includeHandler(controllerMethod, requestPath)) {
				return true;
			}
			// 过滤
			return LzFilterManager.filter();
		} catch (Exception e) {
			if (e instanceof SecurityException) {
				throw e;
			} else if (e.getCause() instanceof SecurityException) {
				throw (SecurityException) e.getCause();
			} else {
				throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "过滤异常", e);
			}
		} finally {
			// 过滤后处理
			if (this.filterAfterHandler != null) {
				this.filterAfterHandler.run();
			}
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
		if ((excludeInterface != null && excludeInterface.open()) ||
				LzFilterManager.getFilterConfigurer().getExcludeUrls().containsPath(requestPath)) {
			return false;
		}
		if (includeInterface != null) {
			return includeInterface.open();
		}
		return LzFilterManager.getFilterConfigurer().getIncludeUrls().containsPath(requestPath);
	}

	@Override
	public void destroy() {
		try {
			LzFilterManager.destroy();
			super.destroy();
		} finally {
			// TODO
		}
	}
}
