package cn.lz.security.defaults;

import cn.lz.security.cache.BaseCacheManager;
import cn.lz.tool.cache.CacheManager;
import cn.lz.tool.cache.utils.CacheUtil;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:57
 */
public class CacheManagerDefault implements BaseCacheManager {
	private final CacheManager<Object, Object> cacheManager;

	public CacheManagerDefault() {
		this.cacheManager = CacheUtil.newCacheManager();
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

	@Override
	public boolean refreshKey(Object key, long timeout) {
		// 默认缓存管理器无法修改过期时间，只能通过访问更新的形式来进行刷新。
		return cacheManager.get(key, true) != null;
	}

	public void destroy(){
		this.cacheManager.destroy();
	}
}
