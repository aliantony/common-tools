package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class AssetTemplateRelationResponse extends  BaseResponse {


    @ApiModelProperty("模板名称")
    private String  name;

    @ApiModelProperty("模板编号")
    private String numberCode;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("软件总数")
    private Integer softNnm;

    @ApiModelProperty("补丁总数")
    private Integer patchNum;

    @ApiModelProperty("装机模板关联时间")
    private Long installTemplateCorrelationGmt;

    public Long getInstallTemplateCorrelationGmt() {
        return installTemplateCorrelationGmt;
    }

    public void setInstallTemplateCorrelationGmt(Long installTemplateCorrelationGmt) {
        this.installTemplateCorrelationGmt = installTemplateCorrelationGmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSoftNnm() {
        return softNnm;
    }

    public void setSoftNnm(Integer softNnm) {
        this.softNnm = softNnm;
    }

    public Integer getPatchNum() {
        return patchNum;
    }

    public void setPatchNum(Integer patchNum) {
        this.patchNum = patchNum;
    }

    @Override
    public String toString() {
        return "AssetTemplateRelationResponse{" +
                "name='" + name + '\'' +
                ", numberCode='" + numberCode + '\'' +
                ", description='" + description + '\'' +
                ", softwareCount=" + softNnm +
                ", patchCount=" + patchNum +
                ", installTemplateCorrelationGmt=" + installTemplateCorrelationGmt +
                '}';
    }
}
