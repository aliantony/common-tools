package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * <p> AssetLinkRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationRequest extends BaseRequest implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty(value = "资产主键",required = true)
    @Encode(message = "子资产主键错误")
    @NotBlank(message = "子资产主键不能为空")
    private String  assetId;
    /**
     * 资产IP
     */
    @ApiModelProperty(value = "资产IP",required = true)
    @NotBlank(message = "子资产IP不能为空")
    private String  assetIp;
    /**
     * 资产端口
     */
    @ApiModelProperty("资产端口")
    private String  assetPort;
    /**
     * 父级设备主键
     */
    @ApiModelProperty(value = "父级设备主键",required = true)
    @Encode(message = "父级设备主键错误")
    @NotBlank(message = "父级设备主键不能为空")
    private String  parentAssetId;
    /**
     * 父级设备IP
     */
    @ApiModelProperty(value = "父级设备IP",required = true)
    @NotBlank(message = "父级设备IP不能为空")
    private String  parentAssetIp;
    /**
     * 父级设备端口
     */
    @ApiModelProperty(value = "父级设备端口",required = true)
    private String  parentAssetPort;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long    gmtModified;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer status;
    /**
     * 配件房间号
     */
    @ApiModelProperty("配件房间号")
    private String partRoomNo;

    /**
     * 办公室网口
     */
    @ApiModelProperty("办公室网口")
    private Integer officeNet;

    /**
     * 办公室网口状态
     */
    @ApiModelProperty("办公室网口状态: 1.正常 2.损坏")
    @Size(min = 1, max = 2)
    private String officeNetState;

    /**
     * 交换机状态
     */
    @ApiModelProperty("交换机状态: 1.正常 2.损坏")
    @Size(min = 1, max = 2)
    private Integer switchState;

    /**
     * vlan号
     */
    @ApiModelProperty("vlan号")
    private String vlan;

    /**
     * 自定义字段
     */
    @ApiModelProperty("自定义字段")
    private String customField;


    public String getPartRoomNo() {
        return partRoomNo;
    }

    public void setPartRoomNo(String partRoomNo) {
        this.partRoomNo = partRoomNo;
    }

    public Integer getOfficeNet() {
        return officeNet;
    }

    public void setOfficeNet(Integer officeNet) {
        this.officeNet = officeNet;
    }

    public String getOfficeNetState() {
        return officeNetState;
    }

    public void setOfficeNetState(String officeNetState) {
        this.officeNetState = officeNetState;
    }

    public Integer getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Integer switchState) {
        this.switchState = switchState;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getAssetIp() {
        return assetIp;
    }

    public void setAssetIp(String assetIp) {
        this.assetIp = assetIp;
    }

    public String getAssetPort() {
        return assetPort;
    }

    public void setAssetPort(String assetPort) {
        this.assetPort = assetPort;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(String parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    public String getParentAssetIp() {
        return parentAssetIp;
    }

    public void setParentAssetIp(String parentAssetIp) {
        this.parentAssetIp = parentAssetIp;
    }

    public String getParentAssetPort() {
        return parentAssetPort;
    }

    public void setParentAssetPort(String parentAssetPort) {
        this.parentAssetPort = parentAssetPort;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void validate() throws RequestParamValidateException {

        if (Objects.nonNull(memo) | Objects.nonNull(customField)){
            if (!(Objects.nonNull(memo) ^ Objects.nonNull(customField))) {
                throw new BusinessException("备注、自定义字段限添加1个");
            }
        }
    }

}