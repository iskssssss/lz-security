package com.sowell.security.annotation;

import java.lang.annotation.*;

/**
 * 响应数据加密
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @date 2021/10/27 10:41
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseDataEncrypt {

}
