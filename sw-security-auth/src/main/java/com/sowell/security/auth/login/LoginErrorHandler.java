package com.sowell.security.auth.login;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.SecurityException;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:22
 */
public interface LoginErrorHandler {

    void error(
            BaseRequest<?> request,
            BaseResponse<?> response,
            SecurityException securityException
    );
}