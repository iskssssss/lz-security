package com.sowell.security;

import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.config.InterfacesMethodMap;
import com.sowell.security.context.IcpContext;
import com.sowell.security.context.model.IcpStorage;
import com.sowell.security.defaults.*;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.token.IAccessTokenHandler;
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

	protected static IcpConfig icpConfig = null;

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

	protected static IcpContext<?, ?> icpContext = null;

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

	protected static BaseCacheManager cacheManager = null;

	/**
	 * 设置缓存管理器
	 *
	 * @param cacheManager 设置缓存管理器
	 */
	public static void setCacheManager(BaseCacheManager cacheManager) {
		if (IcpManager.cacheManager instanceof DefaultCacheManager) {
			((DefaultCacheManager) cacheManager).destroy();
		}
		IcpManager.cacheManager = cacheManager;
	}

	/**
	 * 获取缓存管理器
	 *
	 * @return 缓存管理器
	 */
	public static BaseCacheManager getCacheManager() {
		if (IcpManager.cacheManager == null) {
			synchronized (IcpManager.class) {
				if (IcpManager.cacheManager == null) {
					IcpManager.cacheManager = new DefaultCacheManager();
				}
			}
		}
		return IcpManager.cacheManager;
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
		IcpManager.accessTokenHandler = accessTokenHandler;
	}

	/**
	 * 获取AccessToken处理器
	 *
	 * @return 方法
	 */
	public static IAccessTokenHandler<?> getAccessTokenHandler() {
		if (IcpManager.accessTokenHandler == null) {
			synchronized (IcpManager.class) {
				if (IcpManager.accessTokenHandler == null) {
					final IcpConfig icpConfig = IcpManager.getIcpConfig();
					final IcpConfig.AccessTokenConfig accessAccessTokenConfig = icpConfig.getAccessTokenConfig();
					if (IcpConstant.ACCESS_TOKEN_TYPE_BY_UUID.equals(accessAccessTokenConfig.getAccessTokenType())) {
						IcpManager.accessTokenHandler = new UUIDAccessTokenHandler();
					} else {
						IcpManager.accessTokenHandler = new JwtAccessTokenHandler();
					}
				}
			}
		}
		return IcpManager.accessTokenHandler;
	}

	//====================================================================================================================================

	protected static RequestDataEncryptHandler requestDataEncryptHandler = null;

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
			synchronized (IcpManager.class) {
				if (IcpManager.requestDataEncryptHandler == null) {
					IcpManager.requestDataEncryptHandler = new DefaultRequestDataEncryptHandler();
				}
			}
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
