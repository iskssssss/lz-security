package cn.lz.security.arrays;

import cn.lz.security.LzCoreManager;
import cn.lz.security.context.LzContext;
import cn.lz.tool.reflect.model.ControllerMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/02 10:47
 */
public final class InterfacesMethodMap extends HashMap<String, Map<String, ControllerMethod>> {

	/**
	 * 设置接口方法映射集合
	 *
	 * @param interfacesMethodMap 接口方法映射集合
	 */
	public void putInterfacesMethodMap(Map<String, Map<String, ControllerMethod>> interfacesMethodMap) {
		if (interfacesMethodMap == null || interfacesMethodMap.isEmpty()) {
			return;
		}
		this.clear();
		this.putAll(interfacesMethodMap);
	}

	/**
	 * 获取请求接口对应的方法
	 *
	 * @param url 请求接口
	 * @return 方法
	 */
	public Map<String, ControllerMethod> getMethodByInterfaceUrl(String url) {
		final Map<String, ControllerMethod> methodMap = this.get(url);
		if (methodMap != null) {
			return methodMap;
		}
		final LzContext<?, ?> lzContext = LzCoreManager.getLzContext();
		final Set<Entry<String, Map<String, ControllerMethod>>> entrySet = this.entrySet();
		for (Entry<String, Map<String, ControllerMethod>> stringMethodEntry : entrySet) {
			final String key = stringMethodEntry.getKey();
			if (lzContext.matchUrl(key, url)) {
				return stringMethodEntry.getValue();
			}
		}
		return null;
	}

	/**
	 * 获取请求接口对应的方法
	 *
	 * @param url    请求接口
	 * @param method 请求类型
	 * @return 方法
	 */
	public ControllerMethod getMethodByInterfaceUrl(String url, String method) {
		Map<String, ControllerMethod> methodMap = this.getMethodByInterfaceUrl(url);
		if (methodMap == null) {
			return null;
		}
		return methodMap.get(method);
	}
}
