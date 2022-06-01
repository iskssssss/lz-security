package com.sowell.security.log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:24
 */
public class LzLoggerUtil {

	private static final Map<Class<?>, LzLogger> LZ_LOGGER_MAP = new ConcurrentHashMap<>();

	public static LzLogger getLzLogger(Class<?> clazz) {
		return LZ_LOGGER_MAP.computeIfAbsent(clazz, k -> new LzLogger(clazz));
	}

	public static void info(Class<?> clazz, String format, Object... params) {
		final LzLogger lzLogger = getLzLogger(clazz);
		lzLogger.info(format, params);
	}

	public static void error(Class<?> clazz, String format, Object... params) {
		final LzLogger lzLogger = getLzLogger(clazz);
		lzLogger.error(format, params);
	}

	public static void error(Class<?> clazz, String message, Throwable t) {
		final LzLogger lzLogger = getLzLogger(clazz);
		lzLogger.error(message, t);
	}

	public static void debug(Class<?> clazz, String format, Object... params) {
		final LzLogger lzLogger = getLzLogger(clazz);
		lzLogger.debug(format, params);
	}

	public static void warn(Class<?> clazz, String format, Object... params) {
		final LzLogger lzLogger = getLzLogger(clazz);
		lzLogger.warn(format, params);
	}

	public static boolean removeLzLoggerMap() {
		final Iterator<Map.Entry<Class<?>, LzLogger>> entryIterator = LZ_LOGGER_MAP.entrySet().iterator();
		while (entryIterator.hasNext()) {
			final Map.Entry<Class<?>, LzLogger> loggerEntry = entryIterator.next();
			final LzLogger lzLogger = loggerEntry.getValue();
			try {
				lzLogger.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			entryIterator.remove();
		}
		return LZ_LOGGER_MAP.isEmpty();
	}
}
