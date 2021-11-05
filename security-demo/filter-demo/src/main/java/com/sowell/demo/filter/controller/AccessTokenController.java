package com.sowell.demo.filter.controller;

import com.sowell.common.core.web.result.R;
import com.sowell.demo.filter.model.AccessTokenVO;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.annotation.ResponseDataEncrypt;
import com.sowell.security.defaults.DefaultAuthDetails;
import com.sowell.security.filter.utils.AccessTokenUtil;
import com.sowell.security.plugins.utils.RedisUtil;
import com.sowell.tool.core.enums.RCode;
import com.sowell.tool.core.number.NumberUtil;
import com.sowell.tool.core.string.StringUtil;
import com.sowell.tool.encrypt.EncryptUtil;
import com.sowell.tool.encrypt.model.AppKeyPair;
import com.sowell.tool.encrypt.model.SwPublicKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

	@ApiOperation(value = "获取AccessToken", notes = "获取AccessToken1")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "sign", value = "sign", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "timestamp", value = "时间戳", required = true)
	})
	@GetMapping(value = "/genAccessToken", produces = "application/json; charset=utf-8")
	public R<AccessTokenVO> getAccessToken(
			@RequestParam("appKey") String appKey,
			@RequestParam("sign") String sign,
			@RequestParam("timestamp") String timestamp
	) {
		if (!NumberUtil.checkNumber(timestamp)) {
			return R.failed(RCode.ERROR_PARAMS);
		}
		final String appSecret = ((String) RedisUtil.hashGet("APP::LIST", appKey));
		if (StringUtil.isEmpty(appSecret)) {
			return R.failed(RCode.APP_ID_NOT_EXIST);
		}
		String verSign = EncryptUtil.sign(appKey, appSecret, Long.parseLong(timestamp));
		if (!sign.equals(verSign)) {
			return R.failed(RCode.APP_ID_NOT_EXIST);
		}
		DefaultAuthDetails testAuthDetails = new DefaultAuthDetails();
		testAuthDetails.setId(appKey);
		testAuthDetails.setIdentifier(appKey);
		testAuthDetails.setCredential(appSecret);
		final Object token = AccessTokenUtil.generateAccessToken(testAuthDetails);
		AccessTokenVO accessTokenVO = new AccessTokenVO();
		accessTokenVO.setAccessToken(token);
		accessTokenVO.setTtl(IcpCoreManager.getIcpConfig().getAccessTokenConfig().getTimeoutForMillis());
		return R.success(accessTokenVO);
	}

	@ApiOperation(value = "getAppKeyPair", notes = "getAppKeyPair")
	@GetMapping(value = "/getAppKeyPair", produces = "application/json; charset=utf-8")
	public R<AppKeyPair> getAppKeyPair() {
		final AppKeyPair appKeyPair = AppKeyPair.generateAppPair();
		RedisUtil.hashSet("APP::LIST", appKeyPair.getAppKey(), appKeyPair.getAppSecret());
		return R.success(appKeyPair);
	}

	@ApiOperation(value = "sign", notes = "sign")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "appKey", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "appSecret", value = "appSecret", required = true),
	})
	@GetMapping(value = "/sign", produces = "application/json; charset=utf-8")
	public R<Map<String, Object>> sign(
			@RequestParam("appKey") String appKey,
			@RequestParam("appSecret") String appSecret
	) {
		Map<String, Object> resultMap = new HashMap<>(4);
		final long currentTimeMillis = System.currentTimeMillis();
		final String sign = EncryptUtil.sign(appKey, appSecret, currentTimeMillis);
		resultMap.put("sign", sign);
		resultMap.put("currentTimeMillis", currentTimeMillis);
		return R.success(resultMap);
	}

	@GetMapping("/pubEncrypt")
	@ResponseDataEncrypt(responseEncrypt = false, requestEncrypt = false)
	@ApiOperation(value = "公钥加密数据", notes = "公钥加密数据")
	@ApiImplicitParam(paramType = "query", dataType = "String", name = "text", value = "text", required = true)
	public R<Object> pubEncrypt(
			@RequestParam("text") String text
	) {
		final SwPublicKey publicKey = IcpCoreManager.getIcpConfig().getEncryptConfig().getPublicKey();
		return R.success(publicKey.encrypt(text));
	}

	@GetMapping("/priDecrypt")
	@ResponseDataEncrypt(responseEncrypt = false, requestEncrypt = true)
	@ApiOperation(value = "访问请求数据加密接口", notes = "访问请求数据加密接口")
	@ApiImplicitParam(paramType = "query", dataType = "String", name = "text", value = "text", required = true)
	public R<Object> priDecrypt(
			@RequestParam("text") String text
	) {
		System.out.println(text);
		return R.success(text);
	}

	@PostMapping(value = "/postPriDecrypt", produces = "application/json")
	@ResponseDataEncrypt(responseEncrypt = true, requestEncrypt = true)
	@ApiOperation(value = "post请求数据加密接口", notes = "post请求数据加密接口")
	public R<Object> postPriDecrypt(
			@RequestBody AccessTokenVO accessTokenVO
	) {
		System.out.println(accessTokenVO.toJson());
		return R.success(accessTokenVO.toJson());
	}
}
