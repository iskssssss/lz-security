package cn.lz.tool.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
