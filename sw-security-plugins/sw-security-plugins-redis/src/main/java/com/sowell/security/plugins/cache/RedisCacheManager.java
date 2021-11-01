package com.sowell.security.plugins.cache;

import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.plugins.utils.RedisUtil;
import org.springframework.stereotype.Component;

/**
 * Redis缓存管理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/28 17:44
 */
@Component
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
