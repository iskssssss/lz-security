package com.sowell.tool.http.enums;

/**
 * @Author: sowell
 * @Date: 2021/08/19 20:03
 */
public enum RequestMethodEnum {

	/**
	 * GET
	 */
	GET("GET"),
	/**
	 * POST
	 */
	POST("POST"),
	/**
	 * HEAD
	 */
	HEAD("HEAD"),
	/**
	 * OPTIONS
	 */
	OPTIONS("OPTIONS"),
	/**
	 * PUT
	 */
	PUT("PUT"),
	/**
	 * DELETE
	 */
	DELETE("DELETE"),
	/**
	 * TRACE
	 */
	TRACE("TRACE"),
	/**
	 * CONNECT
	 */
	CONNECT("CONNECT"),
	/**
	 * PATCH
	 */
	PATCH("PATCH");

	public final String name;

	RequestMethodEnum(String name) {
		this.name = name;
	}

	public boolean is(String name) {
		return this.name.equals(name);
	}

	public boolean not(String name) {
		return !is(name);
	}
}
