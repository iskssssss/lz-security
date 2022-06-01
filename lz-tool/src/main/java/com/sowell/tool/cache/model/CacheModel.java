package com.sowell.tool.cache.model;

/**
 * @Author: sowell
 * @Date: 2021/08/25 11:11
 */
public class CacheModel<K, V> {
	private final K k;
	private final V v;
	private final long ttl;
	private volatile long lastTime;

	public CacheModel(K k, V v, long ttl) {
		this.k = k;
		this.v = v;
		this.ttl = ttl;
		this.lastTime = System.currentTimeMillis();
	}

	/**
	 * 判断是否过期
	 *
	 * @return 是否过期
	 */
	public boolean isExpired() {
		if (this.ttl > 0) {
			return (System.currentTimeMillis() - this.lastTime) > this.ttl;
		}
		return false;
	}

	public K getKey() {
		return this.k;
	}

	/**
	 * 获取值
	 *
	 * @param isUpdateLastAccess 是否更新最后访问时间
	 * @return 获得对象
	 */
	public V get(boolean isUpdateLastAccess) {
		if (isUpdateLastAccess) {
			this.lastTime = System.currentTimeMillis();
		}
		return this.v;
	}
}
