package cn.lz.security.filter.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.filter.LzFilterManager;
import cn.lz.security.filter.filters.AbsInterfacesFilterBuilder;
import cn.lz.security.fun.LzFilterAuthStrategy;
import cn.lz.security.handler.BaseFilterErrorHandler;
import cn.lz.security.log.BaseFilterLogHandler;

import java.util.Arrays;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/27 14:19
 */
public class FilterConfigurerBuilder<T extends FilterConfigurer> {

	protected final FilterUrl filterUrl = new FilterUrl();
	protected final FilterConfig filterConfig = new FilterConfig();
	protected LzFilterAuthStrategy filterAfterHandler = null;
	protected LzFilterAuthStrategy filterBeforeHandler = null;

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
		LzFilterManager.setFilterLogHandler(filterLogHandler);
		return this;
	}

	/**
	 * 设置过滤错误处理器
	 *
	 * @param filterErrorHandler 过滤错误处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterErrorHandler(BaseFilterErrorHandler<?> filterErrorHandler) {
		LzFilterManager.setFilterErrorHandler(filterErrorHandler);
		return this;
	}

	/**
	 * 过滤前处理
	 *
	 * @param filterBeforeHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterBeforeHandler(LzFilterAuthStrategy filterBeforeHandler) {
		this.filterBeforeHandler = filterBeforeHandler;
		return this;
	}

	/**
	 * 过滤后处理
	 *
	 * @param filterAfterHandler 过滤后处理器
	 * @return this
	 */
	public FilterConfigurerBuilder<T> setFilterAfterHandler(LzFilterAuthStrategy filterAfterHandler) {
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
		/**
		 * 开始记录日志的过滤器类
		 */
		protected Class<? extends AbsInterfacesFilterBuilder> logBeforeFilterClass;

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
			LzFilterManager.linkInterfacesFilter(interfacesFilterList);
			return this;
		}

		/**
		 * 设置开始记录日志的过滤器类
		 *
		 * @param logBeforeFilterClass 过滤器类
		 * @return this
		 */
		public FilterConfig setLogBeforeFilterClass(Class<? extends AbsInterfacesFilterBuilder> logBeforeFilterClass) {
			this.logBeforeFilterClass = logBeforeFilterClass;
			return this;
		}

		public FilterConfigurerBuilder<T> and() {
			return FilterConfigurerBuilder.this;
		}
	}

	public class FilterUrl {

		/**
		 * 设置拦截接口
		 *
		 * @param includeUrls 拦截接口列表
		 */
		public FilterUrl addIncludeUrls(String... includeUrls) {
			addUrlHashSet(LzCoreManager.getFilterConfig().getIncludeUrlList(), includeUrls);
			return this;
		}

		/**
		 * 设置开放接口
		 *
		 * @param excludeUrls 开放接口列表
		 */
		public FilterUrl addExcludeUrls(String... excludeUrls) {
			addUrlHashSet(LzCoreManager.getFilterConfig().getExcludeUrlList(), excludeUrls);
			return this;
		}

		public FilterConfigurerBuilder<T> and() {
			return FilterConfigurerBuilder.this;
		}
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
