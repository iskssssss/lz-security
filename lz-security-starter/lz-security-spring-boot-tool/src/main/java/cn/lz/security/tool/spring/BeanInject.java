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
import cn.lz.security.tool.context.SpringBootContextTheadLocal;
import cn.lz.security.tool.utils.SpringUtil;
import cn.lz.tool.reflect.model.ControllerMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;

import java.util.Map;

/**
 * 自动注入类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 10:42
 */
public class BeanInject extends LzCoreManager {

	/**
	 * 自动注入<b>基础配置文件</b>
	 */
	@Autowired
	public void injectLzConfig(LzConfig lzConfig) {
		LzCoreManager.setLzConfig(lzConfig);
	}

	/**
	 * 自动注入<b>token配置文件</b>
	 */
	@Autowired
	public void injectTokenConfig(TokenConfig tokenConfig) {
		LzCoreManager.setTokenConfig(tokenConfig);
	}

	/**
	 * 自动注入<b>加解密配置文件</b>
	 */
	@Autowired
	public void injectEncryptConfig(EncryptConfig encryptConfig) {
		LzCoreManager.setEncryptConfig(encryptConfig);
	}

	/**
	 * 自动注入<b>过滤配置文件</b>
	 */
	@Autowired
	public void injectFilterConfig(FilterConfig filterConfig) {
		LzCoreManager.setFilterConfig(filterConfig);
	}

	/**
	 * 自动注入<b>接口方法映射信息</b>
	 */
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
		LzCoreManager.setLzContext(new SpringBootContextTheadLocal(pathMatcher));
	}
}
