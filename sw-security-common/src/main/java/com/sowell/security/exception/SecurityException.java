package com.sowell.security.exception;

import com.sowell.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 14:11
 */
public class SecurityException extends RuntimeException {

    private final Integer code;
    private Object responseData;

    public SecurityException(RCode rCode) {
        this(rCode, null);
    }

    public SecurityException(RCode rCode, Throwable cause) {
        this(rCode.getCode(), rCode.getMessage(), cause);
    }

    public SecurityException(Integer code, String message) {
        this(code, message, null);
    }

    public SecurityException(Integer code, Object responseData) {
        this(code, "", responseData);
    }

    public SecurityException(Integer code, String message, Throwable cause) {
        this(code, message, null, cause);
    }

    public SecurityException(Integer code, String message, Object responseData) {
        this(code, message, responseData, null);
    }

    public SecurityException(Integer code, String message, Object responseData, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.responseData = responseData;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public Integer getCode() {
        return code;
    }
}
