package com.sowell.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:56
 */
public abstract class AbstractAuthorizationHandler {


    public abstract boolean authorization(HttpServletRequest request, HttpServletResponse response);
}
