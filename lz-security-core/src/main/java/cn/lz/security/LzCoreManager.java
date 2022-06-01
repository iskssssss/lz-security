package cn.lz.security;

import cn.lz.security.arrays.InterfacesMethodMap;
import cn.lz.security.config.LzConfig;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.defaults.*;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.handler.EncodeSwitchHandler;
import cn.lz.security.token.IAccessTokenHandler;
import cn.lz.security.cache.BaseCacheManager;
import cn.lz.security.config.CoreConfigurer;
import cn.lz.security.context.LzContext;
import cn.lz.security.context.model.LzStorage;
import cn.lz.security.defaults.*;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.reflect.model.ControllerMethod;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 15:21
 */
public class LzCoreManager {
	//====================================================================================================================================

	protected static LzConfig lzConfig = null;

	/**
	 * 设置配置文件
	 *
	 * @param lzConfig 配置文件
	 */
	public static void setLzConfig(LzConfig lzConfig) {
		LzCoreManager.lzConfig = lzConfig;
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置文件
	 */
	public static LzConfig getLzConfig() {
		return lzConfig;
	}

	//====================================================================================================================================

	protected static LzContext<?, ?> lzContext = null;

	/**
	 * 设置上下文
	 *
	 * @param lzContext 上下文
	 */
	public static void setLzContext(LzContext<?, ?> lzContext) {
		LzCoreManager.lzContext = lzContext;
	}

	/**
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	public static LzContext<?, ?> getLzContext() {
		return lzContext;
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
		if (LzCoreManager.cacheManager instanceof DefaultCacheManager) {
			((DefaultCacheManager) cacheManager).destroy();
		}
		LzCoreManager.cacheManager = cacheManager;
	}

	/**
	 * 获取缓存管理器
	 *
	 * @return 缓存管理器
	 */
	public static BaseCacheManager getCacheManager() {
		if (LzCoreManager.cacheManager == null) {
			synchronized (LzCoreManager.class) {
				if (LzCoreManager.cacheManager == null) {
					LzCoreManager.cacheManager = new DefaultCacheManager();
				}
			}
		}
		return LzCoreManager.cacheManager;
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
		LzCoreManager.accessTokenHandler = accessTokenHandler;
	}

	/**
	 * 获取AccessToken处理器
	 *
	 * @return 方法
	 */
	public static IAccessTokenHandler getAccessTokenHandler() {
		if (LzCoreManager.accessTokenHandler == null) {
			synchronized (LzCoreManager.class) {
				if (LzCoreManager.accessTokenHandler == null) {
					final TokenConfig accessTokenConfig = LzCoreManager.getLzConfig().getTokenConfig();
					switch (accessTokenConfig.getType()) {
						case LzConstant.TOKEN_TYPE_BY_UUID:
							LzCoreManager.accessTokenHandler = new UUIDAccessTokenHandler();
							break;
						case LzConstant.TOKEN_TYPE_BY_JWT:
							LzCoreManager.accessTokenHandler = new JwtAccessTokenHandler();
							break;
						default:
							throw new SecurityException(RCode.INTERNAL_SERVER_ERROR);
					}
				}
			}
		}
		return LzCoreManager.accessTokenHandler;
	}

	//====================================================================================================================================

	protected static DataEncoder dataEncoder = null;

	/**
	 * 设置请求加解密处理器
	 *
	 * @param dataEncoder 请求加解密处理器
	 */
	public static void setRequestDataEncryptHandler(DataEncoder dataEncoder) {
		LzCoreManager.dataEncoder = dataEncoder;
	}

	/**
	 * 获取请求加解密处理器
	 *
	 * @return 请求加解密处理器
	 */
	public static DataEncoder getRequestDataEncryptHandler() {
		if (LzCoreManager.dataEncoder == null) {
			synchronized (LzCoreManager.class) {
				if (LzCoreManager.dataEncoder == null) {
					LzCoreManager.dataEncoder = new DefaultDataEncoder();
				}
			}
		}
		return LzCoreManager.dataEncoder;
	}

	//====================================================================================================================================
	protected static EncodeSwitchHandler encodeSwitchHandler = null;

	public static void setEncryptSwitchHandler(EncodeSwitchHandler encodeSwitchHandler) {
		LzCoreManager.encodeSwitchHandler = encodeSwitchHandler;
	}

	public static EncodeSwitchHandler getEncryptSwitchHandler() {
		if (LzCoreManager.encodeSwitchHandler == null) {
			synchronized (LzCoreManager.class) {
				if (LzCoreManager.encodeSwitchHandler == null) {
					LzCoreManager.encodeSwitchHandler = new DefaultEncodeSwitchHandler();
				}
			}
		}
		return LzCoreManager.encodeSwitchHandler;
	}

	//====================================================================================================================================

	/**
	 * 从{@link LzCoreManager#getLzContext()}中获取存储信息
	 *
	 * @return 存储信息
	 */
	public static LzStorage<?> getStorage() {
		return LzCoreManager.getLzContext().getStorage();
	}
}
