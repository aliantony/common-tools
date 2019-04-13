package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * 描述:
 *
 * @author xuemeng
 * @create 2019-04-04 15:14
 */
public class ConfigRegisterRequest {
    /**
     * 配置建议
     */
    @ApiModelProperty(value = "配置建议", required = true)
    @NotBlank(message = "配置建议")
    private String       suggest;

    /**
     * 资产Id
     */
    @ApiModelProperty(value = "资产Id", required = true)
    @NotBlank(message = "资产Id不能为空")
    @Encode(message = "资产Id解密失败")
    private String       assetId;

    /**
     * 软件Id
     */
    @ApiModelProperty(value = "软件Id", required = true)
    @NotBlank(message = "软件Id不能为空")
    @Encode(message = "软件Id解密失败")
    private String       softwareId;

    /**
     * 来源 1,硬件登记; 2,软件; 3,漏洞修复; 4,补丁安装
     */
    @ApiModelProperty(value = "来源 1,硬件登记, 2,软件配置;", required = true)
    @NotBlank(message = "来源不能为空")
    private String       source;

    /**
     * 对象Id，漏洞Id或者补丁Id
     */
    @ApiModelProperty("对象Id，硬件Id/软件Id/漏洞Id/补丁Id")
    @Encode(message = "对象ID解密失败")
    private String       relId;

    /**
     * 对象Id，漏洞Id或者补丁Id
     */
    @ApiModelProperty("配置人员Id,如果是多个配置人员则以逗号分开")
    @Encode(message = "配置人员Id解密失败")
    private List<String> configUserIds;

    @ApiModelProperty(value = "文件JSON传")
    private String       files;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRelId() {
        return relId;
    }

    public List<String> getConfigUserIds() {
        return configUserIds;
    }

    public void setConfigUserIds(List<String> configUserIds) {
        this.configUserIds = configUserIds;
    }
}
