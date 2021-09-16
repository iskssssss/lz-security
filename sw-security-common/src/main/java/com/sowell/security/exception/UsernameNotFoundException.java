package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:01
 */
public class UsernameNotFoundException extends SecurityException {

    public UsernameNotFoundException() {
        super("user.login.not.exists");
    }

    public UsernameNotFoundException(Object object) {
        super("user.login.not.exists", object);
    }
}