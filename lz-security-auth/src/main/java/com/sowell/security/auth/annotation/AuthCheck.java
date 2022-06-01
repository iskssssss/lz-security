package com.sowell.security.auth.annotation;

import java.lang.annotation.*;

/**
 * 认证验证
 * <p>添加此注解后，后续访问接口都必须经过认证才可访问。</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 14:27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	boolean open() default true;
}
