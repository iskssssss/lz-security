package com.sowell.tool.core.model;

/**
 * 存放响应信息
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/26 16:16
 */
public class RequestResult extends BaseModel {
	private Integer code;
	private Object data;
	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "RequestResult{" +
				"code=" + code +
				", data=" + data +
				", message='" + message + '\'' +
				'}';
	}
}
