package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/1 9:50
 */
public final class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    public static <T> T toBean(String jsonStr, Class<T> tClass) {
        final Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);
        Set<Field> fieldList = new HashSet<>();
        ObjectUtil.getFieldList(fieldList, tClass);
        final T t = ObjectUtil.newInstance(tClass);
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
