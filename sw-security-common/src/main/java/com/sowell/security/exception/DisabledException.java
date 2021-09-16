package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:02
 */
public class DisabledException extends SecurityException {

    public DisabledException() {
        super("user.login.account.disable");
    }

    public DisabledException(Object object) {
        super("user.login.account.disable", object);
    }
}