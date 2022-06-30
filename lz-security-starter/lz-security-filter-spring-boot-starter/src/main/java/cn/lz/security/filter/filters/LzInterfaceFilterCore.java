package cn.lz.security.filter.filters;

import cn.lz.security.annotation.LogBeforeFilter;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.fun.LzFilterAuthStrategy;
import cn.lz.security.tool.filters.BaseFilterCore;
import cn.lz.security.tool.mode.LzRequest;
import cn.lz.security.tool.mode.LzResponse;
import cn.lz.security.tool.utils.SpringUtil;
import cn.lz.security.annotation.ExcludeInterface;
import cn.lz.security.annotation.IncludeInterface;
import cn.lz.tool.core.enums.HttpStatus;
import cn.lz.tool.reflect.model.ControllerMethod;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * 接口过滤处理中心
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 11:22
 */
public final class LzInterfaceFilterCore extends BaseFilterCore {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (SpringUtil.getBeansWithAnnotationCount(LogBeforeFilter.class) > 1) {
			throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
		}
		LzFilterManager.init();
	}

	@Override
	public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse) {
		try {
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
