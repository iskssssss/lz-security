package cn.lz.tool.poi.reader.reader.excel;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import cn.lz.tool.poi.model.ReadCellInfo;
import cn.lz.tool.poi.reader.abs.AbsDataReader;
import cn.lz.tool.poi.reader.abs.AbsRowHandler;
import cn.lz.tool.poi.reader.model.ErrorInfo;
import cn.lz.tool.poi.reader.model.ImportInfo;
import cn.lz.tool.poi.reader.model.ReadData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 表格文件内容读取器
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 17:26
 */
public class ExcelDataReader<T> extends AbsDataReader<T> {
    private final Workbook workbook;
    private ExcelIterator excelIterator;

    public ExcelDataReader(File importExcelTemp, AbsRowHandler<T> rowHandler) throws IOException {
        super(importExcelTemp, rowHandler);
        this.workbook = WorkbookFactory.create(super.fileInputStream, null);
    }

    @Override
    public Iterator<ReadData> readIterator() {
        if (this.excelIterator == null)
            synchronized (ExcelDataReader.class) {
                if (this.excelIterator == null) {
                    this.excelIterator = new ExcelIterator(this, this.workbook);
                }
            }
        return this.excelIterator;
    }

    @Override
    public void _writeErrorInfo() {
        if (errorInfoList == null || errorInfoList.isEmpty()) {
            return;
        }
        try (BufferedOutputStream outputStream = FileUtil.getOutputStream(super.importFile)) {
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(Font.COLOR_RED);
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(font);
            for (ErrorInfo errorInfo : errorInfoList) {
                String tableIndexStr = errorInfo.getTableIndex();
                if (!NumberUtil.isNumber(tableIndexStr)) {
                    continue;
                }
                int tableIndex = Integer.parseInt(tableIndexStr);
                Sheet sheet = workbook.getSheetAt(tableIndex);
                Row sheetRow = sheet.getRow(errorInfo.getY());
                sheetRow = sheetRow == null ? sheet.createRow(errorInfo.getY()) : sheetRow;
                Cell cell = sheetRow.getCell(errorInfo.getX());
                cell = cell == null ? sheetRow.createCell(errorInfo.getX()) : cell;
                CellStyle style = cell.getCellStyle();
                cellStyle.setBorderBottom(style.getBorderBottom());
                cellStyle.setBorderLeft(style.getBorderLeft());
                cellStyle.setBorderRight(style.getBorderRight());
                cellStyle.setBorderTop(style.getBorderTop());
                cell.setCellStyle(cellStyle);
                cell.setCellValue(errorInfo.getVal());
            }
            workbook.write(outputStream);
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
        try {
            if (this.excelIterator != null) {
                this.excelIterator.close();
            }
            workbook.close();
        } catch (IOException ignored) { }
    }


    private static class ExcelIterator implements Iterator<ReadData> {
        private boolean isNextSheet = true;
        private final Workbook workbook;
        private final List<Sheet> sheetList = new LinkedList<>();
        private Sheet currentSheet;
        private int lastSheetIndex = 0, currentSheetIndex = 0;
        private int lastRowIndex = 0, currentRowIndex = 0;
        private int rowTotal, columnCount;

        private final List<String> modelTitleInfoList;
        private final Map<Integer, String> titleIndexMap;
        private final List<ReadCellInfo> dataList = new LinkedList<>();
        private List<CellRangeAddress> mergedRegions;

        public ExcelIterator(ImportInfo<?> importInfo, Workbook workbook) {
            this.workbook = workbook;
            this.modelTitleInfoList = importInfo.getModelTitleInfoList();
            this.titleIndexMap = importInfo.getTitleIndexMap();
            java.util.Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                this.sheetList.add(sheetIterator.next());
            }
        }

        @Override
        public boolean hasNext() {
            this.dataList.forEach(ReadCellInfo::close);
            this.dataList.clear();
            if (this.isNextSheet) {
                if (this.currentSheetIndex >= this.sheetList.size()) {
                    return false;
                }
                this.titleIndexMap.clear();
                this.lastSheetIndex = this.currentSheetIndex;
                this.currentSheet = this.sheetList.get(this.currentSheetIndex++);
                this.rowTotal = this.currentSheet.getLastRowNum();
                if (this.mergedRegions != null) {
                    this.mergedRegions.clear();
                }
                this.mergedRegions = currentSheet.getMergedRegions();
                this.lastRowIndex = currentRowIndex = 0;
                this.isNextSheet = false;
            }
            if (titleIndexMap.isEmpty()) {
                Row row = this.currentSheet.getRow(this.currentRowIndex);
                this.columnCount = row == null ? -1 : row.getLastCellNum();
            }
            int readRow = 1;
            for (int i = 0; i < this.columnCount; i++) {
                CellRangeAddress merged = this.checkMerged(i, this.currentRowIndex);
                if (merged != null && i == 0) {
                    int firstRow = merged.getFirstRow();
                    int lastRow = merged.getLastRow() + 1;
                    readRow = lastRow - firstRow;
                }
                if (readRow > 1 && merged == null) {
                    List<Object> valueList = new LinkedList<>();
                    for (int y = this.currentRowIndex; y < this.currentRowIndex + readRow; y++) {
                        Object cellValue = this.readCellValue(i, y);
                        valueList.add(cellValue);
                    }
                    ReadCellInfo readCellInfo = new ReadCellInfo(valueList);
                    readCellInfo.setBackgroundColor(getColor(this.getCell(i, this.currentRowIndex)));
                    dataList.add(readCellInfo);
                    continue;
                }
                Object cellValue = this.readCellValue(i, this.currentRowIndex);
                ReadCellInfo readCellInfo = new ReadCellInfo(cellValue);
                readCellInfo.setBackgroundColor(getColor(this.getCell(i, this.currentRowIndex)));
                dataList.add(readCellInfo);
            }
            this.lastRowIndex = this.currentRowIndex;
            this.currentRowIndex = this.currentRowIndex + readRow;
            if (titleIndexMap.isEmpty()) {
                this.checkTitle(this.dataList);
                this.columnCount = titleIndexMap.size();
                return this.hasNext();
            }
            if (this.currentRowIndex > this.rowTotal) {
                this.isNextSheet = true;
            }
            return true;
        }

        @Override
        public ReadData next() {
            return new ReadData(String.valueOf(this.lastSheetIndex), this.lastRowIndex, this.dataList);
        }

        @Override
        public void close() throws IOException {
            this.modelTitleInfoList.clear();
            this.titleIndexMap.clear();
            this.dataList.clear();
            this.mergedRegions.clear();
            this.sheetList.clear();
        }

        /**
         * 获取单元格信息
         *
         * @param x 纵轴
         * @param y 横轴
         * @return 单元格信息
         */
        private Cell getCell(int x, int y) {
            Row row = this.currentSheet.getRow(y);
            if (row == null) {
                return null;
            }
            return row.getCell(x);
        }

        /**
         * 获取单元格的值
         *
         * @param x 纵轴
         * @param y 横轴
         * @return 值
         */
        private Object readCellValue(int x, int y) {
            Cell cell = this.getCell(x, y);
            if (cell == null) {
                return null;
            }
            return CellUtil.getCellValue(cell);
        }

        /**
         * 获取单元格颜色
         *
         * @param cell 单元格
         * @return 颜色
         */
        private String getColor(Cell cell) {
            if (cell == null) {
                return null;
            }
            CellStyle cellStyle = cell.getCellStyle();
            if (cellStyle == null) {
                return null;
            }
            if (cellStyle instanceof XSSFCellStyle) {
                XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
                XSSFColor xssfcolor = Optional.ofNullable(xssfCellStyle.getFillForegroundColorColor()).orElse(xssfCellStyle.getFillBackgroundColorColor());
                xssfcolor = Optional.ofNullable(xssfcolor).orElse(xssfCellStyle.getFillForegroundXSSFColor());
                xssfcolor = Optional.ofNullable(xssfcolor).orElse(xssfCellStyle.getFillBackgroundXSSFColor());
                xssfcolor = Optional.ofNullable(xssfcolor).orElse(xssfCellStyle.getBottomBorderXSSFColor());
                if (xssfcolor == null) {
                    return null;
                }
                byte[] brgb = xssfcolor.getRGB();
                if (brgb == null) {
                    return null;
                }
                return String.format("%02X", brgb[0]) + String.format("%02X", brgb[1]) + String.format("%02X", brgb[2]);
            }
            if (cellStyle instanceof HSSFCellStyle) {
                short colorIndex = cellStyle.getFillForegroundColor();
                HSSFPalette palette = ((HSSFWorkbook) this.workbook).getCustomPalette();
                HSSFColor hssfcolor = palette.getColor(colorIndex);
                if (hssfcolor != null) {
                    short[] srgb = hssfcolor.getTriplet();
                    return String.format("%02X", srgb[0]) + String.format("%02X", srgb[1]) + String.format("%02X", srgb[2]);
                }
            }
            return null;
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
                if (!this.modelTitleInfoList.contains(title)) {
                    this.titleIndexMap.clear();
                    return;
                }
                this.titleIndexMap.put(i, title);
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
            for (CellRangeAddress mergedRegion : this.mergedRegions) {
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
