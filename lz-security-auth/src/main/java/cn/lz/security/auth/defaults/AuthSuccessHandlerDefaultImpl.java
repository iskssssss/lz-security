package cn.lz.security.auth.defaults;

import cn.lz.security.auth.login.AuthSuccessHandler;
import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;
import cn.lz.tool.core.enums.RCode;
import cn.lz.tool.http.enums.MediaType;
import cn.lz.tool.jwt.model.AuthDetails;
import cn.lz.security.utils.ServletUtil;

/**
 * 认证成功处理器 默认实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/6/23 17:21
 */
public class AuthSuccessHandlerDefaultImpl implements AuthSuccessHandler {

	@Override
	public void success(BaseRequest<?> request, BaseResponse<?> response, AuthDetails<?> authDetails) {
		response.setStatus(200);
		ServletUtil.printResponse(response, MediaType.APPLICATION_JSON_VALUE, RCode.SUCCESS);
	}
}
