package com.sowell.security.enums;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 错误码
 *
 * @version 1.0
 * Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName: RCode
 * @Descripton: Token错误码
 * @author: sowell
 * @date: 2021年3月30日19:19:02
 */
public enum RCode {
    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功。"),
    /**
     * 未查询到相关数据
     */
    EMPTY(1, "未查询到相关数据。"),
    /**
     * 参数有误
     */
    ERROR_PARAMS(2, "参数有误。"),
    /**
     * 发生未知错误
     */
    UNKNOWN_MISTAKE(3, "发生未知错误。"),
    /**
     * 请求失败
     */
    REQUEST_ERROR(4, "请求失败。"),
    /**
     * 设置读取请求参数字符编码错误
     */
    UNSUPPORTED_ENCODING_EXCEPTION(5,"设置读取请求参数字符编码错误。"),

    /**
     * appId或appSecret错误
     */
    APP_ID_NOT_EXIST(6001, "appId或appSecret错误。"),
    /**
     * timestamp 非一小时内时间
     */
    TIMESTAMP_NOT_WITHIN_AN_HOUR(6002, "timestamp 非一小时内时间。"),
    /**
     * AccessToken不存在或已过期
     */
    TOKEN_EXPIRE(6003, "AccessToken不存在或已过期。"),
    /**
     * 未获得访问该接口的权限
     */
    NOT_ACCESS_INTERFACE(6004, "未获得访问该接口的权限。"),
    /**
     * 未携带AccessToken
     */
    NOT_AUTHORIZATION(6005, "未携带AccessToken。"),
    /**
     * 您的IP未添加至白名单中，请联系管理员。
     */
    NOT_WHITE_IP(6006, "您的IP未添加至白名单中，请联系管理员。"),
    /**
     * 访问该URL只可在匿名状态下。
     */
    ANONYMOUS(6007, "访问该URL只可在匿名状态下。"),
    /**
     * 访问该URL需认证。
     */
    AUTHORIZATION(6008, "访问该URL需认证。"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(100, "用户不存在。"),
    /**
     * 组织不存在
     */
    ORG_NOT_EXIST(101, "组织不存在。"),
    /**
     * 临时授权码失效
     */
    USER_CODE_ERROR(102, "临时授权码失效。");

    private Integer code;
    private String message;

    RCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() throws IllegalAccessException {
        JSONObject resultJson = new JSONObject();
        final Field[] fields = RCode.class.getDeclaredFields();
        for (Field declaredField : fields) {
            declaredField.setAccessible(true);
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            final String name = declaredField.getName();
            final Object value = declaredField.get(this);
            resultJson.put(name, value);
        }
        return resultJson.toJSONString();
    }
}
