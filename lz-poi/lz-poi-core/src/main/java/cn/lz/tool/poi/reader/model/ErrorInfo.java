package cn.lz.tool.poi.reader.model;

import cn.hutool.core.util.ObjectUtil;

/**
 * 错误信息
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 14:30
 */
public abstract class ErrorInfo {
    private final String tableIndex;
    private final int x;
    private final int y;
    private final String title;
    private final Object value;
    private final String message;

    public ErrorInfo(String tableIndex, int x, int y, String title, Object value, String message) {
        this.tableIndex = tableIndex;
        this.x = x;
        this.y = y;
        this.title = title;
        this.value = value;
        this.message = message;
    }

    public String getVal() {
        if (ObjectUtil.isEmpty(this.value)) {
            return "(" + this.message + ")";
        }
        return this.value + "(" + this.message + ")";
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTitle() {
        return title;
    }

    public Object getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
