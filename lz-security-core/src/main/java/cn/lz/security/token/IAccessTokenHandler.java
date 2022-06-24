package cn.lz.security.token;

import cn.lz.security.config.TokenConfig;
import cn.lz.security.LzConstant;
import cn.lz.security.LzCoreManager;
import cn.lz.security.context.LzSecurityContextThreadLocal;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.exception.HeaderNotAccessTokenException;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.jwt.model.AuthDetails;

/**
 * AccessToken处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 17:13
 */
public interface IAccessTokenHandler<T extends AuthDetails> {

	/**
	 * 获取获取客户端的AccessToken
	 *
	 * @return AccessToken
	 */
	default String getAccessTokenInfo() {
		final BaseRequest<?> servletRequest = LzSecurityContextThreadLocal.getServletRequest();
		if (servletRequest == null) {
			throw new SecurityException(RCode.INTERNAL_SERVER_ERROR);
		}
		final TokenConfig tokenConfig = LzCoreManager.getTokenConfig();
		final String saveName = tokenConfig.getName();
		// 从请求头中获取Token
		String accessToken = servletRequest.getHeader(saveName);
		if (StringUtil.isEmpty(accessToken)) {
			// 从Cookie中获取Token
			accessToken = servletRequest.getCookieValue(saveName);
		}
		if (StringUtil.isEmpty(accessToken)) {
			throw new HeaderNotAccessTokenException();
		}
		final String prefix = tokenConfig.getPrefix();
		// 是否开启前缀，开启的话进行截取。
		if (StringUtil.isEmpty(prefix)) {
			return accessToken;
		}
		if (accessToken.startsWith(prefix + LzConstant.PREFIX_TOKEN_SPLIT)) {
			return StringUtil.delString(accessToken, prefix + LzConstant.PREFIX_TOKEN_SPLIT);
		} else if (accessToken.startsWith(prefix + LzConstant.PREFIX_TOKEN_SPLIT_FOR_COOKIE)) {
			return StringUtil.delString(accessToken, prefix + LzConstant.PREFIX_TOKEN_SPLIT_FOR_COOKIE);
		} else {
			throw new HeaderNotAccessTokenException();
		}
	}

	/**
	 * 生成AccessToken
	 *
	 * @param authDetails 认证信息
	 * @return AccessToken
	 */
	String generateAccessToken(T authDetails);

	/**
	 * 无效Token
	 */
 	default void invalid() {
		invalid(this.getAccessTokenInfo());
	}

	/**
	 * 无效指定Token
	 *
	 * @param token Token
	 */
	void invalid(String token);

	/**
	 * 校验AccessToken是否过期
	 *
	 * @return true：过期 反之未过期
	 */
	default boolean checkExpiration() {
		try {
			return this.checkExpiration(this.getAccessTokenInfo());
		} catch (SecurityException securityException) {
			return true;
		}
	}

	/**
	 * 校验AccessToken是否过期
	 *
	 * @param accessToken AccessToken
	 * @return true：过期 反之未过期
	 */
	boolean checkExpiration(String accessToken);

	/**
	 * 获取认证信息
	 *
	 * @return 认证信息
	 */
	default T getAuthDetails() {
		return this.getAuthDetails(this.getAccessTokenInfo());
	}

	/**
	 * 获取认证信息
	 *
	 * @param accessToken AccessToken
	 * @return 认证信息
	 */
	T getAuthDetails(String accessToken);

	/**
	 * 设置认证信息
	 *
	 * @param authDetails 认证信息
	 */
	void setAuthDetails(T authDetails);

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @return 刷新后的AccessToken
	 */
	default String refreshAccessToken() {
		return this.refreshAccessToken(this.getAccessTokenInfo());
	}

	/**
	 * 刷新AccessToken过期时间
	 *
	 * @param accessToken AccessToken
	 * @return 刷新后的AccessToken
	 */
	String refreshAccessToken(String accessToken);

}
