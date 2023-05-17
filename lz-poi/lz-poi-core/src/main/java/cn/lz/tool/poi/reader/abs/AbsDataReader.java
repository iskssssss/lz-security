package cn.lz.tool.poi.reader.abs;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.lz.tool.fun.SBiConsumer;
import cn.lz.tool.poi.exception.DataImportException;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.model.ImportInfo;
import cn.lz.tool.poi.reader.model.ReadData;

import java.io.Closeable;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 抽象数据读取器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 17:24
 */
public abstract class AbsDataReader<T> extends ImportInfo<T> implements Runnable {
    private transient final AtomicBoolean read = new AtomicBoolean(false);
    private transient final CountDownLatch dataReadLatch = new CountDownLatch(1);
    private transient DataImportException dataImportException;
    protected transient final AbsRowHandler<T> rowHandler;
    protected transient boolean isAsync = true;
    private transient SBiConsumer<AbsDataReader<T>, DataImportException> callback;

    protected AbsDataReader(File importFile, AbsRowHandler<T> rowHandler) {
        super(IdUtil.fastSimpleUUID() + System.currentTimeMillis(), importFile);
        this.rowHandler = rowHandler;
        ImportInfo.IMPORT_FILE_MAP.put(fileId, this.importFile);
        rowHandler.init(this);
    }

    /**
     * 获取读取流
     *
     * @return 读取流
     */
    public abstract Iterator<ReadData> readIterator();

    /**
     * 写入异常信息
     */
    protected abstract void _writeErrorInfo();

    /**
     * 写入异常信息
     */
    public final void writeErrorInfo() {
        // 判断
        this._writeErrorInfo();
    }

    /**
     * 读取数据
     *
     * @return this
     */
    public final AbsDataReader<T> read() {
        if (read.get()) {
            return this;
        }
        read.set(true);
        new Thread(this).start();
        return this;
    }

    @Override
    public final void run() {
        try {
            Iterator<ReadData> readIterator = this.readIterator();
            while (readIterator.hasNext()) {
                ReadData readData = readIterator.next();
                String tableIndex = readData.getTableIndex();
                int y = readData.getY();
                List<ReadCellInfo> dataList = readData.getDataList();
                AbsRowHandler.ToResult<T> toResult = rowHandler._to(tableIndex, y, dataList);
                T result = toResult.getResult();
                if (toResult.isError()) {
                    super.addError(result);
                    toResult.copy(super.errorInfoList);
                    continue;
                }
                super.addSuccess(result);
            }
        } catch (Exception e) {
            this.dataImportException = new DataImportException(e);
        } finally {
            super.setEndTimeMillis(System.currentTimeMillis());
            dataReadLatch.countDown();
            if (this.isAsync && this.callback != null) {
                this.callback.accept(this, this.dataImportException);
                this.isAsync = false;
                this.close();
            }
        }
    }

    @Override
    public void close() {
        if (this.isAsync) {
            return;
        }
        super.close();
        if (super.error) {
            return;
        }
        this.del();
    }

    /**
     * 删除本地文件
     */
    public void del() {
        try {
            if (this.importFile.exists()) {
                FileUtil.del(this.importFile);
            }
        } catch (Exception e) {
            System.err.println("导入文件删除失败");
        }
    }

    /**
     * 获取行处理器
     *
     * @return 行处理器
     */
    public final AbsRowHandler<T> getRowHandler() {
        return rowHandler;
    }

    /**
     * 同步读取
     *
     * @return this
     * @throws DataImportException 异常
     */
    public final AbsDataReader<T> sync() throws DataImportException {
        try {
            this.isAsync = true;
            dataReadLatch.await();
        } catch (InterruptedException e) {
            this.dataImportException = new DataImportException(e);
        }
        if (this.dataImportException != null) {
            throw this.dataImportException;
        }
        return this;
    }


    /**
     * 设置在异步读取下的回调函数
     *
     * @param callback 回调函数
     */
    public void setCallback(SBiConsumer<AbsDataReader<T>, DataImportException> callback) {
        this.callback = callback;
    }

    /**
     * 数据链式读取接口
     *
     * @param <E> 类型
     */
    protected interface Iterator<E> extends Closeable {

        /**
         * 是否有下一行
         *
         * @return 是否有下一行
         */
        boolean hasNext();

        /**
         * 获取下一行数据
         *
         * @return 数据
         */
        E next();
    }

    /**
     * 判断读取信息是否为空
     *
     * @param dataList 数据列表
     * @param size     正确数据长度
     * @return 是否为空
     */
    protected static boolean isEmpty(List<ReadCellInfo> dataList, int size) {
        if (dataList.isEmpty() || dataList.size() < size) {
            return true;
        }
        for (ReadCellInfo readCellInfo : dataList) {
            Object value = readCellInfo.getValue();
            if (value instanceof Collection) {
                value = ((Collection<?>) value).stream().map(StrUtil::toString).filter(StrUtil::isNotBlank).collect(Collectors.joining());
            }
            if (StrUtil.isNotBlank(StrUtil.toString(value).replaceAll(" ", ""))) {
                return false;
            }
        }
        return true;
    }
}
