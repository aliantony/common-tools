package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> SchemeRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SchemeRequest extends BasicRequest implements ObjectValidator {

    @Encode
    @ApiModelProperty(value = "主键")
    private String  id;
    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案、6.硬件基准配置分析、7.硬件基准验证）
     */
    @ApiModelProperty(required = true, value = "类型（1.准入实施、2.效果检查、3.制定待退役方案、4.实施退役方案、5.资产变更、6.硬件基准配置分析、7.硬件基准验证,11.软件退役待分析方案12.软件待退役方案,13.软件待分析,14.软件发起退役）")
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 实施用户主键
     */
    @ApiModelProperty("实施用户主键")
    @Encode
    private String  putintoUserId;
    /**
     * 实施时间
     */
    @ApiModelProperty("实施时间")
    private Long    putintoTime;
    /**
     * 实施人
     */
    @ApiModelProperty("实施人")
    private String  putintoUser;
    /**
     * 方案来源
     */
    @ApiModelProperty("方案来源,1 硬件，2软件")
    private Integer schemeSource;

    /**
     * 方案内容
     */
    @ApiModelProperty(value = "方案内容")
    private String  content;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String  memo;

    @ApiModelProperty(value = "方案文件,JSON串,{\n" + "\t\"name\": \"zhangsan\",\n" + "\t\"url\": \"http://www.baidu.com\"\n"
                              + "}")
    private String  fileInfo;

    @ApiModelProperty(value = "扩展字段,JSON串,基准分析才有 {baseline:true,implement:true}")
    private String  extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPutintoUserId() {
        return putintoUserId;
    }

    public void setPutintoUserId(String putintoUserId) {
        this.putintoUserId = putintoUserId;
    }

    public Long getPutintoTime() {
        return putintoTime;
    }

    public void setPutintoTime(Long putintoTime) {
        this.putintoTime = putintoTime;
    }

    public String getPutintoUser() {
        return putintoUser;
    }

    public void setPutintoUser(String putintoUser) {
        this.putintoUser = putintoUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSchemeSource() {
        return schemeSource;
    }

    public void setSchemeSource(Integer schemeSource) {
        this.schemeSource = schemeSource;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    @Override
    public String toString() {
        return "SchemeRequest{" + "id='" + id + '\'' + ", type=" + type + ", putintoUserId='" + putintoUserId + '\''
               + ", putintoTime=" + putintoTime + ", putintoUser='" + putintoUser + '\'' + ", schemeSource="
               + schemeSource + ", content='" + content + '\'' + ", fileInfo='" + fileInfo + '\'' + ", extension='"
               + extension + '\'' + '}';
    }
}