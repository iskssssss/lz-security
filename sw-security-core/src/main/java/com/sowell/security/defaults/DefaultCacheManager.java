package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.cache.CacheManager;
import com.sowell.security.cache.utils.CacheUtil;
import com.sowell.security.config.IcpConfig;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:57
 */
public class DefaultCacheManager implements BaseCacheManager {
	private final CacheManager<String, Object> cacheManager;
	private final long timeoutMillis;

	public DefaultCacheManager() {
		IcpConfig icpConfig = IcpManager.getIcpConfig();
		IcpConfig.AccessTokenConfig accessAccessTokenConfig = icpConfig.getAccessTokenConfig();
		timeoutMillis = accessAccessTokenConfig.getTimeoutForMillis();
		this.cacheManager = CacheUtil.newCacheManager();
		this.cacheManager.schedulePrune(5);
	}

	@Override
	public boolean put(String key, Object value) {
		try {
			this.put(key, value, timeoutMillis);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean put(String key, Object value, long timeout) {
		try {
			cacheManager.put(key, value, timeout);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean remove(String key) {
		try {
			cacheManager.remove(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object get(String key) {
		try {
			return cacheManager.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean existKey(String key) {
		return this.get(key) != null;
	}
}
