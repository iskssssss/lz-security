package com.sowell.tool.jwt.model;

import com.sowell.tool.json.JsonUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/18 16:52
 */
public abstract class AuthDetails<T> implements Serializable {
	static final long serialVersionUID = 42L;
	private final String uuid = UUID.randomUUID().toString() + "" + System.currentTimeMillis();
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

	/**
	 * 返回继承本类类的类型
	 *
	 * @return 源类
	 */
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

	@Override
	public String toString() {
		return "AuthDetails{" +
				"uuid='" + uuid + '\'' +
				", id='" + id + '\'' +
				", identifier='" + identifier + '\'' +
				", credential='" + credential + '\'' +
				", sourceClassName='" + sourceClassName + '\'' +
				'}';
	}
}
