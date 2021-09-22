package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.cache.utils.GlobalScheduled;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.router.IcpRouter;
import com.sowell.security.utils.SpringUtil;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.Map;

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
		final Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(LogBeforeFilter.class);
		if (beansWithAnnotation.size() > 1) {
			throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
		}
		FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
		super.filterBeforeHandler = filterConfigurer.getFilterBeforeHandler();
		super.filterAfterHandler = filterConfigurer.getFilterAfterHandler();
		super.authorizationHandler = IcpManager.getAuthorizationHandler();
		super.abstractLogoutHandler = IcpManager.getLogoutHandler();
		IcpRouter.init();
	}

	@Override
	public boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse
	) throws Exception {
		return IcpRouter.filter();
	}

	@Override
	public void destroy() {
		IcpRouter.destroy();
		if (GlobalScheduled.INSTANCE.shutdownNow().isEmpty()) {
			// TODO ...
		}
		if (IcpLoggerUtil.removeIcpLoggerMap()) {
			// TODO ...
		}
	}
}
