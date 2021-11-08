package com.sowell.demo.filter.config;

import com.sowell.security.IcpCoreManager;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.CoreConfigurerBuilder;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.filter.config.FilterConfigurerBuilder;
import com.sowell.security.filter.config.SecurityFilterConfigurerAdapter;
import com.sowell.security.filter.utils.FilterUtil;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.tool.context.IcpContextManager;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 14:00
 */
@Configuration
public class FilterSecurityConfig extends SecurityFilterConfigurerAdapter {

	@Override
	protected void config(CoreConfigurerBuilder<CoreConfigurer> coreConfigurer) {
	}

	@Override
	protected void filter(FilterConfigurerBuilder<FilterConfigurer> filterConfigurer) {
		System.out.println(IcpCoreManager.getIcpConfig().toString());
		filterConfigurer
				.filterUrl()
				// 拦截的接口
				.addIncludeUrls("/**")
				// [非必需] 排除的接口
				.addExcludeUrls(
						"/favicon.ico", "/webjars/**", "/doc.html", "/swagger-resources",
						"/v2/api-docs", "/v2/api-docs-ext", "/auth/**", "/exclude/**"
				).and()
				.filterConfig()
				// [非必需] 设置接口过滤执行链
				.linkInterfacesFilter(
						FilterUtil.accessTokenFilterOnLogBefore(),
						FilterUtil.ipFilter(params -> {
							final LinkedList<String> ipList = new LinkedList<>();
							ipList.add("*");
							return ipList;
						}),
						FilterUtil.requestInterfaceFilter(params -> {
							UrlHashSet urlHashSet = new UrlHashSet();
							urlHashSet.add("/**");
							return urlHashSet;
						})
				)
				.setLogBeforeFilter(FilterUtil.AccessTokenFilter.class)
				.and()
				// [非必需] 过滤前处理
				.setFilterBeforeHandler(params -> {
					if (IcpContextManager.getRequest().isDecrypt()) {
						IcpLoggerUtil.info(getClass(), "请求数据已加密。");
						return;
					}
					IcpLoggerUtil.info(getClass(), "请求数据未加密。");
				})
				// [非必需] 过滤后处理
				.setFilterAfterHandler(params -> {
					if (IcpContextManager.getResponse().isEncrypt()) {
						IcpLoggerUtil.info(getClass(), "响应数据需加密。");
						return;
					}
					IcpLoggerUtil.info(getClass(), "响应数据无需加密。");
				}).end();
	}
}
