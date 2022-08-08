package cn.lz.tool.poi.reader.model;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.lz.tool.poi.anno.ImportItem;
import cn.lz.tool.poi.convert.ValueConvert;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.handler.ImportInfo;

import java.lang.reflect.Field;

/**
 * 单元格信息
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 14:30
 */
public class CellInfo {
    private final Field field;
    private final int xIndex;
    private final boolean required;
    private final ValueConvert<Object> valueConvert;
    private boolean write = false;

    public CellInfo(ImportInfo<?> importInfo, Field field) {
        this.field = field;
        ImportItem importItem = field.getAnnotation(ImportItem.class);
        this.xIndex = importItem.xIndex();
        this.required = importItem.required();
        this.valueConvert = importInfo.getValueConvertByClass(importItem.typeHandler());
    }

    public ErrorInfo setValue(Object obj, String title, String tableIndex, ReadCellInfo readCellInfo, int yIndex, boolean write, Object... otherParams) {
        this.write = write;
        ErrorInfo result = setValue(obj, title, tableIndex, readCellInfo, yIndex);
        return result;
    }

    public ErrorInfo setValue(Object obj, String title, String tableIndex, ReadCellInfo readCellInfo, int yIndex, Object... otherParams) {
        if (this.write) {
            return null;
        }
        if (ObjectUtil.isEmpty(readCellInfo.getValue()) && this.required) {
            return new ErrorInfoDefault(tableIndex, this.xIndex, yIndex, title, readCellInfo.getValue(), "不可为空");
        }
        this.valueConvert.clear();
        Object toValue = this.valueConvert.to(title, readCellInfo, otherParams);
        ReflectUtil.setFieldValue(obj, this.field, toValue);
        write = true;
        return null;
    }
}
