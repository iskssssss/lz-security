package cn.lz.tool.poi.reader.abs;

import cn.hutool.core.util.ReflectUtil;
import cn.lz.tool.poi.anno.ImportItem;
import cn.lz.tool.poi.reader.handler.ImportInfo;
import cn.lz.tool.poi.reader.model.CellInfo;
import cn.lz.tool.poi.reader.model.ErrorInfo;
import cn.lz.tool.poi.model.ReadCellInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象行处理器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 14:31
 */
public abstract class AbsRowHandler<T> {
    private final AtomicBoolean init = new AtomicBoolean(false);
    private ImportInfo<T> importInfo;
    private Map<Integer, String> titleIndexMap;
    private Map<String, List<CellInfo>> titleCellInfoMap;

    /**
     * 初始化导入信息
     */
    protected final void init(ImportInfo<T> importInfo) {
        if (init.get()) {
            return;
        }
        init.set(true);
        this.importInfo = importInfo;
        List<String> modelTitleInfoList = importInfo.getModelTitleInfoList();
        this.titleCellInfoMap = importInfo.getTitleCellInfoMap();
        this.titleIndexMap = importInfo.getTitleIndexMap();
        Class<T> importModelClass = this.importModelClass();
        Field[] fields = importModelClass.getDeclaredFields();
        for (Field field : fields) {
            ImportItem importItem = field.getAnnotation(ImportItem.class);
            if (importItem == null) {
                continue;
            }
            String[] titles = importItem.value();
            for (String title : titles) {
                modelTitleInfoList.add(title);
                List<CellInfo> cellInfoList = this.titleCellInfoMap.computeIfAbsent(title, item -> new ArrayList<>());
                cellInfoList.add(new CellInfo(importInfo, field));
            }
        }
    }

    /**
     * 转换的实体类类型
     *
     * @return 实体类类型
     */
    protected abstract Class<T> importModelClass();

    protected final T _to(String tableIndex, int rowIndex, List<ReadCellInfo> dataList, Object... otherParams) {
        if (!init.get()) {
            throw new IllegalArgumentException("未初始化");
        }
        return this.to(tableIndex, rowIndex, dataList, otherParams);
    }

    /**
     * 处理行数据
     *
     * @param tableIndex  表格编号
     * @param dataList    行数据
     * @param otherParams 其它参数
     * @return 处理结果
     */
    protected T to(String tableIndex, int rowIndex, List<ReadCellInfo> dataList, Object... otherParams) {
        T t = ReflectUtil.newInstance(this.importModelClass());
        for (int i = 0; i < dataList.size(); i++) {
            ReadCellInfo readCellInfo = dataList.get(i);
            String title = this.titleIndexMap.get(i);
            List<CellInfo> cellInfoList = this.titleCellInfoMap.get(title);
            for (CellInfo cellInfo : cellInfoList) {
                ErrorInfo errorInfo = cellInfo.setValue(t, title, tableIndex, readCellInfo, rowIndex, false, otherParams);
                if (errorInfo == null) {
                    continue;
                }
                importInfo.addErrorInfoList(errorInfo);
            }
        }
        return t;
    }
}
