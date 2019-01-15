package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLinkRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationRequest extends BasicRequest implements ObjectValidator {

    /**
     * 通联名称
     */
    @ApiModelProperty("通联名称")
    private String  name;
    /**
     * 通联类型:1光纤，2双绞线
     */
    @ApiModelProperty("通联类型:1光纤，2双绞线")
    private Integer linkType;
    /**
     * 头节点资产
     */
    @ApiModelProperty("头节点资产")
    private Integer headAsset;
    /**
     * 尾节点资产
     */
    @ApiModelProperty("尾节点资产")
    private Integer tailAsse;
    /**
     * 头节点类型
     */
    @ApiModelProperty("头节点类型")
    private Integer headType;
    /**
     * 尾节点类型
     */
    @ApiModelProperty("尾节点类型")
    private Integer tailType;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer linkStatus;

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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}