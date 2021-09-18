package com.sowell.security.filter;

import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 16:55
 */
public abstract class SwInterfacesFilter extends AbstractInterfacesFilter {

	@Override
	public final boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object... params
	) throws SecurityException {
		return doFilter(((SwRequest) request), ((SwResponse) response), params);
	}

	public abstract boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse,
			Object... params
	);
}
