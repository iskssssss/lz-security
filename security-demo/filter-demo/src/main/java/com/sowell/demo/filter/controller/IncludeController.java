package com.sowell.demo.filter.controller;

import com.sowell.common.core.web.result.R;
import com.sowell.security.IcpCoreManager;
import com.sowell.security.annotation.DataEncrypt;
import com.sowell.security.defaults.DefaultAuthDetails;
import com.sowell.security.log.IcpLoggerUtil;
import com.sowell.security.filter.utils.AccessTokenUtil;
import com.sowell.tool.encrypt.EncryptUtil;
import com.sowell.tool.encrypt.model.SwPublicKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拦截相关接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/22 16:46
 */
@RestController
@DataEncrypt
@RequestMapping("/include")
@Api(tags = "拦截相关接口")
public class IncludeController {

	@ApiOperation(value = "刷新AccessToken", notes = "刷新AccessToken")
	@GetMapping("/refreshAccessToken")
	public R<Object> refreshAccessToken() {

		final Object authDetails = AccessTokenUtil.refreshAccessToken();
		return R.success(authDetails);
	}

	@ApiOperation(value = "获取当前过滤用户信息", notes = "获取当前过滤用户信息")
	@GetMapping("/getUserInfo")
	public R<DefaultAuthDetails> generateAccessTo77ken() {
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@ApiOperation(value = "1", notes = "1")
	@GetMapping("/1")
	public R<DefaultAuthDetails> interface1() {
		IcpLoggerUtil.info(getClass(), "用户1请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@ApiOperation(value = "2", notes = "2")
	@GetMapping("/2")
	public R<DefaultAuthDetails> interface2() {
		IcpLoggerUtil.info(getClass(), "用户2请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@ApiOperation(value = "3", notes = "3")
	@GetMapping("/3")
	public R<DefaultAuthDetails> interface3() {
		IcpLoggerUtil.info(getClass(), "用户3请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@ApiOperation(value = "error", notes = "error")
	@GetMapping("/error")
	public R<DefaultAuthDetails> error() {
		IcpLoggerUtil.info(getClass(), "用户3请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		final int i = 1 / 0;
		return R.success(authDetails);
	}

	@GetMapping("/decrypt")
	@DataEncrypt(responseEncrypt = false)
	@ApiOperation(value = "解密", notes = "解密")
	@ApiImplicitParam(paramType = "query", dataType = "String", name = "encryptText", value = "encryptText", required = true)
	public R<Object> decrypt(
			@RequestParam("encryptText") String encryptText
	) {
		final SwPublicKey publicKey = IcpCoreManager.getIcpConfig().getEncryptConfig().getPublicKeyStr();
		final Object decrypt = publicKey.decrypt(encryptText);
		return R.success(decrypt);
	}

	public static void main(String[] args) {

		final long currentTimeMillis = System.currentTimeMillis();
		final String sign = EncryptUtil.sign("53ed9a21d9b9c10de4856001338a1f5aab59df40", "2d5fa60a13d0f5c5d144dc1eb916ee59f48a7ddf5b0eb5061e38209088102b9a", currentTimeMillis);

		System.out.println(currentTimeMillis);
		System.out.println(sign);
	}
}
