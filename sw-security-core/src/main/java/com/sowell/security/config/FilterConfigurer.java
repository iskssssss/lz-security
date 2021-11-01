package com.sowell.security.config;

import com.sowell.security.arrays.UrlHashSet;
//import com.sowell.security.auth.*;
//import com.sowell.security.defaults.DefaultCheckAccessAuthStatusHandler;
//import com.sowell.security.defaults.DefaultLoginSuccessHandler;
//import com.sowell.security.defaults.DefaultPasswordEncoder;
//import com.sowell.security.defaults.DefaultLoginErrorHandler;
import com.sowell.security.config.builder.FilterConfigurerBuilder;
import com.sowell.security.filter.IcpFilterAuthStrategy;
//import com.sowell.security.service.PasswordEncoder;
//import com.sowell.security.service.UserDetailsService;


/**
 * 过滤配置文件
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/10 21:55
 */
public final class FilterConfigurer extends FilterConfigurerBuilder<FilterConfigurer> {

    /**
     * 获取过滤前处理方法
     *
     * @return 过滤前处理方法
     */
    public IcpFilterAuthStrategy getFilterBeforeHandler() {
        return super.filterBeforeHandler;
    }
    /**
     * 获取过滤后处理方法
     *
     * @return 过滤后处理方法
     */
    public IcpFilterAuthStrategy getFilterAfterHandler() {
        return super.filterAfterHandler;
    }

    /**
     * 获取拦截的接口列表
     *
     * @return 拦截的接口列表
     */
    public UrlHashSet getIncludeUrls() {
        return super.getFilterUrlIncludeUrls();
    }
    /**
     * 获取排除的接口列表
     *
     * @return 排除的接口列表
     */
    public UrlHashSet getExcludeUrls() {
        return super.getFilterUrlExcludeUrls();
    }

    //    public String getLoginUrl() {
    //        return this.filterUrl.loginUrl;
    //    }
    //    public String getLogoutUrl() {
    //        return this.filterUrl.logoutUrl;
    //    }
    //    public UrlHashSet getAuthorizationUrls() {
    //        return this.filterUrl.authorizationUrlSet;
    //    }
    //    public UrlHashSet getAnonymousUrls() {
    //        return this.filterUrl.anonymousUrlSet;
    //    }
    //    public PasswordEncoder getPasswordEncoder() {
    //        if (this.loginHandlerInfo.passwordEncoder == null) {
    //            this.loginHandlerInfo.passwordEncoder = new DefaultPasswordEncoder();
    //        }
    //        return this.loginHandlerInfo.passwordEncoder;
    //    }
    //    public UserDetailsService getUserDetailsService() {
    //        return this.loginHandlerInfo.userDetailsService;
    //    }
    //    public CaptchaHandler getCaptchaHandler() {
    //        return this.loginHandlerInfo.captchaHandler;
    //    }
    //    public AccessStatusHandler getAccessStatusHandler() {
    //        return this.loginHandlerInfo.accessStatusHandler;
    //    }
    //    public LoginSuccessHandler getLoginSuccessHandler() {
    //        if (this.loginHandlerInfo.loginSuccessHandler == null) {
    //            this.loginHandlerInfo.loginSuccessHandler = new DefaultLoginSuccessHandler();
    //        }
    //        return this.loginHandlerInfo.loginSuccessHandler;
    //    }
    //    public LoginErrorHandler getLoginErrorHandler() {
    //        if (this.loginHandlerInfo.loginErrorHandler == null) {
    //            this.loginHandlerInfo.loginErrorHandler = new DefaultLoginErrorHandler();
    //        }
    //        return this.loginHandlerInfo.loginErrorHandler;
    //    }
    //    public ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
    //        if (this.loginHandlerInfo.checkAccessAuthStatusHandler == null) {
    //            this.loginHandlerInfo.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
    //        }
    //        return this.loginHandlerInfo.checkAccessAuthStatusHandler;
    //    }
    //    public String getIdentifierKey() {
    //        return this.loginHandlerInfo.identifierKey;
    //    }
    //    public String getCredentialKey() {
    //        return this.loginHandlerInfo.credentialKey;
    //    }
    //    public String getCodeKey() {
    //        return this.loginHandlerInfo.codeKey;
    //    }
    //    public String getRememberMeKey() {
    //        return this.loginHandlerInfo.rememberMeKey;
    //    }
    //
    //    public LogoutService getLogoutService() {
    //        return this.logoutHandlerInfo.logoutService;
    //    }
}