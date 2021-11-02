package com.sowell.tool.test.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 15:15
 */
public class FacadeProxy<T> implements InvocationHandler {
	private final Class<T> tClass;
	private final T source;

	public FacadeProxy(T source, Class<T> tClass) {
		this.source = source;
		this.tClass = tClass;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before");
		final Object invoke = method.invoke(source);
		System.out.println("after");
		return invoke;
	}

	public T getProxy() {
		ClassLoader classLoader = this.tClass.getClassLoader();
		Class<?>[] interfaces = new Class[] {this.tClass};
		return (T) Proxy.newProxyInstance(classLoader, interfaces, this);
	}
}
