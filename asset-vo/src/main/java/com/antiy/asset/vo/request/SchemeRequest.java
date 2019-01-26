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
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.制定待退役方案、4.实施退役方案、5.资产变更）")
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
    @ApiModelProperty("方案来源")
    private Integer schemeSource;

    /**
     * 方案内容
     */
    @ApiModelProperty(value = "方案内容")
    private String  content;

    @ApiModelProperty(value = "方案文件,JSON串")
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