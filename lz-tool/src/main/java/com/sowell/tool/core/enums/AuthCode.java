package com.sowell.tool.core.enums;

import com.sowell.tool.json.JsonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/03 15:07
 */
public enum AuthCode implements ICode {
	/**
	 * 操作成功
	 */
	SUCCESS(0, "操作成功。"),
	/**
	 * 未查询到相关数据
	 */
	EMPTY(1, "未查询到相关数据。"),
	/**
	 * 参数有误
	 */
	ERROR_PARAMS(2, "参数有误。"),
	/**
	 * 发生未知错误
	 */
	UNKNOWN_MISTAKE(3, "发生未知错误。"),
	/**
	 * 请求失败
	 */
	REQUEST_ERROR(4, "请求失败。"),
	/**
	 * 设置读取请求参数字符编码错误
	 */
	UNSUPPORTED_ENCODING_EXCEPTION(5, "设置读取请求参数字符编码错误。"),
	/**
	 * 内部服务器错误
	 */
	INTERNAL_SERVER_ERROR(500, "内部服务器错误"),

	BAD_CREDENTIAL(441, "您输入的账户名或密码不正确"),
	ACCOUNT_NOT_EXISTS(442, "您输入的账户名或密码不正确."),
	PASSWORD_NOT_MATCHES(443, "您输入的账户名或密码不正确."),
	VERIFY_CODE_ERROR(445, "验证码错误，请重新输入."),

	/**
	 * 访问该URL只可在匿名状态下。
	 */
	ANONYMOUS(461, "访问该URL只可在匿名状态下。"),
	/**
	 * 访问该URL需认证。
	 */
	AUTHORIZATION(462, "访问该URL需认证。"),

	ACCOUNT_NOT_VERIFICATION(801, "当前账号未验证，请验证后登录."),
	ACCOUNT_LOCKED(802, "账号已锁定,请联系管理员."),
	CREDENTIALS_EXPIRED(803, "证书已过期,请联系管理员."),
	ACCOUNT_EXPIRED(804, "账号已过期,请联系管理员."),
	ACCOUNT_DISABLE(805, "账号已封禁,请联系管理员.");

	private Integer code;
	private String message;

	AuthCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() throws IllegalAccessException {
		Map<String, Object> resultJson = new LinkedHashMap<>();
		final Field[] fields = RCode.class.getDeclaredFields();
		for (Field declaredField : fields) {
			declaredField.setAccessible(true);
			if (Modifier.isStatic(declaredField.getModifiers())) {
				continue;
			}
			final String name = declaredField.getName();
			final Object value = declaredField.get(this);
			resultJson.put(name, value);
		}
		return JsonUtil.toJsonString(resultJson);
	}
}
