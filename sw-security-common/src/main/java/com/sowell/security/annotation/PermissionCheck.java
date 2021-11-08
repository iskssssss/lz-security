package com.sowell.security.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 * <p>添加此注解后，后续访问接口都必须拥有相关权限才可访问。</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/08 14:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionCheck {

	/**
	 * 权限列表
	 *
	 * @return 权限列表
	 */
	String[] permissions() default {};

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	boolean open() default true;
}
