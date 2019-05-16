package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.ApiCommandType;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 给智甲的软件数据请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SoftwareInstallRequest implements ObjectValidator {

    /**
     * 资产UUID
     */
    private List<String>   uuidList;

    /**
     * 不同类型对应的业务编号
     */
    private List<String>   noList;

    @ApiModelProperty(value = "操作类型", required = true)
    @NotNull(message = "操作类型不能为空")
    private ApiCommandType commandType;

    public List<String> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<String> uuidList) {
        this.uuidList = uuidList;
    }

    public List<String> getNoList() {
        return noList;
    }

    public void setNoList(List<String> noList) {
        this.noList = noList;
    }

    public ApiCommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(ApiCommandType commandType) {
        this.commandType = commandType;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }

}