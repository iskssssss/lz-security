package cn.lz.demo.filter.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.config.CoreConfigurer;
import cn.lz.security.config.CoreConfigurerBuilder;
import cn.lz.security.filter.config.FilterConfigurer;
import cn.lz.security.filter.config.FilterConfigurerBuilder;
import cn.lz.security.filter.config.SecurityFilterConfigurerAdapter;
import cn.lz.security.filter.utils.FilterUtil;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
		System.out.println(LzCoreManager.getLzConfig().print());
		filterConfigurer
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
				);
				//.setLogBeforeFilter(FilterUtil.AccessTokenFilter.class)
	}
}
