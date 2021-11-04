package com.sowell.security.auth.filters;

import com.sowell.security.auth.IcpAuthManager;
import com.sowell.security.auth.handler.AbstractAuthorizationHandler;
import com.sowell.security.auth.logout.AbstractLogoutHandler;
import com.sowell.security.filter.IcpFilterManager;
import com.sowell.security.fun.IcpFilterAuthStrategy;
import com.sowell.security.tool.filters.BaseFilter;
import com.sowell.security.tool.mode.SwRequest;
import com.sowell.security.tool.mode.SwResponse;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 11:22
 */
public class IcpAuthFilter extends BaseFilter {

	/**
	 * 过滤前处理
	 */
	private IcpFilterAuthStrategy authBeforeHandler;
	/**
	 * 过滤后处理
	 */
	private IcpFilterAuthStrategy authAfterHandler;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.authBeforeHandler = IcpAuthManager.getAuthConfigurer().getAuthBeforeHandler();
		this.authAfterHandler = IcpAuthManager.getAuthConfigurer().getAuthAfterHandler();
	}

	@Override
	public boolean doFilter(SwRequest swRequest, SwResponse swResponse) throws Exception {
		this.authBeforeHandler.run();
		// 登录处理
		if (swRequest.matchUrl(IcpAuthManager.getAuthConfigurer().getLoginUrl())) {
			/*final AbstractAuthorizationHandler authorizationHandler = IcpAuthManager.getAuthorizationHandler();
			authorizationHandler.authorization(swRequest, swResponse);*/
			return true;
		}
		if (IcpFilterManager.filter()) {
			return true;
		}
		// 登出处理
		if (swRequest.matchUrl(IcpAuthManager.getAuthConfigurer().getLogoutUrl())) {
			/*final AbstractLogoutHandler logoutHandler = IcpAuthManager.getLogoutHandler();
			logoutHandler.logout(swRequest, swResponse);*/
			return true;
		}
		this.authAfterHandler.run();
		return true;
	}

	@Override
	public void destroy() {

	}
}
