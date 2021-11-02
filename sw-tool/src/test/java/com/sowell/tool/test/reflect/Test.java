package com.sowell.tool.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 16:32
 */
public class Test {

	@org.junit.Test
	public void test(){
		final Constructor<?>[] constructors = CouTest.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			final int parameterCount = constructor.getParameterCount();
			final Class<?>[] parameterTypes = constructor.getParameterTypes();
			final Parameter[] parameters = constructor.getParameters();
			System.out.println(parameterCount);
		}

	}
}
