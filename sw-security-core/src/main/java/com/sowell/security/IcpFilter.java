package com.sowell.security;

import com.sowell.security.filter.base.IInterfacesFilter;
import com.sowell.security.filter.base.AbsInterfacesFilterBuilder;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 16:22
 */
public class IcpFilter {

	/**
	 * 开始过滤接口
	 *
	 * @param params 过滤参数
	 * @return 过滤结果
	 * @throws SecurityException 过滤错误
	 */
	public static boolean filter(Object... params) throws SecurityException {
		final IcpContext<?, ?> icpContext = IcpManager.getIcpContext();
		BaseRequest<?> request = icpContext.getRequest();
		BaseResponse<?> response = icpContext.getResponse();
		IInterfacesFilter interfacesFilter = IcpManager.getInterfacesFilter();
		return interfacesFilter.doFilter(request, response, params);
	}

	/**
	 * 过滤器初始化
	 */
	public static void init() {
		AbsInterfacesFilterBuilder interfacesFilter = IcpManager.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.init();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}


	/**
	 * 过滤器销毁
	 */
	public static void destroy() {
		AbsInterfacesFilterBuilder interfacesFilter = IcpManager.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.destroy();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}
}
