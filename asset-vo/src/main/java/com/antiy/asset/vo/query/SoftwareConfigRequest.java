package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * 描述: 软件安装配置请求对象
 *
 * @author zhangyajun
 * @create 2019-04-10 16:14
 */
public class SoftwareConfigRequest {
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
     * 软件ID
     */
    @ApiModelProperty(value = "软件ID", required = true)
    @NotBlank(message = "软件ID不能为空")
    @Encode(message = "软件ID解密失败")
    private String       softwareId;

    /**
     * 对象Id，漏洞Id或者补丁Id
     */
    @ApiModelProperty("执行人员")
    @Encode(message = "配置人员Id解密失败")
    private List<String> configUserId;

    @ApiModelProperty(value = "方案文件,JSON串,{\n" + "\t\"name\": \"zhangsan\",\n" + "\t\"url\": \"http://www.baidu.com}")
    private String       fileInfo;

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

    public List<String> getConfigUserId() {
        return configUserId;
    }

    public void setConfigUserId(List<String> configUserId) {
        this.configUserId = configUserId;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
