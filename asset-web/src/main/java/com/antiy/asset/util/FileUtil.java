package com.antiy.asset.util;

/**
 * 文件工具
 * @author zhangyajun
 * @create 2019-04-28 15:37
 **/
public class FileUtil {

    /**
     * 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {

            int dot = filename.lastIndexOf('.');

            if ((dot > -1) && (dot < (filename.length() - 1))) {

                return filename.substring(dot + 1);

            }

        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {

        if ((filename != null) && (filename.length() > 0)) {

            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);

            }

        }

        return filename;

    }

}
