package com.sowell.security.auth.login;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;

/**
 * 认证失败处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/6/23 17:22
 */
public interface AuthErrorHandler {

    /**
     * 认证失败处理
     *
     * @param request           请求流
     * @param response          响应流
     * @param securityException 失败异常
     */
    void error(BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException);
}
