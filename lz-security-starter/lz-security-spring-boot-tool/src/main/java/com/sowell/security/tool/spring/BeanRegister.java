package com.sowell.security.tool.spring;

import com.sowell.security.LzCoreManager;
import com.sowell.security.config.LzConfig;
import com.sowell.security.tool.utils.SpringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
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
	public SpringUtil registerSpringUtil() {
		return new SpringUtil();
	}

	@Bean
	@DependsOn({"registerLzConfig", "registerSpringUtil"})
	public Map<String, ControllerMethod> registerInterfacesMethodMap() {
		final LzConfig lzConfig = LzCoreManager.getLzConfig();
		final List<String> methodScanPath = lzConfig.getControllerMethodScanPathList();
		return SpringUtil.getControllerMethodMap(methodScanPath);
	}
}
