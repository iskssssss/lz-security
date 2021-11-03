//package com.sowell.demo.config.security.handler;
//
//import cn.hutool.crypto.SmUtil;
//import com.sowell.security.IcpManager;
//import com.sowell.security.cache.BaseCacheManager;
//import com.sowell.security.defaults.DefaultAuthDetails;
//import com.sowell.security.token.IAccessTokenHandler;
//import com.sowell.tool.jwt.model.AuthDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @date 2021/10/29 11:42
// */
//@Component
//public class TestAccessTokenHandler implements IAccessTokenHandler<DefaultAuthDetails> {
//
//	@Override
//	public Object generateAccessToken(DefaultAuthDetails authDetails) {
//		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
//		long timeoutMillis = IcpManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis();
//
//		final String id = "Test::" + authDetails.getId();
//		if (cacheManager.existKey(id)) {
//			final Object mapObj = cacheManager.get(id);
//			if (mapObj instanceof Map) {
//				return mapObj;
//			}
//		}
//		Map<String, Object> map = new HashMap<>(4);
//		String accessToken = SmUtil.sm3(authDetails.toJson() + System.currentTimeMillis());
//		final String refreshToken = UUID.randomUUID().toString();
//		map.put("accessToken", accessToken);
//		map.put("refreshToken", refreshToken);
//		map.put("ttl", timeoutMillis);
//		cacheManager.put(id, map, timeoutMillis);
//		cacheManager.put(accessToken + "::ID", id, timeoutMillis);
//		cacheManager.put(accessToken + "::AUTH_DETAILS", authDetails, timeoutMillis);
//		return map;
//	}
//
//	@Override
//	public boolean checkExpiration(Object accessTokenInfo) {
//
//		return false;
//	}
//
//	@Override
//	public DefaultAuthDetails getAuthDetails(Object accessTokenInfo) {
//		final BaseCacheManager cacheManager = IcpManager.getCacheManager();
//		final Object authDetailsObj = cacheManager.get(accessTokenInfo+ "::AUTH_DETAILS");
//		if (authDetailsObj instanceof AuthDetails) {
//			return ((DefaultAuthDetails) authDetailsObj);
//		}
//		return null;
//	}
//
//	@Override
//	public void setAuthDetails(DefaultAuthDetails authDetails) {
//
//
//	}
//
//	@Override
//	public Object refreshAccessToken(Object accessTokenInfo) {
//
//		return null;
//	}
//}
