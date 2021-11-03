package com.sowell.demo.model;

import com.sowell.tool.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/25 12:00
 */
public class AccessTokenVO extends BaseModel {
	/**
	 * AccessToken
	 */
	@ApiModelProperty("AccessToken")
	private Object accessToken;
	/**
	 * 过期时间
	 */
	@ApiModelProperty("过期时间（毫秒）")
	private Long ttl;

	public Object getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Object accessToken) {
		this.accessToken = accessToken;
	}

	public Long getTtl() {
		return ttl;
	}

	public void setTtl(Long ttl) {
		this.ttl = ttl;
	}

	@Override
	public String toString() {
		return "AccessTokenVO{" +
				"accessToken='" + accessToken + '\'' +
				", ttl=" + ttl +
				'}';
	}
}
