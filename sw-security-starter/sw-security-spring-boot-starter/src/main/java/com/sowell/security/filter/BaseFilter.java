package com.sowell.security.filter;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.impl.AuthorizationHandler;
import com.sowell.security.context.IcpSpringContextHolder;
import com.sowell.security.enums.HttpStatus;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.handler.FilterDataHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.wrapper.HttpServletResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Optional;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 12:38
 */
public abstract class BaseFilter implements Filter {

	protected FilterDataHandler filterDataHandler;
	protected IcpFilterAuthStrategy filterBeforeHandler;
	protected IcpFilterAuthStrategy filterAfterHandler;
	/**
	 * 认证过滤器
	 */
	protected AuthorizationHandler authorizationHandler;

	/**
	 * 登出处理器
	 */
	protected AbstractLogoutHandler abstractLogoutHandler;

	private UrlHashSet includeUrls = null;
	private UrlHashSet excludeUrls = null;

	protected FilterDataHandler getFilterDataHandler() {
		if (this.filterDataHandler == null) {
			this.filterDataHandler = IcpManager.getFilterDataHandler();
		}
		return this.filterDataHandler;
	}

	private UrlHashSet excludeUrls() {
		if (this.excludeUrls == null) {
			this.excludeUrls = IcpManager.getFilterConfigurer().getExcludeUrls();
		}
		return this.excludeUrls;
	}

	private UrlHashSet includeUrls() {
		if (this.includeUrls == null) {
			this.includeUrls = IcpManager.getFilterConfigurer().getIncludeUrls();
		}
		return this.includeUrls;
	}

	public abstract boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse
	) throws Exception;

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) {
		try {
			IcpSpringContextHolder.setContext(request, response, System.currentTimeMillis());
			SwRequest swRequest = IcpSpringContextHolder.getRequest();
			SwResponse swResponse = IcpSpringContextHolder.getResponse();
			if (filterHandler(swRequest, swResponse)) {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			SecurityException securityException;
			if (e instanceof SecurityException) {
				securityException = (SecurityException) e;
			} else {
				securityException = new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "过滤异常", e);
			}
			ServletUtil.printResponse(IcpSpringContextHolder.getResponse(), securityException);
			IcpLoggerUtil.error(getClass(), securityException.getMessage(), securityException);
		} finally {
			IcpSpringContextHolder.removeContext();
		}
	}

	private boolean filterHandler(SwRequest swRequest, SwResponse swResponse) {
		Exception ex = null;
		try {
			final String requestPath = swRequest.getRequestPath();
			/**
			 * 判断当前访问地址 是否是开放地址 or 是否在拦截地址中
			 */
			if (!includeUrls().containsUrl(requestPath) || excludeUrls().containsUrl(requestPath)) {
				return true;
			}
			// 过滤前处理
			this.filterBeforeHandler.run();
			// 过滤
			boolean filterResult = this.doFilter(swRequest, swResponse);
			// 过滤后处理
			this.filterAfterHandler.run();
			return filterResult;
		} catch (Exception exception) {
			ex = exception;
			return false;
		} finally {
			if (!this.getFilterDataHandler().handler(swRequest, swResponse, ex)) {
				if (swResponse.getResponse() instanceof HttpServletResponseWrapper) {
					final byte[] responseDataBytes = swResponse.getResponseDataBytes();
					swResponse.print(responseDataBytes);
				}
			}
			final Optional<Object> logEntityOptional = Optional.ofNullable(swRequest.getAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY));
			if (logEntityOptional.isPresent()) {
				BaseFilterLogHandler filterLogHandler = IcpManager.getFilterLogHandler();
				filterLogHandler.afterHandler(swRequest, swResponse, logEntityOptional.get(), ex);
			}
		}
	}
}
