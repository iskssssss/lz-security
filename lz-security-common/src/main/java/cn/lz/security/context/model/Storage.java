package cn.lz.security.context.model;

import cn.lz.security.utils.IpUtil;
import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.UserAgentUtil;
import cn.lz.tool.http.model.UserAgentInfo;

import java.io.Closeable;

/**
 * 存储器
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public abstract class Storage<T> implements Closeable {

	/**
	 * 请求时间
	 */
	protected final long startRequestTime;
	/**
	 * 请求流
	 */
	protected final BaseRequest<T> request;
	/**
	 * 请求端信息
	 */
	protected UserAgentInfo userAgentInfo = null;

	protected Storage(BaseRequest<T> request, long startRequestTime) {
		this.request = request;
		this.startRequestTime = startRequestTime;
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public BaseRequest<T> getRequest() {
		return request;
	}

	/**
	 * 获取 请求端信息
	 *
	 * @return 请求端信息
	 */
	public UserAgentInfo getUserAgentInfo() {
		if (this.userAgentInfo == null) {
			synchronized (Storage.class) {
				if (this.userAgentInfo == null) {
					String ua = request.getHeader("User-Agent");
					if (StringUtil.isEmpty(ua)) {
						return null;
					}
					this.userAgentInfo = UserAgentUtil.getUserAgentInfo(ua, IpUtil.getIp(request));
				}
			}
		}
		return this.userAgentInfo;
	}

	/**
	 * 获取本次请求耗时
	 *
	 * @return 耗时
	 */
	public Long getRequestTime() {
		return System.currentTimeMillis() - startRequestTime;
	}

	/**
	 * 获取请求开始时间
	 *
	 * @return 请求开始时间
	 */
	public final Long getStartRequestTime() {
		return startRequestTime;
	}
}
