package com.sowell.security.config;

import com.sowell.security.LzCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.handler.EncodeSwitchHandler;
import com.sowell.security.handler.DataEncoder;
import com.sowell.security.token.IAccessTokenHandler;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 10:34
 */
public class CoreConfigurerBuilder<T extends CoreConfigurer> {

	/**
	 * 设置缓存管理器
	 *
	 * @param cacheManager 缓存管理器
	 * @return this
	 */
	public CoreConfigurerBuilder<T> setCacheManager(BaseCacheManager cacheManager) {
		LzCoreManager.setCacheManager(cacheManager);
		return this;
	}

	/**
	 * 设置accessToken处理器
	 *
	 * @param accessTokenHandler accessToken处理器
	 */
	public CoreConfigurerBuilder<T> setAccessTokenHandler(IAccessTokenHandler<?> accessTokenHandler) {
		LzCoreManager.setAccessTokenHandler(accessTokenHandler);
		return this;
	}

	/**
	 * 设置请求加解密处理器
	 *
	 * @param dataEncoder 请求加解密处理器
	 */
	public CoreConfigurerBuilder<T> setRequestDataEncryptHandler(DataEncoder dataEncoder) {
		LzCoreManager.setRequestDataEncryptHandler(dataEncoder);
		return this;
	}

	/**
	 * 设置加解密开关处理器
	 *
	 * @param encryptHandler 加解密开关处理器
	 */
	public CoreConfigurerBuilder<T> setEncryptSwitchHandler(EncodeSwitchHandler encryptHandler) {
		LzCoreManager.setEncryptSwitchHandler(encryptHandler);
		return this;
	}
}
