package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;

import java.util.List;

/**
 * 批量查询vo封装主键id列表
 */
public class BatchQueryRequest extends BasicRequest {
    public BatchQueryRequest() {
    }

    public BatchQueryRequest(List<String> ids) {
        this.ids = ids;
    }

    @Encode
    private List<String> ids;
    private List<String> processInstanceIds;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(List<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }

    @Override
    public String toString() {
        return "BatchQueryRequest{" +
                "ids=" + ids +
                '}';
    }

}

