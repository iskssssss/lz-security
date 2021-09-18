package com.sowell.security;

import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.impl.AuthorizationHandler;
import com.sowell.security.auth.impl.LogoutHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.config.InterfacesMethodMap;
import com.sowell.security.context.IcpContext;
import com.sowell.security.defaults.*;
import com.sowell.security.filter.*;
import com.sowell.security.handler.FilterDataHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.token.IAccessTokenHandler;

import java.lang.reflect.Method;
import java.util.Locale;
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
	private static IcpConfig icpConfig;
	public static void setIcpConfig(IcpConfig icpConfig) {
		IcpManager.icpConfig = icpConfig;
	}
	public static IcpConfig getIcpConfig() {
		return icpConfig;
	}
	//====================================================================================================================================
	private static final FilterConfigurer FILTER_CONFIGURER = new FilterConfigurer();
	public static FilterConfigurer getFilterConfigurer() {
		return FILTER_CONFIGURER;
	}
	//====================================================================================================================================
	private static IcpContext icpContext;
	public static void setIcpContext(IcpContext icpContext) {
		IcpManager.icpContext = icpContext;
	}
	public static IcpContext getIcpContext() {
		return icpContext;
	}
	//====================================================================================================================================
	private static volatile AuthorizationHandler authorizationHandler;
	public static AuthorizationHandler getAuthorizationHandler() {
		if (authorizationHandler == null) {
			synchronized (IcpManager.class) {
				if (authorizationHandler == null) {
					authorizationHandler = new AuthorizationHandler();
				}
			}
		}
		return authorizationHandler;
	}
	//====================================================================================================================================	private static volatile AuthorizationHandler authorizationHandler;
	private static volatile AbstractLogoutHandler logoutHandler;
	public static AbstractLogoutHandler getLogoutHandler() {
		if (logoutHandler == null) {
			synchronized (IcpManager.class) {
				if (logoutHandler == null) {
					logoutHandler = new LogoutHandler();
				}
			}
		}
		return logoutHandler;
	}
	//====================================================================================================================================
	private static BaseCacheManager cacheManager;
	public static void setCacheManager(BaseCacheManager cacheManager) {
		IcpManager.cacheManager = cacheManager;
	}
	public static BaseCacheManager getCacheManager() {
		if (IcpManager.cacheManager == null){
			IcpManager.cacheManager = new DefaultCacheManager();
		}
		return IcpManager.cacheManager;
	}
	//====================================================================================================================================
	private static AbstractInterfacesFilter interfacesFilter = null;
	/**
	 * 设置接口过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 *
	 * @param abstractInterfacesFilterList 过滤执行链
	 */
	public static void linkInterfacesFilter(AbstractInterfacesFilter... abstractInterfacesFilterList) {
		if (abstractInterfacesFilterList == null || abstractInterfacesFilterList.length < 1) {
			throw new RuntimeException("接口过滤执行链不可为空!");
		}
		AbstractInterfacesFilter ai = IcpManager.interfacesFilter = new StartFilter();
		AbstractInterfacesFilter[] filterList = new AbstractInterfacesFilter[] {new ExcludeFilter()};
		for (AbstractInterfacesFilter abstractInterfacesFilter : filterList) {
			ai.linkFilter(abstractInterfacesFilter);
			ai = abstractInterfacesFilter;
		}
		for (AbstractInterfacesFilter interfacesFilter : abstractInterfacesFilterList) {
			ai.linkFilter(interfacesFilter);
			ai = interfacesFilter;
		}
		ai.linkFilter(new EndFilter());
	}
	/**
	 * 获取接口过滤执行链
	 *
	 * @return 接口过滤执行链
	 */
	public static AbstractInterfacesFilter getInterfacesFilter() {
		return IcpManager.interfacesFilter;
	}
	//====================================================================================================================================
	private static final AbstractInterfacesFilter AUTH_FILTER = linkAuthFilter();
	/**
	 * 设置认证过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 */
	private static AbstractInterfacesFilter linkAuthFilter() {
		AbstractInterfacesFilter authFilter;
		AbstractInterfacesFilter ai = authFilter = new StartFilter();
		AbstractInterfacesFilter[] filterList = new AbstractInterfacesFilter[] {new ExcludeFilter(), new AuthorizationFilter(), new AnonymousFilter()};
		for (AbstractInterfacesFilter abstractInterfacesFilter : filterList) {
			ai.linkFilter(abstractInterfacesFilter);
			ai = abstractInterfacesFilter;
		}
		ai.linkFilter(new EndFilter());
		return authFilter;
	}
	/**
	 * 获取接口过滤执行链
	 *
	 * @return 接口过滤执行链
	 */
	public static AbstractInterfacesFilter getAuthFilter() {
		return IcpManager.AUTH_FILTER;
	}
	//====================================================================================================================================
	/**
	 * 日志处理器
	 */
	private static BaseFilterLogHandler filterLogHandler;
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
		if (IcpManager.filterLogHandler == null){
			IcpManager.filterLogHandler = new DefaultFilterLogHandler();
		}
		return IcpManager.filterLogHandler;
	}
	//====================================================================================================================================
	/**
	 * 过滤错误处理
	 */
	private static BaseFilterErrorHandler<?> filterErrorHandler;
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
		if (IcpManager.filterErrorHandler == null){
			IcpManager.filterErrorHandler = new DefaultFilterErrorHandler();
		}
		return IcpManager.filterErrorHandler;
	}
	//====================================================================================================================================
	/**
	 * 数据处理器
	 */
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
	public static void setInterfacesMethodMap(Map<String, Method> interfacesMethodMap) {
		INTERFACES_METHOD_MAP.putInterfacesMethodMap(interfacesMethodMap);
	}
	/**
	 * 获取请求接口对应的方法
	 *
	 * @param url 请求接口
	 * @return 方法
	 */
	public static Method getMethodByInterfaceUrl(String url) {
		return INTERFACES_METHOD_MAP.getMethodByInterfaceUrl(url);
	}
	//====================================================================================================================================
	private static IAccessTokenHandler accessTokenHandler;
	/**
	 * 设置accessToken处理器
	 *
	 * @param accessTokenHandler accessToken处理器
	 */
	public static void setAccessTokenHandler(IAccessTokenHandler accessTokenHandler) {
		IcpManager.accessTokenHandler = accessTokenHandler;
	}
	/**
	 * 获取accessToken处理器
	 *
	 * @return 方法
	 */
	public static IAccessTokenHandler getAccessTokenHandler() {
		if (IcpManager.accessTokenHandler == null){
			final IcpConfig icpConfig = IcpManager.getIcpConfig();
			if (IcpConstant.ACCESS_TOKEN_TYPE_BY_UUID.equals(icpConfig.getAccessTokenType().toUpperCase(Locale.ROOT))){
				IcpManager.accessTokenHandler = new UUIDAccessTokenHandler();
			} else {
				IcpManager.accessTokenHandler = new JwtAccessTokenHandler();
			}
		}
		return IcpManager.accessTokenHandler;
	}
	//====================================================================================================================================
}
