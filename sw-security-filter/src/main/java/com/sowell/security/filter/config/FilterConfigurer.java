package com.sowell.security.filter.config;

import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.fun.IcpFilterAuthStrategy;


/**
 * 过滤配置文件
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/7/10 21:55
 */
public final class FilterConfigurer extends FilterConfigurerBuilder<FilterConfigurer> {

    /**
     * 获取过滤前处理方法
     *
     * @return 过滤前处理方法
     */
    public IcpFilterAuthStrategy getFilterBeforeHandler() {
        return super.filterBeforeHandler;
    }

    /**
     * 获取过滤后处理方法
     *
     * @return 过滤后处理方法
     */
    public IcpFilterAuthStrategy getFilterAfterHandler() {
        return super.filterAfterHandler;
    }

    /**
     * 获取拦截的接口列表
     *
     * @return 拦截的接口列表
     */
    public UrlHashSet getIncludeUrls() {
        final UrlHashSet filterUrlIncludeUrls = super.getFilterUrlIncludeUrls();
        if (filterUrlIncludeUrls.isEmpty()) {
            filterUrlIncludeUrls.add("/**");
        }
        return filterUrlIncludeUrls;
    }

    /**
     * 获取排除的接口列表
     *
     * @return 排除的接口列表
     */
    public UrlHashSet getExcludeUrls() {
        return super.getFilterUrlExcludeUrls();
    }

}