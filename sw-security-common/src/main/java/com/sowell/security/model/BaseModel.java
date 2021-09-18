package com.sowell.security.model;

import com.sowell.security.utils.JsonUtil;

import java.io.Serializable;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/18 14:11
 */
public class BaseModel implements Serializable {

	static final long serialVersionUID = 1L;

	public String toJson() {
		return JsonUtil.toJsonString(this);
	}
}
