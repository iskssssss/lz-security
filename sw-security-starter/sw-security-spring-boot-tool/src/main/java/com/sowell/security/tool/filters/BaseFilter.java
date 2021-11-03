package com.sowell.security.tool.filters;

import com.sowell.security.IcpConstant;
import com.sowell.security.IcpManager;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.filter.IcpFilter;
import com.sowell.security.filter.utils.ServletUtil;
import com.sowell.security.fun.IcpFilterAuthStrategy;
import com.sowell.security.handler.RequestDataEncryptHandler;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.tool.context.IcpSpringContextHolder;
import com.sowell.security.tool.mode.SwRequest;
import com.sowell.security.tool.mode.SwResponse;
import com.sowell.tool.core.bytes.ByteUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.model.RequestResult;
import com.sowell.tool.http.enums.ContentTypeEnum;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/03 11:16
 */
public abstract class BaseFilter implements Filter {

	/**
	 * 过滤
	 *
	 * @param swRequest  请求流
	 * @param swResponse 响应流
	 * @return 过滤结果
	 * @throws Exception 异常
	 */
	public abstract boolean doFilter(SwRequest swRequest, SwResponse swResponse) throws Exception;

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		IcpSpringContextHolder.setContext(request, response, System.currentTimeMillis(), (swRequest, swResponse) -> {
			SecurityException securityException = null;
			try {
				// TODO 解密处理
				// 过滤处理
				if (this.doFilter(swRequest, swResponse)) {
					chain.doFilter(swRequest.getRequest(), swResponse.getResponse());
				}
				// 加密处理
				if (swResponse.isEncrypt()) {
					try {
						byte[] encryptBytes;
						final RequestDataEncryptHandler requestDataEncryptHandler = IcpManager.getRequestDataEncryptHandler();
						final byte[] responseDataBytes = swResponse.getResponseDataBytes();
						final RequestResult resultObject = ByteUtil.toObject(responseDataBytes, RequestResult.class);
						if (resultObject != null) {
							resultObject.setData(requestDataEncryptHandler.encrypt(ByteUtil.toBytes(resultObject.getData())));
							encryptBytes = ByteUtil.toBytes(resultObject.toJson());
						} else {
							RequestResult requestResult = new RequestResult();
							requestResult.setData(requestDataEncryptHandler.encrypt(responseDataBytes));
							encryptBytes = ByteUtil.toBytes(requestResult.toJson());
						}
						ServletUtil.printResponse(swResponse, ContentTypeEnum.JSON.name, encryptBytes);
					} catch (Exception e) {
						throw new SecurityException(RCode.DATA_ENCRYPT_FAILED);
					}
				}
			}
			catch (Exception e) {
				// 错误处理
				if (e instanceof SecurityException) {
					securityException = (SecurityException) e;
				} else if (e.getCause() instanceof SecurityException) {
					securityException = (SecurityException) e.getCause();
				} else {
					securityException = new SecurityException(RCode.INTERNAL_SERVER_ERROR, e);
				}
				if (securityException.getCause() != null){
					IcpLoggerUtil.error(getClass(), securityException.getMessage(), securityException);
				} else {
					IcpLoggerUtil.error(getClass(), e.getMessage(), e);
				}
			}
			finally {
				final Object handlerData = IcpFilter.getFilterDataHandler().handler(swRequest, swResponse, securityException);
				if (securityException != null && handlerData != null) {
					if (handlerData instanceof RCode) {
						ServletUtil.printResponse(IcpSpringContextHolder.getResponse(), ContentTypeEnum.JSON.name, (RCode) handlerData);
					} else {
						ServletUtil.printResponse(IcpSpringContextHolder.getResponse(), ContentTypeEnum.JSON.name, (byte[]) handlerData);
					}
				}
				final Object logSwitch = swRequest.getAttribute(IcpConstant.LOG_SWITCH);
				if (logSwitch != null) {
					IcpFilter.getFilterLogHandler().afterHandler(swRequest, swResponse, swRequest.getAttribute(IcpConstant.LOG_ENTITY_CACHE_KEY), securityException);
				}
			}
		});
	}
}
