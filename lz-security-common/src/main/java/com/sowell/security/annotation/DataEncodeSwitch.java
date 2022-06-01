package com.sowell.security.annotation;

import java.lang.annotation.*;

/**
 * 数据加密注解
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/10/27 10:41
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataEncodeSwitch {

	/**
	 * 请求数据是否加密
	 *
	 * @return true加密 false 不加密
	 */
	boolean requestEncrypt() default false;

	/**
	 * 响应数据是否加密
	 *
	 * @return true加密 false 不加密
	 */
	boolean responseEncrypt() default true;
}
