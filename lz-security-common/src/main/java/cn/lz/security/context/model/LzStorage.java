package cn.lz.security.context.model;

import cn.lz.tool.core.string.StringUtil;
import cn.lz.tool.http.model.UserAgentInfo;
import cn.lz.security.utils.IpUtil;
import cn.lz.tool.http.UserAgentUtil;
import cn.lz.tool.reflect.BeanUtil;

import java.io.Closeable;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/10 17:29
 */
public abstract class LzStorage<T> implements Closeable {

	protected final long startRequestTime;
	protected BaseRequest<T> request;
	protected UserAgentInfo userAgentInfo = null;

	protected LzStorage(
			BaseRequest<T> request,
			long startRequestTime
	) {
		this.request = request;
		this.startRequestTime = startRequestTime;
	}

	public UserAgentInfo getUserAgentInfo() {
		if (this.userAgentInfo == null) {
			String ua = request.getHeader("User-Agent");
			if (StringUtil.isEmpty(ua)) {
				return null;
			}
			this.userAgentInfo = UserAgentUtil.getUserAgentInfo(ua);
			String clientIp = IpUtil.getIp(request);
			userAgentInfo.setIpAddr(clientIp);
		}
		return this.userAgentInfo;
	}

	public final void setAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}

	public final <AttributeType> AttributeType getAttribute(String key, Class<AttributeType> tClass) {
		final Object attribute = request.getAttribute(key);
		if (attribute == null) {
			return null;
		}
		try {
			return (AttributeType) attribute;
		} catch (Exception e) {
			if (attribute instanceof CharSequence) {
				return BeanUtil.toBean(attribute.toString(), tClass);
			}
		}
		return null;
	}

	public final Object getAttribute(String key) {
		return request.getAttribute(key);
	}

	public Long getRequestTime() {
		return System.currentTimeMillis() - startRequestTime;
	}

	public final Long getStartRequestTime() {
		return startRequestTime;
	}
}
