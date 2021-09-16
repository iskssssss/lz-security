package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:02
 */
public class CredentialsExpiredException extends SecurityException {

    public CredentialsExpiredException() {
        super("user.login.credentials.expired");
    }

    public CredentialsExpiredException(Object object) {
        super("user.login.credentials.expired", object);
    }
}
