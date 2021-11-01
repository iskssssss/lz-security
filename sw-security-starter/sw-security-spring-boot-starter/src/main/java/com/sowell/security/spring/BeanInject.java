package com.sowell.security.spring;

import com.sowell.security.IcpManager;
import com.sowell.security.base.BaseFilterErrorHandler;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.context.IcpContextTheadLocal;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.security.utils.SpringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 15:16
 */
public class BeanInject extends IcpManager {

	/*@Autowired
	public void injectIcpConfig(IcpConfig icpConfig) {
		IcpManager.setIcpConfig(icpConfig);
	}*/

	@Autowired
	public void injectInterfacesMethodMap(Map<String, ControllerMethod> initControllerMethodMap) {
		IcpManager.setInterfacesMethodMap(initControllerMethodMap);
	}

	/**
	 * 自动注入<b>路径匹配器</b>
	 */
	@Autowired(required = false)
	@Qualifier("mvcPathMatcher")
	public void injectIcpContext(PathMatcher pathMatcher) {
		IcpManager.setIcpContext(new IcpContextTheadLocal<HttpServletRequest, HttpServletResponse>() {
			@Override
			public boolean matchUrl(String pattern, String path) {
				return pathMatcher.match(pattern, path);
			}
		});
	}

	/**
	 * 自动注入<b>过滤日志处理器</b>
	 */
	@Autowired(required = false)
	public void injectFilterLogHandler(BaseFilterLogHandler filterLogHandler) {
		if (IcpManager.filterLogHandler != null) {
			SpringUtil.destroyBean(filterLogHandler);
			return;
		}
		IcpManager.setFilterLogHandler(filterLogHandler);
	}

	/**
	 * 自动注入<b>过滤错误处理器</b>
	 */
	@Autowired(required = false)
	public void injectFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		if (IcpManager.filterErrorHandler != null) {
			SpringUtil.destroyBean(filterErrorHandler);
			return;
		}
		IcpManager.setFilterErrorHandler(filterErrorHandler);
	}

	/**
	 * 自动注入<b>AccessToken处理器</b>
	 */
	@Autowired(required = false)
	public void injectAccessTokenHandler(IAccessTokenHandler accessTokenHandler) {
		if (IcpManager.accessTokenHandler != null) {
			SpringUtil.destroyBean(accessTokenHandler);
			return;
		}
		IcpManager.setAccessTokenHandler(accessTokenHandler);
	}

	/**
	 * 自动注入<b>数据加解密处理器</b>
	 */
	@Autowired(required = false)
	public void injectRequestDataEncryptHandler(RequestDataEncryptHandler requestDataEncryptHandler) {
		if (IcpManager.requestDataEncryptHandler != null) {
			SpringUtil.destroyBean(requestDataEncryptHandler);
			return;
		}
		IcpManager.setRequestDataEncryptHandler(requestDataEncryptHandler);
	}

	/**
	 * 自动注入<b>缓存处理器</b>
	 */
	@Autowired(required = false)
	public void injectCacheManager(BaseCacheManager cacheManager) {
		if (IcpManager.cacheManager != null) {
			SpringUtil.destroyBean(cacheManager);
			return;
		}
		IcpManager.setCacheManager(cacheManager);
	}

//	/**
//	 * 自动注入<b>密码验证</b>
//	 */
//	@Autowired(required = false)
//	public void injectPasswordEncoder(
//			PasswordEncoder passwordEncoder
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.passwordEncoder(passwordEncoder);
//	}
//
//	/**
//	 * 自动注入<b>校验认证状态处理器</b>
//	 */
//	@Autowired(required = false)
//	public void injectCheckAccessAuthStatusHandler(
//			ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.checkAccessAuthStatusHandler(checkAccessAuthStatusHandler);
//	}
//
//	/**
//	 * 自动注入<b>用户获取服务类</b>
//	 */
//	@Autowired(required = false)
//	public void injectUserDetailsService(
//			UserDetailsService userDetailsService
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.userDetailsService(userDetailsService);
//	}
//
//	/**
//	 * 自动注入<b>验证码处理器</b>
//	 */
//	@Autowired(required = false)
//	public void injectCaptchaHandler(
//			CaptchaHandler captchaHandler
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.captchaHandler(captchaHandler);
//	}
//
//	/**
//	 * 自动注入<b>账号状态验证</b>
//	 */
//	@Autowired(required = false)
//	public void injectAccessStatusHandler(
//			AccessStatusHandler accessStatusHandler
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.accessStatusHandler(accessStatusHandler);
//	}
//
//	/**
//	 * 自动注入<b>登录成功处理器</b>
//	 */
//	@Autowired(required = false)
//	public void injectLoginSuccessHandler(
//			LoginSuccessHandler loginSuccessHandler
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.loginSuccessHandler(loginSuccessHandler);
//	}
//
//	/**
//	 * 自动注入<b>登录错误处理器</b>
//	 */
//	@Autowired(required = false)
//	public void injectLoginErrorHandler(
//			LoginErrorHandler loginErrorHandler
//	) {
//		final FilterConfigurer filterConfigurer = IcpManager.getFilterConfigurer();
//		final FilterConfigurer.LoginHandlerInfo login = filterConfigurer.login();
//		login.loginErrorHandler(loginErrorHandler);
//	}
}
