package com.antiy.asset.asset.entity.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetGroupRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRelationRequest extends BasicRequest implements ObjectValidator{

    private static final long serialVersionUID = 1L;

    /**
     *  资产组主键
     */
    @ApiModelProperty("资产组主键")
    private Integer assetGroupId;
    /**
     *  资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     *  备注
     */
    @ApiModelProperty("备注")
    private String memo;
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
     *  状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;

                
                
    public Integer getAssetGroupId() {
    return assetGroupId;
    }

    public void setAssetGroupId(Integer assetGroupId) {
        this.assetGroupId = assetGroupId;
        }
    
                
    public Integer getAssetId() {
    return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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

    }

}