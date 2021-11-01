package com.sowell.security.filter;

import com.sowell.security.base.AbsInterfacesFilterBuilder;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;

/**
 * SpringBoot基础执行链
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/09/17 16:55
 */
public abstract class SwAbsInterfacesFilter extends AbsInterfacesFilterBuilder {

	@Override
	public final boolean doFilter(
			BaseRequest<?> request,
			BaseResponse<?> response,
			Object... params
	) throws SecurityException {
		return doFilter(((SwRequest) request), ((SwResponse) response), params);
	}

	/**
	 * 进行过滤
	 *
	 * @param swRequest  请求流
	 * @param swResponse 响应流
	 * @param params     过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	public abstract boolean doFilter(
			SwRequest swRequest,
			SwResponse swResponse,
			Object... params
	) throws SecurityException;
}
