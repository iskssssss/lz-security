package com.sowell.security.model;

import cn.hutool.json.JSONUtil;

import java.io.Serializable;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 16:52
 */
public abstract class AuthDetails<T> implements Serializable {
	static final long serialVersionUID = 42L;

	/**
	 * id
	 */
	private String id;
	/**
	 * appKey
	 */
	private String appKey;
	/**
	 * appSecret
	 */
	private String appSecret;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户名
	 */
	private String password;
	/**
	 * 源类名称
	 */
	private final String sourceClassName;

	public AuthDetails() {
		final Class<T> setSourceClass = this.setSourceClass();
		if (setSourceClass == null) {
			throw new RuntimeException("请在“setSourceClass()”方法中返回源类。");
		}
		this.sourceClassName = setSourceClass.getName();
	}

	public abstract Class<T> setSourceClass();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSourceClassName() {
		return sourceClassName;
	}

	public String toJson() {
		return JSONUtil.toJsonStr(this);
	}
}
