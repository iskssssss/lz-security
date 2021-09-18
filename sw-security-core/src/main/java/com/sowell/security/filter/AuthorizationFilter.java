package com.sowell.security.filter;

import com.sowell.security.auth.ICheckAccessAuthStatusHandler;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.log.IcpLogger;
import com.sowell.security.log.IcpLoggerUtil;

import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:11
 */
public class AuthorizationFilter extends AbstractInterfacesFilter {
	protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(AbstractInterfacesFilter.class);

	private List<String> authorizationUrls = null;
	private ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

	private List<String> authorizationUrls() {
		if (this.authorizationUrls == null) {
			this.authorizationUrls = super.filterConfigurer.filterUrl().authorizationUrls;
		}
		return this.authorizationUrls;
	}

	@Override
	public void init() {
		icpLogger.info("authorization filter init.");
		checkAccessAuthStatusHandler = super.filterConfigurer.login().getCheckAccessAuthStatusHandler();
	}

	@Override
	public boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object... params
	) throws SecurityException {
		final List<String> authorizationUrls = authorizationUrls();
		for (String authorizationUrl : authorizationUrls) {
			final String securityInterface = authorizationUrl.replace(" ", "");
			if (request.isPath(securityInterface)) {
				if (checkAccessAuthStatusHandler.check(request, response)) {
					return discharged(request);
				}
				return headOff(request, response, RCode.AUTHORIZATION);
			}
		}
		return next(request, response, params);
	}

	@Override
	public void destroy() {
		icpLogger.info("authorization filter destroy.");
		this.checkAccessAuthStatusHandler = null;
		this.authorizationUrls = null;
	}
}
