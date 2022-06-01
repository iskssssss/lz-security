//package com.sowell.security.filter.filters;
//
//import com.sowell.security.LzConstant;
//import com.sowell.security.LzManager;
//import com.sowell.security.tool.context.LzSpringContextHolder;
//import com.sowell.security.exception.base.SecurityException;
//import com.sowell.security.filter.LzFilter;
//import com.sowell.security.fun.LzFilterAuthStrategy;
//import com.sowell.security.handler.RequestDataEncryptHandler;
//import com.sowell.security.log.LzLoggerUtil;
//import com.sowell.security.tool.mode.SwRequest;
//import com.sowell.security.tool.mode.SwResponse;
//import com.sowell.security.utils.ServletUtil;
//import com.sowell.tool.core.bytes.ByteUtil;
//import com.sowell.tool.core.enums.HttpStatus;
//import com.sowell.tool.core.enums.RCode;
//import com.sowell.tool.core.model.RequestResult;
//import com.sowell.tool.http.enums.ContentTypeEnum;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
///**
// * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
// * @ClassName:
// * @Descripton:
// * @Author: 孔胜
// * @Date: 2021/09/12 12:38
// */
//public abstract class BaseFilter implements Filter {
//
//	/**
//	 * 过滤前处理
//	 */
//	protected LzFilterAuthStrategy filterBeforeHandler;
//	/**
//	 * 过滤后处理
//	 */
//	protected LzFilterAuthStrategy filterAfterHandler;
//
//	/**
//	 * 过滤
//	 *
//	 * @param swRequest  请求流
//	 * @param swResponse 响应流
//	 * @return 过滤结果
//	 * @throws Exception 异常
//	 */
//	public abstract boolean doFilter(SwRequest swRequest, SwResponse swResponse) throws Exception;
//
//	@Override
//	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
//		LzSpringContextHolder.setContext(request, response, System.currentTimeMillis(), (swRequest, swResponse) -> {
//			SecurityException securityException = null;
//			try {
//				// TODO 解密处理
//				// 过滤处理
//				if (filterHandler(swRequest, swResponse)) {
//					chain.doFilter(swRequest.getRequest(), swResponse.getResponse());
//				}
//				// 加密处理
//				if (swResponse.isEncrypt()) {
//					try {
//						byte[] encryptBytes;
//						final RequestDataEncryptHandler requestDataEncryptHandler = LzManager.getRequestDataEncryptHandler();
//						final byte[] responseDataBytes = swResponse.getResponseDataBytes();
//						final RequestResult resultObject = ByteUtil.toObject(responseDataBytes, RequestResult.class);
//						if (resultObject != null) {
//							resultObject.setData(requestDataEncryptHandler.encrypt(ByteUtil.toBytes(resultObject.getData())));
//							encryptBytes = ByteUtil.toBytes(resultObject.toJson());
//						} else {
//							RequestResult requestResult = new RequestResult();
//							requestResult.setData(requestDataEncryptHandler.encrypt(responseDataBytes));
//							encryptBytes = ByteUtil.toBytes(requestResult.toJson());
//						}
//						ServletUtil.printResponse(swResponse, ContentTypeEnum.JSON.name, encryptBytes);
//					} catch (Exception e) {
//						throw new SecurityException(RCode.DATA_ENCRYPT_FAILED);
//					}
//				}
//			}
//			catch (Exception e) {
//				// 错误处理
//				if (e instanceof SecurityException) {
//					securityException = (SecurityException) e;
//				} else if (e.getCause() instanceof SecurityException) {
//					securityException = (SecurityException) e.getCause();
//				} else {
//					securityException = new SecurityException(RCode.INTERNAL_SERVER_ERROR, e);
//				}
//				if (securityException.getCause() != null){
//					LzLoggerUtil.error(getClass(), securityException.getMessage(), securityException);
//				} else {
//					LzLoggerUtil.error(getClass(), e.getMessage(), e);
//				}
//			}
//			finally {
//				final Object handlerData = LzFilter.getFilterDataHandler().handler(swRequest, swResponse, securityException);
//				if (securityException != null && handlerData != null) {
//					if (handlerData instanceof RCode) {
//						ServletUtil.printResponse(LzSpringContextHolder.getResponse(), ContentTypeEnum.JSON.name, (RCode) handlerData);
//					} else {
//						ServletUtil.printResponse(LzSpringContextHolder.getResponse(), ContentTypeEnum.JSON.name, (byte[]) handlerData);
//					}
//				}
//				final Object logSwitch = swRequest.getAttribute(LzConstant.LOG_SWITCH);
//				if (logSwitch != null) {
//					LzFilter.getFilterLogHandler().afterHandler(swRequest, swResponse, swRequest.getAttribute(LzConstant.LOG_ENTITY_CACHE_KEY), securityException);
//				}
//			}
//		});
//	}
//
//	/**
//	 * 过滤处理
//	 *
//	 * @param swRequest  swRequest
//	 * @param swResponse swResponse
//	 * @return 是否放行
//	 */
//	private boolean filterHandler(SwRequest swRequest, SwResponse swResponse) throws SecurityException {
//		try {
//			final String requestPath = swRequest.getRequestPath();
//			// 判断当前访问地址 (是否是开放地址 or 是否在拦截地址中)
//			final boolean isIncludeUrl = !LzFilter.getFilterConfigurer().getIncludeUrls().containsUrl(requestPath);
//			final boolean isExcludeUrl = LzFilter.getFilterConfigurer().getExcludeUrls().containsUrl(requestPath);
//			if (isIncludeUrl || isExcludeUrl) {
//				return true;
//			}
//			// 过滤前处理
//			this.filterBeforeHandler.run();
//			// 过滤
//			boolean filterResult = this.doFilter(swRequest, swResponse);
//			// 过滤后处理
//			this.filterAfterHandler.run();
//			return filterResult;
//		} catch (Exception e) {
//			if (e instanceof SecurityException) {
//				throw ((SecurityException) e);
//			} else if (e.getCause() instanceof SecurityException) {
//				throw (SecurityException) e.getCause();
//			} else {
//				throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "过滤异常", e);
//			}
//		}
//	}
//}
