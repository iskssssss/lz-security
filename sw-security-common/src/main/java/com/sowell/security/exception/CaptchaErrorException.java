package com.sowell.security.exception;


import com.sowell.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:24
 */
public class CaptchaErrorException extends SecurityException {

    public CaptchaErrorException() {
        this(null);
    }

    public CaptchaErrorException(Throwable cause) {
        this(null, cause);
    }

    public CaptchaErrorException(Object responseData) {
        this(responseData, null);
    }

    private CaptchaErrorException(Object responseData, Throwable cause) {
        super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
    }
}
