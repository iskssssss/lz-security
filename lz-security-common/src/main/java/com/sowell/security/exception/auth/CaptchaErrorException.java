package com.sowell.security.exception.auth;


import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.AuthCode;

/**
 * 验证码错误异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 17:06
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
        super(AuthCode.VERIFY_CODE_ERROR, responseData, cause);
    }
}
