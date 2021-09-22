package com.sowell.security.log;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/12 10:39
 */
public interface BaseFilterLogHandler {

    /**
     * 过滤前日志记录
     * @param request 请求信息
     * @param response 响应信息
     * @return 返回对象 用于{@link BaseFilterLogHandler#afterHandler(BaseRequest, BaseResponse, Object, Exception)}方法使用
     */
    Object beforeHandler(
            BaseRequest<?> request,
            BaseResponse<?> response
    );

    /**
     * 过滤后日志记录
     * @param request 请求信息
     * @param response 响应信息
     * @param logEntity 由{@link BaseFilterLogHandler#beforeHandler(BaseRequest, BaseResponse)}方法提供
     */
    void afterHandler(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Object logEntity,
            Exception ex
    );
}
