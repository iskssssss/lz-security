package com.sowell.security.router;

import com.sowell.security.IcpManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.context.IcpContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
	 * @throws IllegalAccessException 异常1
	 * @throws ServletException       异常2
	 * @throws IOException            异常3
	 */
	public static boolean filter(
			Object... params
	) throws IllegalAccessException, ServletException, IOException {
		HttpServletRequest servletRequest = IcpContextHandler.getServletRequest();
		HttpServletResponse servletResponse = IcpContextHandler.getServletResponse();
		AbstractInterfacesFilter interfacesFilter = IcpManager.getInterfacesFilter();
		return interfacesFilter.doFilter(servletRequest, servletResponse, params);
	}
}
