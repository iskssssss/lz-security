package cn.lz.tool.poi.reader.reader.world;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.lz.tool.json.JsonUtil;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import cn.lz.tool.poi.reader.handler.ImportInfo;
import cn.lz.tool.poi.reader.model.ErrorInfo;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.model.ReadData;
import cn.lz.tool.poi.reader.reader.excel.ExcelDataReader;
import org.apache.poi.xwpf.usermodel.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档表格读取器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 17:26
 */
public class WorldTableDataReader<T> extends AbsDataReader<T> {
    private final BufferedInputStream inputStream;
    private final XWPFDocument xwpf;

    private WorldIterator worldIterator;

    public WorldTableDataReader(File importExcelTemp, AbsRowHandler<T> rowHandler) throws IOException {
        super(importExcelTemp, rowHandler);
        this.inputStream = FileUtil.getInputStream(importExcelTemp);
        this.xwpf = new XWPFDocument(inputStream);
    }

    @Override
    public Iterator<ReadData> readIterator() {
        if (this.worldIterator == null)
            synchronized (ExcelDataReader.class) {
                if (this.worldIterator == null)
                    this.worldIterator = new WorldIterator(this, this.xwpf);
            }
        return this.worldIterator;
    }

    @Override
    public void writeErrorInfo() {
        System.out.println(importFile);
        for (ErrorInfo errorInfo : errorInfoList) {
            System.err.println(JsonUtil.toJsonString(errorInfo));
        }
    }

    @Override
    public void close() throws IOException {
        boolean error = isError();
        super.close();
        if (this.worldIterator != null) {
            this.worldIterator.close();
        }
        inputStream.close();
        xwpf.close();
        if (error) {
            return;
        }
        this.del();
    }

    private static class WorldIterator implements Iterator<ReadData> {
        private boolean isNextTable = true;
        private final List<String> modelTitleInfoList;
        private final Map<Integer, String> titleIndexMap;
        private final List<ReadCellInfo> dataList = new LinkedList<>();

        private final List<XWPFTable> tableList;
        private int tableIndex = 0;
        private List<XWPFTableRow> rows;
        private int rowIndex = 0;

        private WorldIterator(ImportInfo<?> importInfo, XWPFDocument xwpf) {
            this.tableList = xwpf.getTables();
            this.modelTitleInfoList = importInfo.getModelTitleInfoList();
            this.titleIndexMap = importInfo.getTitleIndexMap();
        }

        @Override
        public boolean hasNext() {
            if (this.isNextTable) {
                if ((tableIndex + 1) >= tableList.size()) {
                    return false;
                }
                XWPFTable table = tableList.get(tableIndex++);
                this.rows = table.getRows();
                this.rowIndex = 0;
                if (!readTitle()) {
                    return this.hasNext();
                }
                this.isNextTable = false;
            }
            this.readData();
            rowIndex++;
            if (this.rowIndex >= rows.size()) {
                this.isNextTable = true;
            }
            return true;
        }

        @Override
        public ReadData next() {
            return new ReadData(String.valueOf(tableIndex), this.rowIndex, this.dataList);
        }

        @Override
        public void close() {
            this.dataList.clear();
        }

        /**
         * 读取数据
         */
        private void readData() {
            dataList.forEach(ReadCellInfo::close);
            dataList.clear();
            XWPFTableRow xwpfTableRow = rows.get(rowIndex);
            List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
            for (XWPFTableCell tableCell : tableCells) {
                List<XWPFParagraph> paragraphs = tableCell.getParagraphs();
                if (paragraphs.size() < 2) {
                    ReadCellInfo readCellInfo = new ReadCellInfo(tableCell.getText());
                    readCellInfo.setBackgroundColor(tableCell.getColor());
                    this.dataList.add(readCellInfo);
                    continue;
                }
                List<Object> valueList = new ArrayList<>();
                for (XWPFParagraph paragraph : paragraphs) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    valueList.add(runs.stream().map(StrUtil::toString).collect(Collectors.joining("")));
                }
                ReadCellInfo readCellInfo = new ReadCellInfo(valueList);
                readCellInfo.setBackgroundColor(tableCell.getColor());
                this.dataList.add(readCellInfo);
            }
        }

        /**
         * 读取标题信息
         *
         * @return 是否进入下一行
         */
        private boolean readTitle() {
            List<XWPFTableCell> tableCells = rows.get(rowIndex).getTableCells();
            for (int i = 0; i < tableCells.size(); i++) {
                XWPFTableCell cell = tableCells.get(i);
                String title = cell.getText().replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
                if (!modelTitleInfoList.contains(title)) {
                    titleIndexMap.clear();
                    return false;
                }
                titleIndexMap.put(i, title);
            }
            rowIndex++;
            return true;
        }
    }

}
