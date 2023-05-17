package cn.lz.security.auth;

import cn.lz.security.auth.service.UserDetailsService;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.tool.jwt.model.AuthDetails;

/**
 * 登录操作类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class LzLogin {

	/**
	 * 登录
	 *
	 * @param userId 用户ID
	 * @return token
	 */
	public static String login(String userId) {
		// 获取用户信息
		final UserDetailsService userDetailsService = LzAuthManager.getUserDetailsService();
		final AuthDetails<?> authDetails = userDetailsService.readUserByIdentifier(userId);

		// 获取token
		final String token = AccessTokenUtil.generateAccessToken(authDetails, true);
		return token;
	}

	/**
	 * 登出
	 */
	public static void logout() {
		AccessTokenUtil.invalid();
		// TODO 登出
	}

	/**
	 * 登出
	 *
	 * @param userId 用户ID
	 */
	public static void logout(String userId) {
		// TODO 登出
	}
}
