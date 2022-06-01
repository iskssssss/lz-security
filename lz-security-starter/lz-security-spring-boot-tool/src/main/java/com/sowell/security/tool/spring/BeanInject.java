package com.sowell.security.tool.spring;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.handler.DataEncoder;
import com.sowell.security.handler.EncodeSwitchHandler;
import com.sowell.security.token.IAccessTokenHandler;
import com.sowell.security.tool.context.SpringContextTheadLocal;
import com.sowell.security.tool.utils.SpringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 15:16
 */
public class BeanInject extends IcpCoreManager {

	@Autowired
	public void injectIcpConfig(IcpConfig icpConfig) {
		IcpCoreManager.setIcpConfig(icpConfig);
	}

	@Autowired
	public void injectInterfacesMethodMap(Map<String, ControllerMethod> initControllerMethodMap) {
		IcpCoreManager.setInterfacesMethodMap(initControllerMethodMap);
	}

	/**
	 * 自动注入<b>AccessToken处理器</b>
	 */
	@Autowired(required = false)
	public void injectAccessTokenHandler(IAccessTokenHandler<?> accessTokenHandler) {
		if (IcpCoreManager.accessTokenHandler != null) {
			SpringUtil.destroyBean(accessTokenHandler);
			return;
		}
		IcpCoreManager.setAccessTokenHandler(accessTokenHandler);
	}

	/**
	 * 自动注入<b>数据加解密处理器</b>
	 */
	@Autowired(required = false)
	public void injectRequestDataEncryptHandler(DataEncoder dataEncoder) {
		if (IcpCoreManager.dataEncoder != null) {
			SpringUtil.destroyBean(dataEncoder);
			return;
		}
		IcpCoreManager.setRequestDataEncryptHandler(dataEncoder);
	}

	/**
	 * 自动注入<b>加解密开关处理器</b>
	 */
	@Autowired(required = false)
	public void injectEncryptSwitchHandler(EncodeSwitchHandler encodeSwitchHandler) {
		if (IcpCoreManager.encodeSwitchHandler != null) {
			SpringUtil.destroyBean(encodeSwitchHandler);
			return;
		}
		IcpCoreManager.setEncryptSwitchHandler(encodeSwitchHandler);
	}

	/**
	 * 自动注入<b>缓存处理器</b>
	 */
	@Autowired(required = false)
	public void injectCacheManager(BaseCacheManager cacheManager) {
		if (IcpCoreManager.cacheManager != null) {
			SpringUtil.destroyBean(cacheManager);
			return;
		}
		IcpCoreManager.setCacheManager(cacheManager);
	}

	/**
	 * 自动注入<b>路径匹配器</b>
	 */
	@Autowired(required = false)
	@Qualifier("mvcPathMatcher")
	public void injectIcpContext(PathMatcher pathMatcher) {
		IcpCoreManager.setIcpContext(new SpringContextTheadLocal(pathMatcher));
	}
}
