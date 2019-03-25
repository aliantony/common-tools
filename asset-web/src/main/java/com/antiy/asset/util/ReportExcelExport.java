package com.antiy.asset.util;

import com.antiy.asset.templet.ReportForm;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportExcelExport {
    private static Logger          logger = LogUtils.get();

    private static String          XLS    = ".xls";
    private static String          XLSX   = ".xlsx";

    /**
     * 工作簿对象
     */
    private XSSFWorkbook           wb;

    /**
     * 工作表对象
     */
    private XSSFSheet              sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    // 表单实体
    private ReportForm             reportForm;

    /**
     * 当前行号
     */
    private int                    rownum = 0;

    public ReportExcelExport(ReportForm reportForm) {
        this.reportForm = reportForm;
        initialize();
    }

    private Map<String, CellStyle> createStyles(XSSFWorkbook wb) {
        styles = new HashMap<>();
        CellStyle style;
        // 数据样式
        style = initDataStyle(wb);
        styles.put("data", style);
        Font dataFont = wb.createFont();

        style = initDataStyle(wb);
        dataFont.setFontHeightInPoints((short) 10);
        dataFont.setFontName("Arial");
        style.setFont(dataFont);
        dataFont.setBold(true);
        style.setFont(dataFont);
        styles.put("header", style);

        return styles;
    }

    private CellStyle initDataStyle(XSSFWorkbook wb) {
        CellStyle style;
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 10);
        dataFont.setFontName("Arial");
        style.setFont(dataFont);
        return style;
    }

    /**
     * 初始化工作簿
     *
     */
    private void initialize() {
        List<String> headerList = reportForm.getHeaderList();
        String title = reportForm.getTitle();

        ParamterExceptionUtils.isNull(headerList, "标题不能为空");

        // 创建工作簿
        this.wb = new XSSFWorkbook();
        // 创建工作表sheet
        this.sheet = wb.createSheet(title);
        // 初始化样式
        this.styles = createStyles(wb);
        // 设置标题
        if (StringUtils.isNotBlank(reportForm.getTitle())) {
            Row titleRow = addRow();
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("header"));
            titleCell.setCellValue(reportForm.getTitle());
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
                headerList.size()));
        }
        // 设置表头
        Row headRow = addRow();
        headRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headRow.createCell(i + 1);
            cell.setCellStyle(styles.get("data"));
            cell.setCellValue(headerList.get(i));
        }
        for (int i = 0; i <= headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        logger.debug("workbook Initialize success.");
    }

    /**
     * 导出数据到客户端
     *
     */
    public void exportToClient(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        // 填充数据
        setData();
        write(response.getOutputStream());
    }

    private void setData() {
        List<String> columnList = reportForm.getColumnList();
        ParamterExceptionUtils.isEmpty(columnList, "列标题不能为空");
        List<String> headerList = reportForm.getHeaderList();
        ParamterExceptionUtils.isEmpty(headerList, "行标题不能为空");
        String[][] data = reportForm.getData();
        ParamterExceptionUtils.isNull(data, "数据不能为空");
        ParamterExceptionUtils.isTrue(data.length <= headerList.size() || data[0].length <= columnList.size(),
            "数据数量不对");
        for (int i = 0; i < columnList.size(); i++) {
            Row row = addRow();
            String columnHeader = columnList.get(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(columnHeader);
            cell.setCellStyle(styles.get("data"));
            for (int j = 0; j < headerList.size(); j++) {
                Cell cel = row.createCell(j + 1);
                cel.setCellValue(data[i][j] == null ? "" : data[i][j]);
                cel.setCellStyle(styles.get("data"));
            }
        }
    }

    private void write(OutputStream outputStream) throws IOException {
        this.wb.write(outputStream);
        this.wb.close();
        outputStream.flush();
        outputStream.close();
    }

    private Row addRow() {
        return sheet.createRow(rownum++);
    }

}
