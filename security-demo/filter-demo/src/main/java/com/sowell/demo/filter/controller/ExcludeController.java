//package com.sowell.demo.filter.controller;
//
//import com.sowell.common.core.web.result.R;
//import com.sowell.security.annotation.ExcludeInterface;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
///**
// * 放行相关接口
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/10/22 16:16
// */
//@RestController
//@ExcludeInterface
//@RequestMapping("/exclude")
//@Api(tags = "放行相关接口")
//public class ExcludeController {
//
//	@ApiOperation(value = "获取UUID1", notes = "获取UUID1")
//	@GetMapping("/uuid1")
//	public R<String> uuid1() {
//		return R.success(UUID.randomUUID().toString().replaceAll("-", ""));
//	}
//
//	@ExcludeInterface
//	@ApiOperation(value = "获取UUID2", notes = "获取UUID2")
//	@GetMapping("/uuid2")
//	public R<String> uuid2() {
//		return R.success(UUID.randomUUID().toString().replaceAll("-", ""));
//	}
//
//	@ExcludeInterface(open = false)
//	@ApiOperation(value = "获取UUID3", notes = "获取UUID3")
//	@GetMapping("/uuid3")
//	public R<String> uuid3() {
//		return R.success(UUID.randomUUID().toString().replaceAll("-", ""));
//	}
//}
