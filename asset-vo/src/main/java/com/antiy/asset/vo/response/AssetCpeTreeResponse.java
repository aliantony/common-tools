package com.antiy.asset.vo.response;

import java.util.List;

/**
 * <p> AssetCpeTreeResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCpeTreeResponse extends BaseResponse {
    /**
     * 唯一键
     */
    private String uniqueId;
    /**
     * 父id
     */
    private String pid;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否默认显示
     */
    private Boolean show;

    /**
     * 子节点数据
     */
    private List<AssetCpeTreeResponse> childrenNode;

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public List<AssetCpeTreeResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<AssetCpeTreeResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}