//package com.sowell.demo.config.security.handler;
//
//import com.sowell.security.cache.BaseCacheManager;
//import com.sowell.security.log.IcpLoggerUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @date 2021/10/29 09:39
// */
////@Component
//public class MapCacheManager implements BaseCacheManager {
//	private final Map<Object, Object> cachaMap = new HashMap<>();
//
//	@Override
//	public boolean put(Object key, Object value) {
//		IcpLoggerUtil.info(getClass(), "MapCacheManager.put(...)");
//		cachaMap.put(key, value);
//		return false;
//	}
//
//	@Override
//	public boolean put(Object key, Object value, long timeout) {
//		IcpLoggerUtil.info(getClass(), "MapCacheManager.put(...)");
//		cachaMap.put(key, value);
//		return false;
//	}
//
//	@Override
//	public boolean remove(Object key) {
//		IcpLoggerUtil.info(getClass(), "MapCacheManager.remove(...)");
//		cachaMap.remove(key);
//		return false;
//	}
//
//	@Override
//	public Object get(Object key) {
//		IcpLoggerUtil.info(getClass(), "MapCacheManager.get(...)");
//		return cachaMap.get(key);
//	}
//
//	@Override
//	public Boolean existKey(Object key) {
//		IcpLoggerUtil.info(getClass(), "MapCacheManager.existKey(...)");
//		return cachaMap.containsKey(key);
//	}
//
//	@Override
//	public boolean refreshKey(Object key, long timeout) {
//		return false;
//	}
//}
