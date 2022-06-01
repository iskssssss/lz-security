package com.sowell.security.auth.handler.impl;

import com.sowell.security.auth.LzAuthManager;
import com.sowell.security.auth.config.AuthConfigurer;
import com.sowell.security.auth.handler.AbstractAuthorizationHandler;
import com.sowell.security.auth.handler.AccessStatusHandler;
import com.sowell.security.auth.handler.CaptchaHandler;
import com.sowell.security.auth.login.AuthErrorHandler;
import com.sowell.security.auth.login.AuthSuccessHandler;
import com.sowell.security.auth.service.PasswordEncoder;
import com.sowell.security.auth.service.UserDetailsService;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.auth.BadCredentialsException;
import com.sowell.security.exception.ParamsException;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.json.JsonUtil;
import com.sowell.tool.jwt.model.AuthDetails;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 8:55
 */
public class AuthorizationHandler extends AbstractAuthorizationHandler {

    private final String identifierName;
    private final String credentialName;
    private final String codeKey;

    public AuthorizationHandler() {
        AuthConfigurer authConfigurer = LzAuthManager.getAuthConfigurer();
        identifierName = authConfigurer.getIdentifierKey();
        credentialName = authConfigurer.getCredentialKey();
        codeKey = authConfigurer.getCodeKey();
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
        final Map loginData = JsonUtil.parseObject(bodyData, Map.class);
        Object usernameValue = null;
        try {
            usernameValue = loginData.get(identifierName);
            final Object passwordValue = loginData.get(credentialName);
            if (StringUtil.isEmpty(usernameValue) || StringUtil.isEmpty(passwordValue)) {
                throw new ParamsException();
            }
            // 验证验证码
            final CaptchaHandler captchaHandler = LzAuthManager.getCaptchaHandler();
            if (captchaHandler != null) {
                final Object codeValue = loginData.get(codeKey);
                if (StringUtil.isEmpty(codeValue)) {
                    throw new ParamsException();
                }
                captchaHandler.handler(codeValue);
            }
            // 获取用户信息
            final UserDetailsService userDetailsService = LzAuthManager.getUserDetailsService();
            final AuthDetails<?> authDetails = userDetailsService.readUserByUsername((String) usernameValue);
            // 验证用户密码
            final String password = authDetails.getCredential();
            final PasswordEncoder passwordEncoder = LzAuthManager.getPasswordEncoder();
            if (!passwordEncoder.matches((String) passwordValue, password)) {
                throw new BadCredentialsException(authDetails.getIdentifier());
            }
            // 验证账号信息
            final AccessStatusHandler accessStatusHandler = LzAuthManager.getAccessStatusHandler();
            accessStatusHandler.verification(authDetails);
            // 验证成功
            final AuthSuccessHandler authSuccessHandler = LzAuthManager.getLoginSuccessHandler();
            authSuccessHandler.success(swRequest, swResponse, authDetails);
            return true;
        } catch (SecurityException securityException) {
            // 验证失败
            if (StringUtil.isEmpty(securityException.getResponseData())) {
                securityException.setResponseData(usernameValue);
            }
            final AuthErrorHandler authErrorHandler = LzAuthManager.getLoginErrorHandler();
            authErrorHandler.error(swRequest, swResponse, securityException);
            return false;
        }
    }
}
