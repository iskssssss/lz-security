package com.sowell.security.base;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/14 9:10
 */
public interface BaseFilterErrorHandler<T> {

    T errorHandler(
            BaseRequest<?> request,
            BaseResponse<?> response,
            Exception error
    );
}
