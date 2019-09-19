package com.antiy.asset.vo.response;

public class AssetTemplateRelationResponse {


    /**
     * 模板名称
     */
    private String  name;
    /**
     * 模板编号
     */
    private String numberCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 关联软件总数
     */
    private Integer softwareCount;
    /**
     * 关联补丁总数
     */
    private Integer patchCount;
    /**
     * 装机模板关联时间
     */
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

    public Integer getSoftwareCount() {
        return softwareCount;
    }

    public void setSoftwareCount(Integer softwareCount) {
        this.softwareCount = softwareCount;
    }

    public Integer getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(Integer patchCount) {
        this.patchCount = patchCount;
    }

    @Override
    public String toString() {
        return "AssetTemplateRelationResponse{" +
                "name='" + name + '\'' +
                ", numberCode='" + numberCode + '\'' +
                ", description='" + description + '\'' +
                ", softwareCount=" + softwareCount +
                ", patchCount=" + patchCount +
                ", installTemplateCorrelationGmt=" + installTemplateCorrelationGmt +
                '}';
    }
}
