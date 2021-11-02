package com.sowell.security.auth.login;

import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.tool.jwt.model.AuthDetails;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:21
 */
public interface LoginSuccessHandler {

    void success(
            BaseRequest<?> request,
            BaseResponse<?> response,
            AuthDetails<?> authDetails
    );
}