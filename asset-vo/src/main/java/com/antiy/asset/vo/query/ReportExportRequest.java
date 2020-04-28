package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.ExportType;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/28 14:37
 * @Description
 */
public class ReportExportRequest {

    @ApiModelProperty(value = "图片base64编码")
    @NotBlank(message = "不能为空")
    private String imageCode;

    @ApiModelProperty(value = "导出类型")
    @NotNull(message = "导出类型不能为空")
    private ExportType type;

    @ApiModelProperty(value = "导出刻度")
    @NotNull(message = "导出刻度不能为空")
    private String scale;

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public ExportType getType() {
        return type;
    }

    public void setType(ExportType type) {
        this.type = type;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "ReportExportRequest{" +
                "imageCode='" + imageCode + '\'' +
                ", type=" + type +
                ", scale='" + scale + '\'' +
                '}';
    }
}
