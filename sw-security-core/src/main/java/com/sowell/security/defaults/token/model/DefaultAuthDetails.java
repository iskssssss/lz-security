package com.sowell.security.defaults.token.model;

import com.sowell.tool.jwt.model.AuthDetails;

/**
 * 默认认证信息实体
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/29 15:11
 */
public class DefaultAuthDetails extends AuthDetails<DefaultAuthDetails> {

	@Override
	public Class<DefaultAuthDetails> setSourceClass() {
		return DefaultAuthDetails.class;
	}
}
