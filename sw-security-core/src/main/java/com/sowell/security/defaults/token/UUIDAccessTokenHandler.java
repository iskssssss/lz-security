package com.sowell.security.defaults.token;

import cn.hutool.crypto.SmUtil;
import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.exception.HeaderNotAccessTokenException;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.AuthDetails;

import java.util.HashMap;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class UUIDAccessTokenHandler implements IAccessTokenHandler {

	private final BaseCacheManager cacheManager;
	private final long timeoutMillis;

	public UUIDAccessTokenHandler() {
		this.cacheManager = IcpManager.getCacheManager();
		IcpConfig icpConfig = IcpManager.getIcpConfig();
		IcpConfig.AccessTokenConfig accessAccessTokenConfig = icpConfig.getAccessTokenConfig();
		timeoutMillis = accessAccessTokenConfig.getTimeoutForMillis();
	}

	@Override
	public <T extends AuthDetails<T>> AuthDetails<T> getAuthDetails() {
		return getAuthDetails(getAccessToken());
	}

	@Override
	public <T extends AuthDetails<T>> String generateAccessToken(AuthDetails<T> authDetails) {
		String accessToken = null;
		String id = authDetails.getId();
		final Object idValue = this.cacheManager.get(id);
		try {
			if (StringUtil.isNotEmpty(idValue)) {
				return ((String) idValue);
			}
			accessToken = getAccessToken();
			if (this.checkExpiration(accessToken)) {
				return ((String) idValue);
			}
		} catch (HeaderNotAccessTokenException ignored) {
		}
		this.cacheManager.remove(id, accessToken);
		accessToken = SmUtil.sm3(authDetails.toJson() + System.currentTimeMillis());
		String finalAccessToken = accessToken;
		this.cacheManager.putAll(new HashMap<String, Object>(4) {{
			put(id, finalAccessToken);
			put(finalAccessToken, authDetails);
		}}, this.timeoutMillis);
		return accessToken;
	}

	@Override
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return false;
		}
		return cacheManager.existKey(accessToken);
	}

	@Override
	public <T extends AuthDetails<T>> AuthDetails<T> getAuthDetails(String accessToken) {
		final Object authDetails = this.cacheManager.get(accessToken);
		if (authDetails instanceof AuthDetails) {
			return ((AuthDetails<T>) authDetails);
		}
		throw new AccountNotExistException();
	}

	@Override
	public <T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails) {
		final String accessToken = getAccessToken();
		this.cacheManager.put(accessToken, authDetails);
	}
}
