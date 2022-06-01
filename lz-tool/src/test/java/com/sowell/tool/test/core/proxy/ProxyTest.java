package com.sowell.tool.test.core.proxy;

import com.sowell.tool.test.core.proxy.model.ILogSave;
import com.sowell.tool.test.core.proxy.model.LogSave;
//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/01 09:51
 */
public class ProxyTest {


//	@Test
//	public void test() {
//		LogSave logSave = new LogSave(false);
//		final LogSave proxy = createProxy(new MethodInterceptor() {
//			@Override
//			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//				System.out.println(method.toString());
//				return proxy.invokeSuper(obj, args);
//			}
//		}, LogSave.class, new Class<?>[] {boolean.class}, new Object[] {true});
//		proxy.save();
//		proxy.delete();
//	}
//
//	public <T> T createProxy(MethodInterceptor methodInterceptor, Class<T> tClass, Class<?>[] argumentTypes, Object[] arguments) {
//		try {
//			Enhancer enhancer = new Enhancer();
//			enhancer.setSuperclass(tClass);
//			enhancer.setCallback(methodInterceptor);
//			return ((T) enhancer.create(argumentTypes, arguments));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Test
	public void test2() {
		final Class<LogSave> logSaveClass = LogSave.class;
		final Constructor<?>[] constructors = logSaveClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			final Parameter[] parameters = constructor.getParameters();
			for (Parameter parameter : parameters) {
				System.out.println(parameter.getName());
			}

		}
	}
	@Test
	public void test3() {
		LogSave logSave = new LogSave(true);
		final FacadeProxy<ILogSave> facadeProxy = new FacadeProxy<>(logSave, ILogSave.class);
		final ILogSave proxy = facadeProxy.getProxy();
		proxy.save();
	}
}
