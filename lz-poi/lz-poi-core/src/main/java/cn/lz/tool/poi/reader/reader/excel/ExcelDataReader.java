package cn.lz.tool.poi.reader.reader.excel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import cn.lz.tool.poi.reader.handler.ImportInfo;
import cn.lz.tool.poi.reader.model.ErrorInfo;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.model.ReadData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 表格文件内容读取器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 17:26
 */
public class ExcelDataReader<T> extends AbsDataReader<T> {
    private final ExcelReader excelReader;
    private ExcelIterator excelIterator;

    public ExcelDataReader(File importExcelTemp, AbsRowHandler<T> rowHandler) {
        super(importExcelTemp, rowHandler);
        this.excelReader = ExcelUtil.getReader(importExcelTemp);
    }

    @Override
    public Iterator<ReadData> readIterator() {
        if (this.excelIterator == null)
            synchronized (ExcelDataReader.class) {
                if (this.excelIterator == null) {
                    this.excelIterator = new ExcelIterator(this, this.excelReader);
                }
            }
        return this.excelIterator;
    }

    @Override
    public void writeErrorInfo() {
        try (ExcelWriter writer = ExcelUtil.getWriter(importFile)) {
            for (ErrorInfo errorInfo : errorInfoList) {
                int xIndex = errorInfo.getX();
                int yIndex = errorInfo.getY();
                writer.writeCellValue(xIndex, yIndex, errorInfo.getVal());
            }
        }
    }

    @Override
    public void close() throws IOException {
        boolean error = isError();
        super.close();
        if (this.excelIterator != null) {
            this.excelIterator.close();
        }
        excelReader.close();
        if (error) {
            return;
        }
        this.del();
    }


    private static class ExcelIterator implements Iterator<ReadData> {
        private int tableIndex = 0;
        private int rowIndex;
        private int lastRowIndex;
        private final ExcelReader excelReader;
        private final List<String> modelTitleInfoList;
        private final Map<Integer, String> titleIndexMap;
        private final List<ReadCellInfo> dataList = new LinkedList<>();
        private final List<CellRangeAddress> mergedRegions;

        public ExcelIterator(ImportInfo<?> importInfo, ExcelReader excelReader) {
            this.excelReader = excelReader;
            this.modelTitleInfoList = importInfo.getModelTitleInfoList();
            this.titleIndexMap = importInfo.getTitleIndexMap();
            Workbook workbook = this.excelReader.getWorkbook();
            Sheet sheet = workbook.getSheetAt(this.tableIndex);
            this.lastRowIndex = sheet.getLastRowNum();
            this.mergedRegions = sheet.getMergedRegions();
        }

        @Override
        public boolean hasNext() {
            dataList.forEach(ReadCellInfo::close);
            dataList.clear();
            if (rowIndex > this.lastRowIndex) {
                return false;
            }
            int columnCount = excelReader.getColumnCount(rowIndex);
            int readRow = 1;
            for (int i = 0; i < columnCount; i++) {
                CellRangeAddress merged = this.checkMerged(i, rowIndex);
                if (merged != null && i == 0) {
                    int firstRow = merged.getFirstRow() + 1;
                    int lastRow = merged.getLastRow() + 1;
                    readRow = (lastRow - firstRow) + 1;
                }
                if (readRow > 1 && merged == null) {
                    List<Object> valueList = new LinkedList<>();
                    for (int y = rowIndex; y < rowIndex + readRow; y++) {
                        Object cellValue = excelReader.readCellValue(i, y);
                        valueList.add(cellValue);
                    }
                    Cell cell = excelReader.getCell(i, rowIndex);
                    ReadCellInfo readCellInfo = new ReadCellInfo(valueList);
                    readCellInfo.setBackgroundColor(getColor(cell.getCellStyle()));
                    dataList.add(readCellInfo);
                    continue;
                }
                Object cellValue = excelReader.readCellValue(i, rowIndex);
                ReadCellInfo readCellInfo = new ReadCellInfo(cellValue);
                Cell cell = excelReader.getCell(i, rowIndex);
                readCellInfo.setBackgroundColor(getColor(cell.getCellStyle()));
                dataList.add(readCellInfo);
            }
            rowIndex = rowIndex + readRow;
            if (titleIndexMap.isEmpty()) {
                this.checkTitle(dataList);
                return this.hasNext();
            }
            return true;
        }

        /**
         * 获取单元格颜色
         *
         * @param cellStyle 单元格样式
         * @return 颜色
         */
        private String getColor(CellStyle cellStyle) {
            if (cellStyle instanceof XSSFCellStyle) {
                XSSFColor xssfcolor = ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor();
                if (xssfcolor != null) {
                    byte[] brgb = xssfcolor.getRGB();
                    return String.format("%02X", brgb[0]) + String.format("%02X", brgb[1]) + String.format("%02X", brgb[2]);
                }
                return null;
            }
            if (cellStyle instanceof HSSFCellStyle) {
                Workbook workbook = excelReader.getWorkbook();
                short colorIndex = cellStyle.getFillForegroundColor();
                HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
                HSSFColor hssfcolor = palette.getColor(colorIndex);
                if (hssfcolor != null) {
                    short[] srgb = hssfcolor.getTriplet();
                    return String.format("%02X", srgb[0]) + String.format("%02X", srgb[1]) + String.format("%02X", srgb[2]);
                }
            }
            return null;
        }

        @Override
        public ReadData next() {
            return new ReadData(String.valueOf(this.tableIndex), this.rowIndex, this.dataList);
        }

        @Override
        public void close() throws IOException {
            modelTitleInfoList.clear();
            titleIndexMap.clear();
            dataList.clear();
            mergedRegions.clear();
        }

        /**
         * 校验是否是标题行
         *
         * @param dataList 数据
         */
        private void checkTitle(List<ReadCellInfo> dataList) {
            for (int i = 0; i < dataList.size(); i++) {
                ReadCellInfo readCellInfo = dataList.get(i);
                Object data = readCellInfo.getValue();
                String title = StrUtil.toString(data).replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
                if (!modelTitleInfoList.contains(title)) {
                    titleIndexMap.clear();
                    return;
                }
                titleIndexMap.put(i, title);
            }
        }

        /**
         * 校验是否是合并单元格
         *
         * @param xIndex 纵坐标
         * @param yIndex 横坐标
         * @return 合并信息
         */
        private CellRangeAddress checkMerged(int xIndex, int yIndex) {
            for (CellRangeAddress mergedRegion : mergedRegions) {
                int firstRow = mergedRegion.getFirstRow();
                int lastRow = mergedRegion.getLastRow();
                int firstColumn = mergedRegion.getFirstColumn();
                int lastColumn = mergedRegion.getLastColumn();
                if ((firstRow <= yIndex && yIndex <= lastRow) &&
                        (firstColumn <= xIndex && xIndex <= lastColumn)) {
                    return mergedRegion;
                }
            }
            return null;
        }
    }
}
