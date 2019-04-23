package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

public class CategoryType {
    private final static String PC      = "计算设备";
    private final static String NET     = "网络设备";
    private final static String STORAGE = "存储设备";
    private final static String SAFTY   = "安全设备";
    private final static String OTHER   = "其它设备";

    /**
     * 是否网络设备
     */
    @ApiModelProperty("是否网络设备")
    private boolean             isNet;
    /**
     * 是否计算设备
     */
    @ApiModelProperty("是否计算设备")
    private boolean             isPc;
    /**
     * 是否安全设备
     */
    @ApiModelProperty("是否安全设备")
    private boolean             isSafety;
    /**
     * 是否存储设备
     */
    @ApiModelProperty("是否存储设备")
    private boolean             isStorage;
    /**
     * 是否其他设备
     */
    @ApiModelProperty("是否其他设备")
    private boolean             isOthers;

    public boolean isNet() {
        return isNet;
    }

    public void setNet(boolean net) {
        isNet = net;
    }

    public boolean isPc() {
        return isPc;
    }

    public void setPc(boolean pc) {
        isPc = pc;
    }

    public boolean isSafety() {
        return isSafety;
    }

    public void setSafety(boolean safety) {
        isSafety = safety;
    }

    public boolean isStorage() {
        return isStorage;
    }

    public void setStorage(boolean storage) {
        isStorage = storage;
    }

    public boolean isOthers() {
        return isOthers;
    }

    public void setOthers(boolean others) {
        isOthers = others;
    }

    public CategoryType(String categoryName) {
        this.setStatus();
        if (StringUtils.isBlank(categoryName)) {
            return;
        }
        switch (categoryName) {
            case NET:
                this.isNet = true;
                return;
            case PC:
                this.isPc = true;
                return;
            case SAFTY:
                this.isSafety = true;
                return;
            case STORAGE:
                this.isStorage = true;
                return;
            case OTHER:
                this.isOthers = true;
                return;
            default:
                return;
        }
    }

    public void setStatus() {
        this.isNet = false;
        this.isPc = false;
        this.isSafety = false;
        this.isStorage = false;
        this.isOthers = false;
    }

    public CategoryType(boolean isNet, boolean isPc, boolean isSafety, boolean isStorage, boolean isOthers) {
        this.isNet = isNet;
        this.isPc = isPc;
        this.isSafety = isSafety;
        this.isStorage = isStorage;
        this.isOthers = isOthers;
    }
}
