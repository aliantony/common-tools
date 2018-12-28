package com.antiy.asset.entity.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetLabelRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLabelRelationRequest extends BasicRequest implements ObjectValidator{

    private static final long serialVersionUID = 1L;

    /**
     *  资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     *  标签主键
     */
    @ApiModelProperty("标签主键")
    private Integer assetLabelId;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间")
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

                
                
    public Integer getAssetId() {
    return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
        }
    
                
    public Integer getAssetLabelId() {
    return assetLabelId;
    }

    public void setAssetLabelId(Integer assetLabelId) {
        this.assetLabelId = assetLabelId;
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