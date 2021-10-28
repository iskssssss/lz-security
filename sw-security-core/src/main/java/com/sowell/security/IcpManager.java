package com.sowell.security;

import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.config.InterfacesMethodMap;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.IcpStorage;
import com.sowell.security.defaults.DefaultCacheManager;
import com.sowell.security.defaults.DefaultFilterErrorHandler;
import com.sowell.security.defaults.DefaultFilterLogHandler;
import com.sowell.security.defaults.DefaultRequestDataEncryptHandler;
import com.sowell.security.defaults.token.JwtAccessTokenHandler;
import com.sowell.security.defaults.token.UUIDAccessTokenHandler;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.filter.StartFilter;
import com.sowell.security.handler.FilterDataHandler;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.enums.HttpStatus;
import com.sowell.tool.reflect.model.ControllerMethod;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:21
 */
public class IcpManager {
	//====================================================================================================================================

	private static IcpConfig icpConfig = null;

	/**
	 * 设置配置文件
	 *
	 * @param icpConfig 配置文件
	 */
	public static void setIcpConfig(IcpConfig icpConfig) {
		IcpManager.icpConfig = icpConfig;
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置文件
	 */
	public static IcpConfig getIcpConfig() {
		return icpConfig;
	}

	//====================================================================================================================================

	private static final FilterConfigurer FILTER_CONFIGURER = new FilterConfigurer();

	/**
	 * 获取过滤配置文件
	 *
	 * @return 过滤配置文件
	 */
	public static FilterConfigurer getFilterConfigurer() {
		return FILTER_CONFIGURER;
	}

	//====================================================================================================================================

	private static IcpContext<?, ?> icpContext = null;

	/**
	 * 设置上下文
	 *
	 * @param icpContext 上下文
	 */
	public static void setIcpContext(IcpContext<?, ?> icpContext) {
		IcpManager.icpContext = icpContext;
	}

	/**
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	public static IcpContext<?, ?> getIcpContext() {
		return icpContext;
	}

	//====================================================================================================================================

	private static BaseCacheManager cacheManager = null;

	/**
	 * 设置缓存管理器
	 *
	 * @param cacheManager 设置缓存管理器
	 */
	public static void setCacheManager(BaseCacheManager cacheManager) {
		IcpManager.cacheManager = cacheManager;
	}

	/**
	 * 获取缓存管理器
	 *
	 * @return 缓存管理器
	 */
	public static BaseCacheManager getCacheManager() {
		if (IcpManager.cacheManager == null) {
			IcpManager.cacheManager = new DefaultCacheManager();
		}
		return IcpManager.cacheManager;
	}

	//====================================================================================================================================

	private static final AbstractInterfacesFilter INTERFACES_FILTER = new StartFilter();

	/**
	 * 设置接口过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 *
	 * @param interfacesFilterList 过滤执行链
	 */
	public static void linkInterfacesFilter(AbstractInterfacesFilter... interfacesFilterList) {
		if (interfacesFilterList == null || interfacesFilterList.length < 1) {
			throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "接口过滤执行链不可为空!");
		}
		AbstractInterfacesFilter ai = IcpManager.INTERFACES_FILTER;
		//AbstractInterfacesFilter[] filterList = new AbstractInterfacesFilter[] {new ExcludeFilter()};
		//for (AbstractInterfacesFilter abstractInterfacesFilter : filterList) {
		//	ai.linkFilter(abstractInterfacesFilter);
		//	ai = abstractInterfacesFilter;
		//}
		for (final AbstractInterfacesFilter interfacesFilter : interfacesFilterList) {
			ai.linkFilter(interfacesFilter);
			ai = interfacesFilter;
		}
	}

	/**
	 * 获取接口过滤执行链
	 *
	 * @return 接口过滤执行链
	 */
	public static AbstractInterfacesFilter getInterfacesFilter() {
		return IcpManager.INTERFACES_FILTER;
	}

	//====================================================================================================================================

	private static BaseFilterLogHandler filterLogHandler = null;

	/**
	 * 设置过滤日志处理器
	 *
	 * @param baseFilterLogHandler 过滤日志处理器
	 */
	public static void setFilterLogHandler(BaseFilterLogHandler baseFilterLogHandler) {
		IcpManager.filterLogHandler = baseFilterLogHandler;
	}

