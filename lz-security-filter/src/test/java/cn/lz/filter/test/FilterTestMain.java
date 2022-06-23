package cn.lz.filter.test;

import cn.lz.security.filter.filters.StartFilter;
import cn.lz.security.filter.utils.FilterUtil;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 杭州设维信息技术有限公司
 * @date 2022/6/1 16:25
 */
public class FilterTestMain {

    public static void main(String[] args) {
        Class<StartFilter> startFilterClass = StartFilter.class;

        Class<FilterUtil.AccessTokenFilter> accessTokenFilterClass = FilterUtil.AccessTokenFilter.class;

        System.out.println(startFilterClass.isAssignableFrom(accessTokenFilterClass));
        System.out.println(startFilterClass.isAssignableFrom(startFilterClass));

        System.out.println(accessTokenFilterClass.isAssignableFrom(startFilterClass));
        System.out.println(startFilterClass.isAssignableFrom(startFilterClass));


    }
}
