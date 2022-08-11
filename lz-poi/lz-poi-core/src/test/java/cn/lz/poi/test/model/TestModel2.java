package cn.lz.poi.test.model;

import cn.lz.tool.poi.anno.ImportItem;
import cn.lz.tool.poi.convert.ValueConvertString;

/**
 * TODO 待实现
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 15:18
 */
public class TestModel2 {

    @ImportItem(value = "序号", xIndex = 0, required = true)
    private String index;

    @ImportItem(value = "名称", xIndex = 1, required = true, typeHandler = ValueConvertString.class)
    private String name;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
