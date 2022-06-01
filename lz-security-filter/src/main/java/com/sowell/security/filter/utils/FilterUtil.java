package com.sowell.security.filter.utils;

import com.sowell.security.annotation.LogBeforeFilter;
import com.sowell.security.arrays.UrlHashSet;
import com.sowell.security.context.model.BaseRequest;
import com.sowell.security.context.model.BaseResponse;
import com.sowell.security.exception.auth.AccountNotExistException;
import com.sowell.security.exception.HeaderNotAccessTokenException;
import com.sowell.security.exception.base.SecurityException;
import com.sowell.security.filter.filters.AbsInterfacesFilterBuilder;
import com.sowell.security.fun.LambdaFunctional;
import com.sowell.security.log.LzLoggerUtil;
import com.sowell.security.token.AccessTokenUtil;
import com.sowell.security.utils.IpUtil;
import com.sowell.tool.core.enums.RCode;

import java.util.List;

/**
 * 预置过滤器集合
 * <p>可通过此工具类获取预置的过滤器</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/28 16:00
 */
public class FilterUtil {
	private static AccessTokenFilter accessTokenFilter;
	private static IpFilter ipFilter;
	private static RequestInterfaceFilter requestInterfaceFilter;

	/**
	 * AccessToken过滤器
	 * <p>进入此过滤器会先记录日志</p>
	 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
	 *
	 * @return AccessToken过滤器
	 */
	public static AccessTokenFilter accessTokenFilterOnLogBefore() {
		return accessTokenFilter(false, true);
	}

	/**
	 * AccessToken过滤器
	 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
	 *
	 * @return AccessToken过滤器
	 */
	public static AccessTokenFilter accessTokenFilter() {
		return accessTokenFilter(false, false);
	}

	/**
	 * AccessToken过滤器
	 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
	 *
	 * @param end 成功是否直接返回，不进行下一步过滤
	 * @return AccessToken过滤器
	 */
	public static AccessTokenFilter accessTokenFilter(boolean end) {
		return accessTokenFilter(end, false);
	}

	/**
	 * AccessToken过滤器
	 * <p>此过滤器可以过滤未携带有效AccessToken的请求</p>
	 *
	 * @param end       成功是否直接返回，不进行下一步过滤
	 * @param logBefore 过滤前是否记录日志
	 * @return AccessToken过滤器
	 */
	public static AccessTokenFilter accessTokenFilter(boolean end, boolean logBefore) {
		if (accessTokenFilter == null) {
			if (logBefore) {
				accessTokenFilter = new AccessTokenFilterOnLogBeforeFilter(end);
			} else {
				accessTokenFilter = new AccessTokenFilter(end);
			}
		}
		return accessTokenFilter;
	}

	/**
	 * ip过滤器
	 * <p>进入此过滤器会先记录日志</p>
	 * <p>此过滤器可排除非限定Ip之外的接口</p>
	 *
	 * @param ipListGetFun ip列表获取方法
	 * @return ip过滤器
	 */
	public static IpFilter ipFilterOnLogBefore(LambdaFunctional<List<String>> ipListGetFun) {
		return ipFilter(ipListGetFun, false, true);
	}

