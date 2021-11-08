package com.sowell.security.defaults;

import cn.hutool.crypto.SmUtil;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.exception.auth.AccountNotExistException;
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
public class UUIDAccessTokenHandler implements IAccessTokenHandler<AuthDetails> {

	@Override
	public String generateAccessToken(AuthDetails authDetails) {
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		long timeoutMillis = IcpCoreManager.getIcpConfig().getTokenConfig().getTimeoutForMillis();
		String id = "UUID::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isNotEmpty(idValue)) {
			return ((String) idValue);
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
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return false;
		}
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		final Object authDetailsObj = cacheManager.get(accessToken);
		if (!(authDetailsObj instanceof AuthDetails)) {
			return true;
		}
		final AuthDetails<?> authDetails = (AuthDetails<?>) authDetailsObj;
		String id = "UUID::" + authDetails.getId();
		final Object idValue = cacheManager.get(id);
		if (StringUtil.isEmpty(idValue)) {
			return true;
		}
		return !accessToken.equals(idValue);
	}

	@Override
	public AuthDetails getAuthDetails(String accessToken) {
		if (this.checkExpiration(accessToken)) {
			throw new AccountNotExistException();
		}
		final Object authDetails = IcpCoreManager.getCacheManager().get(accessToken);
		if (!(authDetails instanceof AuthDetails)) {
			throw new AccountNotExistException();
		}
		return ((AuthDetails) authDetails);
	}

	@Override
	public void setAuthDetails(AuthDetails authDetails) {
		final String accessToken = getAccessTokenInfo();
		IcpCoreManager.getCacheManager().put(accessToken, authDetails);
	}

	@Override
	public String refreshAccessToken(String accessToken) {
		final AuthDetails<?> authDetails = this.getAuthDetails(accessToken);
		String id = "UUID::" + authDetails.getId();
		IcpCoreManager.getCacheManager().remove(id);
		return generateAccessToken(authDetails);
	}
}
