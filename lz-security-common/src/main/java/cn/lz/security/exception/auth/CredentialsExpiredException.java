package cn.lz.security.exception.auth;


import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 凭据过期异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class CredentialsExpiredException extends SecurityException {

    public CredentialsExpiredException() {
        this(null);
    }

    public CredentialsExpiredException(Throwable cause) {
        this(null, cause);
    }

    public CredentialsExpiredException(Object responseData) {
        this(responseData, null);
    }

    private CredentialsExpiredException(Object responseData, Throwable cause) {
        super(AuthCode.CREDENTIALS_EXPIRED, responseData, cause);
    }
}
