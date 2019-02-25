package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new BusinessException("IO流关闭失败");
            }
        }
    }
}
