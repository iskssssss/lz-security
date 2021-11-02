package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.tool.cache.utils.GlobalScheduled;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:25
 */
public final class IcpServletFilter extends BaseFilter {

	@Override
	public void init(
			FilterConfig filterConfig
	) throws ServletException {
		/*if (SpringUtil.getBeansWithAnnotationCount(LogBeforeFilter.class) > 1) {
			throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
		}*/
		FilterConfigurer filterConfigurer = IcpFilter.getFilterConfigurer();
		super.filterBeforeHandler = filterConfigurer.getFilterBeforeHandler();
		super.filterAfterHandler = filterConfigurer.getFilterAfterHandler();
		IcpFilter.init();
	}

	@Override
	public boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse
	) throws Exception {
		return IcpFilter.filter();
	}

	@Override
	public void destroy() {
		try {
			IcpFilter.destroy();
			if (GlobalScheduled.INSTANCE.shutdownNow().isEmpty()) {
				// TODO ...
			}
			if (IcpLoggerUtil.removeIcpLoggerMap()) {
				// TODO ...
			}
		} finally {
			// TODO
		}
	}
}
