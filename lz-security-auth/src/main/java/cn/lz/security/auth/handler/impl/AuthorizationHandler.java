package cn.lz.security.auth.handler.impl;

import cn.lz.security.auth.config.AuthConfigurer;
import cn.lz.security.auth.login.AuthErrorHandler;
import cn.lz.security.auth.login.AuthSuccessHandler;
import cn.lz.security.auth.service.PasswordEncoder;
import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.handler.AbstractAuthorizationHandler;
import cn.lz.security.auth.handler.AccessStatusHandler;
import cn.lz.security.auth.handler.CaptchaHandler;
import cn.lz.security.auth.service.UserDetailsService;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.auth.BadCredentialsException;
import cn.lz.security.exception.ParamsException;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.json.JsonUtil;
import cn.lz.tool.jwt.model.AuthDetails;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
