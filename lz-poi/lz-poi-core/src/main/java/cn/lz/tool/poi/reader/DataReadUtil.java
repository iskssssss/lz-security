package cn.lz.tool.poi.reader;

import cn.hutool.core.io.FileUtil;
import cn.lz.tool.fun.SBiConsumer;
import cn.lz.tool.poi.exception.DataImportException;
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
     * [World] 同步读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param <T>        类型
     * @return 数据读取器
     * @throws DataImportException 异常
     */
    public static <T> AbsDataReader<T> worldReadSync(File file, AbsRowHandler<T> rowHandler) throws DataImportException {
        return readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, false).sync();
    }

    /**
     * [World] 异步读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param callback   回调函数
     * @param <T>        类型
     * @return 数据读取器
     * @throws DataImportException 异常
     */
    public static <T> AbsDataReader<T> worldReadAsync(File file, AbsRowHandler<T> rowHandler, SBiConsumer<AbsDataReader<T>, DataImportException> callback) throws DataImportException {
        AbsDataReader<T> dataReader = readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, false);
        dataReader.setCallback(callback);
        return dataReader;
    }

    /**
     * [Excel] 同步读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param <T>        类型
     * @return 数据读取器
     * @throws DataImportException 异常
     */
    public static <T> AbsDataReader<T> excelReadSync(File file, AbsRowHandler<T> rowHandler) throws DataImportException {
        return readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, true).sync();
    }

    /**
     * [Excel] 异步读取信息
     *
     * @param file       文件
     * @param rowHandler 行处理器
     * @param callback   回调函数
     * @param <T>        类型
     * @return 数据读取器
     * @throws DataImportException 异常
     */
    public static <T> AbsDataReader<T> excelReadAsync(File file, AbsRowHandler<T> rowHandler, SBiConsumer<AbsDataReader<T>, DataImportException> callback) throws DataImportException {
        AbsDataReader<T> dataReader = readTable(file.getName(), FileUtil.getInputStream(file), rowHandler, true);
        dataReader.setCallback(callback);
        return dataReader;
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
    public static <T> AbsDataReader<T> readTable(String fileName, InputStream fileInputStream, AbsRowHandler<T> rowHandler, boolean excel) throws DataImportException {
        String suffix = FileUtil.getSuffix(fileName);
        File importExcelTemp = FileUtil.createTempFile(DataReadUtil.PREFIX, "." + suffix, null, true);
        FileUtil.writeFromStream(fileInputStream, importExcelTemp, false);
        try {
            if (excel) {
                return new ExcelDataReader<>(importExcelTemp, rowHandler).read();
            }
            return new WorldTableDataReader<>(importExcelTemp, rowHandler).read();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new DataImportException("读取异常。", ioException);
        }
    }
}
