package com.sowell.security.utils;

import com.sowell.security.IcpManager;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.exception.AccountNotExistException;
import com.sowell.security.exception.HeaderNotAccessTokenException;
import com.sowell.security.exception.SecurityException;
import com.sowell.security.filter.SwInterfacesFilter;
import com.sowell.security.fun.LambdaFunctional;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.mode.SwRequest;
import com.sowell.security.mode.SwResponse;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.http.model.UserAgentInfo;

import java.util.List;

/**
 * 预置过滤器集合
 * <p>可通过此工具类获取预置的过滤器</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/28 16:00
 */
public class FilterUtil {

	/**
	 * AccessToken过滤器
	 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
	 *
	 * @param end 成功是否直接返回，不进行下一步过滤
	 * @return AccessToken过滤器
	 */
	public static SwInterfacesFilter accessTokenFilter(boolean end) {
		return new SwInterfacesFilter() {
			@Override
			public void init() { IcpLoggerUtil.info(this.getClass(), "AccessTokenFilter-init"); }

			@Override
			public boolean doFilter(SwRequest swRequest, SwResponse swResponse, Object... params) throws SecurityException {
				IcpLoggerUtil.info(this.getClass(), "AccessTokenFilter-filter");
				try {
					AccessTokenUtil.getAuthDetails();
				} catch (HeaderNotAccessTokenException headerNotAccessTokenException) {
					return no(RCode.HEADER_NOT_ACCESS_TOKEN);
				} catch (AccountNotExistException accountNotExistException) {
					return no(RCode.TOKEN_EXPIRE);
				}
				if (end) {
					return yes();
				}
				return next(swRequest, swResponse, params);
			}

			@Override
			public void destroy() { IcpLoggerUtil.info(this.getClass(), "AccessTokenFilter-destroy"); }
		};
	}

	/**
	 * ip过滤器
	 * <p>此过滤器可排除非限定Ip之外的接口</p>
	 *
	 * @param end          成功是否直接返回，不进行下一步过滤
	 * @param ipListGetFun ip列表获取方法
	 * @return ip过滤器
	 */
	public static SwInterfacesFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end) {
		return new SwInterfacesFilter() {
			@Override
			public void init() { IcpLoggerUtil.info(this.getClass(), "IpFilter-init"); }

			@Override
			public boolean doFilter(SwRequest swRequest, SwResponse swResponse, Object... params) throws SecurityException {
				IcpLoggerUtil.info(this.getClass(), "IpFilter-filter");
				final List<String> ipList = ipListGetFun.run(params);
				if (ipList == null || ipList.isEmpty()) {
					return no(RCode.NOT_WHITE_IP);
				}
				final String ipAddr = swRequest.getRemoteAddr();
				boolean isYes = false;
				if (ipList.contains("*") || ipList.contains(ipAddr)) {
					isYes = true;
				}
				if (!isYes) {
					for (String ip : ipList) {
						if (!IpUtil.checkIp(ip, ipAddr)) {
							continue;
						}
						isYes = true;
						break;
					}
				}
				if (isYes) {
					if (end) {
						return yes();
					}
					return next(swRequest, swResponse, params);
				}
				return no(RCode.NOT_WHITE_IP);
			}

			@Override
			public void destroy() { IcpLoggerUtil.info(this.getClass(), "IpFilter-destroy"); }
		};
	}

	/**
	 * 请求接口过滤器
	 * <p>此过滤器可以限定客户端可访问的接口</p>
	 *
	 * @param end                 成功是否直接返回，不进行下一步过滤
	 * @param interfaceListGetFun 接口列表获取方法
	 * @return 请求接口过滤器
	 */
	public static SwInterfacesFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end) {
		return new SwInterfacesFilter() {
			@Override
			public void init() { IcpLoggerUtil.info(this.getClass(), "RequestInterfaceFilter-init"); }

			@Override
			public boolean doFilter(SwRequest swRequest, SwResponse swResponse, Object... params) throws SecurityException {
				IcpLoggerUtil.info(this.getClass(), "RequestInterfaceFilter-filter");
				final UrlHashSet interfaceList = interfaceListGetFun.run(params);
				if (interfaceList == null || interfaceList.isEmpty()) {
					return no(RCode.NOT_ACCESS_INTERFACE);
				}
				final String requestPath = swRequest.getRequestPath();
				boolean isYes = false;
				if (interfaceList.containsUrl(requestPath)) {
					isYes = true;
				}
				if (isYes) {
					if (end) {
						return yes();
					}
					return next(swRequest, swResponse, params);
				}
				return no(RCode.NOT_ACCESS_INTERFACE);
			}

			@Override
			public void destroy() { IcpLoggerUtil.info(this.getClass(), "RequestInterfaceFilter-destroy"); }
		};
	}
}