	/**
	 * 获取日志处理器
	 *
	 * @return 日志处理器
	 */
	public static BaseFilterLogHandler getFilterLogHandler() {
		if (IcpManager.filterLogHandler == null) {
			IcpManager.filterLogHandler = new DefaultFilterLogHandler();
		}
		return IcpManager.filterLogHandler;
	}

	//====================================================================================================================================

	private static BaseFilterErrorHandler<?> filterErrorHandler = null;

	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 */
	public static void setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		IcpManager.filterErrorHandler = filterErrorHandler;
	}

	/**
	 * 获取过滤错误处理器
	 *
	 * @return 过滤错误处理器
	 */
	public static BaseFilterErrorHandler<?> getFilterErrorHandler() {
		if (IcpManager.filterErrorHandler == null) {
			IcpManager.filterErrorHandler = new DefaultFilterErrorHandler();
		}
		return IcpManager.filterErrorHandler;
	}

	//====================================================================================================================================

	private static final FilterDataHandler FILTER_DATA_HANDLER = new FilterDataHandler();

	/**
	 * 获取数据处理器
	 *
	 * @return 数据处理器
	 */
	public static FilterDataHandler getFilterDataHandler() {
		return IcpManager.FILTER_DATA_HANDLER;
	}

	//====================================================================================================================================

	private static final InterfacesMethodMap INTERFACES_METHOD_MAP = new InterfacesMethodMap();

	/**
	 * 设置接口方法映射集合
	 *
	 * @param interfacesMethodMap 接口方法映射集合
	 */
	public static void setInterfacesMethodMap(Map<String, ControllerMethod> interfacesMethodMap) {
		INTERFACES_METHOD_MAP.putInterfacesMethodMap(interfacesMethodMap);
	}

	/**
	 * 获取请求接口对应的方法
	 *
	 * @param url 请求接口
	 * @return 方法
	 */
	public static ControllerMethod getMethodByInterfaceUrl(String url) {
		return INTERFACES_METHOD_MAP.getMethodByInterfaceUrl(url);
	}

	//====================================================================================================================================

	private static IAccessTokenHandler accessTokenHandler = null;

	/**
	 * 设置AccessToken处理器
	 *
	 * @param accessTokenHandler accessToken处理器
	 */
	public static void setAccessTokenHandler(IAccessTokenHandler accessTokenHandler) {
		IcpManager.accessTokenHandler = accessTokenHandler;
	}

	/**
	 * 获取AccessToken处理器
	 *
	 * @return 方法
	 */
	public static IAccessTokenHandler getAccessTokenHandler() {
		if (IcpManager.accessTokenHandler == null) {
			final IcpConfig icpConfig = IcpManager.getIcpConfig();
			final IcpConfig.AccessTokenConfig accessAccessTokenConfig = icpConfig.getAccessTokenConfig();
			if (IcpConstant.ACCESS_TOKEN_TYPE_BY_UUID.equals(accessAccessTokenConfig.getAccessTokenType())) {
				IcpManager.accessTokenHandler = new UUIDAccessTokenHandler();
			} else {
				IcpManager.accessTokenHandler = new JwtAccessTokenHandler();
			}
		}
		return IcpManager.accessTokenHandler;
	}

	//====================================================================================================================================

	private static RequestDataEncryptHandler requestDataEncryptHandler = null;

	/**
	 * 设置请求加解密处理器
	 *
	 * @param requestDataEncryptHandler 请求加解密处理器
	 */
	public static void setRequestDataEncryptHandler(RequestDataEncryptHandler requestDataEncryptHandler) {
		IcpManager.requestDataEncryptHandler = requestDataEncryptHandler;
	}

	/**
	 * 获取请求加解密处理器
	 *
	 * @return 请求加解密处理器
	 */
	public static RequestDataEncryptHandler getRequestDataEncryptHandler() {
		if (IcpManager.requestDataEncryptHandler == null) {
			IcpManager.requestDataEncryptHandler = new DefaultRequestDataEncryptHandler();
		}
		return IcpManager.requestDataEncryptHandler;
	}

	//====================================================================================================================================

	/**
	 * 从{@link IcpManager#getIcpContext()}中获取存储信息
	 *
	 * @return 存储信息
	 */
	public static IcpStorage<?> getStorage() {
		return IcpManager.getIcpContext().getStorage();
	}
}
