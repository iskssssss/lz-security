package com.sowell.security.proxy;

import com.sowell.security.base.IInterfacesFilter;
import com.sowell.security.base.AbsInterfacesFilterBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 10:05
 */
public class InterfacesFilterProxy {
	public static final List<ProxyInfo<IInterfacesFilter>> PROXY_INFO_LIST = new LinkedList<>();

	public static IInterfacesFilter getProxyInstance(IInterfacesFilter target) {
		final ClassLoader classLoader = target.getClass().getClassLoader();
		final Class<?>[] interfaces = target.getClass().getInterfaces();
		final Object instance = Proxy.newProxyInstance(
				classLoader,
				interfaces,
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("start ------------------------");
						final Object invoke = method.invoke(target, args);
						System.out.println("end   ------------------------");
						return invoke;
					}
				}
		);
		if (instance instanceof IInterfacesFilter) {
			System.out.println("instanceof AbstractInterfacesFilter");
		}
		final IInterfacesFilter proxyInstance = ((IInterfacesFilter) instance);
		ProxyInfo<IInterfacesFilter> proxyInfo = new ProxyInfo<>(target, proxyInstance);
		PROXY_INFO_LIST.add(proxyInfo);
		return proxyInstance;
	}
}
