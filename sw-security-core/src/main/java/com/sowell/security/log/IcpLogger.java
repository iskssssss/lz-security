package com.sowell.security.log;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:20
 */
public class IcpLogger {
	private final Logger logger;

	public IcpLogger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}

	public void info(String format, Object... params) {
		if (consoleLogPrint()) {
			return;
		}
		logger.info(format, params);
	}

	public void error(String format, Object... params) {
		logger.error(format, params);
	}

	public void error(String message, Throwable t) {
		logger.error(message, t);
	}

	public void debug(String format, Object... params) {
		if (consoleLogPrint()) {
			return;
		}
		logger.debug(format, params);
	}

	public void warn(String format, Object... params) {
		logger.warn(format, params);
	}

	private boolean consoleLogPrint() {
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		if (icpConfig == null) {
			return false;
		}
		return !icpConfig.getConsoleLogPrint();
	}

	public static String analysisLoggerInfo(
			String format,
			Object... params
	) {
		return format;
	}
}
