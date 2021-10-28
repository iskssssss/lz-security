package com.sowell.tool.http.enums;

/**
 * @Author: sowell
 * @Date: 2021/08/20 10:28
 */
public enum ContentTypeEnum {
	/**
	 * FORM
	 */
	FORM("application/x-www-form-urlencoded"),
	/**
	 * JSON
	 */
	JSON("application/json");

	public final String name;

	ContentTypeEnum(String name) {
		this.name = name;
	}
}
