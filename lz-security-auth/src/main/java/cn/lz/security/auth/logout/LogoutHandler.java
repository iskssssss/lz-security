package cn.lz.security.auth.logout;

import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 15:54
 */
public class LogoutHandler extends AbstractLogoutHandler {

    @Override
    public boolean logout(BaseRequest<?> request, BaseResponse<?> response) {
        final LogoutService logoutService = LzAuthManager.getLogoutService();
        return logoutService.logout(request, response);
    }
}
