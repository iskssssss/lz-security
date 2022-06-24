package cn.lz.security.plugins.cache;

import cn.lz.security.plugins.utils.RedisUtil;
import cn.lz.security.cache.BaseCacheManager;
import org.springframework.stereotype.Component;

/**
 * Redis缓存管理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/28 17:44
 */
public class RedisCacheManager implements BaseCacheManager {

	@Override
	public boolean put(Object key, Object value) {
		return RedisUtil.set(key, value);
	}

	@Override
	public boolean put(Object key, Object value, long timeout) {
		if (timeout < 0){
			return RedisUtil.set(key, value);
		}
		return RedisUtil.set(key, value, timeout);
	}

	@Override
	public boolean remove(Object key) {
		try {
			RedisUtil.del(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object get(Object key) {
		return RedisUtil.get(key);
	}

	@Override
	public Boolean existKey(Object key) {
		return RedisUtil.exists(key);
	}

	@Override
	public boolean refreshKey(Object key, long timeout) {
		return RedisUtil.expire(key, timeout);
	}
}
