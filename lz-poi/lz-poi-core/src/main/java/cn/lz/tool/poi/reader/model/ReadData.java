package cn.lz.tool.poi.reader.model;

import cn.lz.tool.poi.model.ReadCellInfo;

import java.util.List;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 14:24
 */
public class ReadData {
    private final String tableIndex;
    private final int y;
    private final List<ReadCellInfo> dataList;

    public ReadData(String tableIndex, int y, List<ReadCellInfo> dataList) {
        this.tableIndex = tableIndex;
        this.y = y;
        this.dataList = dataList;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public int getY() {
        return y;
    }

    public List<ReadCellInfo> getDataList() {
        return dataList;
    }
}
