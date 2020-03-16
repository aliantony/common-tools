package com.antiy.asset.vo.response;

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