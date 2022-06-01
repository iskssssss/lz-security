package cn.lz.security.exception.auth;


import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 帐户未验证异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class AccountNotVerificationException extends SecurityException {

    public AccountNotVerificationException() {
        this(null);
    }

    public AccountNotVerificationException(Throwable cause) {
        this(null, cause);
    }

    public AccountNotVerificationException(Object responseData) {
        this(responseData, null);
    }

    private AccountNotVerificationException(Object responseData, Throwable cause) {
        super(AuthCode.ACCOUNT_NOT_VERIFICATION, responseData, cause);
    }
}
