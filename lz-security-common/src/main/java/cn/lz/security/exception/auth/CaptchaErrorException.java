package cn.lz.security.exception.auth;


import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.AuthCode;

/**
 * 验证码错误异常
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 17:06
 */
public class CaptchaErrorException extends SecurityException {

    public CaptchaErrorException() {
        this(null);
    }

    public CaptchaErrorException(Throwable cause) {
        this(null, cause);
    }

    public CaptchaErrorException(Object responseData) {
        this(responseData, null);
    }

    private CaptchaErrorException(Object responseData, Throwable cause) {
        super(AuthCode.VERIFY_CODE_ERROR, responseData, cause);
    }
}
