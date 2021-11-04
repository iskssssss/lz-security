package com.sowell.demo.filter.config;

import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.config.CoreConfigurer;
import com.sowell.security.config.CoreConfigurerBuilder;
import com.sowell.security.defaults.DefaultAuthDetails;
import com.sowell.security.filter.config.FilterConfigurer;
import com.sowell.security.filter.config.FilterConfigurerBuilder;
import com.sowell.security.filter.config.SecurityFilterConfigurerAdapter;
import com.sowell.security.filter.utils.AccessTokenUtil;
import com.sowell.security.filter.utils.FilterUtil;
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
						FilterUtil.accessTokenFilter/*OnLogBefore*/(),
						FilterUtil.ipFilter(params -> {
							final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
							final String id = authDetails.getId();
							final LinkedList<String> ipList = new LinkedList<>();
							if ("1".equals(id)) {
								ipList.add("*");
							} else if ("2".equals(id)) {
								ipList.add("127.0.0.0/24");
							} else if ("3".equals(id)) {
								ipList.add("127.0.0.2");
							} else {
								ipList.add("127.0.0.1");
							}
							return ipList;
						}),
						FilterUtil.requestInterfaceFilter(params -> {
							final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
							final String id = authDetails.getId();
							UrlHashSet urlHashSet = new UrlHashSet();
							if ("1".equals(id)) {
								urlHashSet.add("/include/getUserInfo");
								urlHashSet.add("/include/1");
							} else if ("2".equals(id)) {
								urlHashSet.add("/include/getUserInfo");
								urlHashSet.add("/include/2");
							} else if ("3".equals(id)) {
								urlHashSet.add("/include/getUserInfo");
								urlHashSet.add("/include/3");
							} else {
								urlHashSet.add("/**");
							}
							return urlHashSet;
						})
				)
				.setLogBeforeFilter(FilterUtil.AccessTokenFilter.class)
				.and()
				// [非必需] 过滤前处理
				.setFilterBeforeHandler(params -> {})
				// [非必需] 过滤后处理
				.setFilterAfterHandler(params -> {}).end();
	}
}
