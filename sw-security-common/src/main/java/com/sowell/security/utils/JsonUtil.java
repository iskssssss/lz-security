package com.sowell.security.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description json 工具类
 * @Author eternity
 * @Date 2020/4/21 22:50
 */
public final class JsonUtil extends JSONObject {
    private static final String ERROR_INFO = "传入参数不可为空.";

    public static String toJsonString(Object object) {
        if (StringUtil.isNull(object)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return toJSONString(object);
    }

    public static JSONObject toJsonObject(Object object) {
        if (StringUtil.isNull(object)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return (JSONObject) toJSON(object);
    }

    public static JSONObject toJsonObject(String text) {
        if (StringUtil.isEmpty(text)) {
            throw new IllegalArgumentException(ERROR_INFO);
        }
        return parseObject(text);
    }
}
