package cn.lz.security.auth.handler;

import cn.lz.security.context.model.BaseRequest;
import cn.lz.security.context.model.BaseResponse;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 16:22
 */
public interface ICheckAccessAuthStatusHandler {

	/**
	 * 校验认证状态
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return 认证状态
	 */
	boolean check(
			BaseRequest<?> request,
			BaseResponse<?> response
	);

}
