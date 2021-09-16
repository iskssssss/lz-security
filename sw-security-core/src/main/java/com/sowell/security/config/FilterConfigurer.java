package com.sowell.security.config;

import com.sowell.security.auth.*;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.IcpManager;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.filter.IcpFilterAuthStrategy;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.service.PasswordEncoder;
import com.sowell.security.service.UserDetailsService;
import lombok.Getter;

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

    /**
     * 接口过滤设置器
     */
    private final FilterUrl filterUrl;

    private IcpFilterAuthStrategy filterBeforeHandler;

    private IcpFilterAuthStrategy filterAfterHandler;
    /**
     * 登录处理信息
     */
    private final LoginHandlerInfo loginHandlerInfo;
    /**
     * 登出处理信息
     */
    private final LogoutHandlerInfo logoutHandlerInfo;

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

    public FilterConfigurer setFilterBeforeHandler(IcpFilterAuthStrategy filterBeforeHandler) {
        this.filterBeforeHandler = filterBeforeHandler;
        return this;
    }

    public FilterConfigurer setFilterAfterHandler(IcpFilterAuthStrategy filterAfterHandler) {
        this.filterAfterHandler = filterAfterHandler;
        return this;
    }

    public FilterConfigurer linkFilter(AbstractInterfacesFilter... abstractInterfacesFilterList) {
        IcpManager.linkFilter(abstractInterfacesFilterList);
        return this;
    }
    public FilterConfigurer setFilterLogHandler(BaseFilterLogHandler baseFilterLogHandler) {
        IcpManager.setFilterLogHandler(baseFilterLogHandler);
        return this;
    }
    public FilterConfigurer setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
        IcpManager.setFilterErrorHandler(filterErrorHandler);
        return this;
    }

    public FilterConfigurer setCacheManager(BaseCacheManager cacheManager) {
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
        public FilterUrl setExcludeUrls(String... excludeUrls) {
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
        public FilterUrl setAuthorizationUrls(String... authorizationUrls) {
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
        public FilterUrl setAnonymousUrls(String... anonymousUrls) {
            this.anonymousUrls.clear();
            if (anonymousUrls == null) {
                return this;
            }
            this.anonymousUrls.addAll(Arrays.asList(anonymousUrls));
            return this;
        }

        public FilterUrl setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
            return this;
        }

        public FilterUrl setLogoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
            return this;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }


    @Getter
    public class LoginHandlerInfo {

        /**
         * 密码验证
         */
        private PasswordEncoder passwordEncoder;
        /**
         * 用户获取
         */
        private UserDetailsService userDetailsService;
        /**
         * 验证码 验证
         */
        private AbstractCaptchaHandler abstractCaptchaHandler;
        /**
         * 账号状态验证
         */
        private AccessStatusHandler accessStatusHandler;

        private LoginSuccessHandler loginSuccessHandler;

        private LoginErrorHandler loginErrorHandler;

        private String username = "identifier";
        private String password = "credential";
        private String code = "code";
        private String uuid = "uuid";
        private String rememberMe = "rememberMe";

        public LoginHandlerInfo setUserDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setAbstractCaptchaHandler(AbstractCaptchaHandler abstractCaptchaHandler) {
            this.abstractCaptchaHandler = abstractCaptchaHandler;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setPasswordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setAccessStatusHandler(AccessStatusHandler accessStatusHandler) {
            this.accessStatusHandler = accessStatusHandler;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setLoginErrorHandler(LoginErrorHandler loginErrorHandler) {
            this.loginErrorHandler = loginErrorHandler;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
            this.loginSuccessHandler = loginSuccessHandler;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setUsername(String username) {
            this.username = username;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setPassword(String password) {
            this.password = password;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setCode(String code) {
            this.code = code;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setUuid(String uuid) {
            this.uuid = uuid;
            return LoginHandlerInfo.this;
        }

        public LoginHandlerInfo setRememberMe(String rememberMe) {
            this.rememberMe = rememberMe;
            return LoginHandlerInfo.this;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }

    @Getter
    public class LogoutHandlerInfo {
        private LogoutService logoutService;

        public LogoutHandlerInfo setLogoutService(LogoutService logoutService) {
            this.logoutService = logoutService;
            return this;
        }

        public FilterConfigurer and() {
            return FilterConfigurer.this;
        }
    }
}
