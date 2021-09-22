package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.utils.GlobalScheduled;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.lang.IcpRunnable;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.router.IcpRouter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


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
		FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
		super.filterBeforeHandler = filterConfigurer.getFilterBeforeHandler();
		super.filterAfterHandler = filterConfigurer.getFilterAfterHandler();
		super.authorizationHandler = IcpManager.getAuthorizationHandler();
		super.filterUrl = filterConfigurer.filterUrl();
		super.abstractLogoutHandler = IcpManager.getLogoutHandler();
		IcpRouter.init();
	}

	@Override
	public boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse
	) throws Exception {
		final HttpServletRequest request = swRequest.getRequest();
		final HttpServletResponse response = swResponse.getResponse();

		return IcpRouter.filter();
	}

	@Override
	public void destroy() {
		IcpRouter.destroy();
		final List<Runnable> runnableList = GlobalScheduled.INSTANCE.shutdownNow();

		if (runnableList == null || runnableList.isEmpty()) {
			return;
		}
		for (Runnable runnable : runnableList) {
			if (!(runnable instanceof IcpRunnable)) {
				continue;
			}
			final IcpRunnable icpRunnable = (IcpRunnable) runnable;
			try {
				icpRunnable.close();
			} catch (IOException ignored) {
			}
		}
	}
}
