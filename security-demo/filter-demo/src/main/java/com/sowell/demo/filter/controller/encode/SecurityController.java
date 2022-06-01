//package com.sowell.demo.filter.controller.encode;
//
//import com.sowell.common.core.web.result.R;
//import com.sowell.security.LzCoreManager;
//import com.sowell.tool.encrypt.model.SwPublicKey;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 加解密相关接口
// *
// * @author 孔胜
// * @version 版权 Copyright(c)2021 LZ
// * @date 2021/11/05 10:48
// */
//@RestController
//@RequestMapping("/security")
//@Api(tags = "加解密相关接口")
//public class SecurityController {
//
//	@GetMapping("/pubEncrypt")
//	@ApiOperation(value = "公钥加密数据", notes = "公钥加密数据")
//	@ApiImplicitParam(paramType = "query", dataType = "String", name = "text", value = "text", required = true)
//	public R<Object> pubEncrypt(
//			@RequestParam("text") String text
//	) {
//		final SwPublicKey publicKey = LzCoreManager.getLzConfig().getEncryptConfig().getPublicKeyStr();
//		return R.success(publicKey.encrypt(text));
//	}
//}
