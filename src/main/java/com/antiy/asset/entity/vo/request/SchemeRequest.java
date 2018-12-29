package com.antiy.asset.entity.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * SchemeRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SchemeRequest extends BasicRequest implements ObjectValidator{

    private static final long serialVersionUID = 1L;

    /**
     *  类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）")
    private Integer type;
    /**
     *  结果
     */
    @ApiModelProperty("结果")
    private Integer result;
    /**
     *  实施用户主键
     */
    @ApiModelProperty("实施用户主键")
    private Integer putintoUserId;
    /**
     *  实施时间
     */
    @ApiModelProperty("实施时间")
    private Long putintoTime;
    /**
     *  实施人
     */
    @ApiModelProperty("实施人")
    private String putintoUser;
    /**
     *  备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     *  附件路径
     */
    @ApiModelProperty("附件路径")
    private String filepath;
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
    private Integer isDelete;

                
                
    public Integer getType() {
    return type;
    }

    public void setType(Integer type) {
        this.type = type;
        }
    
                
    public Integer getResult() {
    return result;
    }

    public void setResult(Integer result) {
        this.result = result;
        }
    
                
    public Integer getPutintoUserId() {
    return putintoUserId;
    }

    public void setPutintoUserId(Integer putintoUserId) {
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
    
                
    public String getRemark() {
    return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        }
    
                
    public String getFilepath() {
    return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
    
                
    public Integer getIsDelete() {
    return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
        }
    
        
    @Override
    public void validate() throws RequestParamValidateException {

    }

}