package com.sowell.security.exception;


/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
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
