package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:24
 */
public class CaptchaErrorException extends SecurityException {

    public CaptchaErrorException() {
        super("user.login.verify_code.error");
    }

    public CaptchaErrorException(Object object) {
        super("user.login.verify_code.error", object);
    }
}
