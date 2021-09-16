package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.auth.impl.LogoutHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.IcpSecurityContextHandler;
import com.sowell.security.router.IcpRouter;
import com.sowell.security.utils.ServletUtil;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:25
 */
public class IcpServletFilter extends BaseFilter {

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
	}

	@Override
	public void doFilter(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain
	) throws Exception {
		boolean isDoFilter = true;
		if (request instanceof HttpServletRequestWrapper && IcpSecurityContextHandler.isUrl(super.filterUrl.loginUrl)) {
			// 登录处理
			boolean loginResult = super.authorizationHandler.authorization(request, response);
			if (!loginResult){
				return;
			}
			isDoFilter = false;
		}
		if (!isDoFilter) {
			final String authorizationToken = IcpSecurityContextHandler.getAuthorizationToken();
			final boolean filterResult = IcpRouter.filter(authorizationToken);
			if (!filterResult) {
				return;
			}
			isDoFilter = true;
			boolean isLogoutUrl = IcpSecurityContextHandler.isUrl(super.filterUrl.logoutUrl);
			// 登出过滤
			if (isLogoutUrl) {
				if (!abstractLogoutHandler.logout(request, response)) {
					return;
				}
				isDoFilter = false;
			}
		}
		if (!isDoFilter) {
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
