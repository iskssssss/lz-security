package com.sowell.security.filter.anno;

import java.lang.annotation.*;

/**
 * 排除接口
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/11/08 14:33
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeInterface {

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	boolean open() default true;
}