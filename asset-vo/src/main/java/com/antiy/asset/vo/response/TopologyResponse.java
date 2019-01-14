package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2018/12/29 11:41
 * @Description:
 */
@ApiModel(value = "初始化拓扑返回值")
public class TopologyResponse {

    /**
     * 资产的Id
     */
    @ApiModelProperty(value = "资产Id")
    private String       value;
    /**
     * 资产类型
     */
    @ApiModelProperty(value = "资产类型")
    private String       type;

    /**
     * 是否为根节点，0表示为根节点，1不是根节点
     */
    @ApiModelProperty(value = "0表示为根节点，1不是根节点")
    private String       root;

    @ApiModelProperty(value = "需要加入的子节点信息")
    private List<String> join_node;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<String> getJoin_node() {
        return join_node;
    }

    public void setJoin_node(List<String> join_node) {
        this.join_node = join_node;
    }
}
