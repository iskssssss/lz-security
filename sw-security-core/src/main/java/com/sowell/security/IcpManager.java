package com.sowell.security;

import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.impl.AuthorizationHandler;
import com.sowell.security.auth.impl.LogoutHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.config.InterfacesMethodMap;
import com.sowell.security.filter.EndFilter;
import com.sowell.security.filter.ExcludeFilter;
import com.sowell.security.filter.StartFilter;
import com.sowell.security.handler.FilterDataHandler;
import com.sowell.security.log.BaseFilterLogHandler;

import java.lang.reflect.Method;
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
	private static FilterConfigurer filterConfigurer;
	public static void setFilterConfigurer(FilterConfigurer filterConfigurer) {
		IcpManager.filterConfigurer = filterConfigurer;
	}
	public static FilterConfigurer getFilterConfigurer() {
		return filterConfigurer;
	}
	//====================================================================================================================================
	private static volatile AuthorizationHandler authorizationHandler;
	public static AuthorizationHandler getAuthorizationHandler(){
		if (authorizationHandler == null){
			synchronized (IcpManager.class){
				if (authorizationHandler == null){
					authorizationHandler = new AuthorizationHandler();
				}
			}
		}
		return authorizationHandler;
	}
	//====================================================================================================================================	private static volatile AuthorizationHandler authorizationHandler;
	private static volatile AbstractLogoutHandler logoutHandler;
	public static AbstractLogoutHandler getLogoutHandler(){
		if (logoutHandler == null){
			synchronized (IcpManager.class){
				if (logoutHandler == null){
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
		return cacheManager;
	}
	//====================================================================================================================================
	private static AbstractInterfacesFilter interfacesFilter;
	/**
	 * 设置过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 *
	 * @param abstractInterfacesFilterList 过滤执行链
	 */
	public static void linkFilter(AbstractInterfacesFilter... abstractInterfacesFilterList) {
		if (abstractInterfacesFilterList == null || abstractInterfacesFilterList.length < 1) {
			throw new RuntimeException("过滤执行链不可为空!");
		}
		AbstractInterfacesFilter ai = IcpManager.interfacesFilter = new StartFilter();
		ExcludeFilter excludeFilter = new ExcludeFilter();
		ai.linkFilter(excludeFilter);
		ai = excludeFilter;
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
		return IcpManager.filterErrorHandler;
	}
	//====================================================================================================================================
	/**
	 * 数据处理器
	 */
	private static FilterDataHandler filterDataHandler;
	/**
	 * 设置数据处理器
	 *
	 * @param filterDataHandler 数据处理器
	 */
	public static void setFilterDataHandler(FilterDataHandler filterDataHandler) {
		IcpManager.filterDataHandler = filterDataHandler;
	}
	/**
	 * 获取数据处理器
	 *
	 * @return 数据处理器
	 */
	public static FilterDataHandler getFilterDataHandler() {
		return IcpManager.filterDataHandler;
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
}
