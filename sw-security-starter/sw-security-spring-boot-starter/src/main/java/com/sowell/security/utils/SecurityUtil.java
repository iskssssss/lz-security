package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.enums.RCode;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.utils.BeanUtil;
import com.sowell.security.utils.JwtUtil;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.context.IcpSecurityContextHandler;
import com.sowell.security.IcpManager;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 14:41
 */
public class SecurityUtil {

	/**
	 * 获取当前请求 组件信息
	 *
	 * @return 组件信息
	 */
	public static AuthDetails<?> getPrincipal() {
		final String token = SecurityUtil.getToken();
		return getPrincipal(token);
	}

	/**
	 * 获取当前请求 组件信息
	 *
	 * @return 组件信息
	 */
	public static AuthDetails<?> getPrincipal(String token) {
		if (StringUtil.isEmpty(token)) {
			throw new AccountNotExistException(RCode.TOKEN_EXPIRE);
		}
//		final AuthDetails<?> value = JwtUtil.toBean(token);
		final AuthDetails<?> value = SecurityUtil.getAuthDetailsJson(token);
		if (value != null) {
			return value;
		}
		throw new AccountNotExistException(RCode.TOKEN_EXPIRE);
	}

	public static void setPrincipal(String appKey, String token) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		cacheManager.put(appKey, token);
	}

	public static void setPrincipal(String appKey, String token, String authDetailsJson) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		cacheManager.put(appKey, token);
		cacheManager.put(token, authDetailsJson);
	}

	public static AuthDetails<?> getAuthDetailsJson() {
		final String token = IcpSecurityContextHandler.getAuthorizationToken();
		return getAuthDetailsJson(token);
	}

	public static AuthDetails<?> getAuthDetailsJson(String token) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final Object cache = cacheManager.get(token);
		if (!(cache instanceof String)) {
			throw new AccountNotExistException(RCode.TOKEN_EXPIRE);
		}
		final String componentAuthDetailsJson = (String) cache;
		final JSONObject jsonObject = JSONObject.parseObject(componentAuthDetailsJson);
		final String sourceClassName = ((String) jsonObject.get("sourceClassName"));
		final Class<?> aClass = JwtUtil.forName(sourceClassName);
		return (AuthDetails<?>) BeanUtil.toBean(jsonObject, aClass);
	}

	public static String getToken(String appKey) {
		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
		final Object value = cacheManager.get(appKey);
		if (value instanceof String) {
			return (String) value;
		}
		return null;
	}

	public static String getToken() {
		return IcpSecurityContextHandler.getAuthorizationToken();
	}
}
