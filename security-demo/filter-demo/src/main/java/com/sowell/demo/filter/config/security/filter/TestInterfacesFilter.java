//package com.sowell.demo.filter.auth.config.security.filter;
//
//import com.sowell.security.annotation.LogBeforeFilter;
//import com.sowell.security.exception.base.SecurityException;
//import com.sowell.security.filter.SwInterfacesFilter;
//import com.sowell.security.log.IcpLoggerUtil;
//import com.sowell.security.filter.mode.SwRequest;
//import com.sowell.security.filter.mode.SwResponse;
//
///**
// * 测试执行链
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @date 2021/10/22 17:33
// */
//public class TestInterfacesFilter extends SwInterfacesFilter {
//
//	@Override
//	public void init() {
//		IcpLoggerUtil.info(this.getClass(), "TestInterfacesFilter-init");
//	}
//
//	@Override
//	public boolean doFilter(
//			SwRequest swRequest,
//			SwResponse swResponse,
//			Object... params
//	) throws SecurityException {
//		IcpLoggerUtil.info(this.getClass(), "TestInterfacesFilter-filter");
//		return super.next(swRequest, swResponse, params);
//	}
//
//	@Override
//	public void destroy() {
//		IcpLoggerUtil.info(this.getClass(), "TestInterfacesFilter-destroy");
//	}
//}
