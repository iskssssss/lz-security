package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 13:56
 */
public class ParamsException extends SecurityException {

    public ParamsException(String message) {
        super(message);
    }

    public ParamsException(String message, Object object) {
        super(message, object);
    }
}
