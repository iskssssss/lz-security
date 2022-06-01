package cn.lz.demo.filter.auth.controller;

import cn.lz.security.auth.annotation.AuthCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/09 13:50
 */
@AuthCheck
@Controller
public class HomeViewController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
