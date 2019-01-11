package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetOperationRecordRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetOperationRecordRequest extends BasicRequest implements ObjectValidator{

    /**
     *  被操作的对象ID
     */
    @ApiModelProperty("被操作的对象ID")
    private Integer targetObject;
    /**
     *  被修改的表名称
     */
    @ApiModelProperty("被修改的表名称")
    private String targetTableName;
    /**
     *  操作内容
     */
    @ApiModelProperty("操作内容")
    private String content;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;

                
                
    public Integer getTargetObject() {
    return targetObject;
    }

    public void setTargetObject(Integer targetObject) {
        this.targetObject = targetObject;
        }
    
                
    public String getTargetTableName() {
    return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
        }
    
                
    public String getContent() {
    return content;
    }

    public void setContent(String content) {
        this.content = content;
        }
    
                
    public Integer getCreateUser() {
    return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
        }
    
                
    public Long getGmtCreate() {
    return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
        }
    
        
    @Override
    public void validate() throws RequestParamValidateException {

    }

}