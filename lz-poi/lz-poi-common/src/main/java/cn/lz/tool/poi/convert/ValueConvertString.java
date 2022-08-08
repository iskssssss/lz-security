package cn.lz.tool.poi.convert;

import cn.hutool.core.util.StrUtil;
import cn.lz.tool.poi.model.ReadCellInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 14:14
 */
public class ValueConvertString implements ValueConvert<String> {

    @Override
    public String to(String title, ReadCellInfo readCellInfo, Object... otherParams) {
        Object value = readCellInfo.getValue();
        if (value instanceof List) {
            List<?> valueList = (List<?>) value;
            String result = valueList.stream().map(StrUtil::toString).filter(StrUtil::isNotBlank).collect(Collectors.joining("\r\n"));
            return result;
        }
        return StrUtil.toString(value);
    }

    @Override
    public void clear() {

    }
}
