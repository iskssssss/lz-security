package cn.lz.security.context.model;

import cn.lz.security.exception.base.SecurityException;
import cn.lz.tool.core.enums.RCode;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/17 10:33
 */
public abstract class BaseResponse<T> {

	protected T response;

	protected String responseContentType;

	protected boolean encrypt;

	public BaseResponse() {
	}

	public BaseResponse(T response) {
		this.response = response;
	}

	/**
	 * 获取响应流
	 *
	 * @return 响应流
	 */
	public final T getResponse() {
		if (response == null) {
			throw new SecurityException(RCode.REQUEST_ERROR);
		}
		return response;
	}

	/**
	 * 设置响应流
	 *
	 * @param response 响应流
	 */
	public final void setResponse(T response) {
		this.response = response;
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

	public void setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	/**
	 * 设置请求头信息
	 *
	 * @param name  键
	 * @param value 值
	 * @return 响应流
	 */
	public abstract BaseResponse<T> setHeader(String name, String value);

	/**
	 * 向客户端打印信息
	 *
	 * @param message 信息
	 */
	public abstract void print(String message);

	/**
	 * 向客户端打印信息
	 *
	 * @param bytes 字节数组
	 */
	public abstract void print(byte[] bytes);

	/**
	 * 向客户端打印信息
	 *
	 * @param bytes 字节数组
	 * @param off   偏移量
	 * @param len   长度
	 */
	public abstract void print(byte[] bytes, int off, int len);

	/**
	 * 获取响应流中的数据字节数组
	 *
	 * @return 数据字节数组
	 */
	public abstract byte[] getResponseDataBytes();

	/**
	 * 获取响应流响应状态
	 *
	 * @return 响应状态
	 */
	public abstract int getStatus();

	/**
	 * 设置响应流响应状态
	 *
	 * @param value 响应状态
	 */
	public abstract void setStatus(int value);

	/**
	 * 添加Cookie
	 *
	 * @param name   名称
	 * @param value  值
	 * @param path   地址
	 * @param domain 作用域
	 * @param expiry 过期时间
	 */
	public abstract void addCookie(String name, String value, String path, String domain, int expiry);

	/**
	 * 删除Cookie
	 *
	 * @param name 名称
	 */
	public abstract void removeCookie(String name);
}
