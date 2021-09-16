package com.sowell.security.exception;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 14:11
 */
public class ResponseException extends RuntimeException {

    private final Object responseData;

    public ResponseException(String message) {
        super(message);
        this.responseData = null;
    }

    public ResponseException(Object responseData) {
        super("");
        this.responseData = responseData;
    }

    public ResponseException(String message, Object responseData) {
        super(message);
        this.responseData = responseData;
    }

    public Object getResponseData() {
        return responseData;
    }
}
