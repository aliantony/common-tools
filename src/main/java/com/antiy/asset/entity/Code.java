package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-19
 */

public class Code extends BaseEntity {


private static final long serialVersionUID = 1L;

        

               
        

        
    

               
        

                     @ApiModelProperty("")
         private Integer code;

    
    

               
        

                     @ApiModelProperty("")
         private String value;

    
    

               
        

                     @ApiModelProperty("")
         private Integer codeTypeId;

    
    

               
        

                     @ApiModelProperty("")
         private Integer status;

    

                    
                                    
        public Integer getCode() {
                return code;
                }

                        public void setCode(Integer code) {
                            this.code = code;
                            }
        
                                    
        public String getValue() {
                return value;
                }

                        public void setValue(String value) {
                            this.value = value;
                            }
        
                                    
        public Integer getCodeTypeId() {
                return codeTypeId;
                }

                        public void setCodeTypeId(Integer codeTypeId) {
                            this.codeTypeId = codeTypeId;
                            }
        
                                    
        public Integer getStatus() {
                return status;
                }

                        public void setStatus(Integer status) {
                            this.status = status;
                            }
        
    
@Override
public String toString() {
        return "Code{" +
                                                                ", code=" + code +
                                                                ", value=" + value +
                                                                ", codeTypeId=" + codeTypeId +
                                                                ", status=" + status +
                                "}";
        }
        }
