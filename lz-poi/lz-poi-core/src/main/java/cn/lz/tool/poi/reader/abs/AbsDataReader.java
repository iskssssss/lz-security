package cn.lz.tool.poi.reader.abs;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.lz.tool.poi.reader.handler.ImportInfo;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.model.ReadData;

import java.io.Closeable;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象数据读取器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 17:24
 */
public abstract class AbsDataReader<T> extends ImportInfo<T> implements Runnable {
    private final AtomicBoolean read = new AtomicBoolean(false);
    private final CountDownLatch dataReadLatch = new CountDownLatch(1);
    protected final AbsRowHandler<T> rowHandler;

    protected AbsDataReader(File importFile, AbsRowHandler<T> rowHandler) {
        super(importFile);
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
    public abstract void writeErrorInfo();

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
        Iterator<ReadData> readIterator = this.readIterator();
        while (readIterator.hasNext()) {
            ReadData readData = readIterator.next();
            T t = rowHandler._to(readData.getTableIndex(), readData.getY(), readData.getDataList());
            super.addResult(t);
        }
        dataReadLatch.countDown();
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
     * 堵塞当前线程直至数据读取结束
     *
     * @return this
     * @throws InterruptedException 异常
     */
    public final AbsDataReader<T> await() throws InterruptedException {
        dataReadLatch.await();
        return this;
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
            if (StrUtil.isNotEmpty(StrUtil.toString(value).replaceAll(" ", ""))) {
                return false;
            }
        }
        return true;
    }
}
