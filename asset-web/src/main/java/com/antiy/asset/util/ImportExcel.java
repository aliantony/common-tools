package com.antiy.asset.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.common.utils.LogUtils;

/**
 * @Description: 导入excel
 * @Author: lvliang
 * @Date: 2018/12/05
 */
public class ImportExcel {

    private static Logger        log         = LogUtils.get(ImportExcel.class);
    private static String        XLS         = ".xls";
    private static String        XLSX        = ".xlsx";
    /**
     * 导入信息
     */
    private StringBuilder sb          = new StringBuilder();
    /**
     * 空白条数
     */
    private int           blankNums   = 0;
    /**
     * 失败条数
     */
    private int           failNums    = 0;
    /**
     * 成功条数
     */
    private int           successNums = 0;
    /**
     * 总条数
     */
    private int           totalNums   = 0;
    /**
     * 工作薄对象
     */
    private XSSFWorkbook         wb;

    /**
     * 工作表对象
     */
    private XSSFSheet            sheet;

    /**
     * 标题行号
     */
    private int                  headerNum;

    /**
     * 最后一样行号
     */
    private int                  lastRowNum;

    /**
     * 注解列表Obejct{excelField, field/method}
     */
    private List<Object[]>       annotationList;

    /**
     * 构造方法
     *
     * @param filePath 文件路径
     * @param headerNum 标题行号
     */
    public ImportExcel(String filePath, int headerNum) throws IOException {
        this(new File(filePath), headerNum);
    }

    /**
     * 构造方法
     *
     * @param file 文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     */
    public ImportExcel(File file, int headerNum) throws IOException {
        this(file, headerNum, 0);

    }

    /**
     * 构造方法
     *
     * @param filePath 文件路径
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     */
    public ImportExcel(String filePath, int headerNum, int sheetIndex) throws IOException {
        this(new File(filePath), headerNum, sheetIndex);
    }

    /**
     * 构造方法
     *
     * @param file 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     */
    public ImportExcel(File file, int headerNum, int sheetIndex) throws IOException {
        this(file.getPath(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造方法
     *
     * @param multipartFile 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) throws IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造方法
     *
     * @param filePath 文件路径
     * @param is 文件输入流
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(String filePath, InputStream is, int headerNum, int sheetIndex) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            throw new RuntimeException("导入文档为空!");
        } else if (filePath.toLowerCase().endsWith(XLS) || filePath.toLowerCase().endsWith(XLSX)) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        this.lastRowNum = this.sheet.getLastRowNum() + headerNum + 1;
        log.debug("Initialize success.");
    }

