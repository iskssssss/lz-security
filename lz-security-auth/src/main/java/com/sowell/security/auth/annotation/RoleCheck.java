package com.sowell.security.auth.annotation;

import java.lang.annotation.*;

/**
 * 角色注解
 * <p>添加此注解后，后续访问接口都必须拥有相关角色才可访问。</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/08 14:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleCheck {

	/**
	 * 角色列表
	 *
	 * @return 角色列表
	 */
	String[] roles() default {};

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	boolean open() default true;
}
