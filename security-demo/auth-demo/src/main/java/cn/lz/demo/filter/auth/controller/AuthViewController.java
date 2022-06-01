package cn.lz.demo.filter.auth.controller;

import cn.lz.security.auth.annotation.AnonymousCheck;
import cn.lz.security.auth.annotation.AuthCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/09 13:48
 */
@Controller
public class AuthViewController {

	@AnonymousCheck
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@AuthCheck
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
}
