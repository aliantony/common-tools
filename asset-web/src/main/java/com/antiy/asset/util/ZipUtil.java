package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.zip.*;

public class ZipUtil {
    private static final int            BUFFER = 8192;
    private static final Logger logger = LogUtils.get(ZipUtil.class);
    private static final String SUFFIX_NAME = "zip";

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
        if (!file.exists()) {
            throw new BusinessException(srcPathName + "不存在！");
        }
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

    /**
     * 解压文件 filePath所代表文件系统不能与targetStr一致
     * @param filePath 压缩文件路径
     * @param targetStr 解压至所在文件目录
     * @param name 如果名字为空则用原来的名字
     */
    public static void decompression(String filePath, String targetStr, String name) {
        isCheckFileNameInZip(filePath);
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                    File target = new File(targetStr, name == null ? entry.getName() : name);
                    if (!target.getParentFile().exists()) {
                        target.getParentFile().mkdirs();// 创建文件父目录
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read = 0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                closeQuietly(zis, bos);
            }
        }
    }

    /**
     * 关闭一个或多个流对象
     *
     * @param closeables 可关闭的流对象列表
     */
    private static void closeQuietly(Closeable... closeables) {
        try {
            if (closeables != null && closeables.length > 0) {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            }
        } catch (IOException e) {
            // do nothing
        }
    }

    /**
     * 校验目标文件名称是否是zip格式
     * @param zipFile
     * @return
     */
    private static int isCheckFileNameInZip(String zipFile) {
        int las = zipFile.lastIndexOf(".");
        if (las == -1) {
            throw new RuntimeException(zipFile + " is not zip format! this format = ??? ");
        }
        String format = zipFile.substring(las + 1);
        if (!SUFFIX_NAME.equalsIgnoreCase(format)) {
            throw new RuntimeException(zipFile + " is not zip format! this format = " + format);
        }
        return las;
    }
}