package com.sowell.tool.core.proxy;

import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 09:49
 */
public class LogHandler {
	private final Map<Integer, Object> targetMap = new HashMap<>();

	public static Object getProxyInstance(Object target) {
		final Object proxyInstance = Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				(proxy, method, args) -> {
					before();
					final Object invoke = method.invoke(target, args);
					after();
					return invoke;
				}
		);
		return proxyInstance;
	}

	private static void before() {
		System.out.printf("log start time [%s] %n", new Date());
	}

	private static void after() {
		System.out.printf("log end time [%s] %n", new Date());
	}
}
