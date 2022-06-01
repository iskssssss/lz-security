package com.sowell.security.tool.filters;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.filters.AbsInterfacesFilterBuilder;
import com.sowell.security.tool.mode.LzRequest;
import com.sowell.security.tool.mode.LzResponse;

/**
 * SpringBoot基础执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/09/17 16:55
 */
public abstract class LzInterfacesFilter extends AbsInterfacesFilterBuilder {

	@Override
	public final boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object... params
	) throws SecurityException {
		return doFilter(((LzRequest) request), ((LzResponse) response), params);
	}

	/**
	 * 进行过滤
	 *
	 * @param lzRequest  请求流
	 * @param lzResponse 响应流
	 * @param params     过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	public abstract boolean doFilter(
			LzRequest lzRequest,
			LzResponse lzResponse,
			Object... params
	) throws SecurityException;
}
