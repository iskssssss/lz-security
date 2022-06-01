package cn.lz.security.exception.auth;


import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 帐户禁用异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class AccountDisabledException extends SecurityException {

    public AccountDisabledException() {
        this(null);
    }

    public AccountDisabledException(Throwable cause) {
        this(null, cause);
    }

    public AccountDisabledException(Object responseData) {
        this(responseData, null);
    }

    private AccountDisabledException(Object responseData, Throwable cause) {
        super(AuthCode.ACCOUNT_DISABLE, responseData, cause);
    }
}
