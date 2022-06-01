package cn.lz.tool.fun;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/09/16 11:48
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
