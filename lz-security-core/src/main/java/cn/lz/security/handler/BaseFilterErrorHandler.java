package cn.lz.security.handler;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * 过滤错误处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
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
