package com.sowell.security.log;

import com.sowell.security.IcpManager;
import com.sowell.security.filter.config.IcpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:20
 */
public class IcpLogger implements Closeable {
	private Logger logger;

	public IcpLogger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}
	// 普通信息
	public void info0(String format, Object... params) {
		logger.info(format, params);
	}
	public void info(String format, Object... params) {
		if (consoleLogPrint()) {
			return;
		}
		logger.info(format, params);
	}

	// 错误信息
	public void error(String format, Object... params) {
		logger.error(format, params);
	}
	public void error(String message, Throwable t) {
		logger.error(message, t);
	}

	// 调试信息
	public void debug0(String format, Object... params) {
		logger.debug(format, params);
	}
	public void debug(String format, Object... params) {
		if (consoleLogPrint()) {
			return;
		}
		logger.debug(format, params);
	}

	// 警告信息
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
			String format, Object... params
	) {
		return format;
	}

	@Override
	public void close() throws IOException {
		this.logger = null;
	}
}
