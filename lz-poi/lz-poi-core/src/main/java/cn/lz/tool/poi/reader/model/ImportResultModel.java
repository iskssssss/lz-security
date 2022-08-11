package cn.lz.tool.poi.reader.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 导入结果实体类
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/9 15:19
 */
public class ImportResultModel<T> implements Serializable {
    static final long serialVersionUID = 42L;

    /**
     * 文件ID
     */
    protected final String fileId;

    /**
     * 正确集
     */
    protected final List<T> successList = new LinkedList<>();

    /**
     * 错误集
     */
    protected final List<T> errorList = new LinkedList<>();

    /**
     * 错误数据
     */
    protected final List<ErrorInfo> errorInfoList = new LinkedList<>();

    /**
     * 是否发生错误
     */
    protected Boolean error = false;

    /**
     * 导入开始时间
     */
    protected final Long startTimeMillis = System.currentTimeMillis();

    /**
     * 导入结束时间
     */
    protected Long endTimeMillis;

    public ImportResultModel(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public List<T> getSuccessList() {
        return successList;
    }

    public final void addSuccess(T t) {
        successList.add(t);
    }

    public List<T> getErrorList() {
        return errorList;
    }

    public final void addError(T t) {
        errorList.add(t);
        if (this.error) {
            return;
        }
        this.error = true;
    }

    public List<ErrorInfo> getErrorInfoList() {
        return errorInfoList;
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
    public Boolean isError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Long getStartTimeMillis() {
        return startTimeMillis;
    }

    public Long getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setEndTimeMillis(Long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }

    @Override
    public String toString() {
        return "ImportResultModel{" +
                "fileId='" + fileId + '\'' +
                ", successList=" + successList +
                ", errorList=" + errorList +
                ", errorInfoList=" + errorInfoList +
                ", error=" + error +
                ", startImportDate=" + startTimeMillis +
                ", endImportDate=" + endTimeMillis +
                '}';
    }
}
