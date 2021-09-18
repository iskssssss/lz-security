package com.sowell.security.filter;

import com.sowell.security.auth.ICheckAccessAuthStatusHandler;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:11
 */
public class AuthorizationFilter extends AbstractInterfacesFilter {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		logger.info("authorization filter init.");
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
		logger.info("authorization filter destroy.");
		this.checkAccessAuthStatusHandler = null;
		this.authorizationUrls = null;
	}
}
