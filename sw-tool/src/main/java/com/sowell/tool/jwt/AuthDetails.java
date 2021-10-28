package com.sowell.tool.jwt;

import com.sowell.tool.json.JsonUtil;

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
	 * ID
	 */
	private String id;
	/**
	 * 标识
	 */
	private String identifier;
	/**
	 * 凭据
	 */
	private String credential;

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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getSourceClassName() {
		return sourceClassName;
	}

	public String toJson() {
		return JsonUtil.toJsonString(this);
	}
}
