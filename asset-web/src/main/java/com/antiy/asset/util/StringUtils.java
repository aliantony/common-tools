package com.antiy.asset.util;

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
}
