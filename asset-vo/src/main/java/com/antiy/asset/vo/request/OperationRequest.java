package com.antiy.asset.vo.request;

import com.antiy.common.base.QueryCondition;

public class OperationRequest extends QueryCondition {
    /**
     * 1 变更
     *
     *2  登记
     * 3  不予登记
     * 4 拟退役
     */
    Integer operation;

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
