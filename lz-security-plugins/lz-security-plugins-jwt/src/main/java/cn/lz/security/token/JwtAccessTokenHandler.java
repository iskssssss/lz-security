package cn.lz.security.token;

import cn.lz.security.LzCoreManager;
import cn.lz.security.cache.BaseCacheManager;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.context.LzSecurityContextThreadLocal;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.auth.AccountNotExistException;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.jwt.JwtUtil;
import cn.lz.tool.jwt.model.AuthDetails;

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
		long timeoutMillis = LzCoreManager.getTokenConfig().getTimeoutForMillis();
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

		final TokenConfig tokenConfig = LzCoreManager.getTokenConfig();
		final String saveName = tokenConfig.getName();
		final BaseResponse<?> servletResponse = LzSecurityContextThreadLocal.getResponse();
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
