package com.sowell.security.defaults;

import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.security.utils.JwtUtil;
import com.sowell.security.utils.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 10:11
 */
public class JwtAccessTokenHandler implements IAccessTokenHandler {

	@Override
	public <T extends AuthDetails<T>> T getAuthDetails() {
		return getAuthDetails(getAccessToken());
	}

	@Override
	public <T extends AuthDetails<T>> String generateAccessToken(T authDetails) {
		if (StringUtil.isEmpty(authDetails)) {
			return null;
		}
		return JwtUtil.generateToken(authDetails);
	}

	@Override
	public <T extends AuthDetails<T>> T getAuthDetails(String accessToken) {
		if (accessToken == null) {
			throw new AccountNotExistException();
		}
		final T authDetails = JwtUtil.toBean(accessToken);
		if (authDetails == null) {
			throw new AccountNotExistException();
		}
		return authDetails;
	}

	@Override
	public <T extends AuthDetails<T>> void setAuthDetails(T authDetails) {
		// TODO
	}
}
