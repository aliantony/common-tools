package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @auther: zhangyajun
 * @date: 2019/10/10 14:03
 * @description:
 */
@ApiModel(value = "匹配对象")
public class AssetMatchRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("IP+MAC+Port")
    @NotNull(message = "IP+MAC+Port集合不能为空")
    private List<IpMacPort> ipMacPorts;

    @ApiModelProperty(value = "当前用户区域集合")
    @NotNull(message = "当前用户区域集合不能为空")
    private List<Integer> areaIds;

    @ApiModelProperty(value = "IpMacPort对象", hidden = true)
    private IpMacPort       ipMacPort;

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
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
