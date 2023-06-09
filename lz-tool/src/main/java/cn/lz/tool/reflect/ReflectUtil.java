package cn.lz.tool.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/21 14:22
 */
public class ReflectUtil {

	public static Set<Method> getDeclaredMethods(Class<?> aClass) {
		return getDeclaredMethods(new HashSet<>(), aClass);
	}

	public static Set<Method> getDeclaredMethods(Set<Method> methodSet, Class<?> aClass) {
		if (aClass == null) {
			return methodSet;
		}
		Method[] declaredMethods = aClass.getDeclaredMethods();
		methodSet.addAll(Arrays.asList(declaredMethods));
		return getDeclaredMethods(methodSet, aClass.getSuperclass());
	}

	public static Set<Field> getDeclaredFields(Class<?> tClass) {
		return getDeclaredFields(new HashSet<>(), tClass);
	}

	public static Set<Field> getDeclaredFields(Set<Field> fieldList, Class<?> tClass) {
		if (tClass == null) {
			return fieldList;
		}
		final Field[] declaredFields = tClass.getDeclaredFields();
		fieldList.addAll(Arrays.asList(declaredFields));
		return getDeclaredFields(fieldList, tClass.getSuperclass());
	}

	public static List<Field> getDeclaredFields(List<Field> fieldList, Class<?> tClass) {
		if (tClass == null) {
			return fieldList;
		}
		final Field[] declaredFields = tClass.getDeclaredFields();
		fieldList.addAll(Arrays.asList(declaredFields));
		return getDeclaredFields(fieldList, tClass.getSuperclass());
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

	/**
	 * 加载类
	 *
	 * @param className 类名
	 * @return 类
	 */
	public static Class<?> forName(String className) {
		try {
			final String handlerClassName = className
					.replaceAll("\\(\\)L", "")
					.replaceAll("/", ".")
					.replaceAll(";", "");
			return Class.forName(handlerClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
