package com.sowell.security.defaults.token;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.exception.HeaderNotAccessTokenException;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.jwt.JwtUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class JwtAccessTokenHandler implements IAccessTokenHandler {

	private final BaseCacheManager cacheManager;
	private final long timeoutMillis;

	public JwtAccessTokenHandler() {
		this.cacheManager = IcpManager.getCacheManager();
		IcpConfig icpConfig = IcpManager.getIcpConfig();
		IcpConfig.AccessTokenConfig accessAccessTokenConfig = icpConfig.getAccessTokenConfig();
		this.timeoutMillis = accessAccessTokenConfig.getTimeoutForMillis();
	}

	@Override
	public <T extends AuthDetails<T>> AuthDetails<T> getAuthDetails() {
		return getAuthDetails(getAccessToken());
	}

	@Override
	public <T extends AuthDetails<T>> String generateAccessToken(AuthDetails<T> authDetails) {
		String accessToken;
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
		} catch (HeaderNotAccessTokenException ignored) { }
		this.cacheManager.remove(id);
		accessToken = JwtUtil.generateToken(authDetails, ((int) this.timeoutMillis));
		this.cacheManager.put(id, accessToken, this.timeoutMillis);
		return accessToken;
	}

	@Override
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return false;
		}
		return JwtUtil.checkExpiration(accessToken);
	}

	@Override
	public <T extends AuthDetails<T>> AuthDetails<T> getAuthDetails(String accessToken) {
		final Object id = this.cacheManager.get(accessToken);
		if (id == null) {
			throw new AccountNotExistException();
		}
		final AuthDetails<T> authDetails = JwtUtil.toBean(accessToken);
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return authDetails;
	}

	@Override
	public <T extends AuthDetails<T>> void setAuthDetails(AuthDetails<T> authDetails) {
		//final String accessToken = getAccessToken();
		//this.cacheManager.put(accessToken, authDetails);
	}
}
