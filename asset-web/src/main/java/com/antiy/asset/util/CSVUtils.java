package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;
import com.csvreader.CsvWriter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

@Component
public class CSVUtils {
    public static <T> void writeCSV(Collection<T> dataset, String fileName, String[] csvHeaders,
                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            // 定义路径，分隔符，编码
            // CsvWriter csvWriter = new CsvWriter(csvFilePath,',', Charset.forName("UTF-8"));
            // CsvWriter csvWriter = new CsvWriter(csvFilePath,',', Charset.forName("GBK"));
            // 写入临时文件
            File tempFile = File.createTempFile(fileName, ".csv");
            CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("GBK"));
            // 写表头
            csvWriter.writeRecord(csvHeaders);
            // 写内容
            // 遍历集合
            Iterator<T> it = dataset.iterator();
            while (it.hasNext()) {
                T t = (T) it.next();
                // 获取类属性
                Field[] myFields = t.getClass().getDeclaredFields();
                String[] csvContent = new String[myFields.length];
                for (short i = 0; i < myFields.length; i++) {
                    Field field = myFields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    try {
                        Class tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        if (value == null) {
                            continue;
                        }
                        // 取值并赋给数组
                        String textvalue = value.toString();
                        csvContent[i] = textvalue;
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                // 迭代插入记录
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            outputResponse(request, response, fileName, tempFile);
            System.out.println("<--------CSV文件写入成功-------->");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            // response.setContentType("application/octet-stream");
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition",
                String.format("attachment; filename=\"%s\"", handlerFileName(request, fileName)));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception var6) {
            String msg = var6.getCause().toString();
            throw new BusinessException(msg, var6);
        }
    }

    private static String handlerFileName(HttpServletRequest request,
                                          String fileName) throws UnsupportedEncodingException {
        if (Objects.isNull(request)) {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            return fileName;
        } else {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent == null || userAgent.indexOf("firefox") < 0 && userAgent.indexOf("chrome") < 0
                                     && userAgent.indexOf("safari") < 0) {
                return URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
                return fileName;
            }
        }
    }

    public static void outputResponse(HttpServletRequest request, HttpServletResponse response, String fileName,
                                      File tempFile) throws IOException {
        if (!Objects.isNull(tempFile)) {
            ServletOutputStream out = null;

            java.io.File fileLoad = new java.io.File(tempFile.getCanonicalPath());

            setResponseHeader(request, response, fileName + ".csv");
            java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
            out = response.getOutputStream();
            int n;
            byte[] b = new byte[10240];
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();

        }

    }

}