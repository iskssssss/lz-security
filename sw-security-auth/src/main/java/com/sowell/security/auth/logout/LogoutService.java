package com.sowell.security.auth.logout;

import com.sowell.security.filter.context.model.BaseRequest;
import com.sowell.security.filter.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 16:15
 */
public interface LogoutService {

    boolean logout(
            BaseRequest<?> request,
            BaseResponse<?> response
    );
}
