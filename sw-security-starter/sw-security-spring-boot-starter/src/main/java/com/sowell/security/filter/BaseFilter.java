package com.sowell.security.filter;

import com.sowell.security.annotation.RecordRequestData;
import com.sowell.security.annotation.RecordResponseData;
import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.impl.AuthorizationHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.wrapper.HttpServletRequestWrapper;
import com.sowell.security.wrapper.HttpServletResponseWrapper;
import com.sowell.security.context.IcpSecurityContextHandler;
import com.sowell.security.IcpManager;
import com.sowell.security.filter.IcpFilterAuthStrategy;
import com.sowell.security.handler.FilterDataHandler;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

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
			HttpServletRequest request,
			HttpServletResponse response,
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
			// 前
			this.filterBeforeHandler.run();
			IcpSecurityContextHandler.setContext(request, response,
					() -> {
						Exception ex = null;
						byte[] responseBytes = null;
						HttpServletRequest servletRequest = (HttpServletRequest) request;
						HttpServletResponse servletResponse = (HttpServletResponse) response;
						final Method method = IcpSecurityContextHandler.getMethod();
						final String requestMethod = servletRequest.getMethod();
						IcpSecurityContextHandler.setSaveRequestLog(!HttpMethod.OPTIONS.name().equals(requestMethod));
						try {
							RecordResponseData recordResponseData = null;
							RecordRequestData recordRequestData = null;
							if (method != null) {
								recordResponseData = method.getAnnotation(RecordResponseData.class);
								recordRequestData = method.getAnnotation(RecordRequestData.class);
							}
							// 判断当前接口是否记录请求数据
							if (recordRequestData != null || IcpSecurityContextHandler.isUrl(filterUrl.loginUrl)) {
								servletRequest = new HttpServletRequestWrapper(servletRequest);
							}
							// 判断当前接口是否记录响应数据
							if (recordResponseData != null) {
								servletResponse = new HttpServletResponseWrapper(servletResponse);
							}
							//过滤
							this.doFilter(servletRequest, servletResponse, chain);
							if (servletResponse instanceof HttpServletResponseWrapper) {
								responseBytes = ((HttpServletResponseWrapper) response).toByteArray();
							}
						} catch (Exception exception) {
							ex = exception;
						} finally {
							this.getFilterDataHandler().handler((HttpServletRequest) request, (HttpServletResponse) response, responseBytes, ex);
							IcpSecurityContextHandler.removeAllAttribute();
						}
					}
			);
		} catch (Exception e) {
			if (e instanceof SecurityException) {
				throw e;
			}
			throw new SecurityException("过滤异常", e);
		} finally {
			// 后
			this.filterAfterHandler.run();
		}
	}
}
