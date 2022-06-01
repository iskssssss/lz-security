package cn.lz.security.exception.auth;


import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 错误凭据异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class BadCredentialsException extends SecurityException {

    public BadCredentialsException() {
        this(null);
    }

    public BadCredentialsException(Throwable cause) {
        this(null, cause);
    }

    public BadCredentialsException(Object responseData) {
        this(responseData, null);
    }

    private BadCredentialsException(Object responseData, Throwable cause) {
        super(AuthCode.PASSWORD_NOT_MATCHES, responseData, cause);
    }
}
