package cn.lz.tool.poi.reader.reader.world;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import cn.lz.tool.poi.reader.model.ErrorInfo;
import cn.lz.tool.poi.reader.model.ImportInfo;
import cn.lz.tool.poi.reader.model.ReadData;
import cn.lz.tool.poi.reader.reader.excel.ExcelDataReader;
import org.apache.poi.xwpf.usermodel.*;

import java.io.BufferedOutputStream;
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

    private final XWPFDocument document;
    private WorldIterator worldIterator;

    public WorldTableDataReader(File importFile, AbsRowHandler<T> rowHandler) throws IOException {
        super(importFile, rowHandler);
        this.document = new XWPFDocument(super.fileInputStream);
    }

    @Override
    public Iterator<ReadData> readIterator() {
        if (this.worldIterator == null)
            synchronized (ExcelDataReader.class) {
                if (this.worldIterator == null)
                    this.worldIterator = new WorldIterator(this, this.document);
            }
        return this.worldIterator;
    }

    @Override
    public void _writeErrorInfo() {
        if (errorInfoList == null || errorInfoList.isEmpty()) {
            return;
        }
        try (BufferedOutputStream outputStream = FileUtil.getOutputStream(super.importFile)) {
            for (ErrorInfo errorInfo : errorInfoList) {
                String tableIndexStr = errorInfo.getTableIndex();
                if (!NumberUtil.isNumber(tableIndexStr)) {
                    continue;
                }
                int tableIndex = Integer.parseInt(tableIndexStr);
                XWPFTable table = this.document.getTableArray(tableIndex);
                XWPFTableRow row = table.getRow(errorInfo.getY());
                XWPFTableCell cell = row.getCell(errorInfo.getX());
                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setBold(true);
                run.setColor("FF0000");
                run.setText(errorInfo.getVal());
            }
            this.document.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[数据导入] 异常文件写入失败：" + e.getMessage());
        }
    }

    @Override
    public void close() {
        if (this.isAsync) {
            return;
        }
        super.close();
        if (this.worldIterator != null) {
            this.worldIterator.close();
        }
        try {
            document.close();
        } catch (IOException ignored) { }
    }

    private static class WorldIterator implements Iterator<ReadData> {
        private boolean isNextTable = true;
        private final List<XWPFTable> tableList;
        private XWPFTable currentTable;
        private int lastTableIndex = 0, currentTableIndex = 0;
        private int lastRowIndex = 0, currentRowIndex = 0;
        private int rowTotal;

        private final List<String> modelTitleInfoList;
        private final Map<Integer, String> titleIndexMap;
        private final List<ReadCellInfo> dataList = new LinkedList<>();

        private WorldIterator(ImportInfo<?> importInfo, XWPFDocument xwpf) {
            this.tableList = xwpf.getTables();
            this.modelTitleInfoList = importInfo.getModelTitleInfoList();
            this.titleIndexMap = importInfo.getTitleIndexMap();
        }

        @Override
        public boolean hasNext() {
            if (this.isNextTable) {
                if (currentTableIndex >= tableList.size()) {
                    return false;
                }
                this.lastTableIndex = currentTableIndex;
                this.currentTable = tableList.get(currentTableIndex++);
                this.rowTotal = this.currentTable.getRows().size();
                this.currentRowIndex = 0;
                if (!readTitle()) {
                    return this.hasNext();
                }
                this.isNextTable = false;
            }
            this.readData();
            lastRowIndex = currentRowIndex;
            currentRowIndex++;
            if (this.currentRowIndex >= rowTotal) {
                this.isNextTable = true;
            }
            return true;
        }

        @Override
        public ReadData next() {
            return new ReadData(String.valueOf(this.lastTableIndex), this.lastRowIndex, this.dataList);
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
            XWPFTableRow xwpfTableRow = this.currentTable.getRow(currentRowIndex);
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
            List<XWPFTableCell> tableCells = this.currentTable.getRow(currentRowIndex).getTableCells();
            for (int i = 0; i < tableCells.size(); i++) {
                XWPFTableCell cell = tableCells.get(i);
                String title = cell.getText().replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
                if (!modelTitleInfoList.contains(title)) {
                    titleIndexMap.clear();
                    return false;
                }
                titleIndexMap.put(i, title);
            }
            currentRowIndex++;
            return true;
        }
    }

}
