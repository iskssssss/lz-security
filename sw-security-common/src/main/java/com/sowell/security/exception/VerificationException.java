package com.sowell.security.exception;


import com.sowell.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:02
 */
public class VerificationException extends SecurityException {

    public VerificationException() {
        this(null);
    }

    public VerificationException(Throwable cause) {
        this(null, cause);
    }

    public VerificationException(Object responseData) {
        this(responseData, null);
    }

    private VerificationException(Object responseData, Throwable cause) {
        super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
    }
}