	/**
	 * ip过滤器
	 * <p>此过滤器可排除非限定Ip之外的接口</p>
	 *
	 * @param ipListGetFun ip列表获取方法
	 * @return ip过滤器
	 */
	public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun) {
		return ipFilter(ipListGetFun, false);
	}

	/**
	 * ip过滤器
	 * <p>此过滤器可排除非限定Ip之外的接口</p>
	 *
	 * @param ipListGetFun ip列表获取方法
	 * @param end          成功是否直接返回，不进行下一步过滤
	 * @return ip过滤器
	 */
	public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end) {
		return ipFilter(ipListGetFun, end, false);
	}

	/**
	 * ip过滤器
	 * <p>此过滤器可排除非限定Ip之外的接口</p>
	 *
	 * @param ipListGetFun ip列表获取方法
	 * @param end          成功是否直接返回，不进行下一步过滤
	 * @param logBefore    过滤前是否记录日志
	 * @return ip过滤器
	 */
	public static IpFilter ipFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end, boolean logBefore) {
		if (ipFilter == null) {
			if (logBefore) {
				ipFilter = new IpFilterOnLogBeforeFilter(ipListGetFun, end);
			} else {
				ipFilter = new IpFilter(ipListGetFun, end);
			}
		}
		return ipFilter;
	}

	/**
	 * 请求接口过滤器
	 * <p>进入此过滤器会先记录日志</p>
	 * <p>此过滤器可以限定客户端可访问的接口</p>
	 *
	 * @param interfaceListGetFun 接口列表获取方法
	 * @return 请求接口过滤器
	 */
	public static RequestInterfaceFilter requestInterfaceFilterOnLogBefore(LambdaFunctional<UrlHashSet> interfaceListGetFun) {
		return requestInterfaceFilter(interfaceListGetFun, false, true);
	}

	/**
	 * 请求接口过滤器
	 * <p>此过滤器可以限定客户端可访问的接口</p>
	 *
	 * @param interfaceListGetFun 接口列表获取方法
	 * @return 请求接口过滤器
	 */
	public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun) {
		return requestInterfaceFilter(interfaceListGetFun, false);
	}

	/**
	 * 请求接口过滤器
	 * <p>此过滤器可以限定客户端可访问的接口</p>
	 *
	 * @param interfaceListGetFun 接口列表获取方法
	 * @param end                 成功是否直接返回，不进行下一步过滤
	 * @return 请求接口过滤器
	 */
	public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end) {
		return requestInterfaceFilter(interfaceListGetFun, end, false);
	}

	/**
	 * 请求接口过滤器
	 * <p>此过滤器可以限定客户端可访问的接口</p>
	 *
	 * @param interfaceListGetFun 接口列表获取方法
	 * @param end                 成功是否直接返回，不进行下一步过滤
	 * @param logBefore           过滤前是否记录日志
	 * @return 请求接口过滤器
	 */
	public static RequestInterfaceFilter requestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end, boolean logBefore) {
		if (requestInterfaceFilter == null) {
			if (logBefore) {
				requestInterfaceFilter = new RequestInterfaceFilterOnLogBeforeFilter(interfaceListGetFun, end);
			} else {
				requestInterfaceFilter = new RequestInterfaceFilter(interfaceListGetFun, end);
			}
		}
		return requestInterfaceFilter;
	}

	public static class AccessTokenFilter extends AbsInterfacesFilterBuilder {
		private final boolean end;

		public AccessTokenFilter(boolean end) {
			this.end = end;
		}

		@Override
		public void init() {
			LzLoggerUtil.info(AccessTokenFilter.class, "AccessTokenFilter-init");
		}

		@Override
		public boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
			LzLoggerUtil.info(AccessTokenFilter.class, "AccessTokenFilter-filter");
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
			return next(request, response, params);
		}

		@Override
		public void destroy() {
			LzLoggerUtil.info(AccessTokenFilter.class, "AccessTokenFilter-destroy");
		}
	}

	public static class IpFilter extends AbsInterfacesFilterBuilder {
		private final LambdaFunctional<List<String>> ipListGetFun;
		private final boolean end;

		public IpFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end) {
			this.ipListGetFun = ipListGetFun;
			this.end = end;
		}

		@Override
		public void init() {
			LzLoggerUtil.info(IpFilter.class, "IpFilter-init");
		}

		@Override
		public boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
			LzLoggerUtil.info(IpFilter.class, "IpFilter-filter");
			final List<String> ipList = ipListGetFun.run(params);
			if (ipList == null || ipList.isEmpty()) {
				return no(RCode.NOT_WHITE_IP);
			}
			final String ipAddr = request.getRemoteAddr();
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
				return next(request, response, params);
			}
			return no(RCode.NOT_WHITE_IP);
		}

		@Override
		public void destroy() {
			LzLoggerUtil.info(IpFilter.class, "IpFilter-destroy");
		}
	}

	public static class RequestInterfaceFilter extends AbsInterfacesFilterBuilder {
		private final LambdaFunctional<UrlHashSet> interfaceListGetFun;
		private final boolean end;

		public RequestInterfaceFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end) {
			this.interfaceListGetFun = interfaceListGetFun;
			this.end = end;
		}

		@Override
		public void init() {
			LzLoggerUtil.info(RequestInterfaceFilter.class, "RequestInterfaceFilter-init");
		}

		@Override
		public boolean doFilter(BaseRequest<?> request, BaseResponse<?> response, Object... params) throws SecurityException {
			LzLoggerUtil.info(RequestInterfaceFilter.class, "RequestInterfaceFilter-filter");
			final UrlHashSet interfaceList = interfaceListGetFun.run(params);
			if (interfaceList == null || interfaceList.isEmpty()) {
				return no(RCode.NOT_ACCESS_INTERFACE);
			}
			final String requestPath = request.getRequestPath();
			boolean isYes = false;
			if (interfaceList.containsPath(requestPath)) {
				isYes = true;
			}
			if (isYes) {
				if (end) {
					return yes();
				}
				return next(request, response, params);
			}
			return no(RCode.NOT_ACCESS_INTERFACE);
		}

		@Override
		public void destroy() {
			LzLoggerUtil.info(RequestInterfaceFilter.class, "RequestInterfaceFilter-destroy");
		}
	}

	@LogBeforeFilter
	static class AccessTokenFilterOnLogBeforeFilter extends AccessTokenFilter {

		public AccessTokenFilterOnLogBeforeFilter(boolean end) {
			super(end);
		}
	}

	@LogBeforeFilter
	static class IpFilterOnLogBeforeFilter extends IpFilter {

		public IpFilterOnLogBeforeFilter(LambdaFunctional<List<String>> ipListGetFun, boolean end) {
			super(ipListGetFun, end);
		}
	}

	@LogBeforeFilter
	static class RequestInterfaceFilterOnLogBeforeFilter extends RequestInterfaceFilter {

		public RequestInterfaceFilterOnLogBeforeFilter(LambdaFunctional<UrlHashSet> interfaceListGetFun, boolean end) {
			super(interfaceListGetFun, end);
		}
	}
}
