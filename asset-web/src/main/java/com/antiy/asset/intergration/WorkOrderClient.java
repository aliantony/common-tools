package com.antiy.asset.intergration;

import com.antiy.asset.vo.request.WorkOrderVO;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/18 11:28
 * @description: 工单的client
 */
public interface WorkOrderClient {

    /**
     * 创建一个工单
     * @param workOrderVO
     * @return
     */
    ActionResponse createWorkOrder(WorkOrderVO workOrderVO);
}
