package com.sowell.security.config;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/26 11:27
 */
@Data
public class IcpConfig {
	private static final long DEFAULT_TIMEOUT = 3600;

	private String headerName;
	private Token accessToken;

	@Data
	static class Token {
		/**
		 * accessToken过期时间（秒）
		 */
		private Long timeout;
	}

	/**
	 * 获取过期时间（秒）
	 *
	 * @return 过期时间（秒）
	 */
	public long getTimeout() {
		if (this.accessToken == null) {
			return DEFAULT_TIMEOUT;
		}
		final Long timeout = this.accessToken.getTimeout();
		if (timeout == null) {
			return DEFAULT_TIMEOUT;
		}
		return timeout.intValue();
	}

	/**
	 * 获取过期时间（毫秒）
	 *
	 * @return 过期时间（毫秒）
	 */
	public long getTimeoutForMillis() {
		final long timeout = getTimeout();
		return TimeUnit.SECONDS.toMillis(timeout);
	}
}
