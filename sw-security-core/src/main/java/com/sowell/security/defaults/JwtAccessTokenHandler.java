package com.sowell.security.defaults;

import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.exception.AccountNotExistException;
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
public class JwtAccessTokenHandler implements IAccessTokenHandler<DefaultAuthDetails> {

	@Override
	public Object generateAccessToken(DefaultAuthDetails authDetails) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		long timeoutMillis = IcpManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis();
		String id = "JWT::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isNotEmpty(idValue)) {
			return idValue;
		}
		String accessToken = JwtUtil.generateToken(authDetails, ((int) timeoutMillis));
		cacheManager.put(id, accessToken, timeoutMillis);
		return accessToken;
	}

	@Override
	public boolean checkExpiration(Object accessTokenInfo) {
		if (StringUtil.isEmpty(accessTokenInfo)) {
			return false;
		}
		final AuthDetails authDetails = JwtUtil.toBean(((String) accessTokenInfo));
		if (authDetails == null) {
			return true;
		}
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		String id = "JWT::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isEmpty(idValue)) {
			return true;
		}
		if (!accessTokenInfo.equals(idValue)) {
			return true;
		}
		return JwtUtil.checkExpiration(((String) accessTokenInfo));
	}

	@Override
	public DefaultAuthDetails getAuthDetails(Object accessTokenInfo) {
		if (this.checkExpiration(accessTokenInfo)) {
			throw new AccountNotExistException();
		}
		final AuthDetails authDetails = JwtUtil.toBean(((String) accessTokenInfo));
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return ((DefaultAuthDetails) authDetails);
	}

	@Override
	public void setAuthDetails(DefaultAuthDetails authDetails) {
		//final String accessToken = getAccessToken();
		//cacheManager.put(accessToken, authDetails);
	}

	@Override
	public Object refreshAccessToken(Object accessTokenInfo) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final AuthDetails authDetails = this.getAuthDetails(accessTokenInfo);
		String id = "JWT::" + authDetails.getId();
		cacheManager.remove(id);
		final Object token = generateAccessToken(((DefaultAuthDetails) authDetails));

		return token;
	}
}
