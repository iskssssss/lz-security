package com.sowell.security.filter.config;

import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.cache.BaseCacheManager;
import com.sowell.security.filter.IcpFilter;
import com.sowell.security.filter.filters.AbsInterfacesFilterBuilder;
import com.sowell.security.fun.IcpFilterAuthStrategy;
import com.sowell.security.handler.BaseFilterErrorHandler;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.log.BaseFilterLogHandler;
import com.sowell.security.token.IAccessTokenHandler;

import java.util.Arrays;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 14:19
 */
public class FilterConfigurerBuilder<T extends FilterConfigurer> {

	protected final FilterUrl filterUrl = new FilterUrl();
	protected final FilterConfig filterConfig = new FilterConfig();
	protected IcpFilterAuthStrategy filterAfterHandler = params -> { };
	protected IcpFilterAuthStrategy filterBeforeHandler = params -> { };

	/**
	 * 获取接口过滤设置器
	 *
	 * @return 接口过滤设置器
	 */
	public FilterUrl filterUrl() {
		return filterUrl;
	}

	public FilterConfig filterConfig() {
		return filterConfig;
	}

	/**
	 * 设置过滤日志处理器
	 *
	 * @param filterLogHandler 过滤日志处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterLogHandler(BaseFilterLogHandler filterLogHandler) {
		IcpFilter.setFilterLogHandler(filterLogHandler);
		return this;
	}

	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		IcpFilter.setFilterErrorHandler(filterErrorHandler);
		return this;
	}

	/**
	 * 过滤前处理
	 *
	 * @param filterBeforeHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterBeforeHandler(IcpFilterAuthStrategy filterBeforeHandler) {
		this.filterBeforeHandler = filterBeforeHandler;
		return this;
	}

	/**
	 * 过滤后处理
	 *
	 * @param filterAfterHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterAfterHandler(IcpFilterAuthStrategy filterAfterHandler) {
		this.filterAfterHandler = filterAfterHandler;
		return this;
	}

	public FilterConfigurerBuilder<T> and() {
		return FilterConfigurerBuilder.this;
	}

	public T end() {
		return ((T) FilterConfigurerBuilder.this);
	}

	public class FilterConfig {
		protected Class<? extends AbsInterfacesFilterBuilder> logBeforeFilter;

		/**
		 * 设置接口过滤执行链
		 * <p>注意：一定要按顺序放入过滤器</p>
		 * <p>如:linkFilter(过滤器1, 过滤器2, 过滤器3)</p>
		 * <p>设置后过滤顺序为 ：开始 -> 过滤器1 -> 过滤器2 -> 过滤器3 -> 结束</p>
		 *
		 * @param interfacesFilterList 过滤执行链
		 * @return this
		 */
		public FilterConfig linkInterfacesFilter(AbsInterfacesFilterBuilder... interfacesFilterList) {
			IcpFilter.linkInterfacesFilter(interfacesFilterList);
			return this;
		}

		/**
		 * 设置
		 *
		 * @param logBeforeFilter
		 * @return
		 */
		public FilterConfig setLogBeforeFilter(Class<? extends AbsInterfacesFilterBuilder> logBeforeFilter) {
			this.logBeforeFilter = logBeforeFilter;
			return this;
		}

		public FilterConfigurerBuilder<T> and() {
			return FilterConfigurerBuilder.this;
		}
	}

	public class FilterUrl {
		/**
		 * 拦截URL
		 */
		protected final UrlHashSet includeUrlSet = new UrlHashSet();
		/**
		 * 排除URL
		 */
		protected final UrlHashSet excludeUrlSet = new UrlHashSet();

		/**
		 * 设置拦截接口
		 *
		 * @param includeUrls 拦截接口列表
		 */
		public FilterUrl addIncludeUrls(String... includeUrls) {
			addUrlHashSet(this.includeUrlSet, includeUrls);
			return this;
		}

		/**
		 * 设置开放接口
		 *
		 * @param excludeUrls 开放接口列表
		 */
		public FilterUrl addExcludeUrls(String... excludeUrls) {
			addUrlHashSet(this.excludeUrlSet, excludeUrls);
			return this;
		}

		public FilterConfigurerBuilder<T> and() {
			return FilterConfigurerBuilder.this;
		}
	}

	protected UrlHashSet getFilterUrlIncludeUrls() {
		return this.filterUrl.includeUrlSet;
	}

	protected UrlHashSet getFilterUrlExcludeUrls() {
		return this.filterUrl.excludeUrlSet;
	}

	private void addUrlHashSet(UrlHashSet urlHashSet, String... urls) {
		//urlHashSet.clear();
		if (urls == null || urls.length < 1) {
			return;
		}
		if (urls.length < 2) {
			urlHashSet.add(urls[0]);
		}
		urlHashSet.addAll(Arrays.asList(urls));
	}
}
