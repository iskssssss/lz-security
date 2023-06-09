package cn.lz.tool.http.enums;

import cn.lz.tool.core.string.StringUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lz
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

	public boolean is(RequestMethodEnum requestMethodEnum) {
		return is(requestMethodEnum.name);
	}

	public boolean not(RequestMethodEnum requestMethodEnum) {
		return not(requestMethodEnum.name);
	}

	public static String allJoin() {
		return allJoin(", ");
	}

	public static String allJoin(String split) {
		Set<String> allSet = new HashSet<>();
		for (RequestMethodEnum value : RequestMethodEnum.values()) {
			allSet.add(value.name);
		}
		return String.join(split, allSet);
	}

	public static RequestMethodEnum to(String name) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		for (RequestMethodEnum value : RequestMethodEnum.values()) {
			if (value.name.intern() == name.toUpperCase(Locale.ROOT).intern()) {
				return value;
			}
		}
		return null;
	}
}
