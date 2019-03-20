package com.antiy.asset.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.antiy.common.exception.BusinessException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.templet.ImportResult;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @Description: Excel操作类
 * @Author: lvliang
 * @Date: 2018/12/06
 */
public class ExcelUtils {

    /**
     * 导出Excel至客户端(通过注解)
     *
     * @param clazz class字节码
     * @param fileName 文件名
     * @param title 文件标题
     * @param dataList 数据
     * @throws IOException
     */
    public static void exportToClient(Class<?> clazz, String fileName, String title, List<?> dataList) {
        ParamterExceptionUtils.isBlank(fileName, "文件名不能为空");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getResponse();
        try {
            new ExportExcel(title, clazz).exportToClient(response, fileName, dataList);
        } catch (IOException e) {
            throw new BusinessException("文件导出异常");
        }

    }

    /**
     * 导出Excel至客户端(通过指定列名)
     *
     * @param headerList 列名
     * @param fileName 文件名
     * @param title 文件标题
     * @param dataList 数据
     * @throws IOException
     */
    public static void exportToClient(List<String> headerList, String fileName, String title, List<?> dataList) {
        ParamterExceptionUtils.isBlank(fileName, "文件名不能为空");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getResponse();
        try {
            new ExportExcel(title, headerList).exportToClient(response, fileName, dataList);
        } catch (IOException e) {
            throw new BusinessException("文件导出异常");
        }
    }

    /**
     * 导出Excel至文件(通过注解)
     *
     * @param clazz class字节码
     * @param fileName 文件名
     * @param title 文件标题
     * @param dataList 数据
     * @throws IOException
     */
    public static void exportToFile(Class<?> clazz, String fileName, String title, List<?> dataList, String fileInfo) {
        try {
            new ExportExcel(title, clazz).exportToFile(fileInfo + fileName, dataList);
        } catch (IOException e) {
            throw new BusinessException("文件导出异常");
        }

    }

    /**
     * 导入excel
     *
     * @param multipartFile 数据文件
     * @param headerNum 数据开始的行号
     * @param sheetIndex sheet下标
     * @return
     */
    public static ImportResult importExcelFromClient(Class<?> clazz, MultipartFile multipartFile, int headerNum,
                                                     int sheetIndex) {
        ImportExcel ie;
        ImportResult ir = new ImportResult();
        try {
            ie = new ImportExcel(multipartFile, headerNum, sheetIndex);
            ir.setDataList(ie.getDataList(clazz));
            ir.setMsg(ie.getResultMsg());
        } catch (IllegalAccessException e) {
            throw new BusinessException("文件导入异常");
        } catch (InstantiationException e) {
            throw new BusinessException("文件导入异常");
        } catch (NoSuchMethodException e) {
            throw new BusinessException("文件导入异常");
        } catch (InvocationTargetException e) {
            throw new BusinessException("文件导入异常");
        } catch (IOException e) {
            throw new BusinessException("文件导入异常");
        } catch (OLE2NotOfficeXmlFileException e) {
            throw new BusinessException("模板选择错误");
        }
        return ir;
    }

    /**
     * 导入excel
     *
     * @param filePath 数据文件路径
     * @param headerNum 数据开始的行号
     * @param sheetIndex sheet下标
     * @return
     */
    public static ImportResult importExcelFromFile(Class<?> clazz, String filePath, int headerNum, int sheetIndex) {
        ImportExcel ie = null;
        ImportResult ir = new ImportResult();
        try {
            ie = new ImportExcel(filePath, headerNum, sheetIndex);
            ir.setDataList(ie.getDataList(clazz));
            ir.setMsg(ie.getResultMsg());
        } catch (IllegalAccessException e) {
            throw new BusinessException("文件导入异常");
        } catch (InstantiationException e) {
            throw new BusinessException("文件导入异常");
        } catch (NoSuchMethodException e) {
            throw new BusinessException("文件导入异常");
        } catch (InvocationTargetException e) {
            throw new BusinessException("文件导入异常");
        } catch (IOException e) {
            throw new BusinessException("文件导入异常");
        }
        return ir;
    }

    /**
     * 导出excel模板至客户端
     *
     * @param clazz
     * @param filename 文件名
     * @param title 文件标题
     */
    public static void exportTemplet(Class<?> clazz, String filename, String title) {
        ParamterExceptionUtils.isBlank(filename, "文件名不能为空");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getResponse();
        try {
            new ExportExcel(title, clazz, 2).exportTempleteToClient(response, filename);
        } catch (IOException e) {
            throw new BusinessException("模板导出异常");
        }
    }

    /**
     * 导出excel模板至文件
     *
     * @param clazz
     * @param filename 文件名
     * @param title 文件标题
     */
    public static void exportTemplet(Class<?> clazz, String filename, String title, String fileInfo) {
        ParamterExceptionUtils.isBlank(filename, "文件名不能为空");
        try {
            new ExportExcel(title, clazz, 2).exportTempleteToFile(fileInfo + filename);
        } catch (IOException e) {
            throw new BusinessException("模板导出异常");
        }
    }

    /**
     * 导出excel模板至客户端
     *
     * @param clazz
     * @param filename 文件名
     * @param title 文件标题
     */
    public static void exportTempletToClient(Class<?> clazz, String filename, String title, String memo,
                                             List<?> dataList) {
        ParamterExceptionUtils.isBlank(filename, "文件名不能为空");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getResponse();
        try {
            new TemplateExcelExport(title, clazz, memo).exportToClient(response, filename, dataList);
        } catch (IOException e) {
            throw new BusinessException("模板导出异常");
        }
    }

    /**
     * 导出excel模板至文件
     *
     * @param clazz
     * @param filename 文件名
     * @param title 文件标题
     */
    public static void exportTempletToFile(Class<?> clazz, String filename, String title, String memo, String fileInfo,
                                           List<?> dataList) {
        ParamterExceptionUtils.isBlank(filename, "文件名不能为空");
        try {
            new TemplateExcelExport(title, clazz, memo).exportToFile(fileInfo + filename, dataList);
        } catch (IOException e) {
            throw new BusinessException("模板导出异常");
        }
    }
}
