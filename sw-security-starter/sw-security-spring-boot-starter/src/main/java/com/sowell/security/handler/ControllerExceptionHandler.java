package com.sowell.security.handler;

import com.sowell.common.core.web.result.ICode;
import com.sowell.common.core.web.result.R;
import com.sowell.common.core.web.result.ResultEntity;
import com.sowell.security.context.IcpSpringContextHolder;
import com.sowell.security.mode.SwResponse;
import com.sowell.security.model.ResponseData;
import com.sowell.security.utils.ErrorDataHandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/19 14:13
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@ExceptionHandler({Exception.class})
	public ResultEntity errorHandler(Exception error) {
		final SwResponse response = IcpSpringContextHolder.getResponse();
		final ResponseData<ResultEntity> responseData = ErrorDataHandlerUtil.handlerErrorData(error);
		response.setStatus(responseData.getHttpStatus().value());
		this.log.error(responseData.getMsg(), error);
		return responseData.getBodyData();
	}
}
