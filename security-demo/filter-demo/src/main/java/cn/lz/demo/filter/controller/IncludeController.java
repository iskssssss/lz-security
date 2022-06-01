package cn.lz.demo.filter.controller;

import cn.lz.demo.filter.model.R;
import cn.lz.security.LzCoreManager;
import cn.lz.security.annotation.DataEncodeSwitch;
import cn.lz.security.defaults.DefaultAuthDetails;
import cn.lz.security.annotation.IncludeInterface;
import cn.lz.security.log.LzLoggerUtil;
import cn.lz.security.token.AccessTokenUtil;
import cn.lz.tool.encrypt.model.SwPublicKey;
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
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/22 16:46
 */
@RestController
@DataEncodeSwitch
@IncludeInterface
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
		LzLoggerUtil.info(getClass(), "用户1请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	//@DataEncodeSwitch(responseContentType = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "2", notes = "2")
	@GetMapping("/2")
	public R<DefaultAuthDetails> interface2() {
		LzLoggerUtil.info(getClass(), "用户2请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@DataEncodeSwitch(responseEncrypt = false)
	@ApiOperation(value = "3", notes = "3")
	@GetMapping("/3")
	public R<DefaultAuthDetails> interface3() {
		LzLoggerUtil.info(getClass(), "用户3请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		return R.success(authDetails);
	}

	@ApiOperation(value = "error", notes = "error")
	@GetMapping("/error")
	public R<DefaultAuthDetails> error() {
		LzLoggerUtil.info(getClass(), "用户3请求");
		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
		final int i = 1 / 0;
		return R.success(authDetails);
	}

	@GetMapping("/decrypt")
	@DataEncodeSwitch(responseEncrypt = false)
	@ApiOperation(value = "解密", notes = "解密")
	@ApiImplicitParam(paramType = "query", dataType = "String", name = "encryptText", value = "encryptText", required = true)
	public Object decrypt(
			@RequestParam("encryptText") String encryptText
	) {
		final SwPublicKey publicKey = LzCoreManager.getLzConfig().getEncryptConfig().getPublicKeyStr();
		final Object decrypt = publicKey.decrypt(encryptText);
		return decrypt;
	}
}
