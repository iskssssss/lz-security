package com.sowell.security.auth;

import com.sowell.security.exception.SecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/23 17:22
 */
public interface LoginErrorHandler {

    void error(HttpServletRequest request, HttpServletResponse response, SecurityException securityException);
}