package com.sowell.security.defaults;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.exception.auth.AccountNotExistException;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.JwtUtil;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class JwtAccessTokenHandler implements IAccessTokenHandler<AuthDetails> {

	@Override
	public String generateAccessToken(AuthDetails authDetails) {
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		long timeoutMillis = IcpCoreManager.getIcpConfig().getTokenConfig().getTimeoutForMillis();
		String id = "JWT::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isNotEmpty(idValue)) {
			return ((String) idValue);
		}
		String accessToken = JwtUtil.generateToken(authDetails, ((int) timeoutMillis));
		cacheManager.put(id, accessToken, timeoutMillis);
		return accessToken;
	}

	@Override
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return false;
		}
		final AuthDetails authDetails = JwtUtil.toBean(accessToken, AuthDetails.class);
		if (authDetails == null) {
			return true;
		}
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		String id = "JWT::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isEmpty(idValue)) {
			return true;
		}
		if (!accessToken.equals(idValue)) {
			return true;
		}
		return JwtUtil.checkExpiration(accessToken);
	}

	@Override
	public AuthDetails getAuthDetails(String accessToken) {
		if (this.checkExpiration(accessToken)) {
			throw new AccountNotExistException();
		}
		final AuthDetails<?> authDetails = JwtUtil.toBean(accessToken);
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return authDetails;
	}

	@Override
	public void setAuthDetails(AuthDetails authDetails) {
	}

	@Override
	public String refreshAccessToken(String accessToken) {
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		final AuthDetails authDetails = this.getAuthDetails(accessToken);
		String id = "JWT::" + authDetails.getId();
		cacheManager.remove(id);
		return generateAccessToken(authDetails);
	}
}
