package cn.lz.tool.poi.exception;

/**
 * 数据导入异常类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/7 17:46
 */
public class DataImportException extends Exception {

    public DataImportException(String message) {
        super(message);
    }

    public DataImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataImportException(Throwable cause) {
        super(cause);
    }
}
