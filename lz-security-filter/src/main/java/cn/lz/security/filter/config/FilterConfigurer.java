package cn.lz.security.filter.config;

import cn.lz.security.LzCoreManager;
import cn.lz.security.arrays.UrlHashSet;
import cn.lz.security.defaults.CorsHandlerDefault;
import cn.lz.security.filter.filters.AbsInterfacesFilterBuilder;
import cn.lz.security.fun.LzFilterAuthStrategy;
import cn.lz.security.handler.CorsHandler;

/**
 * 过滤配置文件
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/7/10 21:55
 */
public final class FilterConfigurer extends FilterConfigurerBuilder<FilterConfigurer> {

    /**
     * 获取过滤前处理方法
     *
     * @return 过滤前处理方法
     */
    public LzFilterAuthStrategy getFilterBeforeHandler() {
        if (super.filterBeforeHandler == null) {
            synchronized (FilterConfigurer.class) {
                if (super.filterBeforeHandler == null) {
                    super.filterBeforeHandler = params -> {
                    };
                }
            }
        }
        return super.filterBeforeHandler;
    }

    /**
     * 获取过滤后处理方法
     *
     * @return 过滤后处理方法
     */
    public LzFilterAuthStrategy getFilterAfterHandler() {
        if (super.filterAfterHandler == null) {
            synchronized (FilterConfigurer.class) {
                if (super.filterAfterHandler == null) {
                    super.filterAfterHandler = params -> {
                    };
                }
            }
        }
        return super.filterAfterHandler;
    }

    /**
     * 获取过滤被拦截处理器
     *
     * @return 过滤被拦截处理器
     */
    public LzFilterAuthStrategy getInterceptHandler() {
        if (super.interceptHandler == null) {
            synchronized (FilterConfigurer.class) {
                if (super.interceptHandler == null) {
                    super.interceptHandler = params -> {
                    };
                }
            }
        }
        return super.interceptHandler;
    }

    /**
     * 获取跨域信息处理器
     *
     * @return 跨域信息处理器
     */
    public CorsHandler getCorsHandler() {
        if (super.corsHandler == null) {
            synchronized (FilterConfigurer.class) {
                if (super.corsHandler == null) {
                    super.corsHandler = new CorsHandlerDefault();
                }
            }
        }
        return super.corsHandler;
    }

    /**
     * 获取开始记录日志的过滤器类
     *
     * @return 过滤器类
     */
    public Class<? extends AbsInterfacesFilterBuilder> getLogBeforeFilterClass() {
        return super.filterConfig.logBeforeFilterClass;
    }

    /**
     * 获取排除的接口列表
     *
     * @return 排除的接口列表
     */
    public UrlHashSet getExcludeUrls() {
        return LzCoreManager.getFilterConfig().getExcludeUrlList();
    }

    /**
     * 获取拦截的接口列表
     *
     * @return 拦截的接口列表
     */
    public UrlHashSet getIncludeUrls() {
        final UrlHashSet filterUrlIncludeUrls = LzCoreManager.getFilterConfig().getIncludeUrlList();
        if (filterUrlIncludeUrls.isEmpty()) {
            filterUrlIncludeUrls.add("/**");
        }
        return filterUrlIncludeUrls;
    }

}
