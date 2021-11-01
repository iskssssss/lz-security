package com.sowell.security.proxy;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/01 10:07
 */
public class ProxyInfo<T> {

	private final T source;
	private final T target;

	public ProxyInfo(T source, T target) {
		this.source = source;
		this.target = target;
	}

	public T getSource() {
		return source;
	}

	public T getTarget() {
		return target;
	}
}
