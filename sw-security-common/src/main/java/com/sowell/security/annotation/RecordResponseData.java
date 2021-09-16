package com.sowell.security.annotation;

import java.lang.annotation.*;

/**
 * 添加此注解后日志会将响应数据记录至数据库中
 * <p></p>
 * @Version 版权 Copyright(c)2021 浙江设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/7/19 9:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordResponseData {
}
