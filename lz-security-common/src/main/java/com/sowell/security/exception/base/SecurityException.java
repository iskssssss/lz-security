package com.sowell.security.exception.base;

import com.sowell.tool.core.enums.ICode;
import com.sowell.tool.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class SecurityException extends RuntimeException {

    private final Integer responseCode;
    private final String responseMessage;
    private Object responseData;

    public SecurityException(ICode securityExceptionCode) {
        this(securityExceptionCode, null);
    }

    public SecurityException(ICode securityExceptionCode, Throwable cause) {
        this(securityExceptionCode, null, cause);
    }

    public SecurityException(Integer responseCode, String responseMessage) {
        this(responseCode, responseMessage, null);
    }

    public SecurityException(ICode securityExceptionCode, Object responseData, Throwable cause) {
        this(securityExceptionCode.getCode(), securityExceptionCode.getMessage(), responseData, cause);
    }

    public SecurityException(Integer responseCode, String responseMessage, Object responseData) {
        this(responseCode, responseMessage, responseData, null);
    }

    public SecurityException(Integer responseCode, String responseMessage, Object responseData, Throwable cause) {
        super(responseMessage, cause);
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String toJson() {
        Map<String, Object> resultJson = new HashMap<>(4);
        resultJson.put("code", responseCode);
        resultJson.put("data", responseData);
        resultJson.put("message", responseMessage);
        return JsonUtil.toJsonString(resultJson);
    }
}
