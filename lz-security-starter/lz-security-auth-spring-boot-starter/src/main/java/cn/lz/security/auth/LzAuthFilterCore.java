package cn.lz.security.auth;

import cn.lz.security.auth.config.AuthConfigurer;
import cn.lz.security.auth.login.LoginService;
import cn.lz.security.auth.logout.LogoutService;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.filter.LzInterfaceFilterCore;
import cn.lz.security.filter.context.SpringBootContextTheadLocal;
import cn.lz.security.filter.mode.LzRequest;
import cn.lz.security.filter.mode.LzResponse;
import cn.lz.security.log.LzLogger;
import cn.lz.security.log.LzLoggerUtil;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * 认证过滤处理中心
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 11:22
 */
public class LzAuthFilterCore extends LzInterfaceFilterCore {
	protected final static LzLogger logger = LzLoggerUtil.getLzLogger(LzAuthFilterCore.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("认证过滤处理中心 - init");
		super.init(filterConfig);
	}

	@Override
	public boolean doFilter(LzRequest lzRequest, LzResponse lzResponse, SpringBootContextTheadLocal context) {
		boolean filterResult = super.doFilter(lzRequest, lzResponse, context);
		if (!filterResult) {
			return false;
		}
		String requestPath = lzRequest.getRequestPath();
		AuthConfigurer authConfigurer = LzAuthManager.getAuthConfigurer();
		String loginUrl = authConfigurer.getLoginUrl();
		String logoutUrl = authConfigurer.getLogoutUrl();
		// 登录处理
		if (requestPath.equals(loginUrl)) {
			LoginService loginService = LzAuthManager.getLoginService();
			// 跨域处理
			LzFilterManager.getFilterConfigurer().getCorsHandler().handler(lzRequest, lzResponse, false);
			loginService.login(lzRequest, lzResponse);
			return false;
		}
		// 登出处理
		if (requestPath.equals(logoutUrl)) {
			LogoutService logoutService = LzAuthManager.getLogoutService();
			// 跨域处理
			LzFilterManager.getFilterConfigurer().getCorsHandler().handler(lzRequest, lzResponse, false);
			logoutService.logout(lzRequest, lzResponse);
			return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		logger.info("认证过滤处理中心 - destroy");
		super.destroy();
	}
}
