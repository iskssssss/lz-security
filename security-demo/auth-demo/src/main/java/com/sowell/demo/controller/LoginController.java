package com.sowell.demo.controller;

import com.sowell.common.core.web.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/02 16:41
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录相关接口")
public class LoginController {

	@ApiOperation(value = "登录", notes = "登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "username", value = "username", required = true),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "pwd", value = "pwd", required = true)
	})
	@PostMapping("/login/login.do")
	public R<Boolean> login(String username, String pwd) {
		System.out.println("账号：" + username);
		System.out.println("密码：" + pwd);
		return R.success(true);
	}

	@ApiOperation(value = "登出", notes = "登出")
	@PostMapping("/logout/logout.do")
	public R<Boolean> logout() {
		System.out.println("登出");
		return R.success(true);
	}
}
