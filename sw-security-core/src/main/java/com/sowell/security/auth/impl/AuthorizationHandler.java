package com.sowell.security.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.IcpManager;
import com.sowell.security.auth.AbstractAuthorizationHandler;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.BadCredentialsException;
import com.sowell.security.exception.ParamsException;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.utils.JsonUtil;
import com.sowell.security.utils.StringUtil;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:55
 */
public class AuthorizationHandler extends AbstractAuthorizationHandler {

    @Override
    public boolean authorization(
            BaseRequest<?> swRequest,
            BaseResponse<?> swResponse
    ) {
        final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
        final FilterConfigurer.LoginHandlerInfo loginHandlerInfo = filterConfigurer.login();
        final byte[] bodyBytes = swRequest.getBodyBytes();
        String bodyData = null;
        if (bodyBytes != null && bodyBytes.length > 1) {
            bodyData = StringUtil.byte2String(bodyBytes);
        }
        if (StringUtil.isEmpty(bodyData)) {
            throw new ParamsException();
        }
        final JSONObject loginData = JsonUtil.parseObject(bodyData);
        Object usernameValue = null;
        try {
            usernameValue = loginData.get(loginHandlerInfo.getIdentifierName());
            final Object passwordValue = loginData.get(loginHandlerInfo.getCredentialName());
            final Object uuidValue = loginData.get(loginHandlerInfo.getUuid());
            final Object codeValue = loginData.get(loginHandlerInfo.getCode());
            if (StringUtil.isEmpty(usernameValue) || StringUtil.isEmpty(passwordValue) ||
                    StringUtil.isEmpty(uuidValue) || StringUtil.isEmpty(codeValue)) {
                throw new ParamsException();
            }
            // 验证 验证码
            loginHandlerInfo.getCaptchaHandler().handler((String) uuidValue, codeValue);
            // 获取用户信息
            final AuthDetails<?> authDetails = loginHandlerInfo.getUserDetailsService()
                    .readUserByUsername((String) usernameValue);
            // 验证用户密码
            final PasswordEncoder passwordEncoder = loginHandlerInfo.getPasswordEncoder();
            final String password = authDetails.getPassword();
            if (!passwordEncoder.matches((String) passwordValue, password)) {
                throw new BadCredentialsException(authDetails.getUsername());
            }
            // 验证账号信息
            loginHandlerInfo.getAccessStatusHandler().verification(authDetails);
            // 验证成功
            loginHandlerInfo.getLoginSuccessHandler().success(swRequest, swResponse, authDetails);
            return true;
        } catch (SecurityException securityException) {
            // 验证失败
            if (StringUtil.isEmpty(securityException.getResponseData())) {
                securityException.setResponseData(usernameValue);
            }
            loginHandlerInfo.getLoginErrorHandler().error(swRequest, swResponse, securityException);
            return false;
        }
    }
}


























