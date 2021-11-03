package com.sowell.demo.controller;

import com.sowell.common.core.web.result.R;
import com.sowell.security.IcpManager;
import com.sowell.security.defaults.DefaultAuthDetails;
import com.sowell.security.filter.utils.AccessTokenUtil;
import com.sowell.demo.model.AccessTokenVO;
//import com.sowell.demo.model.TestAuthDetails;
import com.sowell.demo.service.AopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证相关接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/22 16:36
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "认证相关接口")
public class AccessTokenController {

	private final AopService aopService;

	public AccessTokenController(AopService aopService) {this.aopService = aopService;}

	@ApiOperation(value = "获取AccessToken1", notes = "获取AccessToken1")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "sign", value = "sign", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "timestamp", value = "时间戳", required = true)
	})
	@GetMapping(value = "/genAccessToken1", produces = "application/json; charset=utf-8")
	public R<AccessTokenVO> getAccessToken1(
			@RequestParam("appKey") String appKey,
			@RequestParam("sign") String sign,
			@RequestParam("timestamp") String timestamp
	) {
		aopService.log();
		DefaultAuthDetails testAuthDetails = new DefaultAuthDetails();
		testAuthDetails.setId(appKey);
		testAuthDetails.setIdentifier(sign);
		testAuthDetails.setCredential(timestamp);
		final Object token = AccessTokenUtil.generateAccessToken(testAuthDetails);
		AccessTokenVO accessTokenVO = new AccessTokenVO();
		accessTokenVO.setAccessToken(token);
		accessTokenVO.setTtl(IcpManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis());
		return R.success(accessTokenVO);
	}

	@ApiOperation(value = "获取AccessToken2", notes = "获取AccessToken2")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "sign", value = "sign", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "timestamp", value = "时间戳", required = true)
	})
	@GetMapping(value = "/genAccessToken2", produces = "application/json; charset=utf-8")
	public R<Object> getAccessToken2(
			@RequestParam("appKey") String appKey,
			@RequestParam("sign") String sign,
			@RequestParam("timestamp") String timestamp
	) {
		DefaultAuthDetails testAuthDetails = new DefaultAuthDetails();
		testAuthDetails.setId(appKey);
		testAuthDetails.setIdentifier(sign);
		testAuthDetails.setCredential(timestamp);
		final Object tokenInfo = AccessTokenUtil.generateAccessToken(testAuthDetails);
		return R.success(tokenInfo);
	}
}
