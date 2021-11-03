package com.sowell.security.auth.filters;

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
public class AuthFilter extends BaseFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public boolean doFilter(SwRequest swRequest, SwResponse swResponse) throws Exception {
		System.out.println(swRequest.getRequestPath());
		return true;
	}

	@Override
	public void destroy() {

	}
}
