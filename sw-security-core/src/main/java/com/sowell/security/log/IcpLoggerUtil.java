package com.sowell.security.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:24
 */
public class IcpLoggerUtil {

	private static final Map<Class<?>, IcpLogger> ICP_LOGGER_MAP = new ConcurrentHashMap<>();

	public static IcpLogger getIcpLogger(Class<?> clazz) {
		return ICP_LOGGER_MAP.computeIfAbsent(clazz, k -> new IcpLogger(clazz));
	}

	public static void info(Class<?> clazz, String format, Object... params) {
		final IcpLogger icpLogger = getIcpLogger(clazz);
		icpLogger.info(format, params);
	}

	public static void error(Class<?> clazz, String format, Object... params) {
		final IcpLogger icpLogger = getIcpLogger(clazz);
		icpLogger.error(format, params);
	}

	public static void error(Class<?> clazz, String message, Throwable t) {
		final IcpLogger icpLogger = getIcpLogger(clazz);
		icpLogger.error(message, t);
	}

	public static void debug(Class<?> clazz, String format, Object... params) {
		final IcpLogger icpLogger = getIcpLogger(clazz);
		icpLogger.debug(format, params);
	}

	public static void warn(Class<?> clazz, String format, Object... params) {
		final IcpLogger icpLogger = getIcpLogger(clazz);
		icpLogger.warn(format, params);
	}
}
