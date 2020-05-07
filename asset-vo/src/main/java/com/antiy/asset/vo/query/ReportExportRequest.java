package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.ExportType;
import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/28 14:37
 * @Description
 */
public class ReportExportRequest extends BasicRequest {

    @ApiModelProperty(value = "图片base64编码")
    @NotBlank(message = "不能为空")
    private String imageCode;

    @ApiModelProperty(value = "导出类型")
    @NotNull(message = "导出类型不能为空")
    private ExportType type;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReportExportRequest{" +
                "imageCode='" + imageCode + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
