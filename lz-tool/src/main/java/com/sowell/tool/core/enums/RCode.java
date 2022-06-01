package com.sowell.tool.core.enums;

import com.sowell.tool.json.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 错误码
 *
 * @version 1.0
 * Copyright(c)2021 LZ
 * @ClassName: RCode
 * @Descripton: Token错误码
 * @author: sowell
 * @date: 2021年3月30日19:19:02
 */
public enum RCode implements ICode {
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
    UNSUPPORTED_ENCODING_EXCEPTION(5, "设置读取请求参数字符编码错误。"),
    /**
     * 内部服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "内部服务器错误"),

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
    HEADER_NOT_ACCESS_TOKEN(6005, "未携带AccessToken。"),
    /**
     * 您的IP未添加至白名单中，请联系管理员。
     */
    NOT_WHITE_IP(6006, "您的IP未添加至白名单中，请联系管理员。"),

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
    USER_CODE_ERROR(102, "临时授权码失效。"),

    /**
     * 数据加密失败
     */
    DATA_ENCRYPT_FAILED(901, "数据加密失败。"),
    /**
     * 数据解密失败
     */
    DATA_DECRYPT_FAILED(902, "数据解密失败。");

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

    public String toJson() {
        Map<String, Object> resultJson = new LinkedHashMap<>();
        resultJson.put("code", this.code);
        resultJson.put("message", this.message);
        return JsonUtil.toJsonString(resultJson);
    }
}
