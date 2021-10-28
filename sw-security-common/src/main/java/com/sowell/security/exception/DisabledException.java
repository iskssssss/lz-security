package com.sowell.security.exception;


import com.sowell.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 12:02
 */
public class DisabledException extends SecurityException {

    public DisabledException() {
        this(null);
    }

    public DisabledException(Throwable cause) {
        this(null, cause);
    }

    public DisabledException(Object responseData) {
        this(responseData, null);
    }

    private DisabledException(Object responseData, Throwable cause) {
        super(RCode.TOKEN_EXPIRE.getCode(), RCode.TOKEN_EXPIRE.getMessage(), responseData, cause);
    }
}