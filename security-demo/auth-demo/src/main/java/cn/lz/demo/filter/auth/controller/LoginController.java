//package cn.lz.demo.filter.auth.controller;
//
//import cn.lz.common.core.web.result.R;
//import cn.lz.demo.filter.auth.model.LoginCriteria;
//import cn.lz.security.auth.LzLogin;
//import cn.lz.security.auth.annotation.AnonymousCheck;
//import cn.lz.security.auth.annotation.AuthCheck;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * TODO
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/11/02 16:41
// */
//@RestController
//@RequestMapping("/api")
//@Api(tags = "登录相关接口")
//public class LoginController {
//
//
//	@AnonymousCheck
//	@ApiOperation(value = "登录", notes = "登录")
//	@PostMapping("/login/login.do")
//	public R<String> login(
//			@RequestBody LoginCriteria loginCriteria
//	) {
//		final String token = LzLogin.login(loginCriteria.getUsername());
//		System.out.println("用户信息：" + loginCriteria.toJson());
//		return R.success(token);
//	}
//
//	@AuthCheck
//	@ApiOperation(value = "登出", notes = "登出")
//	@PostMapping("/logout/logout.do")
//	public R<String> logout() {
//		LzLogin.logout();
//		return R.success("登出成功");
//	}
//}
