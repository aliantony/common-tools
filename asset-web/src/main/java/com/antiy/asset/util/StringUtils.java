package com.antiy.asset.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyajun
 * @create 2019-03-02 15:17
 **/
public class StringUtils {
    /**
     * 原文：https://blog.csdn.net/baidu_36415076/article/details/53190616
     * 去除前后指定字符
     * @param source 目标字符串
     * @param beTrim 要删除的指定字符
     * @return 删除之后的字符串
     * 调用示例：System.out.println(trim(", ashuh  ",","));
     */
    public static String trim(String source, String beTrim, String enTrim) {
        if(source==null){
            return "";
        }
        // 循环去掉字符串首的beTrim字符
        source = source.trim();
        if(source.isEmpty()){
            return "";
        }
        String beginChar = source.substring(0, 1);
        if (beginChar.equalsIgnoreCase(beTrim)) {
            source = source.substring(1);
            // 循环去掉字符串尾的enTrim字符
            String endChar = source.substring(source.length() - 1);
            if (endChar.equalsIgnoreCase(enTrim)) {
                source = source.substring(0, source.length() - 1);
            }
        }
        return source;
    }

    public static <S, T> List<T> wrapperListConverter(List<S> sList, Class<T> tClass) {
        List<T> list = null;
        if (sList != null && sList.size() > 0) {
            list = new ArrayList<>();
            for (S s : sList) {
                if (s instanceof Integer) {
                    list.add((T) String.valueOf(s));
                }
            }
        }
        return list;
    }

    /**
     * 将字符串转换成小写。
     *
     * <p> 如果字符串是<code>null</code>则返回<code>null</code>。
     *
     * <pre>
     * StringUtil.toLowerCase(null)  = null
     * StringUtil.toLowerCase("")    = ""
     * StringUtil.toLowerCase("aBc") = "abc"
     * </pre>
     *
     * </p>
     *
     * @param str 要转换的字符串
     *
     * @return 大写字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }
}
