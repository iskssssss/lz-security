package com.sowell.security.cache;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 08:43
 */
public interface BaseCacheManager {

	/**
	 * 增加缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return 结果
	 */
	boolean put(String key, Object value);

	/**
	 * 增加缓存
	 *
	 * @param key     键
	 * @param value   值
	 * @param timeout 过期（毫秒）
	 * @return 结果
	 */
	boolean put(String key, Object value, long timeout);

	/**
	 * 移除缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	boolean remove(String key);

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	Object get(String key);

	/**
	 * 是否存在键
	 *
	 * @param key 键
	 * @return 是否存在
	 */
	Boolean existKey(String key);
}
