package com.sowell.security.config;

import com.sowell.security.auth.*;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.IcpManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.defaults.DefaultCheckAccessAuthStatusHandler;
import com.sowell.security.defaults.DefaultPasswordEncoder;
import com.sowell.security.filter.IcpFilterAuthStrategy;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.service.UserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/10 21:55
 */
public final class FilterConfigurer {

    private final FilterUrl filterUrl;
    private final LoginHandlerInfo loginHandlerInfo;
    private final LogoutHandlerInfo logoutHandlerInfo;
    private IcpFilterAuthStrategy filterAfterHandler;
    private IcpFilterAuthStrategy filterBeforeHandler;

    public FilterConfigurer() {
        this.filterUrl = new FilterUrl();
        this.loginHandlerInfo = new LoginHandlerInfo();
        this.logoutHandlerInfo = new LogoutHandlerInfo();
        this.filterBeforeHandler = params -> { };
        this.filterAfterHandler = params -> { };
    }

    /**
     * 获取接口过滤设置器
     *
     * @return 接口过滤设置器
     */
    public FilterUrl filterUrl() {
        return filterUrl;
    }

    /**
     * 获取登录处理信息设置器
     *
     * @return 登录处理信息设置器
     */
    public LoginHandlerInfo login() {
        return loginHandlerInfo;
    }

    /**
     * 获取登出处理信息设置器
     *
     * @return 登出处理信息设置器
     */
    public LogoutHandlerInfo logout() {
        return logoutHandlerInfo;
    }

