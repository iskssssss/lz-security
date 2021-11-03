package com.sowell.demo.config;

import com.sowell.security.auth.config.AuthConfigurer;
import com.sowell.security.auth.config.AuthConfigurerBuilder;
import com.sowell.security.auth.config.SecurityAuthConfigurerAdapter;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.CoreConfigurerBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 16:04
 */
@Configuration
public class AuthSecurityConfig extends SecurityAuthConfigurerAdapter {

	@Override
	protected void config(CoreConfigurerBuilder<CoreConfigurer> coreConfigurer) {

	}

	@Override
	protected void auth(AuthConfigurerBuilder<AuthConfigurer> authConfigurer) {
//		authConfigurer.
	}
}
