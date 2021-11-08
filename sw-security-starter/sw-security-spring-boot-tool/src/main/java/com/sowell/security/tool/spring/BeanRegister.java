package com.sowell.security.tool.spring;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.config.IcpConfig;
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
	public Map<String, ControllerMethod> registerInterfacesMethodMap() {
		final IcpConfig icpConfig = IcpCoreManager.getIcpConfig();
		final List<String> methodScanPath = icpConfig.getControllerMethodScanPathList();
		return SpringUtil.getControllerMethodMap(methodScanPath);
	}
}
