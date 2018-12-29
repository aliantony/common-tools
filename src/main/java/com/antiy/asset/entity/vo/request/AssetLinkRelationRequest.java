package com.antiy.asset.entity.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetLinkRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationRequest extends BasicRequest implements ObjectValidator{

    private static final long serialVersionUID = 1L;

    /**
     *  通联名称
     */
    @ApiModelProperty("通联名称")
    private String name;
    /**
     *  通联类型:1光纤，2双绞线
     */
    @ApiModelProperty("通联类型:1光纤，2双绞线")
    private Integer linkType;
    /**
     *  头节点资产
     */
    @ApiModelProperty("头节点资产")
    private Integer headAsset;
    /**
     *  尾节点资产
     */
    @ApiModelProperty("尾节点资产")
    private Integer tailAsse;
    /**
     *  头节点类型
     */
    @ApiModelProperty("头节点类型")
    private Integer headType;
    /**
     *  尾节点类型
     */
    @ApiModelProperty("尾节点类型")
    private Integer tailType;
    /**
     *  状态
     */
    @ApiModelProperty("状态")
    private Integer linkStatus;
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

                
                
    public String getName() {
    return name;
    }

    public void setName(String name) {
        this.name = name;
        }
    
                
    public Integer getLinkType() {
    return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
        }
    
                
    public Integer getHeadAsset() {
    return headAsset;
    }

    public void setHeadAsset(Integer headAsset) {
        this.headAsset = headAsset;
        }
    
                
    public Integer getTailAsse() {
    return tailAsse;
    }

    public void setTailAsse(Integer tailAsse) {
        this.tailAsse = tailAsse;
        }
    
                
    public Integer getHeadType() {
    return headType;
    }

    public void setHeadType(Integer headType) {
        this.headType = headType;
        }
    
                
    public Integer getTailType() {
    return tailType;
    }

    public void setTailType(Integer tailType) {
        this.tailType = tailType;
        }
    
                
    public Integer getLinkStatus() {
    return linkStatus;
    }

    public void setLinkStatus(Integer linkStatus) {
        this.linkStatus = linkStatus;
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