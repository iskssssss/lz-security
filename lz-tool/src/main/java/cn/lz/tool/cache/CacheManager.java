package cn.lz.tool.cache;

import cn.lz.tool.cache.listener.CacheListener;
import cn.lz.tool.cache.model.CacheModel;
import cn.lz.tool.cache.utils.GlobalScheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @Author: lz
 * @Date: 2021/08/25 10:49
 */
public class CacheManager<K, V> implements Runnable {
	private long timeout;
	private final StampedLock lock = new StampedLock();
	private final Map<K, CacheModel<K, V>> cacheMap;
	private CacheListener<K, V> listener;
	private ScheduledFuture<?> scheduledFuture;

	public CacheManager() {
		this(0);
	}

	public CacheManager(long timeout) {
		this(new ConcurrentHashMap<>(), timeout);
	}

	public CacheManager(Map<K, CacheModel<K, V>> cacheMap, long timeout) {
		this.cacheMap = cacheMap;
		this.timeout = timeout;
	}

	public void put(K key, V value) {
		this.put(key, value, this.timeout);
	}

	public void put(K key, V value, long timeout) {
		final long stamp = this.lock.writeLock();
		try {
			this.cacheMap.put(key, new CacheModel<>(key, value, timeout));
		} finally {
			this.lock.unlockWrite(stamp);
		}
	}

	public void remove(K key) {
		final long stamp = this.lock.writeLock();
		try {
			CacheListener<K, V> listener = this.listener;
			if (listener != null) {
				CacheModel<K, V> kvCacheModel = this.cacheMap.get(key);
				listener.onRemove(key, kvCacheModel.get(false));
			}
			this.cacheMap.remove(key);
		} finally {
			this.lock.unlockWrite(stamp);
		}
	}

	public V get(K key) {
		return get(key, false);
	}

	public V get(K key, boolean isUpdateLastAccess) {
		long stamp = this.lock.tryOptimisticRead();
		CacheModel<K, V> kvCacheModel = this.cacheMap.get(key);
		if (!this.lock.validate(stamp)) {
			stamp = this.lock.readLock();
			try {
				kvCacheModel = this.cacheMap.get(key);
			} finally {
				this.lock.unlockRead(stamp);
			}
		}
		if (kvCacheModel == null) {
			return null;
		}
		if (kvCacheModel.isExpired()) {
			return null;
		}
		return kvCacheModel.get(isUpdateLastAccess);
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setListener(CacheListener<K, V> listener) {
		this.listener = listener;
	}

	public void schedulePrune(long delay) {
		scheduledFuture = GlobalScheduled.INSTANCE.scheduleAtFixedRate(this, delay, TimeUnit.MILLISECONDS);
	}

	public void cancel(boolean mayInterruptIfRunning) {
		scheduledFuture.cancel(mayInterruptIfRunning);
	}

	public void destroy() {
		cancel(true);
		cacheMap.clear();
	}

	@Override
	public void run() {
		final long stamp = lock.writeLock();
		try {
			this.cacheMap.values().removeIf(CacheModel::isExpired);
		} finally {
			lock.unlockWrite(stamp);
		}
	}
}
