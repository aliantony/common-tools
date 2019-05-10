package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 文件用途枚举
 * @author zhangyajun
 * @create 2019-04-28 15:07
 **/
public enum FileUseEnum {
                         INSTALL_PACKAGE("INSTALL_PACKAGE", Arrays.asList("zip", "rar", "7z"),
                                         5368709120L), INSTALL_INTRODUCE_MANUAL("INSTALL_INTRODUCE_MANUAL",
                                                                                Arrays.asList("rar", "zip", "pdf",
                                                                                    "doc", "docx", "txt", "7z", "png",
                                                                                    "jpg", "xlsx", "xls"),
                                                                                209715200L), SCHEME_FILE("SCHEME_FILE",
                                                                                                         Arrays.asList(
                                                                                                             "rar",
                                                                                                             "zip",
                                                                                                             "pdf",
                                                                                                             "doc",
                                                                                                             "docx",
                                                                                                             "txt",
                                                                                                             "7z",
                                                                                                             "jpg",
                                                                                                             "png",
                                                                                                             "xlsx",
                                                                                                             "xls"),
                                                                                                         10485760L);
    private String       code;
    private List<String> format;
    private Long         size;

    public String getCode() {
        return code;
    }

    public List<String> getFormat() {
        return format;
    }

    public Long getSize() {
        return size;
    }

    FileUseEnum(String code, List<String> format, Long size) {
        this.code = code;
        this.format = format;
        this.size = size;
    }
}
