package cn.lz.tool.poi.convert;

import cn.lz.tool.poi.model.ReadCellInfo;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 14:13
 */
public class ValueConvertDefault implements ValueConvert<Object> {
    @Override
    public Object to(String title, ReadCellInfo readCellInfo, Object... otherParams) {
        return readCellInfo.getValue();
    }

    @Override
    public void clear() {

    }
}
