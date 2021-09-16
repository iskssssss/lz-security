package com.sowell.security.auth.impl;

import com.sowell.security.IcpManager;
import com.sowell.security.auth.AbstractLogoutHandler;
import com.sowell.security.auth.LogoutService;
import com.sowell.security.config.FilterConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
        final FilterConfigurer.LogoutHandlerInfo logoutHandlerInfo = filterConfigurer.logout();
        final LogoutService logoutService = logoutHandlerInfo.getLogoutService();
        return logoutService.logout(request, response);
    }
}
