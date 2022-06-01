package com.sowell.security.auth.login;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * 认证成功处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/6/23 17:21
 */
public interface AuthSuccessHandler {

    /**
     * 认证成功处理
     *
     * @param request     请求流
     * @param response    响应流
     * @param authDetails 认证信息
     */
    void success(BaseRequest<?> request, BaseResponse<?> response, AuthDetails<?> authDetails);
}
