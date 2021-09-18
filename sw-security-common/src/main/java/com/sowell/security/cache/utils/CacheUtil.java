package com.sowell.security.cache.utils;

import com.sowell.security.cache.CacheManager;

/**
 * @Author: sowell
 * @Date: 2021/08/25 11:22
 */
public class CacheUtil {

	/**
	 * 创建缓存管理器
	 *
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @return 缓存管理器
	 */
	public static <K, V> CacheManager<K, V> newCacheManager() {
		return new CacheManager<>();
	}

}
