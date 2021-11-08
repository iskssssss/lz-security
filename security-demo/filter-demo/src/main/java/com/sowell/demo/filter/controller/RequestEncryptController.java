package com.sowell.demo.filter.controller;

import com.sowell.common.core.web.result.R;
import com.sowell.demo.filter.model.AccessTokenVO;
import com.sowell.security.annotation.DataEncrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求数据加密、响应数据不加密 接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/05 10:29
 */
@RestController
@DataEncrypt(responseEncrypt = false, requestEncrypt = true)
@RequestMapping("/requestEncrypt")
@Api(tags = "请求数据加密、响应数据不加密 接口")
public class RequestEncryptController {

	@PostMapping(value = "/postPriDecrypt", produces = "application/json")
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public R<AccessTokenVO> postPriDecrypt(
			@RequestBody AccessTokenVO accessTokenVO
	) {
		return R.success(accessTokenVO);
	}
}
