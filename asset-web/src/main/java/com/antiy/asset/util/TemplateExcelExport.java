package com.antiy.asset.util;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class TemplateExcelExport {
    private static Logger          logger         = LogUtils.get();

    private static String          XLS            = ".xls";
    private static String          XLSX           = ".xlsx";

    private String                 memo;
    private String                 title;
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

    private List<String>           headerList;

    /**
     * 当前行号
     */
    private int                    rownum         = 0;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    private List<Object[]>         annotationList = Lists.newArrayList();

    /**
     * 构造方法
     *
     * @param title 表格标题，空值表示无标题
     * @param clazz 实体对象的class，用于获取注解列
     * @param memo 备注
     */
    public TemplateExcelExport(String title, Class clazz, String memo) {
        this.title = title;
        this.memo = memo;
        // 处理属性注解
        Field[] fields = clazz.getDeclaredFields();
        Arrays.asList(fields).stream().forEach(field -> {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                annotationList.add(new Object[] { excelField, field });
            }
        });
        // 排序
        annotationList.sort(Comparator.comparing(o -> new Integer(((ExcelField) o[0]).sort())));
        headerList = Lists.newArrayList();
        // 获取表头信息
        annotationList.forEach(ef -> {
            headerList.add(((ExcelField) ef[0]).title());
        });
        initialize(title, annotationList);

    }

    /**
     * 导出数据到客户端
     *
     * @param dataList
     */
    public void exportToClient(HttpServletResponse response, String fileName, List<?> dataList) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        // 填充数据
        setDataList(dataList);
        write(response.getOutputStream());
    }

    private void write(OutputStream outputStream) throws IOException {
        this.wb.write(outputStream);
        this.wb.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 添加一个单元格
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return
     */
    private void addCell(Row row, int column, Object val, CellStyle style) {
        if (Objects.isNull(val)) {
            return;
        }
        // 第四行备注信息需要合并单元格，进行了特殊处理
        if (rownum == 4) {
            CellRangeAddress cellAddresses = new CellRangeAddress(3, 3, 0, annotationList.size() - 1);
            sheet.addMergedRegion(cellAddresses);
            Cell cell = row.createCell(column);
            cell.setCellStyle(style);
            RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddresses, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, cellAddresses, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cellAddresses, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellAddresses, sheet);
            RegionUtil.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(), cellAddresses, sheet);
            RegionUtil.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(), cellAddresses, sheet);
            RegionUtil.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(), cellAddresses, sheet);
            RegionUtil.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(), cellAddresses, sheet);
            try {
                if (val instanceof String) {
                    String value = (String) val;
                    cell.setCellValue(value);
                }
            } catch (Exception e) {
                logger.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + e.toString());
                cell.setCellValue(val.toString());
            }
            return;
        }
        Cell cell = row.createCell(column);
        try {
            if (val instanceof String) {
                String value = (String) val;
                cell.setCellValue(value);
                // 根据样例数据的长度设置单元格的宽度
                if (value.length() > 10 && value.length() <= 20) {
                    sheet.setColumnWidth(column, 3000 + (value.length() - 10) * 300);
                } else if (value.length() > 20) {
                    sheet.setColumnWidth(column, 3000 + (value.length() - 10) * 600);
                }
            } else if (val instanceof Integer) {
                cell.setCellValue(val.toString());
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            }

        } catch (Exception e) {
            logger.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + e.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
    }

    private void setDataList(List<?> list) {
        if (!list.isEmpty()) {
            Object data = list.get(0);
            if (annotationList != null && annotationList.size() > 0) {
                for (int i = 1; i <= 6; i++) {
                    int colunm = 0;
                    Row row = addRow();
                    if (i == 1) {
                        // 加载提示信息
                        addCell(row, 0, title + "的示例：", styles.get("memo"));
                    } else if (i == 4) {
                        // 加载备注信息
                        addCell(row, 0, memo, styles.get("memo"));
                    } else if (i == 2) {
                        // 加载模板头部数据
                        addCellList(row, headerList, styles.get("templateHeader"));
                    } else if (i == 6) {
                        // 加载头部数据
                        addCellList(row, headerList, styles.get("header"));
                    } else if (i != 5) {
                        // 加载样例数据
                        for (Object[] object : annotationList) {
                            // 获取该属性注解
                            ExcelField excelField = (ExcelField) object[0];
                            Object value = null;
                            // 获取属性的值
                            try {
                                if (StringUtils.isNotBlank(excelField.value())) {
                                    value = ReflectionUtils.invokeGetterMethod(data, excelField.value());
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                                value = "";
                            }
                            if (StringUtils.isNotBlank(excelField.dictType())) {
                                if (value != null) {
                                    value = CodeUtils.getCodeName(excelField.dictType(),
                                        Integer.parseInt(value.toString()));
                                } else {
                                    value = "";
                                }
                            } else if (excelField.isDate()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
                                String s = Objects.toString(value);
                                if (s != null && !s.equals("null") && !s.equals("")) {
                                    value = simpleDateFormat.format(new Date(Long.parseLong(Objects.toString(value))));
                                } else {
                                    value = "";
                                }
                                addCell(row, colunm++, value, styles.get("date"));
                                continue;
                            }
                            addCell(row, colunm++, value, styles.get("templateData"));
                        }
                    }
                }
            }
        }
    }

    /**
     * 导出数据到文件
     *
     * @param dataList
     */
    public void exportToFile(String fileName, List<?> dataList) throws IOException {
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            fileName = fileName + XLSX;
        }
        FileOutputStream os = new FileOutputStream(fileName);
        // 填充数据
        setDataList(dataList);
        write(os);
    }

    private void addCellList(Row row, List<String> dataList, CellStyle style) {
        for (int i = 0; i < dataList.size(); i++) {
            addCell(row, i, dataList.get(i), style);
        }
    }

    private void initialize(String title, List<Object[]> annotationList) {
        // 创建工作簿
        this.wb = new XSSFWorkbook();
        // 创建工作表sheet
        this.sheet = wb.createSheet("Export");
        // 初始化样式
        this.styles = createStyles(wb);

        ParamterExceptionUtils.isEmpty(headerList, "The table title can not be null");
        for (int i = 0; i < headerList.size(); i++) {
            sheet.autoSizeColumn(i);
            // sheet.trackAllColumnsForAutoSizing();
            // 为码表类型，设置下拉框
            if (annotationList.size() > 0
                && StringUtils.isNotBlank(((ExcelField) annotationList.get(i)[0]).dictType())) {
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                    .createExplicitListConstraint(
                        CodeUtils.getCodeArray(((ExcelField) annotationList.get(i)[0]).dictType()));
                // 设置区域边界
                CellRangeAddressList addressList = new CellRangeAddressList(6, 100000, i, i);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint,
                    addressList);
                // 输入非法数据时，弹窗警告框
                // validation.setShowErrorBox(true);
                validation.setShowPromptBox(true);
                sheet.addValidationData(validation);
            }
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        logger.debug("workbook Initialize success.");
    }

    private Map<String, CellStyle> createStyles(XSSFWorkbook wb) {
        styles = new HashMap<>();
        CellStyle style;
        // 数据样式
        style = initDataStyle(wb);
        styles.put("data", style);

        // 表头样式
        style = wb.createCellStyle();
        Font headerFont = getFont(wb, style);
        headerFont.setBold(true);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        // 模板表头样式
        style = wb.createCellStyle();
        Font templateHeaderFont = getFont(wb, style);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        templateHeaderFont.setBold(true);
        templateHeaderFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("templateHeader", style);

        // 备注样式
        style = wb.createCellStyle();
        Font memoFont = getFont(wb, style);
        memoFont.setBold(true);
        memoFont.setColor(IndexedColors.RED.getIndex());
        style.setFont(memoFont);
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("memo", style);

        // 模板数据样式
        style = wb.createCellStyle();
        Font templateDataFont = getFont(wb, style);
        templateDataFont.setColor(IndexedColors.RED.getIndex());
        style.setFont(templateDataFont);
        styles.put("templateData", style);

        // 日期格式
        style = wb.createCellStyle();
        style.setFont(templateDataFont);
        style.setDataFormat(wb.createDataFormat().getFormat("yyyy/M/d"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("date", style);
        return styles;
    }

    private CellStyle initDataStyle(XSSFWorkbook wb) {
        CellStyle style;
        style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        return style;
    }

    private Font getFont(XSSFWorkbook wb, CellStyle style) {
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return wb.createFont();
    }

    private Row addRow() {
        return sheet.createRow(rownum++);
    }
}
