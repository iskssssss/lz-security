package com.sowell.security.spring;

import com.sowell.security.config.IcpConfig;
import com.sowell.security.utils.SpringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 13:47
 */
@Import({BeanInject.class})
public class BeanRegister {

	@Bean
	@Primary
	@ConfigurationProperties("sw.security")
	public IcpConfig registerIcpConfig() {
		return new IcpConfig();
	}

	@Bean
	@Primary
	public SpringUtil registerSpringUtil() {
		return new SpringUtil();
	}

	@Bean
	@Primary
	@DependsOn({"registerIcpConfig", "registerSpringUtil"})
	public Map<String, ControllerMethod> registerInterfacesMethodMap() {
		final IcpConfig icpConfig = SpringUtil.getBean(IcpConfig.class);
		final List<String> methodScanPath = icpConfig.getControllerMethodScanPathList();
		return SpringUtil.getControllerMethodMap(methodScanPath);
	}
}
