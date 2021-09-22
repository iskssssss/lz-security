package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.security.utils.JwtUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class JwtAccessTokenHandler implements IAccessTokenHandler {

	private final BaseCacheManager cacheManager;

	public JwtAccessTokenHandler() {
		this.cacheManager = IcpManager.getCacheManager();
	}

	@Override
	public <T extends AuthDetails<T>> T getAuthDetails() {
		return getAuthDetails(getAccessToken());
	}

	@Override
	public <T extends AuthDetails<T>> String generateAccessToken(T authDetails) {
		String accessToken = getAccessToken();
		if (cacheManager.existKey(accessToken)) {
			return getAccessToken();
		}
		cacheManager.remove(accessToken);
		accessToken = JwtUtil.generateToken(authDetails);
		cacheManager.put(accessToken, authDetails);
		return accessToken;
	}

	@Override
	public <T extends AuthDetails<T>> T getAuthDetails(String accessToken) {
		final Object authDetails = this.cacheManager.get(accessToken);
		if (authDetails instanceof AuthDetails) {
			return (T) authDetails;
		}
		throw new AccountNotExistException();
	}

	@Override
	public <T extends AuthDetails<T>> void setAuthDetails(T authDetails) {
		final String accessToken = getAccessToken();
		this.cacheManager.put(accessToken, authDetails);
	}
}