    public FilterConfigurer filterBeforeHandler(IcpFilterAuthStrategy filterBeforeHandler) {
        this.filterBeforeHandler = filterBeforeHandler;
        return this;
    }
    public FilterConfigurer filterAfterHandler(IcpFilterAuthStrategy filterAfterHandler) {
        this.filterAfterHandler = filterAfterHandler;
        return this;
    }
    public FilterConfigurer linkInterfacesFilter(AbstractInterfacesFilter... abstractInterfacesFilterList) {
        IcpManager.linkInterfacesFilter(abstractInterfacesFilterList);
        return this;
    }
    public FilterConfigurer filterLogHandler(BaseFilterLogHandler baseFilterLogHandler) {
        IcpManager.setFilterLogHandler(baseFilterLogHandler);
        return this;
    }
    public FilterConfigurer filterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
        IcpManager.setFilterErrorHandler(filterErrorHandler);
        return this;
    }
    public FilterConfigurer cacheManager(BaseCacheManager cacheManager) {
        IcpManager.setCacheManager(cacheManager);
        return this;
    }

    public FilterConfigurer and() {
        return FilterConfigurer.this;
    }

    public IcpFilterAuthStrategy getFilterBeforeHandler() {
        return filterBeforeHandler;
    }
    public IcpFilterAuthStrategy getFilterAfterHandler() {
        return filterAfterHandler;
    }

    public class FilterUrl {

        public String loginUrl = "/api/login/login.do";
        public String logoutUrl = "/api/logout/logout.do";

        /**
         * 排除URL
         */
        public final List<String> excludeUrls = new ArrayList<>();
        /**
         * 需认证URL
         */
        public final List<String> authorizationUrls = new ArrayList<>();
        /**
         * 只可在匿名状态下访问
         */
        public final List<String> anonymousUrls = new ArrayList<>();

        /**
         * 设置开放接口
         *
         * @param excludeUrls 开放接口列表
         */
        public FilterUrl excludeUrls(String... excludeUrls) {
            this.excludeUrls.clear();
            if (excludeUrls == null) {
                return this;
            }
            this.excludeUrls.addAll(Arrays.asList(excludeUrls));
            return this;
        }
        /**
         * 设置需在认证状态下可访问的接口
         *
         * @param authorizationUrls 认证接口列表
         */
        public FilterUrl authorizationUrls(String... authorizationUrls) {
            this.authorizationUrls.clear();
            if (authorizationUrls == null) {
                return this;
            }
            this.authorizationUrls.addAll(Arrays.asList(authorizationUrls));
            return this;
        }
        /**
         * 设置需在匿名状态下可访问的接口
         *
         * @param anonymousUrls 匿名接口列表
         */
        public FilterUrl anonymousUrls(String... anonymousUrls) {
            this.anonymousUrls.clear();
            if (anonymousUrls == null) {
                return this;
            }
            this.anonymousUrls.addAll(Arrays.asList(anonymousUrls));
            return this;
        }
        /**
         * 设置登录地址
         *
         * @param loginUrl 登录地址
         */
        public FilterUrl loginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
            return this;
        }
        /**
         * 设置登出地址
         *
         * @param logoutUrl 登出地址
         */
        public FilterUrl logoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
            return this;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }
    public class LoginHandlerInfo {

        /**
         * 密码验证
         */
        private PasswordEncoder passwordEncoder;
        /**
         * 用户获取服务类
         */
        private UserDetailsService userDetailsService;
        /**
         * 验证码处理器
         */
        private AbstractCaptchaHandler captchaHandler;
        /**
         * 账号状态验证
         */
        private AccessStatusHandler accessStatusHandler;
        /**
         * 登录成功处理器
         */
        private LoginSuccessHandler loginSuccessHandler;
        /**
         * 登录错误处理器
         */
        private LoginErrorHandler loginErrorHandler;
        /**
         * 校验认证状态处理器
         */
        private ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;

        private String identifierName = "identifier";
        private String credentialName = "credential";
        private String code = "code";
        private String uuid = "uuid";
        private String rememberMe = "rememberMe";

        public LoginHandlerInfo userDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo captchaHandler(AbstractCaptchaHandler captchaHandler) {
            this.captchaHandler = captchaHandler;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo passwordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo accessStatusHandler(AccessStatusHandler accessStatusHandler) {
            this.accessStatusHandler = accessStatusHandler;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo loginErrorHandler(LoginErrorHandler loginErrorHandler) {
            this.loginErrorHandler = loginErrorHandler;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo loginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
            this.loginSuccessHandler = loginSuccessHandler;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo username(String username) {
            this.identifierName = username;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo password(String password) {
            this.credentialName = password;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo code(String code) {
            this.code = code;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo uuid(String uuid) {
            this.uuid = uuid;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo rememberMe(String rememberMe) {
            this.rememberMe = rememberMe;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo checkAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
            this.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
            return LoginHandlerInfo.this;
        }

        public PasswordEncoder getPasswordEncoder() {
            if (passwordEncoder == null){
                passwordEncoder = new DefaultPasswordEncoder();
            }
            return passwordEncoder;
        }
        public UserDetailsService getUserDetailsService() {
            return userDetailsService;
        }
        public AbstractCaptchaHandler getCaptchaHandler() {
            return captchaHandler;
        }
        public AccessStatusHandler getAccessStatusHandler() {
            return accessStatusHandler;
        }
        public LoginSuccessHandler getLoginSuccessHandler() {
            return loginSuccessHandler;
        }
        public LoginErrorHandler getLoginErrorHandler() {
            return loginErrorHandler;
        }
        public ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
            if (checkAccessAuthStatusHandler == null){
                checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
            }
            return checkAccessAuthStatusHandler;
        }
        public String getIdentifierName() {
            return identifierName;
        }
        public String getCredentialName() {
            return credentialName;
        }
        public String getCode() {
            return code;
        }
        public String getUuid() {
            return uuid;
        }
        public String getRememberMe() {
            return rememberMe;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }

    public class LogoutHandlerInfo {
        private LogoutService logoutService;

        public LogoutHandlerInfo logoutService(LogoutService logoutService) {
            this.logoutService = logoutService;
            return this;
        }

        public LogoutService getLogoutService() {
            return logoutService;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }
}