package com.sowell.security.config.builder;

import com.sowell.security.IcpManager;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.base.AbstractInterfacesFilter;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.filter.IcpFilterAuthStrategy;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.token.IAccessTokenHandler;

import java.util.Arrays;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 14:19
 */
public class FilterConfigurerBuilder<T extends FilterConfigurer> {

	protected final FilterUrl filterUrl = new FilterUrl();
	protected IcpFilterAuthStrategy filterAfterHandler = params -> { };
	protected IcpFilterAuthStrategy filterBeforeHandler = params -> { };

	/**
	 * 获取接口过滤设置器
	 *
	 * @return 接口过滤设置器
	 */
	public FilterUrl filterUrl() {
		return filterUrl;
	}

	/**
	 * 设置接口过滤执行链
	 * <p>注意：一定要按顺序放入过滤器</p>
	 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
	 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
	 *
	 * @param abstractInterfacesFilterList 过滤执行链
	 * @return this
	 */
	public FilterConfigurerBuilder<T> linkInterfacesFilter(AbstractInterfacesFilter... abstractInterfacesFilterList) {
		IcpManager.linkInterfacesFilter(abstractInterfacesFilterList);
		return this;
	}
	/**
	 * 设置过滤日志处理器
	 *
	 * @param baseFilterLogHandler 过滤日志处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> filterLogHandler(BaseFilterLogHandler baseFilterLogHandler) {
		IcpManager.setFilterLogHandler(baseFilterLogHandler);
		return this;
	}
	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> filterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		IcpManager.setFilterErrorHandler(filterErrorHandler);
		return this;
	}
	/**
	 * 设置缓存管理器
	 *
	 * @param cacheManager 缓存管理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> cacheManager(BaseCacheManager cacheManager) {
		IcpManager.setCacheManager(cacheManager);
		return this;
	}
	/**
	 * 设置accessToken处理器
	 *
	 * @param accessTokenHandler accessToken处理器
	 */
	public FilterConfigurerBuilder<T> accessTokenHandler(IAccessTokenHandler accessTokenHandler) {
		IcpManager.setAccessTokenHandler(accessTokenHandler);
		return this;
	}
	/**
	 * 设置请求加解密处理器
	 *
	 * @param requestDataEncryptHandler 请求加解密处理器
	 */
	public FilterConfigurerBuilder<T> requestDataEncryptHandler(RequestDataEncryptHandler requestDataEncryptHandler) {
		IcpManager.setRequestDataEncryptHandler(requestDataEncryptHandler);
		return this;
	}
	/**
	 * 过滤前处理
	 *
	 * @param filterBeforeHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> filterBeforeHandler(IcpFilterAuthStrategy filterBeforeHandler) {
		this.filterBeforeHandler = filterBeforeHandler;
		return this;
	}
	/**
	 * 过滤后处理
	 *
	 * @param filterAfterHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> filterAfterHandler(IcpFilterAuthStrategy filterAfterHandler) {
		this.filterAfterHandler = filterAfterHandler;
		return this;
	}

	public FilterConfigurerBuilder<T> and() {
		return FilterConfigurerBuilder.this;
	}
	public T end() {
		return ((T) FilterConfigurerBuilder.this);
	}

	public class FilterUrl {
		/**
		 * 拦截URL
		 */
		protected final UrlHashSet includeUrlSet = new UrlHashSet();
		/**
		 * 排除URL
		 */
		protected final UrlHashSet excludeUrlSet = new UrlHashSet();

		/**
		 * 设置拦截接口
		 *
		 * @param includeUrls 拦截接口列表
		 */
		public FilterUrl addIncludeUrls(String... includeUrls) {
			addUrlHashSet(this.includeUrlSet, includeUrls);
			return this;
		}
		/**
		 * 设置开放接口
		 *
		 * @param excludeUrls 开放接口列表
		 */
		public FilterUrl addExcludeUrls(String... excludeUrls) {
			addUrlHashSet(this.excludeUrlSet, excludeUrls);
			return this;
		}

