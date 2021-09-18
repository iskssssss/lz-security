package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.impl.AuthorizationHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.IcpSpringContextHolder;
import com.sowell.security.enums.HttpStatus;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.handler.FilterDataHandler;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.wrapper.HttpServletResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

	protected FilterConfigurer.FilterUrl filterUrl;

	protected FilterDataHandler getFilterDataHandler() {
		if (this.filterDataHandler == null) {
			this.filterDataHandler = IcpManager.getFilterDataHandler();
		}
		return this.filterDataHandler;
	}

	public abstract void doFilter(
			SwRequest swRequest,
			SwResponse swResponse,
			FilterChain chain
	) throws Exception;

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		try {
			IcpSpringContextHolder.setContext(request, response, (swRequest, swResponse) -> {
						this.filterBeforeHandler.run();
						Exception ex = null;
						byte[] responseBytes = null;
						try {
							final HttpServletResponse responseResponse = swResponse.getResponse();
							//过滤
							this.doFilter(swRequest, swResponse, chain);
							if (responseResponse instanceof HttpServletResponseWrapper) {
								responseBytes = ((HttpServletResponseWrapper) response).toByteArray();
							}
						} catch (Exception exception) {
							ex = exception;
						} finally {
							this.getFilterDataHandler().handler(swRequest, swResponse, responseBytes, ex);
							this.filterAfterHandler.run();
						}
					}
			);
		} catch (Exception e) {
			if (e instanceof SecurityException) {
				throw e;
			}
			throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "过滤异常", e);
		}
	}
}
