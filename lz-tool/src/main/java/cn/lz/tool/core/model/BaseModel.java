package cn.lz.tool.core.model;

import cn.lz.tool.json.JsonUtil;

import java.io.Serializable;

/**
 * @Version 版权 Copyright(c)2021 LZ
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
