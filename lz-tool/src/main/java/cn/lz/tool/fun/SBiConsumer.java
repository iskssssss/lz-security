package cn.lz.tool.fun;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/10 20:21
 */
@FunctionalInterface
public interface SBiConsumer<T, U> extends BiConsumer<T, U>, Serializable {
}
