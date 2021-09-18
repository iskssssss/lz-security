package com.sowell.security.exception;


import com.sowell.security.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:01
 */
public class BadCredentialsException extends SecurityException {

    public BadCredentialsException() {
        this(null);
    }

    public BadCredentialsException(Throwable cause) {
        this(null, cause);
    }

    public BadCredentialsException(Object responseData) {
        this(responseData, null);
    }

    private BadCredentialsException(Object responseData, Throwable cause) {
        super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
    }
}