package com.sowell.security.arrays;


import com.sowell.security.IcpCoreManager;
import com.sowell.security.context.IcpContext;
import com.sowell.tool.reflect.model.ControllerMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/02 10:47
 */
public final class InterfacesMethodMap extends HashMap<String, ControllerMethod> {

	/**
	 * 设置接口方法映射集合
	 *
	 * @param interfacesMethodMap 接口方法映射集合
	 */
	public void putInterfacesMethodMap(Map<String, ControllerMethod> interfacesMethodMap) {
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
	public ControllerMethod getMethodByInterfaceUrl(String url) {
		final ControllerMethod method = this.get(url);
		if (method != null) {
			return method;
		}
		final IcpContext<?, ?> icpContext = IcpCoreManager.getIcpContext();
		final Set<Entry<String, ControllerMethod>> entrySet = this.entrySet();
		for (Entry<String, ControllerMethod> stringMethodEntry : entrySet) {
			final String key = stringMethodEntry.getKey();
			if (icpContext.matchUrl(key, url)) {
				return stringMethodEntry.getValue();
			}
		}
		return null;
	}
}
