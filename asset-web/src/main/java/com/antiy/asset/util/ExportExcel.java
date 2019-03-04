package com.antiy.asset.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @Description: 导出excel 支持大数据量导出
 * @Author: lvliang
 * @Date: 2018/12/02
 */
public class ExportExcel {

    private static Logger          logger         = LogUtils.get();

    private static String          XLS            = ".xls";
    private static String          XLSX           = ".xlsx";
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

    /**
     * 当前行号
     */
    private int                    rownum         = 0;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]>                 annotationList = Lists.newArrayList();

    /**
     * 构造方法
     *
     * @param title 表格标题，空值表示无标题
     * @param headers 标题列
     */
    public ExportExcel(String title, String[] headers) {
        initialize(title, Arrays.asList(headers), annotationList, 1);
    }

    /**
     * 构造方法
     *
     * @param title 表格标题，空值表示无标题
     * @param headerList 标题列
     */
    public ExportExcel(String title, List<String> headerList) {
        initialize(title, headerList, annotationList, 1);
    }

    /**
     * 构造方法
     *
     * @param title 表格标题，空值表示无标题
     * @param clazz 实体对象的class，用于获取注解列
     */
    public ExportExcel(String title, Class<?> clazz) {
        this(title, clazz, 1);
    }

    /**
     * 构造方法
     *
     * @param title 表格标题，空值表示无标题
     * @param clazz 实体对象的class，用于获取注解列
     * @param type 导出类型，1导出数据，2导出模板
     */
    public ExportExcel(String title, Class<?> clazz, int type) {
        // 处理属性注解
        Field[] fields = clazz.getDeclaredFields();
        Arrays.asList(fields).stream().forEach(field -> {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                if (excelField.type() == 0 || excelField.type() == type) {
                    annotationList.add(new Object[] { excelField, field });
                }
            }
        });
        // 处理方法上的注解
        Method[] methods = clazz.getMethods();
        Arrays.asList(methods).stream().forEach(method -> {
            ExcelField excelField = method.getAnnotation(ExcelField.class);
            if (excelField != null) {
                if (excelField.type() == 0 || excelField.type() == type) {
                    annotationList.add(new Object[] { excelField, method });
                }
            }
        });
        // 排序
        annotationList.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
            }
        });
        List headerList = Lists.newArrayList();
        // 获取表头信息
        annotationList.forEach(ef -> {
            headerList.add(((ExcelField) ef[0]).title());
        });
        initialize(title, headerList, annotationList, type);
    }

    /**
     * 初始化工作簿
     *
     * @param title 表格标题，空表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, List<String> headerList, List<Object[]> annotationList, int type) {
        // 创建工作簿
        this.wb = new XSSFWorkbook();
        // 创建工作表sheet
        this.sheet = wb.createSheet("Export");
        // 初始化样式
        this.styles = createStyles(wb);
        // 设置标题
        // if (StringUtils.isNotBlank(title)) {
        // Row titleRow = addRow();
        // Cell titleCell = titleRow.createCell(0);
        // titleCell.setCellStyle(styles.get("title"));
        // titleCell.setCellValue(title);
        // sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
        // headerList.size() - 1));
        // }
        // 设置表头
        ParamterExceptionUtils.isEmpty(headerList, "The table title can not be null");
        Row headRow = addRow();
        headRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            cell.setCellValue(headerList.get(i));
            sheet.autoSizeColumn(i);
            // sheet.trackAllColumnsForAutoSizing();
            // 为码表类型，设置下拉框
            if (type == 2 && annotationList.size() > 0
                && StringUtils.isNotBlank(((ExcelField) annotationList.get(i)[0]).dictType())) {
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                    .createExplicitListConstraint(
                        CodeUtils.getCodeArray(((ExcelField) annotationList.get(i)[0]).dictType()));
                // 设置区域边界
                CellRangeAddressList addressList = new CellRangeAddressList(1, 100000, i, i);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint,
                    addressList);
                // 输入非法数据时，弹窗警告框
                validation.setShowErrorBox(true);
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

    /**
     * 创建样式
     *
     * @param wb 工作簿对象
     * @return
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>(16);
        // 标题样式
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        // 数据样式
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
        styles.put("data", style);

        // 居左样式
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);
        // 居中样式
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);
        // 居右样式
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);
        // 表头样式
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);
        return styles;
    }

    /**
     * 创建日期样式
     *
     * @param wb 工作簿对象
     * @return
     */
    private CellStyle createDateStyle(Workbook wb, int align) {
        // 数据样式
        CellStyle style = wb.createCellStyle();
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
        DataFormat format = wb.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy-MM-dd"));
        if (align == 0) {
            style.setAlignment(HorizontalAlignment.GENERAL);
        } else if (align == 1) {
            style.setAlignment(HorizontalAlignment.LEFT);
        } else if (align == 2) {
            style.setAlignment(HorizontalAlignment.CENTER);
        } else if (align == 3) {
            style.setAlignment(HorizontalAlignment.RIGHT);
        }
        return style;
    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }

    /**
     * 添加一个单元格
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return 单元格对象
     */
    public void addCell(Row row, int column, Object val) {
        this.addCell(row, column, val, 0);
    }

    /**
     * 添加一个单元格
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @param align 对齐方式（1：靠左；2：居中；3：靠右）
     * @return
     */
    public void addCell(Row row, int column, Object val, int align) {
        if (Objects.isNull(val)) {
            return;
        }
        Cell cell = row.createCell(column);
        /* if (this.sheet.getColumnWidth(column) < val.toString().getBytes().length * 255) {
         * this.sheet.setColumnWidth(column, val.toString().getBytes().length * 255); } */
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        cell.setCellStyle(style);
        try {
            if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                cell.setCellStyle(createDateStyle(wb, align));
                cell.setCellValue((Date) val);
            }
        } catch (Exception e) {
            logger.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + e.toString());
            cell.setCellValue(val.toString());
        }

    }

    /**
     * 添加数据
     *
     * @param list 数据列表
     * @param <T>
     * @return
     */
    public <T> ExportExcel setDataList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            if (annotationList != null && annotationList.size() > 0) {
                list.forEach(data -> {
                    int colunm = 0;
                    Row row = addRow();
                    for (Object[] object : annotationList) {
                        // 获取该属性注解
                        ExcelField excelField = (ExcelField) object[0];
                        Object value = null;
                        // 获取属性的值
                        try {
                            if (StringUtils.isNotBlank(excelField.value())) {
                                value = ReflectionUtils.invokeGetterMethod(data, excelField.value());
                            } else {
                                if (object[1] instanceof Field) {
                                    value = ReflectionUtils.invokeGetterMethod(data, ((Field) object[1]).getName());
                                } else if (object[1] instanceof Method) {
                                    value = ReflectionUtils.invokeMethod(data, ((Method) object[1]).getName(),
                                        new Class[] {}, new Object[] {});
                                }
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
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            String s = Objects.toString(value);
                            if (s != null && !s.equals("null")) {
                                value = simpleDateFormat.format(new Date(Long.parseLong(Objects.toString(value))));
                            } else {
                                value = "";
                            }
                        }
                        this.addCell(row, colunm++, value, excelField.align());
                    }
                });
            } else {
                if (list.get(0) instanceof Object[]) {
                    for (T data : list) {
                        int colunm = 0;
                        Row row = addRow();
                        Object[] d = (Object[]) data;
                        for (Object value : d) {
                            this.addCell(row, colunm++, value, 0);
                        }
                    }
                } else if (list.get(0) instanceof List) {
                    for (T data : list) {
                        int colunm = 0;
                        Row row = addRow();
                        List d = (List) data;
                        for (Object value : d) {
                            this.addCell(row, colunm++, value, 0);
                        }
                    }
                }
            }
        }
        return this;
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

    /**
     * 输出流
     *
     * @param outputStream
     * @return
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        this.wb.write(outputStream);
        this.wb.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 导出模板到文件
     *
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public void exportTempleteToFile(String filePath) throws IOException {
        if (!filePath.endsWith(XLS) && !filePath.endsWith(XLSX)) {
            filePath = filePath + XLSX;
        }
        FileOutputStream os = new FileOutputStream(filePath);
        write(os);
    }

    /**
     * 导出模板到客户端
     *
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public void exportTempleteToClient(HttpServletResponse response, String fileName) throws IOException {
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            fileName = fileName + XLSX;
        }
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        write(response.getOutputStream());
    }
}
