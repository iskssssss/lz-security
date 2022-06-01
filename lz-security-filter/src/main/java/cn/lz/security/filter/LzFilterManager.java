package cn.lz.security.filter;

import cn.lz.security.LzCoreManager;
import cn.lz.security.annotation.LogBeforeFilter;
import cn.lz.security.context.LzContext;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.filters.IInterfacesFilter;
import cn.lz.security.filter.filters.StartFilter;
import cn.lz.security.handler.BaseFilterErrorHandler;
import cn.lz.security.log.BaseFilterLogHandler;
import cn.lz.security.filter.defaults.DefaultFilterErrorHandler;
import cn.lz.security.filter.defaults.DefaultFilterLogHandler;
import cn.lz.security.filter.filters.AbsInterfacesFilterBuilder;
import cn.lz.security.filter.handler.FilterDataHandler;
import cn.lz.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 16:22
 */
public class LzFilterManager extends LzCoreManager {

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
			throw new SecurityException(RCode.INTERNAL_SERVER_ERROR.getCode(), "接口过滤执行链不可为空!");
		}
		AbsInterfacesFilterBuilder ai = LzFilterManager.INTERFACES_FILTER;
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
		return LzFilterManager.INTERFACES_FILTER;
	}

	//====================================================================================================================================

	protected static BaseFilterLogHandler filterLogHandler = null;

	/**
	 * 设置过滤日志处理器
	 *
	 * @param filterLogHandler 过滤日志处理器
	 */
	public static void setFilterLogHandler(BaseFilterLogHandler filterLogHandler) {
		LzFilterManager.filterLogHandler = filterLogHandler;
	}

	/**
	 * 获取日志处理器
	 *
	 * @return 日志处理器
	 */
	public static BaseFilterLogHandler getFilterLogHandler() {
		if (LzFilterManager.filterLogHandler == null) {
			synchronized (LzFilterManager.class) {
				if (LzFilterManager.filterLogHandler == null) {
					LzFilterManager.filterLogHandler = new DefaultFilterLogHandler();
				}
			}
		}
		return LzFilterManager.filterLogHandler;
	}

	//====================================================================================================================================

	protected static BaseFilterErrorHandler<?> filterErrorHandler = null;

	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 */
	public static void setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		LzFilterManager.filterErrorHandler = filterErrorHandler;
	}

	/**
	 * 获取过滤错误处理器
	 *
	 * @return 过滤错误处理器
	 */
	public static BaseFilterErrorHandler<?> getFilterErrorHandler() {
		if (LzFilterManager.filterErrorHandler == null) {
			synchronized (LzFilterManager.class) {
				if (LzFilterManager.filterErrorHandler == null) {
					LzFilterManager.filterErrorHandler = new DefaultFilterErrorHandler();
				}
			}
		}
		return LzFilterManager.filterErrorHandler;
	}

	//====================================================================================================================================

	protected static final FilterDataHandler FILTER_DATA_HANDLER = new FilterDataHandler();

	/**
	 * 获取数据处理器
	 *
	 * @return 数据处理器
	 */
	public static FilterDataHandler getFilterDataHandler() {
		return LzFilterManager.FILTER_DATA_HANDLER;
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
		final LzContext<?, ?> lzContext = LzCoreManager.getLzContext();
		BaseRequest<?> request = lzContext.getRequest();
		BaseResponse<?> response = lzContext.getResponse();
		IInterfacesFilter interfacesFilter = LzFilterManager.getInterfacesFilter();
		return interfacesFilter.doFilter(request, response, params);
	}

	/**
	 * 过滤器初始化
	 */
	public static void init() {
		AbsInterfacesFilterBuilder interfacesFilter = LzFilterManager.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.init();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}

	/**
	 * 过滤器销毁
	 */
	public static void destroy() {
		AbsInterfacesFilterBuilder interfacesFilter = LzFilterManager.getInterfacesFilter();
		while (interfacesFilter != null) {
			interfacesFilter.destroy();
			interfacesFilter = ((AbsInterfacesFilterBuilder) interfacesFilter.getNextFilter());
		}
	}
}
