package com.sowell.security.config;

import com.sowell.security.auth.*;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.IcpManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.defaults.DefaultCheckAccessAuthStatusHandler;
import com.sowell.security.defaults.DefaultLoginSuccessHandler;
import com.sowell.security.defaults.DefaultPasswordEncoder;
import com.sowell.security.defaults.auth.DefaultLoginErrorHandler;
import com.sowell.security.filter.IcpFilterAuthStrategy;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.service.UserDetailsService;

import java.util.Arrays;
import java.util.LinkedList;
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

    public String getLoginUrl() {
        return this.filterUrl.loginUrl;
    }
    public String getLogoutUrl() {
        return this.filterUrl.logoutUrl;
    }
    public List<String> getExcludeUrls() {
        return this.filterUrl.excludeUrls;
    }
    public List<String> getAuthorizationUrls() {
        return this.filterUrl.authorizationUrls;
    }
    public List<String> getAnonymousUrls() {
        return this.filterUrl.anonymousUrls;
    }

    public PasswordEncoder getPasswordEncoder() {
        if (this.loginHandlerInfo.passwordEncoder == null) {
            this.loginHandlerInfo.passwordEncoder = new DefaultPasswordEncoder();
        }
        return this.loginHandlerInfo.passwordEncoder;
    }
    public UserDetailsService getUserDetailsService() {
        return this.loginHandlerInfo.userDetailsService;
    }
    public CaptchaHandler getCaptchaHandler() {
        return this.loginHandlerInfo.captchaHandler;
    }
    public AccessStatusHandler getAccessStatusHandler() {
        return this.loginHandlerInfo.accessStatusHandler;
    }
    public LoginSuccessHandler getLoginSuccessHandler() {
        if (this.loginHandlerInfo.loginSuccessHandler == null) {
            this.loginHandlerInfo.loginSuccessHandler = new DefaultLoginSuccessHandler();
        }
        return this.loginHandlerInfo.loginSuccessHandler;
    }
    public LoginErrorHandler getLoginErrorHandler() {
        if (this.loginHandlerInfo.loginErrorHandler == null) {
            this.loginHandlerInfo.loginErrorHandler = new DefaultLoginErrorHandler();
        }
        return this.loginHandlerInfo.loginErrorHandler;
    }
    public ICheckAccessAuthStatusHandler getCheckAccessAuthStatusHandler() {
        if (this.loginHandlerInfo.checkAccessAuthStatusHandler == null) {
            this.loginHandlerInfo.checkAccessAuthStatusHandler = new DefaultCheckAccessAuthStatusHandler();
        }
        return this.loginHandlerInfo.checkAccessAuthStatusHandler;
    }
    public String getIdentifierKey() {
        return this.loginHandlerInfo.identifierKey;
    }
    public String getCredentialKey() {
        return this.loginHandlerInfo.credentialKey;
    }
    public String getCodeKey() {
        return this.loginHandlerInfo.codeKey;
    }
    public String getRememberMeKey() {
        return this.loginHandlerInfo.rememberMeKey;
    }

    public LogoutService getLogoutService() {
        return this.logoutHandlerInfo.logoutService;
    }

    public class FilterUrl {
        private String loginUrl = "/api/login/login.do";
        private String logoutUrl = "/api/logout/logout.do";

        /**
         * 排除URL
         */
        private final List<String> excludeUrls = new LinkedList<>();
        /**
         * 需认证URL
         */
        private final List<String> authorizationUrls = new LinkedList<>();
        /**
         * 只可在匿名状态下访问
         */
        private final List<String> anonymousUrls = new LinkedList<>();

        /**
         * 设置开放接口
         *
         * @param excludeUrls 开放接口列表
         */
        public FilterUrl addExcludeUrls(String... excludeUrls) {
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
        public FilterUrl addAuthorizationUrls(String... authorizationUrls) {
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
        public FilterUrl addAnonymousUrls(String... anonymousUrls) {
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
        private CaptchaHandler captchaHandler;
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

        private String identifierKey = "identifier";
        private String credentialKey = "credential";
        private String codeKey = "code";
        private String rememberMeKey = "rememberMe";

        public LoginHandlerInfo userDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo captchaHandler(CaptchaHandler captchaHandler) {
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
        public LoginHandlerInfo identifierKey(String identifierKey) {
            this.identifierKey = identifierKey;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo credentialKey(String credentialKey) {
            this.credentialKey = credentialKey;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo codeKey(String codeKey) {
            this.codeKey = codeKey;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo rememberMeKey(String rememberMeKey) {
            this.rememberMeKey = rememberMeKey;
            return LoginHandlerInfo.this;
        }
        public LoginHandlerInfo checkAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
            this.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
            return LoginHandlerInfo.this;
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

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }
}