package cn.lz.security.tool.spring;

import cn.lz.security.LzCoreManager;
import cn.lz.security.config.LzConfig;
import cn.lz.security.tool.utils.SpringUtil;
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