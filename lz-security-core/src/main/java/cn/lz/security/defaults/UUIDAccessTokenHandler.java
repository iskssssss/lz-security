package cn.lz.security.defaults;

import cn.hutool.crypto.SmUtil;
import cn.lz.security.LzCoreManager;
import cn.lz.security.cache.BaseCacheManager;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.context.LzSecurityContextThreadLocal;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.auth.AccountNotExistException;
import cn.lz.security.token.IAccessTokenHandler;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class UUIDAccessTokenHandler implements IAccessTokenHandler<AuthDetails> {

	@Override
	public String generateAccessToken(AuthDetails authDetails) {
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
		long timeoutMillis = LzCoreManager.getLzConfig().getTokenConfig().getTimeoutForMillis();
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
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
		final Object o = cacheManager.get(token);
		if (!(o instanceof AuthDetails)) {
			return;
		}
		final AuthDetails<?> authDetails = (AuthDetails<?>) o;
		String id = "UUID::" + authDetails.getId();
		cacheManager.remove(id, token);

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
		final BaseCacheManager cacheManager = LzCoreManager.getCacheManager();
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
		final Object authDetails = LzCoreManager.getCacheManager().get(accessToken);
		if (!(authDetails instanceof AuthDetails)) {
			throw new AccountNotExistException();
		}
		return ((AuthDetails) authDetails);
	}

	@Override
	public void setAuthDetails(AuthDetails authDetails) {
		final String accessToken = getAccessTokenInfo();
		LzCoreManager.getCacheManager().put(accessToken, authDetails);
	}

	@Override
	public String refreshAccessToken(String accessToken) {
		final AuthDetails<?> authDetails = this.getAuthDetails(accessToken);
		String id = "UUID::" + authDetails.getId();
		LzCoreManager.getCacheManager().remove(id);
		return generateAccessToken(authDetails);
	}
}
