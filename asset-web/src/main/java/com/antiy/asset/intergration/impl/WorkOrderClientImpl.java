package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetLogOperationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.WorkOrderVO;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/18 11:29
 * @description: 工单远程调用处理类
 */
@Component
public class WorkOrderClientImpl implements WorkOrderClient {

    @Value("${createWorkOrderUrl}")
    private String     createWorkOrderUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    @AssetLog(description = "创建工单",operationType = AssetLogOperationType.CREATE)
    public ActionResponse createWorkOrder(WorkOrderVO workOrderVO) {
        return (ActionResponse) baseClient.post(workOrderVO, new ParameterizedTypeReference<ActionResponse>() {
        }, createWorkOrderUrl);
    }
}
