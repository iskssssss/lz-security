package cn.lz.tool.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/23 11:05
 */
@SuppressWarnings("unchecked")
public final class ObjectUtil {

	public static void getFieldList(Set<Field> fieldList, Class<?> tClass) {
		if (tClass == null) {
			return;
		}
		final Field[] declaredFields = tClass.getDeclaredFields();
		fieldList.addAll(Arrays.asList(declaredFields));
		getFieldList(fieldList, tClass.getSuperclass());
	}

	public static <T> T newInstance(Class<T> tClass) {
		final Constructor<?>[] constructors = tClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterCount() == 0) {
				try {
					return (T) constructor.newInstance();
				} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	public static boolean equals(Object anObject1, Object anObject2) {
		return Objects.equals(anObject1, anObject2);
	}
}
