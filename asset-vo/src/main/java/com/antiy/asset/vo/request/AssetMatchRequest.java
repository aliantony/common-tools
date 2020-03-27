package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangyajun
 * @date: 2019/10/10 14:03
 * @description:
 */
@ApiModel(value = "匹配对象")
public class AssetMatchRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty(value = "IP+MAC+Port", required = true)
    @NotNull(message = "IP+MAC+Port集合不能为空")
    private List<IpMacPort> ipMacPorts;

    @ApiModelProperty(value = "当前用户区域集合")
    //区域判空已注释,若要验证，请在代码里验证
//    @NotNull(message = "当前用户区域集合不能为空")
    private List<String>    areaIds;

    @ApiModelProperty(value = "IpMacPort对象", hidden = true)
    private IpMacPort       ipMacPort;

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    public List<IpMacPort> getIpMacPorts() {
        return ipMacPorts;
    }

    public void setIpMacPorts(List<IpMacPort> ipMacPorts) {
        this.ipMacPorts = ipMacPorts;
    }

    public IpMacPort getIpMacPort() {
        return ipMacPort;
    }

    public void setIpMacPort(IpMacPort ipMacPort) {
        this.ipMacPort = ipMacPort;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (CollectionUtils.isEmpty(this.ipMacPorts)) {
            throw new BusinessException("IP+MAC+Port集合不能为空");
        }
    }
}
