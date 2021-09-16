package com.sowell.security.spring;

import com.sowell.security.config.IcpConfig;
import com.sowell.security.utils.BeanUtil;
import com.sowell.security.IcpManager;
import com.sowell.security.config.FilterConfigurer;
import com.sowell.security.handler.FilterDataHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/12 13:47
 */
@Component("beanRegister")
public class BeanRegister {

	@Bean
	public void beanRegisterInit() {
		initFilterConfigurer();
		initFilterDataHandler();
		initInterfacesMethodMap();
	}

	@Bean
	@ConfigurationProperties("sw.security")
	public IcpConfig initIcpConfig() {
		return new IcpConfig();
	}

	public void initFilterConfigurer() {
		FilterConfigurer filterConfigurer = new FilterConfigurer();
		IcpManager.setFilterConfigurer(filterConfigurer);
	}

	public void initFilterDataHandler() {
		FilterDataHandler filterDataHandler = new FilterDataHandler();
		IcpManager.setFilterDataHandler(filterDataHandler);
	}

	public void initInterfacesMethodMap() {
		final Map<String, Method> initControllerMethodMap = BeanUtil.initControllerMethodMap();
		IcpManager.setInterfacesMethodMap(initControllerMethodMap);
	}
}
