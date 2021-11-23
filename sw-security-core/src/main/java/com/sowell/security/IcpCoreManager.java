package com.sowell.security;

import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.arrays.InterfacesMethodMap;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.IcpStorage;
import com.sowell.security.defaults.*;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.handler.DataEncoder;
import com.sowell.security.handler.EncodeSwitchHandler;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.reflect.model.ControllerMethod;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:21
 */
public class IcpCoreManager {
	//====================================================================================================================================

	protected static IcpConfig icpConfig = null;

	/**
	 * 设置配置文件
	 *
	 * @param icpConfig 配置文件
	 */
	public static void setIcpConfig(IcpConfig icpConfig) {
		IcpCoreManager.icpConfig = icpConfig;
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

	protected static IcpContext<?, ?> icpContext = null;

	/**
	 * 设置上下文
	 *
	 * @param icpContext 上下文
	 */
	public static void setIcpContext(IcpContext<?, ?> icpContext) {
		IcpCoreManager.icpContext = icpContext;
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

	protected final static CoreConfigurer CORE_CONFIGURER = new CoreConfigurer();

	public static CoreConfigurer getCoreConfigurer() {
		return CORE_CONFIGURER;
	}

	//====================================================================================================================================

	protected static BaseCacheManager cacheManager = null;

	/**
	 * 设置缓存管理器
	 *
	 * @param cacheManager 设置缓存管理器
	 */
	public static void setCacheManager(BaseCacheManager cacheManager) {
		if (IcpCoreManager.cacheManager instanceof DefaultCacheManager) {
			((DefaultCacheManager) cacheManager).destroy();
		}
		IcpCoreManager.cacheManager = cacheManager;
	}

	/**
	 * 获取缓存管理器
	 *
	 * @return 缓存管理器
	 */
	public static BaseCacheManager getCacheManager() {
		if (IcpCoreManager.cacheManager == null) {
			synchronized (IcpCoreManager.class) {
				if (IcpCoreManager.cacheManager == null) {
					IcpCoreManager.cacheManager = new DefaultCacheManager();
				}
			}
		}
		return IcpCoreManager.cacheManager;
	}

	//====================================================================================================================================

	protected static final InterfacesMethodMap INTERFACES_METHOD_MAP = new InterfacesMethodMap();

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

	protected static IAccessTokenHandler<?> accessTokenHandler = null;

	/**
	 * 设置AccessToken处理器
	 *
	 * @param accessTokenHandler accessToken处理器
	 */
	public static void setAccessTokenHandler(IAccessTokenHandler<?> accessTokenHandler) {
		IcpCoreManager.accessTokenHandler = accessTokenHandler;
	}

	/**
	 * 获取AccessToken处理器
	 *
	 * @return 方法
	 */
	public static IAccessTokenHandler getAccessTokenHandler() {
		if (IcpCoreManager.accessTokenHandler == null) {
			synchronized (IcpCoreManager.class) {
				if (IcpCoreManager.accessTokenHandler == null) {
					final IcpConfig.TokenConfig accessTokenConfig = IcpCoreManager.getIcpConfig().getTokenConfig();
					switch (accessTokenConfig.getType()) {
						case IcpConstant.TOKEN_TYPE_BY_UUID:
							IcpCoreManager.accessTokenHandler = new UUIDAccessTokenHandler();
							break;
						case IcpConstant.TOKEN_TYPE_BY_JWT:
							IcpCoreManager.accessTokenHandler = new JwtAccessTokenHandler();
							break;
						default:
							throw new SecurityException(RCode.INTERNAL_SERVER_ERROR);
					}
				}
			}
		}
		return IcpCoreManager.accessTokenHandler;
	}

	//====================================================================================================================================

	protected static DataEncoder dataEncoder = null;

	/**
	 * 设置请求加解密处理器
	 *
	 * @param dataEncoder 请求加解密处理器
	 */
	public static void setRequestDataEncryptHandler(DataEncoder dataEncoder) {
		IcpCoreManager.dataEncoder = dataEncoder;
	}

	/**
	 * 获取请求加解密处理器
	 *
	 * @return 请求加解密处理器
	 */
	public static DataEncoder getRequestDataEncryptHandler() {
		if (IcpCoreManager.dataEncoder == null) {
			synchronized (IcpCoreManager.class) {
				if (IcpCoreManager.dataEncoder == null) {
					IcpCoreManager.dataEncoder = new DefaultDataEncoder();
				}
			}
		}
		return IcpCoreManager.dataEncoder;
	}

	//====================================================================================================================================
	protected static EncodeSwitchHandler encodeSwitchHandler = null;

	public static void setEncryptSwitchHandler(EncodeSwitchHandler encodeSwitchHandler) {
		IcpCoreManager.encodeSwitchHandler = encodeSwitchHandler;
	}

	public static EncodeSwitchHandler getEncryptSwitchHandler() {
		if (IcpCoreManager.encodeSwitchHandler == null) {
			synchronized (IcpCoreManager.class) {
				if (IcpCoreManager.encodeSwitchHandler == null) {
					IcpCoreManager.encodeSwitchHandler = new DefaultEncodeSwitchHandler();
				}
			}
		}
		return IcpCoreManager.encodeSwitchHandler;
	}

	//====================================================================================================================================

	/**
	 * 从{@link IcpCoreManager#getIcpContext()}中获取存储信息
	 *
	 * @return 存储信息
	 */
	public static IcpStorage<?> getStorage() {
		return IcpCoreManager.getIcpContext().getStorage();
	}
}
