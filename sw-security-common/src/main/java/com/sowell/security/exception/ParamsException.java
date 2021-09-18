package com.sowell.security.exception;


import com.sowell.security.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 13:56
 */
public class ParamsException extends SecurityException {

    public ParamsException() {
        this(null);
    }

    public ParamsException(Throwable cause) {
        this(null, cause);
    }

    public ParamsException(Object responseData) {
        this(responseData, null);
    }

    private ParamsException(Object responseData, Throwable cause) {
        super(RCode.ERROR_PARAMS.getCode(), RCode.ERROR_PARAMS.getMessage(), responseData, cause);
    }
}
