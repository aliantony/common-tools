package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @auther: zhangyajun
 * @date: 2019/10/10 14:03
 * @description:
 */
@ApiModel(value = "匹配对象")
public class AssetMatchRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("IP")
    @NotNull(message = "IP不能为空")
    private String        ip;
    @ApiModelProperty("MAC")
    @NotNull(message = "MAC不能为空")
    private String        mac;
    @ApiModelProperty(hidden = true)
    private List<Integer> areaIds;

    @ApiModelProperty(value = "当前用户区域集合", hidden = true)
    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
