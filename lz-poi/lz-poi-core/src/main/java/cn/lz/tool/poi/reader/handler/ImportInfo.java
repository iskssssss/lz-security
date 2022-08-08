package cn.lz.tool.poi.reader.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.lz.tool.cache.CacheManager;
import cn.lz.tool.cache.utils.CacheUtil;
import cn.lz.tool.poi.convert.ValueConvert;
import cn.lz.tool.poi.reader.model.CellInfo;
import cn.lz.tool.poi.reader.model.ErrorInfo;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
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
public abstract class ImportInfo<T> implements Closeable, Serializable {
    private static final long serialVersionUID = 42L;

    /**
     * 文件ID
     */
    protected final String fileId;
    /**
     * 导入文件
     */
    protected final File importFile;
    /**
     * 数据处理器
     */
    protected final Map<String, ValueConvert<Object>> valueConvertMap = new ConcurrentHashMap<>(16);
    /**
     * 标题 : 下标
     */
    protected final Map<Integer, String> titleIndexMap = new ConcurrentHashMap<>();
    /**
     * 标题 : 单元格信息
     */
    protected final Map<String, List<CellInfo>> titleCellInfoMap = new ConcurrentHashMap<>();
    /**
     * 实体类标题信息
     */
    protected final List<String> modelTitleInfoList = new ArrayList<>();
    /**
     * 处理结果
     */
    protected final List<T> resultList = new LinkedList<>();
    /**
     * 错误数据
     */
    protected final List<ErrorInfo> errorInfoList = new LinkedList<>();

    protected ImportInfo(File importFile) {
        this.fileId = IdUtil.fastSimpleUUID() + System.currentTimeMillis();
        this.importFile = importFile;
    }

    /**
     * 添加结果
     *
     * @param t 转换结果
     */
    public final void addResult(T t) {
        this.resultList.add(t);
    }

    /**
     * 添加错误信息
     *
     * @param errorInfo 错误信息
     */
    public final void addErrorInfoList(ErrorInfo errorInfo) {
        this.errorInfoList.add(errorInfo);
    }

    /**
     * 是否再导入时发生错误
     *
     * @return 是否再导入时发生错误
     */
    public final boolean isError() {
        return !this.errorInfoList.isEmpty();
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
     * 获取结果集
     *
     * @return 结果集
     */
    public final List<T> getResultList() {
        return resultList;
    }

    /**
     * 获取导入异常信息
     *
     * @return 导入异常信息
     */
    public final List<ErrorInfo> getErrorInfoList() {
        return errorInfoList;
    }

    /**
     * 文件id
     *
     * @return 文件id
     */
    public final String getFileId() {
        return fileId;
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
    public void close() throws IOException {
        this.titleIndexMap.clear();
        this.titleCellInfoMap.values().parallelStream().forEach(List::clear);
        this.titleCellInfoMap.clear();
        this.modelTitleInfoList.clear();
        this.resultList.clear();
        this.errorInfoList.clear();
        this.valueConvertMap.values().parallelStream().forEach(ValueConvert::clear);
        this.valueConvertMap.clear();
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
