package cn.lz.tool.poi.convert;

import cn.lz.tool.poi.model.ReadCellInfo;

/**
 * 类型转换器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 12:54
 */
public interface ValueConvert<T> {

    /**
     * 转换
     *
     * @param title        对应标题
     * @param readCellInfo 值信息
     * @param otherParams  其它参数
     * @return 转换后的值
     */
    T to(String title, ReadCellInfo readCellInfo, Object... otherParams);

    /**
     * 清除信息
     */
    void clear();
}
