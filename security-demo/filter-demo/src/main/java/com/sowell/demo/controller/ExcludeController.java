package com.sowell.demo.controller;

import com.sowell.common.core.web.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 放行相关接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/22 16:16
 */
@RestController
@RequestMapping("/exclude")
@Api(tags = "放行相关接口")
public class ExcludeController {

	@Value("${cas.server-url-prefix}")
	String serverUrlPrefix;

	@Value("${cas.server-login-url}")
	String serverLoginUrl;

	@ApiOperation(value = "获取UUID", notes = "获取UUID")
	@GetMapping("/uuid")
	public R<String> generateAccessTo77ken() {
//		Assertion assertion = AssertionHolder.getAssertion();
//		String userId3 = assertion.getPrincipal().getName();
//		System.out.println(userId3);
		return R.success(UUID.randomUUID().toString().replaceAll("-", ""));
	}

//	@ApiOperation(value = "登出", notes = "登出")
//	@GetMapping("/logout")
//	public void logout(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			HttpSession session
//	) throws IOException {
//		session.invalidate();
//		response.sendRedirect(serverUrlPrefix + "/logout?service=" + URLEncoder.encode("http://localhost:8080/doc.html", "UTF-8"));
//	}
}
