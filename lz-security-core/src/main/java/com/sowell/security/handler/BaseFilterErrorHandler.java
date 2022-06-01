package com.sowell.security.handler;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.base.SecurityException;

/**
 * 过滤错误处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/14 9:10
 */
public interface BaseFilterErrorHandler<T> {

    /**
     * 错误处理
     *
     * @param request           请求流
     * @param response          响应流
     * @param securityException 错误信息
     * @return 要打印至客户端的信息
     */
    T errorHandler(BaseRequest<?> request, BaseResponse<?> response, SecurityException securityException);
}