		public FilterConfigurerBuilder<T> and() {
			return FilterConfigurerBuilder.this;
		}
	}
	protected UrlHashSet getFilterUrlIncludeUrls() {
		return this.filterUrl.includeUrlSet;
	}
	protected UrlHashSet getFilterUrlExcludeUrls() {
		return this.filterUrl.excludeUrlSet;
	}
	private void addUrlHashSet(UrlHashSet urlHashSet, String... urls) {
		//urlHashSet.clear();
		if (urls == null || urls.length < 1) {
			return;
		}
		if (urls.length < 2) {
			urlHashSet.add(urls[0]);
		}
		urlHashSet.addAll(Arrays.asList(urls));
	}

	//private final LoginHandlerInfo loginHandlerInfo;
	//private final LogoutHandlerInfo logoutHandlerInfo;
	//this.loginHandlerInfo = new LoginHandlerInfo();
	//this.logoutHandlerInfo = new LogoutHandlerInfo();
	//    /**
	//     * 获取登录处理信息设置器
	//     *
	//     * @return 登录处理信息设置器
	//     */
	//    public LoginHandlerInfo login() {
	//        return loginHandlerInfo;
	//    }
	//
	//    /**
	//     * 获取登出处理信息设置器
	//     *
	//     * @return 登出处理信息设置器
	//     */
	//    public LogoutHandlerInfo logout() {
	//        return logoutHandlerInfo;
	//    }
	//private String loginUrl = "/api/login/login.do";
	//private String logoutUrl = "/api/logout/logout.do";
	///**
	// * 需认证URL
	// */
	//private final UrlHashSet authorizationUrlSet = new UrlHashSet();
	///**
	// * 只可在匿名状态下访问
	// */
	//private final UrlHashSet anonymousUrlSet = new UrlHashSet();
	///**
	// * 设置需在认证状态下可访问的接口
	// *
	// * @param authorizationUrls 认证接口列表
	// */
	//public FilterUrl addAuthorizationUrls(String... authorizationUrls) {
	//    addUrlHashSet(this.authorizationUrlSet, authorizationUrls);
	//    return this;
	//}
	//
	///**
	// * 设置需在匿名状态下可访问的接口
	// *
	// * @param anonymousUrls 匿名接口列表
	// */
	//public FilterUrl addAnonymousUrls(String... anonymousUrls) {
	//    addUrlHashSet(this.anonymousUrlSet, anonymousUrls);
	//    return this;
	//}
	///**
	// * 设置登录地址
	// *
	// * @param loginUrl 登录地址
	// */
	//public FilterUrl loginUrl(String loginUrl) {
	//    this.loginUrl = loginUrl;
	//    return this;
	//}
	//
	///**
	// * 设置登出地址
	// *
	// * @param logoutUrl 登出地址
	// */
	//public FilterUrl logoutUrl(String logoutUrl) {
	//    this.logoutUrl = logoutUrl;
	//    return this;
	//}
	//    public class LoginHandlerInfo {
	//
	//        /**
	//         * 密码验证
	//         */
	//        private PasswordEncoder passwordEncoder;
	//        /**
	//         * 用户获取服务类
	//         */
	//        private UserDetailsService userDetailsService;
	//        /**
	//         * 验证码处理器
	//         */
	//        private CaptchaHandler captchaHandler;
	//        /**
	//         * 账号状态验证
	//         */
	//        private AccessStatusHandler accessStatusHandler;
	//        /**
	//         * 登录成功处理器
	//         */
	//        private LoginSuccessHandler loginSuccessHandler;
	//        /**
	//         * 登录错误处理器
	//         */
	//        private LoginErrorHandler loginErrorHandler;
	//        /**
	//         * 校验认证状态处理器
	//         */
	//        private ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;
	//
	//        private String identifierKey = "identifier";
	//        private String credentialKey = "credential";
	//        private String codeKey = "code";
	//        private String rememberMeKey = "rememberMe";
	//
	//        public LoginHandlerInfo userDetailsService(UserDetailsService userDetailsService) {
	//            this.userDetailsService = userDetailsService;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo captchaHandler(CaptchaHandler captchaHandler) {
	//            this.captchaHandler = captchaHandler;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo passwordEncoder(PasswordEncoder passwordEncoder) {
	//            this.passwordEncoder = passwordEncoder;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo accessStatusHandler(AccessStatusHandler accessStatusHandler) {
	//            this.accessStatusHandler = accessStatusHandler;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo loginErrorHandler(LoginErrorHandler loginErrorHandler) {
	//            this.loginErrorHandler = loginErrorHandler;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo loginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
	//            this.loginSuccessHandler = loginSuccessHandler;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo identifierKey(String identifierKey) {
	//            this.identifierKey = identifierKey;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo credentialKey(String credentialKey) {
	//            this.credentialKey = credentialKey;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo codeKey(String codeKey) {
	//            this.codeKey = codeKey;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo rememberMeKey(String rememberMeKey) {
	//            this.rememberMeKey = rememberMeKey;
	//            return LoginHandlerInfo.this;
	//        }
	//        public LoginHandlerInfo checkAccessAuthStatusHandler(ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler) {
	//            this.checkAccessAuthStatusHandler = checkAccessAuthStatusHandler;
	//            return LoginHandlerInfo.this;
	//        }
	//
	//        public FilterConfigurer and() {
	//            return FilterConfigurer.this;
	//        }
	//    }
	//    public class LogoutHandlerInfo {
	//        private LogoutService logoutService;
	//
	//        public LogoutHandlerInfo logoutService(LogoutService logoutService) {
	//            this.logoutService = logoutService;
	//            return this;
	//        }
	//
	//        public FilterConfigurer and() {
	//            return FilterConfigurer.this;
	//        }
	//    }
}
