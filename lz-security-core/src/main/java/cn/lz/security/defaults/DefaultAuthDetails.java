package cn.lz.security.defaults;

import cn.lz.tool.jwt.model.AuthDetails;

/**
 * 默认认证信息实体
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/29 15:11
 */
public class DefaultAuthDetails extends AuthDetails<DefaultAuthDetails> {

	@Override
	public Class<DefaultAuthDetails> setSourceClass() {
		return DefaultAuthDetails.class;
	}
}
