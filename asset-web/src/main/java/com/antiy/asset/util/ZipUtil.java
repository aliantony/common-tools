package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    static final int            BUFFER = 8192;
    private static final Logger logger = LogUtils.get(ZipUtil.class);

    /**
     *
     * @param outFile 输出文件
     * @param files 压缩的文件
     */
    public static void compress(File outFile, File[] files) {
        ZipOutputStream out = null;
        FileOutputStream fileOutputStream = null;
        CheckedOutputStream cos = null;
        try {
            fileOutputStream = new FileOutputStream(outFile);
            cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i = 0; i < files.length; i++) {
                compress(new File(files[i].getAbsolutePath()), out, basedir);
            }
        } catch (Exception e) {
            throw new BusinessException("文件压缩异常");
        } finally {
            CloseUtils.close(out);
            CloseUtils.close(cos);
            CloseUtils.close(fileOutputStream);
        }
    }

    

    public static void compress(File outFile, String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists())
            throw new BusinessException(srcPathName + "不存在！");
        FileOutputStream fileOutputStream = null;
        CheckedOutputStream cos = null;
        ZipOutputStream out = null;
        try {
            fileOutputStream = new FileOutputStream(outFile);
            cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir);
        } catch (Exception e) {
            throw new BusinessException("文件压缩异常");
        } finally {
            CloseUtils.close(out);
            CloseUtils.close(cos);
            CloseUtils.close(fileOutputStream);
        }
    }

    private static void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            logger.info("压缩：" + basedir + file.getName());
            compressDirectory(file, out, basedir);
        } else {
            logger.info("压缩：" + basedir + file.getName());
            compressFile(file, out, basedir);
        }
    }

    /** 压缩一个目录 */
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /** 压缩一个文件 */
    private static void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        } catch (Exception e) {
            throw new BusinessException("文件压缩异常");
        } finally {
            CloseUtils.close(bis);
        }
    }

}