    /**
     * 初始化注解列表
     *
     * @param clazz
     */
    public void initAnnotationList(Class<?> clazz) {
        annotationList = Lists.newArrayList();
        // 处理属性注解
        Field[] fields = clazz.getDeclaredFields();
        Arrays.asList(fields).stream().forEach(field -> {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                if (excelField.type() == 0 || excelField.type() == 2) {
                    annotationList.add(new Object[] { excelField, field });
                }
            }
        });
        // 处理方法上的注解
        Method[] methods = clazz.getMethods();
        Arrays.asList(methods).stream().forEach(method -> {
            ExcelField excelField = method.getAnnotation(ExcelField.class);
            if (excelField != null) {
                if (excelField.type() == 0 || excelField.type() == 2) {
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
    }

    /**
     * 获取Sheet行
     *
     * @return
     */
    public Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取行号
     *
     * @return
     */
    public int getDataRownum() {
        return this.headerNum + 1;
    }

    /**
     * 获取最后一行行号
     *
     * @return
     */
    public int getLastRowNum() {
        return this.sheet.getLastRowNum() + headerNum;
    }

    /**
     * 获取最后一个列号
     *
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格数据
     *
     * @param row 行
     * @param colunm 列数
     * @return
     */
    public Object getCellValue(Row row, int colunm) {
        Cell cell = row.getCell(colunm);
        Object val = null;
        try {
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    val = cell.getNumericCellValue();
                    // 判断是否为科学计数法（包含E、e、+等符号）
                    if (val.toString().indexOf("E") != -1 || val.toString().indexOf("e") != -1
                        || val.toString().indexOf("+") != -1) {
                        BigDecimal bd = new BigDecimal(val + "");
                        val = bd.toPlainString();
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.FORMULA) {
                    val = cell.getCellFormula();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            } else {
                return val;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return val;
    }

    /**
     * 获取Excel数据
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getDataList(Class<T> clazz) throws IllegalAccessException, InstantiationException,
                                                   NoSuchMethodException, InvocationTargetException {
        initAnnotationList(clazz);
        List<T> dataList = new ArrayList<>();
        boolean flag = true;
        for (int i = getDataRownum(); i < lastRowNum; i++) {
            // 数据行
            Row dataRow = this.getRow(i);
            if (isRowEmpty(dataRow)) {
                blankNums++;
                break;
            }
            // 反射创建实例对象
            T data = clazz.newInstance();
            // 列号
            int column = 0;
            for (Object[] os : annotationList) {
                Object val = this.getCellValue(dataRow, column++);
                ExcelField ef = (ExcelField) os[0];
                if (val == null && ef.required()) {
                    failNums++;
                    sb.append("数据不能为空,第").append(dataRow.getRowNum()).append("行，第").append(column)
                            .append("列").append(ef.title()).append("\n");
                    log.error("数据不能为空,第" + dataRow.getRowNum() + "行，第" + column + "列" + ef.title() + " "
                            + val);
                    flag = false;
                    break;
                }
                if (val != null) {
                    // 是码表数据
                    if (StringUtils.isNotBlank(ef.dictType())) {
                        // 转换
                        val = CodeUtils.getCodeValue(ef.dictType(), val.toString());
                    }
                    // 获取属性类型
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((Field) os[1]).getType();
                    } else if (os[1] instanceof Method) {
                        Method method = ((Method) os[1]);
                        if (method.getName().startsWith("get")) {
                            valType = method.getReturnType();
                        } else if (method.getName().startsWith("set")) {
                            valType = method.getParameterTypes()[0];
                        }
                    }
                    // 转换数据
                    if (valType == String.class) {
                        val = val.toString();
                    } else if (valType == int.class) {
                        val = Integer.parseInt(val.toString().substring(0, val.toString().lastIndexOf(".")));
                    } else if (valType == Integer.class) {
                        try {
                            val = Double.valueOf(val.toString()).intValue();
                        } catch (Exception e) {
                            failNums++;
                            flag = false;
                            sb.append("数据格式错误,第").append(dataRow.getRowNum()).append("行，第").append(column).append("列")
                                .append(ef.title()).append(val).append("\n");
                            log.error("数据格式错误,第" + dataRow.getRowNum() + "行，第" + column + "列" + ef.title() + " " + val);
                            break;
                        }
                    } else if (valType == Long.class) {
                        try {
                            Date date = DateUtil.getJavaDate(Double.valueOf(val.toString()).doubleValue());
                            val = date.getTime();
                        } catch (NumberFormatException e) {
                            flag = false;
                            failNums++;
                            sb.append("数据格式错误,第").append(dataRow.getRowNum()).append("行，第").append(column).append("列")
                                .append(ef.title()).append(val).append("\n");
                            log.error(
                                "数据格式错误,第" + dataRow.getRowNum() + "行，第" + column + "列：" + ef.title() + " " + val);
                            break;
                        }
                    } else if (valType == Double.class) {
                        val = Double.valueOf(val.toString());
                    } else if (valType == Float.class) {
                        val = Float.valueOf(val.toString());
                    } else if (valType == Date.class) {
                        val = DateUtil.getJavaDate((Double) val);
                    }
                    if (os[1] instanceof Field) {
                        ReflectionUtils.invokeSetterMethod(data, ((Field) os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        String methodName = ((Method) os[1]).getName();
                        if (methodName.startsWith("set")) {
                            methodName = methodName.replaceAll("set", "get");
                        }
                        clazz.getMethod(methodName, valType).invoke(data, val);
                    }
                }
            }
            if (flag) {
                dataList.add(data);
                successNums++;
            }
            flag = true;
        }
        totalNums = successNums + failNums;
        return dataList;
    }

    /**
     * 判断是否是空白行
     *
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取导入后的结果信息
     *
     * @return
     */
    public String getResultMsg() {
        sb.append("成功条数:").append(successNums).append("\n").append("空白条数:").append(blankNums).append("\n")
            .append("失败条数:").append(failNums).append("\n").append("总条数:").append(totalNums).append("\n");
        String resultString = sb.toString();
        sb.delete(0, sb.length());
        return resultString;
    }
}
