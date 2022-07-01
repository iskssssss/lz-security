package cn.lz.security.auth.defaults;

import cn.lz.security.auth.LzAuthManager;
import cn.lz.security.auth.login.*;
import cn.lz.security.auth.service.CredentialEncoder;
import cn.lz.security.auth.service.UserDetailsService;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.security.exception.ParamsException;
import cn.lz.security.exception.auth.BadCredentialsException;
import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.json.JsonUtil;
import cn.lz.tool.jwt.model.AuthDetails;

import java.util.Map;

/**
 * 登录服务类 默认实现类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/6/30 17:52
 */
public class LoginServiceDefault implements LoginService {

    @Override
    public boolean login(BaseRequest<?> swRequest, BaseResponse<?> swResponse) {
        final byte[] bodyBytes = swRequest.getBodyBytes();
        String bodyData = null;
        if (bodyBytes != null && bodyBytes.length > 1) {
            bodyData = StringUtil.byte2String(bodyBytes);
        }
        if (StringUtil.isEmpty(bodyData)) {
            throw new ParamsException();
        }
        final Map loginData = JsonUtil.parseObject(bodyData, Map.class);
        Object identifierValue = null;
        try {
            identifierValue = loginData.get(LzAuthManager.getAuthConfigurer().getIdentifierKey());
            final Object credentialValue = loginData.get(LzAuthManager.getAuthConfigurer().getCredentialKey());
            if (StringUtil.isEmpty(identifierValue) || StringUtil.isEmpty(credentialValue)) {
                throw new ParamsException();
            }
            // 验证验证码
            final CaptchaHandler captchaHandler = LzAuthManager.getCaptchaHandler();
            if (captchaHandler != null) {
                final Object codeValue = loginData.get(LzAuthManager.getAuthConfigurer().getCodeKey());
                if (StringUtil.isEmpty(codeValue)) {
                    throw new ParamsException();
                }
                captchaHandler.handler(codeValue);
            }
            // 获取用户信息
            final UserDetailsService userDetailsService = LzAuthManager.getUserDetailsService();
            final AuthDetails<?> authDetails = userDetailsService.readUserByIdentifier((String) identifierValue);
            // 验证用户密码
            final CredentialEncoder credentialEncoder = LzAuthManager.getCredentialEncoder();
            if (!credentialEncoder.matches((String) credentialValue, authDetails.getCredential())) {
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
                securityException.setResponseData(identifierValue);
            }
            final AuthErrorHandler authErrorHandler = LzAuthManager.getLoginErrorHandler();
            authErrorHandler.error(swRequest, swResponse, securityException);
            return false;
        }
    }
}
