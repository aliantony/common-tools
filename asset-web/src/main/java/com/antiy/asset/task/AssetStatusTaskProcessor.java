package com.antiy.asset.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetStatusTaskDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetStatusTask;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.vo.enums.ActivityNodeStatusEnum;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.ProcessDefinitionKey;
import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.response.WaitingTaskReponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * 资产状态维护定时任务
 * @author zhangyajun
 * @create 2019-05-22 10:45
 **/
// @Component
// @Configuration // 1.主要用于标记配置类，兼备Component的效果。
// @EnableScheduling // 2.开启定时任务
public class AssetStatusTaskProcessor {
    private Logger     logger = LogUtils.get(this.getClass());

    @Resource
    AssetStatusTaskDao statusTaskDao;
    @Resource
    AssetDao           assetDao;
    @Resource
    ActivityClient     activityClient;
    @Resource
    private AesEncoder aesEncoder;

    // 3.添加定时任务 5分钟/次
    @Scheduled(cron = "0 0/5 * * * ?")
    // 或直接指定时间间隔，例如：5秒
    // @Scheduled(fixedRate= 5*60*1000)
    private void configureTasks() {

        // 查询代办任务节点信息
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();

        // 登记的资产待办信息
        activityWaitingQuery.setProcessDefinitionKey(ProcessDefinitionKey.HARDWARE_ADMITTANCE.getKey());
        ActionResponse<List<WaitingTaskReponse>> registerWaitingTask = activityClient
            .queryAllWaitingTask(activityWaitingQuery);

        // 退役的资产待办信息
        activityWaitingQuery.setProcessDefinitionKey(ProcessDefinitionKey.HARDWARE_RETIRE.getKey());
        ActionResponse<List<WaitingTaskReponse>> retireWaitingTask = activityClient
            .queryAllWaitingTask(activityWaitingQuery);

        // 构建任务ID与资产状态的映射关系
        Map<String, String> statusMap = new HashMap<>();
        List<WaitingTaskReponse> waitingTaskReponseList = getWaitingTask(registerWaitingTask, retireWaitingTask);

        // 记录日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STATUS_SCHEDULE_TASK.getName(), null, null,
            waitingTaskReponseList, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));

        if (waitingTaskReponseList.size() > 0) {
            for (WaitingTaskReponse waitingTaskReponse : waitingTaskReponseList) {
                if (ActivityNodeStatusEnum.getNodeStatus(waitingTaskReponse.getName()) != null) {
                    Integer status = ActivityNodeStatusEnum.getNodeStatus(waitingTaskReponse.getName()).getStatusEnum()
                        .getCode();
                    statusMap.put(waitingTaskReponse.getTaskId(), status.toString());
                }
            }

            try {
                // 登记的资产信息
                List<AssetStatusTask> statusTaskList = statusTaskDao.getAll();
                if (CollectionUtils.isNotEmpty(statusTaskList)) {
                    for (AssetStatusTask assetStatusTask : statusTaskList) {
                        String assetId = assetStatusTask.getAssetId();
                        String taskId = assetStatusTask.getTaskId();
                        if (statusMap.containsKey(assetStatusTask.getTaskId())) {
                            Integer currentStatus = assetDao.getById(assetId).getAssetStatus();
                            if (!statusMap.get(taskId).equals(currentStatus.toString())) {
                                Asset asset = new Asset();
                                asset.setId(DataTypeUtils.stringToInteger(assetStatusTask.getAssetId()));
                                asset.setAssetStatus(DataTypeUtils.stringToInteger(statusMap.get(taskId)));
                                asset.setGmtModified(System.currentTimeMillis());
                                asset.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
                                assetDao.updateStatus(asset);

                                // 删除使用过的任务
                                statusTaskDao.deleteById(assetStatusTask.getStringId());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LogUtils.warn(logger, "查询失败 {}", e.getMessage());
            }
        }
    }

    private List<WaitingTaskReponse> getWaitingTask(ActionResponse<List<WaitingTaskReponse>> registerWaitingTask,
                                                    ActionResponse<List<WaitingTaskReponse>> retireWaitingTask) {
        List<WaitingTaskReponse> mergeWaitingTaskList = new ArrayList<>();
        if (registerWaitingTask != null
            && RespBasicCode.SUCCESS.getResultCode().equals(registerWaitingTask.getHead().getCode())) {
            List<WaitingTaskReponse> waitingTaskReponseList = registerWaitingTask.getBody();
            if (CollectionUtils.isNotEmpty(waitingTaskReponseList)) {
                mergeWaitingTaskList.addAll(waitingTaskReponseList);
            }
        }

        if (retireWaitingTask != null
            && RespBasicCode.SUCCESS.getResultCode().equals(retireWaitingTask.getHead().getCode())) {
            List<WaitingTaskReponse> waitingTaskReponseList = retireWaitingTask.getBody();
            if (CollectionUtils.isNotEmpty(waitingTaskReponseList)) {
                mergeWaitingTaskList.addAll(waitingTaskReponseList);
            }
        }
        return mergeWaitingTaskList;
    }
}
