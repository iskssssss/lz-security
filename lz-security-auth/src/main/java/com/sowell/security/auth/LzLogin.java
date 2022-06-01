package com.sowell.security.auth;

import com.sowell.security.auth.service.UserDetailsService;
import com.sowell.security.token.AccessTokenUtil;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 17:06
 */
public class LzLogin {

	public static String login(String userId) {
		// 获取用户信息
		final UserDetailsService userDetailsService = LzAuthManager.getUserDetailsService();
		final AuthDetails<?> authDetails = userDetailsService.readUserByUsername(userId);

		// 获取token
		final String token = AccessTokenUtil.generateAccessToken(authDetails, true);

		return token;
	}

	public static void logout() {
		AccessTokenUtil.invalid();
		// TODO 登出
	}

	public static void logout(String userId) {
		// TODO 登出
	}
}
