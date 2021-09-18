package com.sowell.security.router;

import com.sowell.security.IcpManager;
import com.sowell.security.base.AbstractInterfacesFilter;
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
public class IcpRouter {

	/**
	 * 开始过滤接口
	 *
	 * @param params 参数
	 * @return 过滤结果
	 * @throws SecurityException 异常
	 */
	public static boolean filter(
			Object... params
	) throws SecurityException {
		final IcpContext icpContext = IcpManager.getIcpContext();
		BaseRequest<?> request = icpContext.getRequest();
		BaseResponse<?> response = icpContext.getResponse();
		AbstractInterfacesFilter interfacesFilter = IcpManager.getInterfacesFilter();
		return interfacesFilter.doFilter(request, response, params);
	}

	public static void init() {
		AbstractInterfacesFilter interfacesFilter = IcpManager.getInterfacesFilter();
		while (interfacesFilter.hasNext()) {
			interfacesFilter.init();
			interfacesFilter = interfacesFilter.next();
		}
	}

	public static void destroy() {
		AbstractInterfacesFilter interfacesFilter = IcpManager.getInterfacesFilter();
		while (interfacesFilter.hasNext()) {
			interfacesFilter.destroy();
			interfacesFilter = interfacesFilter.next();
		}
	}
}
