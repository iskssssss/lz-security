//package com.sowell.tool.test.core.proxy;
//
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
//
//import java.lang.reflect.Method;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/11/01 16:07
// */
//public class MyMethodInterceptor implements MethodInterceptor {
//
//	@Override
//	public Object intercept(
//			Object obj,
//			Method method,
//			Object[] objects,
//			MethodProxy methodProxy
//	) throws Throwable {
//		System.out.println("======插入前置通知======");
//		Object object = methodProxy.invokeSuper(obj, objects);
//		System.out.println("======插入后者通知======");
//		return object;
//	}
//}
