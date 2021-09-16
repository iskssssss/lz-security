package com.sowell.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:56
 */
public abstract class AbstractAuthorizationHandler {


    public abstract boolean authorization(HttpServletRequest request, HttpServletResponse response);
}
