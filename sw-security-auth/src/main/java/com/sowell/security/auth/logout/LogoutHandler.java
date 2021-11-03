package com.sowell.security.auth.logout;

import com.sowell.security.auth.IcpAuth;
import com.sowell.security.filter.context.model.BaseRequest;
import com.sowell.security.filter.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 15:54
 */
public class LogoutHandler extends AbstractLogoutHandler {

    @Override
    public boolean logout(
            BaseRequest<?> request,
            BaseResponse<?> response
    ) {
        final LogoutService logoutService = IcpAuth.getLogoutService();
        return logoutService.logout(request, response);
    }
}
