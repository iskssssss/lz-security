//package com.sowell.demo.filter.auth.config.security.handler;
//
//import com.sowell.security.cache.BaseCacheManager;
//import com.sowell.security.log.LzLoggerUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/10/29 09:39
// */
////@Component
//public class MapCacheManager implements BaseCacheManager {
//	private final Map<Object, Object> cachaMap = new HashMap<>();
//
//	@Override
//	public boolean put(Object key, Object value) {
//		LzLoggerUtil.info(getClass(), "MapCacheManager.put(...)");
//		cachaMap.put(key, value);
//		return false;
//	}
//
//	@Override
//	public boolean put(Object key, Object value, long timeout) {
//		LzLoggerUtil.info(getClass(), "MapCacheManager.put(...)");
//		cachaMap.put(key, value);
//		return false;
//	}
//
//	@Override
//	public boolean remove(Object key) {
//		LzLoggerUtil.info(getClass(), "MapCacheManager.remove(...)");
//		cachaMap.remove(key);
//		return false;
//	}
//
//	@Override
//	public Object get(Object key) {
//		LzLoggerUtil.info(getClass(), "MapCacheManager.get(...)");
//		return cachaMap.get(key);
//	}
//
//	@Override
//	public Boolean existKey(Object key) {
//		LzLoggerUtil.info(getClass(), "MapCacheManager.existKey(...)");
//		return cachaMap.containsKey(key);
//	}
//
//	@Override
//	public boolean refreshKey(Object key, long timeout) {
//		return false;
//	}
//}
