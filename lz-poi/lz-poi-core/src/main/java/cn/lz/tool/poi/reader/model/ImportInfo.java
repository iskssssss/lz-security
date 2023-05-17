package cn.lz.tool.poi.reader.model;

import cn.hutool.core.util.ReflectUtil;
import cn.lz.tool.cache.CacheManager;
import cn.lz.tool.cache.utils.CacheUtil;
import cn.lz.tool.io.FileUtil;
import cn.lz.tool.poi.convert.ValueConvert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 导入信息
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/7 12:15
 */
public abstract class ImportInfo<T> extends ImportResultModel<T> implements Closeable {

    /**
     * 导入文件
     */
    protected transient final File importFile;
    /**
     * 导入文件-输入流
     */
    protected transient final InputStream fileInputStream;
    /**
     * 数据处理器
     */
    protected transient final Map<String, ValueConvert<Object>> valueConvertMap = new ConcurrentHashMap<>(16);
    /**
     * 标题 : 下标
     */
    protected transient final Map<Integer, String> titleIndexMap = new ConcurrentHashMap<>();
    /**
     * 标题 : 单元格信息
     */
    protected transient final Map<String, List<CellInfo>> titleCellInfoMap = new ConcurrentHashMap<>();
    /**
     * 实体类标题信息
     */
    protected transient final List<String> modelTitleInfoList = new ArrayList<>();

    protected ImportInfo(String fileId, File importFile) {
        super(fileId);
        this.importFile = importFile;
        this.fileInputStream = FileUtil.getInputStream(importFile);
    }

    /**
     * 根据类获取值转换器
     *
     * @param valueConvertClass 类
     * @return 值转换器
     */
    public final ValueConvert<Object> getValueConvertByClass(Class<? extends ValueConvert> valueConvertClass) {
        String className = valueConvertClass.getName();
        ValueConvert<Object> valueConvert = valueConvertMap.get(className);
        if (valueConvert == null) {
            synchronized (ImportInfo.class) {
                if (valueConvert == null) {
                    valueConvert = ReflectUtil.newInstance(valueConvertClass);
                    valueConvertMap.put(className, valueConvert);
                }
            }
        }
        return valueConvert;
    }

    /**
     * 获取{标题 : 下标}集合
     *
     * @return {标题 : 下标}集合
     */
    public final Map<Integer, String> getTitleIndexMap() {
        return titleIndexMap;
    }

    /**
     * 获取{标题 : 单元格信息}集合
     *
     * @return {标题 : 单元格信息}集合
     */
    public final Map<String, List<CellInfo>> getTitleCellInfoMap() {
        return titleCellInfoMap;
    }

    /**
     * 获取实体类标题列表
     *
     * @return 实体类标题列表
     */
    public final List<String> getModelTitleInfoList() {
        return modelTitleInfoList;
    }

    /**
     * 获取导入文件
     *
     * @return 导入文件
     */
    public final File getImportFile() {
        return importFile;
    }

    @Override
    public void close() {
        this.titleIndexMap.clear();
        this.titleCellInfoMap.values().parallelStream().forEach(List::clear);
        this.titleCellInfoMap.clear();
        this.modelTitleInfoList.clear();
        this.valueConvertMap.values().parallelStream().forEach(ValueConvert::clear);
        this.valueConvertMap.clear();
        try {
            this.fileInputStream.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 导入文件
     */
    protected static final CacheManager<String, File> IMPORT_FILE_MAP = CacheUtil.newCacheManager((30 * 60) * 1000);

    static {
        IMPORT_FILE_MAP.setListener((key, file) -> {
            FileUtil.del(file);
            System.out.println("删除数据导入临时文件：" + file.getName());
        });
    }

    /**
     * 根据文件id获取错误文件
     *
     * @param fileId 文件id
     * @return 错误文件
     */
    public static File getErrorImportFile(String fileId) {
        File file = IMPORT_FILE_MAP.get(fileId);
        return file;
    }
}
