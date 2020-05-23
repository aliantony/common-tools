package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author liulusheng
 * @since 2019/10/11
 */
public class AssetTemplateSoftwareRelationQuery extends ObjectQuery {

    @ApiModelProperty("操作系统id")
    private String operationSystem;
    @ApiModelProperty("名称")
    private String softwareName;
    @ApiModelProperty("厂商")
    private String manufacturer;
    @ApiModelProperty("软件类型")
    @Pattern(regexp = "^[ademf]$",message = "软件类型参数错误，只能传ademf任意一个")
    private String type;
    @ApiModelProperty("移除软件关联id")
    @Encode
    private List<String> removeSoftIds;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<String> getRemoveSoftIds() {
        return removeSoftIds;
    }

    public void setRemoveSoftIds(List<String> removeSoftIds) {
        this.removeSoftIds = removeSoftIds;
    }
}
