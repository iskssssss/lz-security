package cn.lz.security.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.handler.EncodeSwitchHandler;
import cn.lz.security.token.IAccessTokenHandler;
import cn.lz.security.cache.BaseCacheManager;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
		LzCoreManager.setDataEncryptHandler(dataEncoder);
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
