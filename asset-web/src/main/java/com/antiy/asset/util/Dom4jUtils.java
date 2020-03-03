package com.antiy.asset.util;

/**
 * @Filename: Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2020/3/2
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antiy.common.exception.BusinessException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * dom4j工具类
 * @author why
 */
@Component
public class Dom4jUtils {

    /**
     * dom4j xml生成方法
     * @param
     * @param list
     * @param clz
     */
    @SuppressWarnings("unchecked")
    public static <T> void createXml(List<T> list, Class<T> clz, HttpServletRequest request,
                                     HttpServletResponse response, String fileName) {

        try {
            // 创建Document
            Document document = DocumentHelper.createDocument();

            // 创建根节点
            Element root = document.addElement("root");

            // 获取类中所有的字段
            Field[] fields = clz.getDeclaredFields();

            // 先把List<T>对象转成json字符串
            String str = JSONObject.toJSONString(list);

            // 把json字符串转换成List<Map<Object, Object>>
            List<Map<Object, Object>> mapList = (List<Map<Object, Object>>) JSONArray.parse(str);

            Element element;
            Map<Object, Object> map;
            // 迭代拼接xml节点数据
            for (int i = 0; i < mapList.size(); i++) {
                // 在根节点下添加子节点
                element = root.addElement(clz.getSimpleName());

                // 获取Map<Object, Object>对象
                map = mapList.get(i);

                // 从map中获取数据，拼接xml
                for (Field field : fields) {
                    // 在子节点下再添加子节点
                    element.addElement(field.getName()).addAttribute("attr", field.getType().getName())
                        .addText(String.valueOf(map.get(field.getName())));
                }
            }
            // 把xml内容输出到文件中
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(format);
            writer.write(document);
            outputResponse(request, response, fileName, writer);

            System.out.println("Dom4jUtils Create Xml success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            response.setContentType("application/octet-stream");
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

    private static void outputResponse(HttpServletRequest request, HttpServletResponse response, String fileName,
                                       XMLWriter writer) {
        if (!Objects.isNull(writer)) {
            ServletOutputStream os = null;

            try {
                setResponseHeader(request, response, fileName);
                os = response.getOutputStream();
                writer.write(os);
                os.flush();
            } catch (IOException var15) {
                throwException(var15);
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException var14) {
                    throwException(var14);
                }

            }

        }
    }

    private static void throwException(Exception e) {
        String msg = e.getCause().toString();
        throw new BusinessException(msg, e);
    }
}
