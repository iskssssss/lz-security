package cn.lz.tool.reflect;

import cn.lz.tool.json.JsonUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
}
