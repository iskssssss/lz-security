//package com.sowell.security.porxy;
//
//import com.sowell.security.IcpManager;
//import com.sowell.security.filter.base.AbsInterfacesFilterBuilder;
//import com.sowell.security.filter.base.IInterfacesFilter;
//import com.sowell.security.log.BaseFilterLogHandler;
//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @date 2021/11/01 16:23
// */
//public class InterfaceFilterInterceptor implements MethodInterceptor {
//
//	@Override
//	public Object intercept(
//			Object obj, Method method, Object[] args, MethodProxy proxy
//	) throws Throwable {
//		final BaseFilterLogHandler filterLogHandler = IcpManager.getFilterLogHandler();
//		System.out.println(method.getName());
//		Object object = proxy.invokeSuper(obj, args);
//
//		return object;
//	}
//
//	public static AbsInterfacesFilterBuilder getInterfaceFilterProxy(
//			AbsInterfacesFilterBuilder interfacesFilterBuilder
//	) {
//
//		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(interfacesFilterBuilder.getClass());
//		enhancer.setCallback(new InterfaceFilterInterceptor());
//		return ((AbsInterfacesFilterBuilder) enhancer.create());
//	}
//}
