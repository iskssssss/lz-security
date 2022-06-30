package cn.lz.security.tool.spring;

import cn.lz.security.config.EncryptConfig;
import cn.lz.security.config.FilterConfig;
import cn.lz.security.config.LzConfig;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.handler.DataEncoder;
import cn.lz.security.handler.EncodeSwitchHandler;
import cn.lz.security.LzCoreManager;
import cn.lz.security.cache.BaseCacheManager;
import cn.lz.security.token.IAccessTokenHandler;
import cn.lz.security.tool.context.SpringContextTheadLocal;
import cn.lz.security.tool.utils.SpringUtil;
import cn.lz.tool.reflect.model.ControllerMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;

import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 15:16
 */
public class BeanInject extends LzCoreManager {

	@Autowired
	public void injectLzConfig(LzConfig lzConfig) {
		LzCoreManager.setLzConfig(lzConfig);
	}

	@Autowired
	public void injectLzConfig(TokenConfig tokenConfig) {
		LzCoreManager.setTokenConfig(tokenConfig);
	}

	@Autowired
	public void injectLzConfig(EncryptConfig encryptConfig) {
		LzCoreManager.setEncryptConfig(encryptConfig);
	}

	@Autowired
	public void injectLzConfig(FilterConfig filterConfig) {
		LzCoreManager.setFilterConfig(filterConfig);
	}

	@Autowired
	public void injectInterfacesMethodMap(Map<String, Map<String, ControllerMethod>> initControllerMethodMap) {
		LzCoreManager.setInterfacesMethodMap(initControllerMethodMap);
	}

	/**
	 * 自动注入<b>AccessToken处理器</b>
	 */
	@Autowired(required = false)
	public void injectAccessTokenHandler(IAccessTokenHandler<?> accessTokenHandler) {
		if (LzCoreManager.accessTokenHandler != null) {
			SpringUtil.destroyBean(accessTokenHandler);
			return;
		}
		LzCoreManager.setAccessTokenHandler(accessTokenHandler);
	}

	/**
	 * 自动注入<b>数据加解密处理器</b>
	 */
	@Autowired(required = false)
	public void injectRequestDataEncryptHandler(DataEncoder dataEncoder) {
		if (LzCoreManager.dataEncoder != null) {
			SpringUtil.destroyBean(dataEncoder);
			return;
		}
		LzCoreManager.setRequestDataEncryptHandler(dataEncoder);
	}

	/**
	 * 自动注入<b>加解密开关处理器</b>
	 */
	@Autowired(required = false)
	public void injectEncryptSwitchHandler(EncodeSwitchHandler encodeSwitchHandler) {
		if (LzCoreManager.encodeSwitchHandler != null) {
			SpringUtil.destroyBean(encodeSwitchHandler);
			return;
		}
		LzCoreManager.setEncryptSwitchHandler(encodeSwitchHandler);
	}

	/**
	 * 自动注入<b>缓存处理器</b>
	 */
	@Autowired(required = false)
	public void injectCacheManager(BaseCacheManager cacheManager) {
		if (LzCoreManager.cacheManager != null) {
			SpringUtil.destroyBean(cacheManager);
			return;
		}
		LzCoreManager.setCacheManager(cacheManager);
	}

	/**
	 * 自动注入<b>路径匹配器</b>
	 */
	@Autowired(required = false)
	@Qualifier("mvcPathMatcher")
	public void injectLzContext(PathMatcher pathMatcher) {
		LzCoreManager.setLzContext(new SpringContextTheadLocal(pathMatcher));
	}
}
