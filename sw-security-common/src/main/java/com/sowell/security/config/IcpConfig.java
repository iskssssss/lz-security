package com.sowell.security.config;

import com.sowell.security.IcpConstant;
import com.sowell.security.utils.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/26 11:27
 */
public class IcpConfig {
	private static final long DEFAULT_TIMEOUT = 3600;

	private String headerName;
	private Token accessToken;
	private String controllerMethodScanPathList;

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	public void setControllerMethodScanPathList(String controllerMethodScanPathList) {
		this.controllerMethodScanPathList = controllerMethodScanPathList;
	}

	public String getHeaderName() {
		return headerName;
	}

	public Token getAccessToken() {
		return accessToken;
	}

	public Set<String> getControllerMethodScanPathSet() {
		final String controllerMethodScanPath = this.controllerMethodScanPathList;
		if (StringUtil.isEmpty(controllerMethodScanPath)) {
			return Collections.emptySet();
		}
		final String cmspl = controllerMethodScanPath.replaceAll(" ", "");
		final String[] cmsplSplit = cmspl.split(",");
		if (cmsplSplit.length < 1) {
			return Collections.emptySet();
		}
		return Arrays.stream(cmsplSplit).filter(StringUtil::isNotEmpty).collect(Collectors.toSet());
	}

	public String getAccessTokenType() {
		if (this.accessToken == null || StringUtil.isEmpty(this.accessToken.accessTokenType)) {
			return IcpConstant.ACCESS_TOKEN_TYPE_BY_UUID;
		}
		return this.accessToken.accessTokenType.toUpperCase(Locale.ROOT);
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
		final Long timeout = this.accessToken.timeout;
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

	@Override
	public String toString() {
		return " 配置信息：" +
				"\n     头名称：" + headerName +
				"\n     token配置信息：" + accessToken +
				"\n     扫描位置：'" + controllerMethodScanPathList + '\'' +
				'}';
	}

	static class Token {
		/**
		 * AccessToken类型（uuid, jwt）
		 */
		private String accessTokenType;
		/**
		 * AccessToken过期时间（秒）
		 */
		private Long timeout;

		public void setAccessTokenType(String accessTokenType) {
			this.accessTokenType = accessTokenType;
		}

		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}
	}
}
