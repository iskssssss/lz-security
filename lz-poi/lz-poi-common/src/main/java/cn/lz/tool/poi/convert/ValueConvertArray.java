package cn.lz.tool.poi.convert;

import cn.hutool.core.util.StrUtil;
import cn.lz.tool.poi.model.ReadCellInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 14:14
 */
public class ValueConvertArray implements ValueConvert<List<String>> {

    @Override
    public List<String> to(String title, ReadCellInfo readCellInfo, Object... otherParams) {
        Object value = readCellInfo.getValue();
        if (value instanceof List) {
            return ((List<String>) value).stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
        }
        if (value == null) {
            return null;
        }
        String[] split = value.toString().split("\n");
        List<String> list = Arrays.stream(split).filter(StrUtil::isNotBlank).collect(Collectors.toList());
        return list;
    }

    @Override
    public void clear() {

    }
}
