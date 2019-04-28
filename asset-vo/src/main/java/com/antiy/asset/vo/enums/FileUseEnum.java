package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 文件用途枚举
 * @author zhangyajun
 * @create 2019-04-28 15:07
 **/
public enum FileUseEnum {
                         INSTALL_PACKAGE("INSTALL_PACKAGE", Arrays.asList("zip", "rar", "jpg"),
                                         4294967296L), INSTALL_INTRODUCE_MANUAL("INSTALL_INTRODUCE_MANUAL", Arrays
                                             .asList("rar", "zip", "pdf", "doc", "docx", "txt"), 209715200L);
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
