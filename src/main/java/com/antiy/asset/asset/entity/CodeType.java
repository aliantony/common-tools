package com.antiy.asset.asset.entity;


import java.util.Date;
import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 
 * </p>
 *
 * @author xxxxxxxxx
 * @since 2018-12-29
 */

public class CodeType extends BaseEntity {


private static final long serialVersionUID = 1L;

        @ApiModelProperty("")
    private String code;
        @ApiModelProperty("")
    private String value;
        @ApiModelProperty("")
    private Integer status;

                    
                                    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
                                    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
                                    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
        @Override
    public String toString() {
            return "CodeType{" +
                                                                                            ", code=" + code +
                                                                                        ", value=" + value +
                                                                                        ", status=" + status +
                                                "}";
    }
    }