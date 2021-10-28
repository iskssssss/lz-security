package com.sowell.tool.reflect;

import com.sowell.tool.json.JsonUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/10/22 11:27
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

	public static <T> T toBean(String jsonStr, Class<T> tClass) {
		final Map<String, Object> map = JsonUtil.parseObject(jsonStr, Map.class);
		if (map == null){
			return null;
		}
		Set<Field> fieldList = new HashSet<>();
		ReflectUtil.getDeclaredFields(fieldList, tClass);
		final T t = ReflectUtil.newInstance(tClass);
		for (Field declaredField : fieldList) {
			final String name = declaredField.getName();
			if (!map.containsKey(name)) {
				continue;
			}
			declaredField.setAccessible(true);
			final Object o = map.get(name);
			try {
				declaredField.set(t, o);
			} catch (IllegalAccessException ignored) {
			}
		}
		return t;
	}

	public static <T> T toBean(Object obj, Class<T> tClass) {
		T t;
		final Class<?> aClass = obj.getClass();
		final List<Field> aClassFields = ReflectUtil.getDeclaredFields(new LinkedList<>(), aClass);
		Map<String, Field> fieldMap = new HashMap<>();
		for (Field aClassField : aClassFields) {
			final String name = aClassField.getName();
			if ("serialVersionUID".equals(name)) {
				continue;
			}
			aClassField.setAccessible(true);
			fieldMap.put(name, aClassField);
		}
		final List<Field> tClassFields = ReflectUtil.getDeclaredFields(new LinkedList<>(), tClass);
		try {
			t = tClass.newInstance();
			for (Field tClassField : tClassFields) {
				final String name = tClassField.getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				final Field aClassField = fieldMap.get(name);
				if (aClassField == null) {
					continue;
				}
				tClassField.setAccessible(true);
				tClassField.set(t, aClassField.get(obj));
			}
			return t;
		} catch (InstantiationException | IllegalAccessException instantiationException) {
			return null;
		}
	}

	public static <T> T toBean(Map<String, Object> fieldMap, Class<T> tClass) {
		T t;
		final List<Field> tClassFields = ReflectUtil.getDeclaredFields(new LinkedList<>(), tClass);
		try {
			t = tClass.newInstance();
			for (Field tClassField : tClassFields) {
				final String name = tClassField.getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				final Object aClassField = fieldMap.get(name);
				if (aClassField == null) {
					continue;
				}
				tClassField.setAccessible(true);
				tClassField.set(t, aClassField);
			}
			return t;
		} catch (InstantiationException | IllegalAccessException instantiationException) {
			return null;
		}
	}
}
