package com.sowell.security.defaults;

import com.sowell.security.LzCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.TokenConfig;
import com.sowell.security.context.LzSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.auth.AccountNotExistException;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.JwtUtil;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class JwtAccessTokenHandler implements IAccessTokenHandler<AuthDetails> {

	@Override
	public String generateAccessToken(AuthDetails authDetails) {
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
		long timeoutMillis = LzCoreManager.getLzConfig().getTokenConfig().getTimeoutForMillis();
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
	public void invalid(String token) {
		if (this.checkExpiration(token)) {
			return;
		}
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
		final AuthDetails<?> authDetails = JwtUtil.toBean(token);
		if (authDetails == null) {
			return;
		}
		String id = "JWT::" + authDetails.getId();
		cacheManager.remove(id);

		final TokenConfig tokenConfig = LzCoreManager.getLzConfig().getTokenConfig();
		final String saveName = tokenConfig.getName();
		final BaseResponse<?> servletResponse = LzSecurityContextThreadLocal.getServletResponse();
		servletResponse.removeCookie(saveName);
	}

	@Override
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return true;
		}
		final AuthDetails<?> authDetails = JwtUtil.toBean(accessToken);
		if (authDetails == null) {
			return true;
		}
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
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
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
		final AuthDetails<?> authDetails = this.getAuthDetails(accessToken);
		String id = "JWT::" + authDetails.getId();
		cacheManager.remove(id);
		return generateAccessToken(authDetails);
	}
}
