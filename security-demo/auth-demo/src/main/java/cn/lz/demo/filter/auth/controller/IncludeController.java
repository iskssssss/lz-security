//package cn.lz.demo.filter.auth.controller;
//
//import cn.lz.common.core.web.result.R;
//import cn.lz.security.auth.annotation.AuthCheck;
//import cn.lz.security.defaults.DefaultAuthDetails;
//import cn.lz.security.log.LzLoggerUtil;
//import cn.lz.security.token.AccessTokenUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 拦截相关接口
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/10/22 16:46
// */
//@RestController
//@RequestMapping("/include")
//@Api(tags = "拦截相关接口")
//@AuthCheck
//public class IncludeController {
//
//	@ApiOperation(value = "刷新AccessToken", notes = "刷新AccessToken")
//	@GetMapping("/refreshAccessToken")
//	public R<Object> refreshAccessToken() {
//
//		final Object authDetails = AccessTokenUtil.refreshAccessToken();
//		return R.success(authDetails);
//	}
//
//	//@ResponseDataEncrypt
//	@ApiOperation(value = "获取当前过滤用户信息", notes = "获取当前过滤用户信息")
//	@GetMapping("/getUserInfo")
//	public R<DefaultAuthDetails> generateAccessTo77ken() {
//		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
//		return R.success(authDetails);
//	}
//
//	@ApiOperation(value = "1", notes = "1")
//	@GetMapping("/1")
//	public R<DefaultAuthDetails> interface1() {
//		LzLoggerUtil.info(getClass(), "用户1请求");
//		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
//		return R.success(authDetails);
//	}
//
//	@ApiOperation(value = "2", notes = "2")
//	@GetMapping("/2")
//	public R<DefaultAuthDetails> interface2() {
//		LzLoggerUtil.info(getClass(), "用户2请求");
//		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
//		return R.success(authDetails);
//	}
//
//	@ApiOperation(value = "3", notes = "3")
//	@GetMapping("/3")
//	public R<DefaultAuthDetails> interface3() {
//		LzLoggerUtil.info(getClass(), "用户3请求");
//		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
//		return R.success(authDetails);
//	}
//
//	@ApiOperation(value = "error", notes = "error")
//	@GetMapping("/error")
//	public R<DefaultAuthDetails> error() {
//		LzLoggerUtil.info(getClass(), "用户3请求");
//		final DefaultAuthDetails authDetails = AccessTokenUtil.getAuthDetails(DefaultAuthDetails.class);
//		final int i = 1 / 0;
//		return R.success(authDetails);
//	}
//}
