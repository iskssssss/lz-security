package cn.lz.security.log;

import cn.lz.security.LzCoreManager;
import cn.lz.security.config.LzConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:20
 */
public class LzLogger implements Closeable {
	private Logger logger;

	public LzLogger(Class<?> clazz) {
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
		final LzConfig lzConfig = LzCoreManager.getLzConfig();
		if (lzConfig == null) {
			return false;
		}
		return !lzConfig.getConsoleLogPrint();
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
