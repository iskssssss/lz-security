package com.sowell.security.spring;

import com.sowell.security.IcpManager;
import com.sowell.security.config.IcpConfig;
import com.sowell.security.filter.IcpServletFilter;
import com.sowell.security.utils.SpringUtil;
import com.sowell.tool.reflect.model.ControllerMethod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

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
	protected FilterRegistrationBean<IcpServletFilter> initFilterRegistrationBean() {
		FilterRegistrationBean<IcpServletFilter> registration = new FilterRegistrationBean<>();
		if (registration.getUrlPatterns().isEmpty()) {
			registration.addUrlPatterns("/*");
		}
		IcpServletFilter filterContainer = new IcpServletFilter();
		registration.setFilter(filterContainer);
		registration.setOrder(Integer.MIN_VALUE);
		registration.setName("filterContainer");
		return registration;
	}

	/*@Bean
	@ConfigurationProperties("sw.security")
	public IcpConfig registerIcpConfig() {
		return new IcpConfig();
	}*/

	@Bean
	public SpringUtil registerSpringUtil() {
		return new SpringUtil();
	}

	@Bean
	@DependsOn({"registerSpringUtil"})
	public Map<String, ControllerMethod> registerInterfacesMethodMap() {
		final IcpConfig icpConfig = IcpManager.getIcpConfig();
		final List<String> methodScanPath = icpConfig.getControllerMethodScanPathList();
		return SpringUtil.getControllerMethodMap(methodScanPath);
	}
}
