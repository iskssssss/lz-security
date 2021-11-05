package com.sowell.security.context.model;

import com.sowell.security.exception.base.SecurityException;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.reflect.BeanUtil;
import com.sowell.tool.reflect.model.ControllerMethod;

import java.util.List;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 10:33
 */
public abstract class BaseRequest<T> {

	protected T request;
	protected boolean encrypt;
	private ControllerMethod controllerMethod;

	public BaseRequest() {
	}

	public BaseRequest(T request) {
		this.request = request;
	}

	/**
	 * 获取请求流
	 *
	 * @return 请求流
	 */
	public final T getRequest() {
		if (request == null) {
			throw new SecurityException(RCode.REQUEST_ERROR);
		}
		return request;
	}

	/**
	 * 设置请求流
	 *
	 * @param request 请求流
	 */
	public final void setRequest(T request) {
		this.request = request;
	}

	/**
	 * 设置是否加密
	 *
	 * @param encrypt 是否加密
	 */
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	/**
	 * 获取是否加密
	 *
	 * @return 是否加密
	 */
	public boolean isEncrypt() {
		return this.encrypt;
	}

	/**
	 * 获取当前完整请求地址
	 *
	 * @return 完整请求地址
	 */
	public abstract String getRequestURL();

	/**
	 * 获取当前请求接口地址
	 *
	 * @return 请求接口地址
	 */
	public abstract String getRequestPath();

	/**
	 * 路径匹配
	 * <p>当前请求接口地址是否和{path}相匹配</p>
	 *
	 * @param path 待匹配地址
	 * @return 是否匹配
	 */
	public abstract boolean matchUrl(String path);

	/**
	 * 获取请求的HTTP方法名称
	 *
	 * @return HTTP方法名称
	 */
	public abstract String getMethod();

	/**
	 * 获取请求头中的信息
	 *
	 * @param name 键
	 * @return 值
	 */
	public abstract String getHeader(String name);

	/**
	 * 在请求中存储信息
	 *
	 * @param key   键
	 * @param value 值
	 */
	public abstract void setAttribute(String key, Object value);

	/**
	 * 从请求的存储中获取值
	 *
	 * @param key 键
	 * @return 值
	 */
	public abstract Object getAttribute(String key);

	/**
	 * 从请求的存储中获取值并转换为相应的类型
	 *
	 * @param key    键
	 * @param tClass 转换后的类型
	 * @param <V>    类型
	 * @return 值
	 */
	public <V> V getAttribute(String key, Class<V> tClass) {
		final Object attribute = this.getAttribute(key);
		if (attribute == null) {
			return null;
		}
		try {
			return (V) attribute;
		} catch (Exception e) {
			if (attribute instanceof CharSequence) {
				return BeanUtil.toBean(attribute.toString(), tClass);
			}
		}
		return null;
	}

	/**
	 * 获取请求的存储中所有键的名称
	 *
	 * @return 键的名称列表
	 */
	public abstract List<String> getAttributeNames();

	/**
	 * 从请求的存储中删除{key}值
	 *
	 * @param key 键
	 */
	public abstract void removeAttribute(String key);

	/**
	 * 删除所有请求中存储信
	 */
	public final void removeAllAttribute() {
		final List<String> attributeNames = this.getAttributeNames();
		for (String attributeName : attributeNames) {
			this.removeAttribute(attributeName);
		}
	}

	/**
	 * 获取body中的数据
	 *
	 * @return 字节数组
	 */
	public abstract byte[] getBodyBytes();

	/**
	 * 从cookie中获取值
	 *
	 * @param name 键名
	 * @return 值
	 */
	public abstract String getCookieValue(String name);

	/**
	 * 获取发送请求的客户端的IP地址的字符串
	 *
	 * @return IP地址
	 */
	public abstract String getRemoteAddr();

	public ControllerMethod getControllerMethod() {
		return this.controllerMethod;
	}

	public void setControllerMethod(ControllerMethod controllerMethod) {
		this.controllerMethod = controllerMethod;
	}
}
