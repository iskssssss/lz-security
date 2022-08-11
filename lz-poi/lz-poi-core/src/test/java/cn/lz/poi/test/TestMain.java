package cn.lz.poi.test;

import cn.lz.poi.test.model.TestModel2;
import cn.lz.tool.fun.SBiConsumer;
import cn.lz.tool.poi.exception.DataImportException;
import cn.lz.tool.poi.reader.DataReadUtil;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 15:10
 */
public class TestMain {

    @Test
    public void excelTest() throws IOException, InterruptedException {
        File file = new File("E:\\临时文件\\easd.xlsx");
        final CountDownLatch dataReadLatch = new CountDownLatch(1);
        SBiConsumer<AbsDataReader<TestModel2>, DataImportException> callback = (dataReader, error) -> {
            dataReader.writeErrorInfo();
            List<TestModel2> successList = dataReader.getSuccessList();
            //successList.stream().map(JsonUtil::toJsonString).forEach(System.out::println);
            dataReadLatch.countDown();
        };
        try (AbsDataReader<TestModel2> dataReader = DataReadUtil.excelReadAsync(file, new Test2RowHandler(), callback)) {
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("342134");
        System.out.println();
        dataReadLatch.await();
    }

    static class Test2RowHandler extends AbsRowHandler<TestModel2> {

        @Override
        protected Class<TestModel2> importModelClass() {
            return TestModel2.class;
        }
    }
}
