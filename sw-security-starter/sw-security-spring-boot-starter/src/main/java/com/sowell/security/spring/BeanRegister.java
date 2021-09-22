package com.sowell.security.spring;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.utils.SpringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 13:47
 */
public class BeanRegister {

	@Bean
	@ConfigurationProperties("sw.security")
	public IcpConfig registerIcpConfig() {
		return new IcpConfig();
	}

	@Bean
	public SpringUtil registerSpringUtil() {
		return new SpringUtil();
	}

	@Bean
	@DependsOn({"registerIcpConfig", "registerSpringUtil"})
	public Map<String, Method> registerInterfacesMethodMap() {
		final IcpConfig icpConfig = SpringUtil.getBean(IcpConfig.class);
		final Set<String> methodScanPath = icpConfig.getControllerMethodScanPathList();
		return SpringUtil.initControllerMethodMap(methodScanPath);
	}
}
