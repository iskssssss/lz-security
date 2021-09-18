package com.sowell.security.fun;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @Version 版权 Copyright(c)2021 杭州设维信息技术有限公司
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 11:48
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
