package cn.lz.security.cache;

import java.util.Map;
import java.util.Set;

/**
 * 缓存管理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/08/18 08:43
 */
public interface BaseCacheManager {

	/**
	 * 增加缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return 结果
	 */
	boolean put(Object key, Object value);

	/**
	 * 增加缓存
	 *
	 * @param key     键
	 * @param value   值
	 * @param timeout 过期（毫秒）
	 * @return 结果
	 */
	boolean put(Object key, Object value, long timeout);

	/**
	 * 批量添加缓存
	 *
	 * @param cacheMap 缓存集合
	 */
	default void putAll(Map<String, Object> cacheMap) {
		final Set<Map.Entry<String, Object>> cacheMapEntries = cacheMap.entrySet();
		for (Map.Entry<String, Object> cacheMapEntry : cacheMapEntries) {
			final String key = cacheMapEntry.getKey();
			final Object value = cacheMapEntry.getValue();
			this.put(key, value);
		}
		cacheMap.clear();
	}

	/**
	 * 批量添加缓存
	 *
	 * @param cacheMap 缓存集合
	 * @param timeout  过期时间(毫秒)
	 */
	default void putAll(Map<String, Object> cacheMap, long timeout) {
		final Set<Map.Entry<String, Object>> cacheMapEntries = cacheMap.entrySet();
		for (Map.Entry<String, Object> cacheMapEntry : cacheMapEntries) {
			final String key = cacheMapEntry.getKey();
			final Object value = cacheMapEntry.getValue();
			this.put(key, value, timeout);
		}
		cacheMap.clear();
	}

	/**
	 * 移除缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	boolean remove(Object key);

	/**
	 * 移除缓存
	 *
	 * @param keys 键集合
	 * @return 结果
	 */
	default boolean remove(Object... keys) {
		if (keys == null || keys.length < 1) {
			return false;
		}
		final int length = keys.length;
		for (int i = 0; i < length; i++) {
			final Object key = keys[i];
			try {
				this.remove(key);
				keys[i] = null;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 结果
	 */
	Object get(Object key);

	/**
	 * 是否存在键
	 *
	 * @param key 键
	 * @return 是否存在
	 */
	Boolean existKey(Object key);

	/**
	 * 刷新过期时间
	 *
	 * @param key     键
	 * @param timeout 过期时间(毫秒)
	 * @return 结果
	 */
	boolean refreshKey(Object key, long timeout);

	/**
	 * 刷新过期时间
	 *
	 * @param timeout 过期时间(毫秒)
	 * @param keys    键集合
	 * @return 结果
	 */
	default boolean refreshKeys(long timeout, Object... keys) {
		if (keys == null || keys.length < 1) {
			return false;
		}
		final int length = keys.length;
		for (int i = 0; i < length; i++) {
			final Object key = keys[i];
			try {
				if (this.refreshKey(key, timeout)) {
					// TODO 刷新失败处理
				}
				keys[i] = null;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
