package com.sowell.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 16:15
 */
public interface LogoutService {

    boolean logout(HttpServletRequest request, HttpServletResponse response);
}
