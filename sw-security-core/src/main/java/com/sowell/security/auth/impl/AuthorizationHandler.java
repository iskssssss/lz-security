package com.sowell.security.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.IcpManager;
import com.sowell.security.auth.AbstractAuthorizationHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.exception.BadCredentialsException;
import com.sowell.security.exception.ParamsException;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.utils.JsonUtil;
import com.sowell.security.utils.ServletUtil;
import com.sowell.security.utils.StringUtil;
import com.sowell.security.wrapper.HttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Version 版权 Copyright(c)2021 LZJ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:55
 */
public class AuthorizationHandler extends AbstractAuthorizationHandler {

    @Override
    public boolean authorization(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
        final FilterConfigurer.LoginHandlerInfo loginHandlerInfo = filterConfigurer.login();
        HttpServletRequestWrapper requestWrapper = ((HttpServletRequestWrapper) request);
        final byte[] bodyBytes = ServletUtil.getBodyBytes(requestWrapper);
        String bodyData = null;
        if (bodyBytes != null && bodyBytes.length > 1) {
            bodyData = StringUtil.byte2String(bodyBytes);
        }
        if (StringUtil.isEmpty(bodyData)) {
            return false;
        }
        final JSONObject loginData = JsonUtil.parseObject(bodyData);
        Object usernameValue = null;
        try {
            usernameValue = loginData.get(loginHandlerInfo.getUsername());
            final Object passwordValue = loginData.get(loginHandlerInfo.getPassword());
            final Object uuidValue = loginData.get(loginHandlerInfo.getUuid());
            final Object codeValue = loginData.get(loginHandlerInfo.getCode());
            if (StringUtil.isEmpty(usernameValue) || StringUtil.isEmpty(passwordValue) ||
                    StringUtil.isEmpty(uuidValue) || StringUtil.isEmpty(codeValue)) {
                throw new ParamsException("参数缺失。");
            }
            // 验证 验证码
            loginHandlerInfo.getAbstractCaptchaHandler().handler((String) uuidValue, codeValue);
            // 获取用户信息
            final AuthDetails<?> authDetails = loginHandlerInfo.getUserDetailsService().readUserByUsername((String) usernameValue);
            // 验证用户密码
            final PasswordEncoder passwordEncoder = loginHandlerInfo.getPasswordEncoder();
            final String password = authDetails.getPassword();
            if (!passwordEncoder.matches((String) passwordValue, password)) {
                throw new BadCredentialsException(authDetails.getUsername());
            }
            // 验证账号信息
            loginHandlerInfo.getAccessStatusHandler().verification(authDetails);
            // 验证成功
            loginHandlerInfo.getLoginSuccessHandler().success(request, response, authDetails);
            return true;
        } catch (SecurityException securityException) {
            // 验证失败
            if (StringUtil.isEmpty(securityException.getResponseData())) {
                securityException.setResponseData(usernameValue);
            }
            loginHandlerInfo.getLoginErrorHandler().error(request, response, securityException);
            return false;
        }
    }
}


























