package com.sowell.tool.cache.utils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: sowell
 * @Date: 2021/08/25 08:37
 */
public enum GlobalScheduled {
	/**
	 *
	 */
	INSTANCE;

	private ScheduledExecutorService scheduledExecutorService;

	GlobalScheduled() {
		create();
	}

	public void create() {
		if (null != scheduledExecutorService) {
			shutdownNow();
		}

		this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1, r -> {
			final Thread thread = new Thread(null, r, "scheduledExecutorService-1");
			thread.setDaemon(true);
			if (thread.getPriority() != Thread.NORM_PRIORITY) {
				thread.setPriority(Thread.NORM_PRIORITY);
			}
			return thread;
		});
	}

	public ScheduledFuture<?> scheduleAtFixedRate(
			Runnable command, long period, TimeUnit unit
	) {
		return scheduledExecutorService.scheduleAtFixedRate(command, period, period, unit);
	}

	/**
	 * 销毁全局定时器
	 */
	public void shutdown() {
		if (null != scheduledExecutorService) {
			scheduledExecutorService.shutdown();
		}
	}

	/**
	 * 销毁全局定时器
	 *
	 * @return 销毁时未被执行的任务列表
	 */
	public List<Runnable> shutdownNow() {
		if (null != scheduledExecutorService) {
			return scheduledExecutorService.shutdownNow();
		}
		return Collections.emptyList();
	}
}
