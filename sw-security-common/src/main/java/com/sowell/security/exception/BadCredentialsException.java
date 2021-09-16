package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:01
 */
public class BadCredentialsException extends SecurityException {

    public BadCredentialsException() {
        super("user.login.password.not.matches");
    }

    public BadCredentialsException(Object object) {
        super("user.login.password.not.matches", object);
    }
}