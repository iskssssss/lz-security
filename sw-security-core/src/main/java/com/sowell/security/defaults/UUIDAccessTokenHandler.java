package com.sowell.security.defaults;

import cn.hutool.crypto.SmUtil;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.context.IcpSecurityContextThreadLocal;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.auth.AccountNotExistException;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.jwt.model.AuthDetails;

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
		cacheManager.put(id, accessToken, timeoutMillis);
		cacheManager.put(accessToken, authDetails, timeoutMillis);
		return accessToken;
	}

	@Override
	public void invalid(String token) {
		if (this.checkExpiration(token)) {
			return;
		}
		final BaseCacheManager cacheManager = IcpCoreManager.getCacheManager();
		final Object o = cacheManager.get(token);
		if (!(o instanceof AuthDetails)) {
			return;
		}
		final AuthDetails<?> authDetails = (AuthDetails<?>) o;
		String id = "UUID::" + authDetails.getId();
		cacheManager.remove(id, token);

		final IcpConfig.TokenConfig tokenConfig = IcpCoreManager.getIcpConfig().getTokenConfig();
		final String saveName = tokenConfig.getName();
		final BaseResponse<?> servletResponse = IcpSecurityContextThreadLocal.getServletResponse();
		servletResponse.removeCookie(saveName);
	}

	@Override
	public boolean checkExpiration(String accessToken) {
		if (StringUtil.isEmpty(accessToken)) {
			return true;
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
