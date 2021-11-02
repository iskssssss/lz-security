package com.sowell.security.filter;

import com.sowell.security.IcpManager;
import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.filter.defaults.DefaultFilterErrorHandler;
import com.sowell.security.filter.defaults.DefaultFilterLogHandler;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.filter.filters.AbsInterfacesFilterBuilder;
import com.sowell.security.filter.filters.IInterfacesFilter;
import com.sowell.security.filter.filters.StartFilter;
import com.sowell.security.filter.handler.FilterDataHandler;
import com.sowell.security.handler.BaseFilterErrorHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.tool.core.enums.HttpStatus;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 16:22
 */
public class IcpFilter extends IcpManager {

	//====================================================================================================================================

	protected static final FilterConfigurer FILTER_CONFIGURER = new FilterConfigurer();

	/**
	 * 获取过滤配置文件
	 *
	 * @return 过滤配置文件
	 */
	public static FilterConfigurer getFilterConfigurer() {
		return FILTER_CONFIGURER;
	}

	//====================================================================================================================================

	protected static final AbsInterfacesFilterBuilder INTERFACES_FILTER = new StartFilter();

	/**
	 * 设置接口过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 *
	 * @param interfacesFilterList 过滤执行链
	 */
	public static void linkInterfacesFilter(AbsInterfacesFilterBuilder... interfacesFilterList) {
		if (interfacesFilterList == null || interfacesFilterList.length < 1) {
			throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "接口过滤执行链不可为空!");
		}
		AbsInterfacesFilterBuilder ai = IcpFilter.INTERFACES_FILTER;
		boolean existLogBeforeFilter = false;
		for (final AbsInterfacesFilterBuilder interfacesFilter : interfacesFilterList) {
			if (interfacesFilter.getClass().getAnnotation(LogBeforeFilter.class) != null) {
				if (existLogBeforeFilter) {
					throw new RuntimeException("注解(LogBeforeFilter)全局只可存在一个。");
				}
				existLogBeforeFilter = true;
			}
			ai.linkFilter(interfacesFilter);
			ai = interfacesFilter;
		}
	}

	/**
	 * 获取接口过滤执行链
	 *
	 * @return 接口过滤执行链
	 */
	public static AbsInterfacesFilterBuilder getInterfacesFilter() {
		return IcpFilter.INTERFACES_FILTER;
	}

	//====================================================================================================================================

	protected static BaseFilterLogHandler filterLogHandler = null;

	/**
	 * 设置过滤日志处理器
	 *
	 * @param filterLogHandler 过滤日志处理器
	 */
	public static void setFilterLogHandler(BaseFilterLogHandler filterLogHandler) {
		IcpFilter.filterLogHandler = filterLogHandler;
	}

	/**
	 * 获取日志处理器
	 *
	 * @return 日志处理器
	 */
	public static BaseFilterLogHandler getFilterLogHandler() {
		if (IcpFilter.filterLogHandler == null) {
			synchronized (IcpFilter.class) {
				if (IcpFilter.filterLogHandler == null) {
					IcpFilter.filterLogHandler = new DefaultFilterLogHandler();
				}
			}
		}
		return IcpFilter.filterLogHandler;
	}

	//====================================================================================================================================

	protected static BaseFilterErrorHandler<?> filterErrorHandler = null;

	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 */
	public static void setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		IcpFilter.filterErrorHandler = filterErrorHandler;
	}

	/**
	 * 获取过滤错误处理器
	 *
	 * @return 过滤错误处理器
	 */
	public static BaseFilterErrorHandler<?> getFilterErrorHandler() {
		if (IcpFilter.filterErrorHandler == null) {
			synchronized (IcpFilter.class) {
				if (IcpFilter.filterErrorHandler == null) {
					IcpFilter.filterErrorHandler = new DefaultFilterErrorHandler();
				}
			}
		}
		return IcpFilter.filterErrorHandler;
	}

	//====================================================================================================================================

	protected static final FilterDataHandler FILTER_DATA_HANDLER = new FilterDataHandler();

	/**
	 * 获取数据处理器
	 *
	 * @return 数据处理器
	 */
	public static FilterDataHandler getFilterDataHandler() {
		return IcpFilter.FILTER_DATA_HANDLER;
	}

	//====================================================================================================================================

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
		IInterfacesFilter interfacesFilter = IcpFilter.getInterfacesFilter();
		return interfacesFilter.doFilter(request, response, params);
	}

	/**
	 * 过滤器初始化
	 */
	public static void init() {
		AbsInterfacesFilterBuilder interfacesFilter = IcpFilter.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.init();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}

	/**
	 * 过滤器销毁
	 */
	public static void destroy() {
		AbsInterfacesFilterBuilder interfacesFilter = IcpFilter.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.destroy();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}
}
