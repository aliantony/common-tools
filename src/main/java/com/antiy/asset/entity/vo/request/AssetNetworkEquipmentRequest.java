package com.antiy.asset.asset.entity.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetNetworkEquipmentRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkEquipmentRequest extends BasicRequest implements ObjectValidator{

    private static final long serialVersionUID = 1L;

    /**
     *  资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     *  接口数目
     */
    @ApiModelProperty("接口数目")
    private Integer interfaceSize;
    /**
     *  是否无线:0-否,1-是
     */
    @ApiModelProperty("是否无线:0-否,1-是")
    private Boolean isWireless;
    /**
     *  内网IP
     */
    @ApiModelProperty("内网IP")
    private String innerIp;
    /**
     *  外网IP
     */
    @ApiModelProperty("外网IP")
    private String outerIp;
    /**
     *  MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String macAddress;
    /**
     *  子网掩码
     */
    @ApiModelProperty("子网掩码")
    private String subnetMask;
    /**
     *  预计带宽(M)
     */
    @ApiModelProperty("预计带宽(M)")
    private Integer expectBandwidth;
    /**
     *  配置寄存器(GB)
     */
    @ApiModelProperty("配置寄存器(GB)")
    private Integer register;
    /**
     *  DRAM大小
     */
    @ApiModelProperty("DRAM大小")
    private Float dramSize;
    /**
     *  FLASH大小
     */
    @ApiModelProperty("FLASH大小")
    private Float flashSize;
    /**
     *  NCRM大小
     */
    @ApiModelProperty("NCRM大小")
    private Float ncrmSize;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  备注
     */
    @ApiModelProperty("备注")
    private byte[] memo;
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;
    /**
     *  状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;

                
                
    public Integer getAssetId() {
    return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
        }
    
                
    public Integer getInterfaceSize() {
    return interfaceSize;
    }

    public void setInterfaceSize(Integer interfaceSize) {
        this.interfaceSize = interfaceSize;
        }
    
                
    public Boolean getWireless() {
    return isWireless;
    }

    public void setWireless(Boolean isWireless) {
        this.isWireless = isWireless;
        }
    
                
    public String getInnerIp() {
    return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
        }
    
                
    public String getOuterIp() {
    return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
        }
    
                
    public String getMacAddress() {
    return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        }
    
                
    public String getSubnetMask() {
    return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
        }
    
                
    public Integer getExpectBandwidth() {
    return expectBandwidth;
    }

    public void setExpectBandwidth(Integer expectBandwidth) {
        this.expectBandwidth = expectBandwidth;
        }
    
                
    public Integer getRegister() {
    return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
        }
    
                
    public Float getDramSize() {
    return dramSize;
    }

    public void setDramSize(Float dramSize) {
        this.dramSize = dramSize;
        }
    
                
    public Float getFlashSize() {
    return flashSize;
    }

    public void setFlashSize(Float flashSize) {
        this.flashSize = flashSize;
        }
    
                
    public Float getNcrmSize() {
    return ncrmSize;
    }

    public void setNcrmSize(Float ncrmSize) {
        this.ncrmSize = ncrmSize;
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
    
                
    public Long getGmtCreate() {
    return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
        }
    
                
    public byte[] getMemo() {
    return memo;
    }

    public void setMemo(byte[] memo) {
        this.memo = memo;
        }
    
                
    public Long getGmtModified() {
    return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
        }
    
                
    public Integer getStatus() {
    return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        }
    
        
    @Override
    public void validate() throws RequestParamValidateException {

    }

}