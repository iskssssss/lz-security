//package com.sowell.security.filter;
//
//import com.sowell.security.arrays.UrlHashSet;
//import com.sowell.security.auth.ICheckAccessAuthStatusHandler;
//import com.sowell.security.base.AbstractInterfacesFilter;
//import com.sowell.security.context.model.BaseRequest;
//import com.sowell.security.context.model.BaseResponse;
//import com.sowell.security.enums.RCode;
//import com.sowell.security.exception.SecurityException;
//import com.sowell.security.log.IcpLogger;
//import com.sowell.security.log.IcpLoggerUtil;
//
///**
// * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/09/16 16:11
// */
//public class AnonymousFilter extends AbstractInterfacesFilter {
//	protected final IcpLogger icpLogger = IcpLoggerUtil.getIcpLogger(AbstractInterfacesFilter.class);
//
//	private UrlHashSet anonymousUrls = null;
//	private ICheckAccessAuthStatusHandler checkAccessAuthStatusHandler;
//
//	private UrlHashSet anonymousUrls() {
//		if (this.anonymousUrls == null) {
//			this.anonymousUrls = super.filterConfigurer.getAnonymousUrls();
//		}
//		return this.anonymousUrls;
//	}
//
//	@Override
//	public void init() {
//		icpLogger.info("anonymous filter init.");
//		this.checkAccessAuthStatusHandler = super.filterConfigurer.getCheckAccessAuthStatusHandler();
//	}
//
//	@Override
//	public boolean doFilter(
//			BaseRequest<?> request,
//			BaseResponse<?> response,
//			Object... params
//	) throws SecurityException {
//		if (anonymousUrls().containsUrl(request.getRequestPath())) {
//			if (checkAccessAuthStatusHandler.check(request, response)) {
//				return headOff(request, response, RCode.ANONYMOUS);
//			}
//			return discharged(request);
//		}
//		return next(request, response, params);
//	}
//
//	@Override
//	public void destroy() {
//		icpLogger.info("anonymous filter destroy.");
//		this.checkAccessAuthStatusHandler = null;
//		this.anonymousUrls = null;
//	}
//}
