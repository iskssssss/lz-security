package cn.lz.tool.test.core.proxy;

import cn.lz.tool.test.core.proxy.model.ILogSave;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/01 09:49
 */
public class LogHandler {
	private final Map<Integer, Object> targetMap = new HashMap<>();

	public static ILogSave getProxyInstance(ILogSave target) {
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
		final Class<?> aClass = proxyInstance.getClass();
		final Field[] declaredFields = aClass.getDeclaredFields();
		return ((ILogSave) proxyInstance);
	}

	private static void before() {
		System.out.printf("log start time [%s] %n", new Date());
	}

	private static void after() {
		System.out.printf("log end time [%s] %n", new Date());
	}
}
