package com.sowell.security.defaults.token;

import cn.hutool.crypto.SmUtil;
import com.sowell.security.IcpManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.defaults.token.model.DefaultAuthDetails;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.model.AuthDetails;

import java.util.HashMap;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class UUIDAccessTokenHandler implements IAccessTokenHandler<DefaultAuthDetails> {

	@Override
	public Object generateAccessToken(DefaultAuthDetails authDetails) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		long timeoutMillis = IcpManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis();
		String id = "UUID::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isNotEmpty(idValue)) {
			return idValue;
		}
		cacheManager.remove(idValue);
		String accessToken = SmUtil.sm3(authDetails.toJson() + System.currentTimeMillis());
		cacheManager.putAll(new HashMap<String, Object>(4) {{
			put(id, accessToken);
			put(accessToken, authDetails);
		}}, timeoutMillis);
		return accessToken;
	}

	@Override
	public boolean checkExpiration(Object accessTokenInfo) {
		if (StringUtil.isEmpty(accessTokenInfo)) {
			return false;
		}
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final Object authDetailsObj = cacheManager.get(accessTokenInfo);
		if (!(authDetailsObj instanceof AuthDetails)) {
			return true;
		}
		final AuthDetails<?> authDetails = (AuthDetails<?>) authDetailsObj;
		String id = "UUID::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isEmpty(idValue)) {
			return true;
		}
		return !accessTokenInfo.equals(idValue);
	}

	@Override
	public DefaultAuthDetails getAuthDetails(Object accessTokenInfo) {
		if (this.checkExpiration(accessTokenInfo)) {
			throw new AccountNotExistException();
		}
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final Object authDetails = cacheManager.get(accessTokenInfo);
		if (!(authDetails instanceof AuthDetails)) {
			throw new AccountNotExistException();
		}
		return ((DefaultAuthDetails) authDetails);
	}

	@Override
	public void setAuthDetails(DefaultAuthDetails authDetails) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final Object accessToken = getAccessTokenInfo();
		cacheManager.put(accessToken, authDetails);
	}

	@Override
	public Object refreshAccessToken(Object accessTokenInfo) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		long timeoutMillis = IcpManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis();
		final AuthDetails<?> authDetails = this.getAuthDetails(accessTokenInfo);
		String id = "UUID::" + authDetails.getId();
		if (cacheManager.refreshKeys(timeoutMillis, id, accessTokenInfo)) {
			return accessTokenInfo;
		}
		return null;
	}
}
