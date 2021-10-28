package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.tool.cache.CacheManager;
import com.sowell.tool.cache.utils.CacheUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:57
 */
public class DefaultCacheManager implements BaseCacheManager {
	private final CacheManager<Object, Object> cacheManager;

	public DefaultCacheManager() {
		this.cacheManager = CacheUtil.newCacheManager();
		this.cacheManager.schedulePrune(5);
	}

	@Override
	public boolean put(Object key, Object value) {
		try {
			cacheManager.put(key, value, -1);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean put(Object key, Object value, long timeout) {
		try {
			cacheManager.put(key, value, timeout);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean remove(Object key) {
		try {
			cacheManager.remove(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object get(Object key) {
		try {
			return cacheManager.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean existKey(Object key) {
		return this.get(key) != null;
	}
}
