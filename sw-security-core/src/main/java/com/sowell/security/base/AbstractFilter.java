package com.sowell.security.base;//package com.sowell.security.base;
//
//import com.sowell.security.FilterContainer;
//import com.sowell.security.IcpManager;
//import com.sowell.security.config.FilterConfigurer;
//
//import javax.servlet.Filter;
//
///**
// * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/7/12 14:05
// */
//public abstract class AbstractFilter implements Filter {
//
//	protected final FilterConfigurer filterConfigurer;
//
//	protected AbstractFilter() {
//		filterConfigurer = IcpManager.getHttpConfigurer();
//	}
//
//	public FilterConfigurer getHttpConfigurer() {
//		return filterConfigurer;
//	}
//
//	private static AbstractFilter ABSTRACT_FILTER;
//	public static AbstractFilter getInstance() {
//		if (ABSTRACT_FILTER == null) {
//			synchronized (AbstractFilter.class) {
//				if (ABSTRACT_FILTER == null) {
//					ABSTRACT_FILTER = new FilterContainer();
//				}
//			}
//		}
//		return ABSTRACT_FILTER;
//	}
//}
