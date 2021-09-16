package com.sowell.security.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 *
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/08/02 10:32
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface LogBeforeFilter {
}
