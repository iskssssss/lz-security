package cn.lz.security.filter.spring;

import cn.lz.security.LzCoreManager;
import cn.lz.security.config.encrypt.EncryptConfig;
import cn.lz.security.config.FilterConfig;
import cn.lz.security.config.LzConfig;
import cn.lz.security.config.TokenConfig;
import cn.lz.security.filter.utils.SpringUtil;
import cn.lz.tool.reflect.model.ControllerMethod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 13:47
 */
public class BeanRegister {

	@Bean
	@ConfigurationProperties("lz.security")
	public LzConfig registerLzConfig() {
		return new LzConfig();
	}

	@Bean
	@ConfigurationProperties("lz.security.token-config")
	public TokenConfig registerTokenConfig() {
		return new TokenConfig();
	}

	@Bean
	@ConfigurationProperties("lz.security.encrypt-config")
	public EncryptConfig registerEncryptConfig() {
		return new EncryptConfig();
	}

	@Bean
	@ConfigurationProperties("lz.security.filter-config")
	public FilterConfig registerFilterConfig() {
		return new FilterConfig();
	}

	@Bean
	public SpringUtil registerSpringUtil() {
		return new SpringUtil();
	}

	@Bean
	@DependsOn({"registerLzConfig", "registerSpringUtil"})
	public Map<String, Map<String, ControllerMethod>> registerInterfacesMethodMap() {
		final LzConfig lzConfig = LzCoreManager.getLzConfig();
		final List<String> methodScanPath = lzConfig.getControllerMethodScanPathList();
		return SpringUtil.getControllerMethodMap(methodScanPath);
	}
}
