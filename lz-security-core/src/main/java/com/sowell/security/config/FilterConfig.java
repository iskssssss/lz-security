package com.sowell.security.config;

import com.sowell.security.arrays.UrlHashSet;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZ
 * @date 2022/6/1 11:53
 */
public class FilterConfig {

    /**
     * 排除URL列表
     */
    UrlHashSet excludeUrlList = new UrlHashSet();

    /**
     * 拦截URL列表
     */
    UrlHashSet includeUrlList = new UrlHashSet();

    /**
     * 获取排除URL列表
     *
     * @return 排除URL列表
     */
    public UrlHashSet getExcludeUrlList() {
        return excludeUrlList;
    }

    /**
     * 设置排除URL
     *
     * @param excludeUrlList 排除URL
     */
    public void setExcludeUrlList(UrlHashSet excludeUrlList) {
        this.excludeUrlList.addAll(excludeUrlList);
    }

    /**
     * 获取拦截URL列表
     *
     * @return 拦截URL列表
     */
    public UrlHashSet getIncludeUrlList() {
        return includeUrlList;
    }

    /**
     * 设置拦截URL
     *
     * @param includeUrlList 拦截URL
     */
    public void setIncludeUrlList(UrlHashSet includeUrlList) {
        this.includeUrlList.addAll(includeUrlList);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n          ").append("• 排除URL列表").append("：");
        for (String excludeUrl : excludeUrlList) {
            sb.append("\n          ").append("    - ").append(excludeUrl);
        }
        sb.append("\n          ").append("• 拦截URL列表").append("：");
        for (String includeUrl : includeUrlList) {
            sb.append("\n          ").append("    - ").append(includeUrl);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "FilterConfig{" +
                "excludeUrlList=" + excludeUrlList +
                ", includeUrlList=" + includeUrlList +
                '}';
    }
}
