package cn.lz.security.exception;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.RCode;

/**
 * 参数错误异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
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
        super(RCode.ERROR_PARAMS, responseData, cause);
    }
}
