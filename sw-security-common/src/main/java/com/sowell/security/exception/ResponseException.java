package com.sowell.security.exception;

import com.sowell.security.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 14:11
 */
public class ResponseException extends SecurityException {

    public ResponseException(RCode rCode) {
        this(rCode, null);
    }

    public ResponseException(RCode rCode, Throwable cause) {
        this(rCode, null, cause);
    }

    public ResponseException(RCode rCode, Object responseData) {
        this(rCode, responseData, null);
    }

    private ResponseException(RCode rCode, Object responseData, Throwable cause) {
        super(rCode.getCode(), rCode.getMessage(), responseData, cause);
    }
}
