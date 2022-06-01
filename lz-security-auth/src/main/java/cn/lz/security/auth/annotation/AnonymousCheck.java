package cn.lz.security.auth.annotation;

import java.lang.annotation.*;

/**
 * 匿名验证
 * <p>添加此注解后，后续访问接口都必须在匿名状态下才可访问。</p>
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 LZ
 * @date 2021/11/09 08:57
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnonymousCheck {

	/**
	 * 是否启用
	 *
	 * @return 是否启用
	 */
	boolean open() default true;
}
