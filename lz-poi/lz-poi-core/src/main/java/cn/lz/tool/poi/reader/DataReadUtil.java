package cn.lz.tool.poi.reader;

import cn.hutool.core.io.FileUtil;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import cn.lz.tool.poi.reader.reader.excel.ExcelDataReader;
import cn.lz.tool.poi.reader.reader.world.WorldTableDataReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据读取工具类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/3 17:28
 */
public final class DataReadUtil {
    public final static String PREFIX = "DATA_READER_FILE_";

    /**
     * [World] 读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param <T>        类型
     * @return 数据读取器
     * @throws IOException 异常
     */
    public static <T> AbsDataReader<T> worldRead(File file, AbsRowHandler<T> rowHandler) throws IOException {
        return readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, false);
    }

    /**
     * [Excel] 读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param <T>        类型
     * @return 数据读取器
     * @throws IOException 异常
     */
    public static <T> AbsDataReader<T> excelRead(File file, AbsRowHandler<T> rowHandler) throws IOException {
        return readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, true);
    }

    /**
     * 读取表格信息信息
     *
     * @param fileName        文件名称
     * @param fileInputStream 文件流
     * @param rowHandler      行处理器
     * @param excel           是否是表格
     * @param <T>             类型
     * @return 数据读取器
     * @throws IOException 异常
     */
    public static <T> AbsDataReader<T> readTable(String fileName, InputStream fileInputStream, AbsRowHandler<T> rowHandler, boolean excel) throws IOException {
        String suffix = FileUtil.getSuffix(fileName);
        File importExcelTemp = FileUtil.createTempFile(DataReadUtil.PREFIX, "." + suffix, null, true);
        FileUtil.writeFromStream(fileInputStream, importExcelTemp, false);
        if (excel) {
            return new ExcelDataReader<>(importExcelTemp, rowHandler).read();
        }
        return new WorldTableDataReader<>(importExcelTemp, rowHandler).read();
    }
}
