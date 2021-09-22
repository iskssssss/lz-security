package com.sowell.security.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.sowell.security.IcpManager;
import com.sowell.security.auth.*;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.BadCredentialsException;
import com.sowell.security.exception.ParamsException;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.model.AuthDetails;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.service.UserDetailsService;
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

    private final FilterConfigurer filterConfigurer;
    private final String identifierName;
    private final String credentialName;
    private final String codeKey;

    public AuthorizationHandler() {
        filterConfigurer = IcpManager.getFilterConfigurer();
        identifierName = filterConfigurer.getIdentifierKey();
        credentialName = filterConfigurer.getCredentialKey();
        codeKey = filterConfigurer.getCodeKey();
    }

    @Override
    public boolean authorization(BaseRequest<?> swRequest, BaseResponse<?> swResponse) {
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
            usernameValue = loginData.get(identifierName);
            final Object passwordValue = loginData.get(credentialName);
            if (StringUtil.isEmpty(usernameValue) || StringUtil.isEmpty(passwordValue)) {
                throw new ParamsException();
            }
            // 验证验证码
            final CaptchaHandler captchaHandler = filterConfigurer.getCaptchaHandler();
            if (captchaHandler != null) {
                final Object codeValue = loginData.get(codeKey);
                if (StringUtil.isEmpty(codeValue)) {
                    throw new ParamsException();
                }
                captchaHandler.handler(codeValue);
            }
            // 获取用户信息
            final UserDetailsService userDetailsService = filterConfigurer.getUserDetailsService();
            final AuthDetails<?> authDetails = userDetailsService.readUserByUsername((String) usernameValue);
            // 验证用户密码
            final String password = authDetails.getCredential();
            final PasswordEncoder passwordEncoder = filterConfigurer.getPasswordEncoder();
            if (!passwordEncoder.matches((String) passwordValue, password)) {
                throw new BadCredentialsException(authDetails.getIdentifier());
            }
            // 验证账号信息
            final AccessStatusHandler accessStatusHandler = filterConfigurer.getAccessStatusHandler();
            accessStatusHandler.verification(authDetails);
            // 验证成功
            final LoginSuccessHandler loginSuccessHandler = filterConfigurer.getLoginSuccessHandler();
            loginSuccessHandler.success(swRequest, swResponse, authDetails);
            return true;
        } catch (SecurityException securityException) {
            // 验证失败
            if (StringUtil.isEmpty(securityException.getResponseData())) {
                securityException.setResponseData(usernameValue);
            }
            final LoginErrorHandler loginErrorHandler = filterConfigurer.getLoginErrorHandler();
            loginErrorHandler.error(swRequest, swResponse, securityException);
            return false;
        }
    }
}


























