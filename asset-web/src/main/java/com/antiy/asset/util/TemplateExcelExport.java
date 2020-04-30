package com.antiy.asset.util;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.utils.SpringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.antiy.asset.util.Constants.HIDDEN_SHEET_HEAD;
import static com.antiy.asset.util.Constants.MAX_EXCEL_SELECT_LENGTH;

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
     * 注解列表（Object[]{ ExcelField, Field/Method/defaultValue }）
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
    public void exportToClient(HttpServletRequest request, HttpServletResponse response, String fileName,
                               List<?> dataList) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");

        // 判断是否是IE11
        Boolean flag = request.getHeader("User-Agent").indexOf("like Gecko") > 0;
        if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0 || flag) {
            fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
        } else {
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,
            // 这个文件名称用于浏览器的下载框中自动显示的文件名
            fileName = new String(fileName.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
            // firefox浏览器
            // firefox浏览器User-Agent字符串:
            // Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
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
            CellRangeAddress cellAddresses = null;
            if (annotationList.size() == 1) {
                cellAddresses = new CellRangeAddress(3, 3, 0, 1);

            } else {

                cellAddresses = new CellRangeAddress(3, 3, 0, annotationList.size() - 1);
            }
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
        int hiddenSheetIndex = 1;
        for (int i = 0; i < headerList.size(); i++) {
            sheet.autoSizeColumn(i);
            // 为码表类型，设置下拉框
            if (annotationList.size() > 0 && StringUtils.isNotBlank(((ExcelField) annotationList.get(i)[0]).dictType())
                || StringUtils.isNotBlank(((ExcelField) annotationList.get(i)[0]).defaultDataMethod())) {

                List<String> datas = new ArrayList<>();
                if (StringUtils.isNotBlank(((ExcelField) annotationList.get(i)[0]).dictType())) {
                    datas = Stream.of(CodeUtils.getCodeArray(((ExcelField) annotationList.get(i)[0]).dictType()))
                        .collect(Collectors.toList());
                } else {
                    String clazzName = ((ExcelField) annotationList.get(i)[0]).defaultDataBeanName();
                    ParamterExceptionUtils.isBlank(clazzName, "下载导出模板失败");
                    // 采用Spring的方式调用方法并且返回值
                    Object springBean = SpringUtil.getBean(clazzName);
                    if (springBean != null) {
                        try {
                            Object data = ReflectionUtils.invokeMethod(springBean,
                                ((ExcelField) annotationList.get(i)[0]).defaultDataMethod(), null, null);
                            if (data instanceof List) {
                                datas = (List<String>) data;
                            }
                        } catch (Exception e) {
                            logger.warn("Excel获取bean失败", e);
                        }
                    }
                }

                if (CollectionUtils.isEmpty(datas)) {
                    continue;
                }

                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
                String jsonData = JsonUtil.object2Json(datas);
                XSSFDataValidationConstraint constraint = null;
                if (jsonData.length() < MAX_EXCEL_SELECT_LENGTH) {
                    // 设置区域边界
                    CellRangeAddressList addressList = new CellRangeAddressList(6, 100000, i, i);
                    String[] data = new String[datas.size()];
                    constraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(datas.toArray(data));
                    XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(constraint,
                        addressList);
                    // 输入非法数据时，弹窗警告框
                    validation.setShowErrorBox(true);
                    validation.setShowPromptBox(true);
                    sheet.addValidationData(validation);
                } else {

                    // 由于数据量超过300个字符，导出模板会失败的情况,就会创建隐藏sheet
                    XSSFSheet hidden = wb.createSheet(HIDDEN_SHEET_HEAD + hiddenSheetIndex);
                    wb.setSheetHidden(hiddenSheetIndex, true);
                    // 将下拉列表的数据放在数据源sheet上
                    XSSFRow row = null;
                    XSSFCell cell = null;
                    for (int j = 0, length = datas.size(); j < length; j++) {
                        row = hidden.createRow(j);
                        cell = row.createCell(0);
                        cell.setCellValue(datas.get(j));
                    }

                    // 指定隐藏的sheet到下拉框中
                    constraint = (XSSFDataValidationConstraint) dvHelper
                        .createFormulaListConstraint(HIDDEN_SHEET_HEAD + hiddenSheetIndex + "!$A$1:$A" + datas.size());
                    CellRangeAddressList addressList = null;
                    // 循环指定单元格下拉数据
                    for (int n = 6; n <= 100; n++) {
                        row = (XSSFRow) sheet.createRow(n);
                        cell = row.createCell(i);
                        addressList = new CellRangeAddressList(n, n, i, i);
                        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(constraint,
                            addressList);
                        this.sheet.addValidationData(validation);
                    }
                    hiddenSheetIndex++;
                }
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
