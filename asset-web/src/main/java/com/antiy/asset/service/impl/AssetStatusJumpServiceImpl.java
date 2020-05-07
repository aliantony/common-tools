package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.RollbackEntity;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.SysUserClient;
import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.AssetAssemblyScrapRequest;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service
public class AssetStatusJumpServiceImpl implements IAssetStatusJumpService {
    private static final Logger logger = LogUtils.get(AssetStatusJumpServiceImpl.class);
    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetLinkRelationDao assetLinkRelationDao;
    @Resource
    private AssetOperationRecordDao assetOperationRecordDao;

    @Resource
    private AssetBusinessRelationDao assetBusinessRelationDao;

    @Resource
    private AesEncoder aesEncoder;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private BaseLineClient baseLineClient;
    @Resource
    private AssetServiceImpl assetService;
    @Resource
    private AssetEntryServiceImpl entryService;
    @Autowired
    private HttpServletRequest servletRequest;
    @Resource
    private BaseConverter baseConverter;
    @Resource
    private SysMessageSender messageSender;
    @Resource
    private AssetCategoryModelDao assetCategoryModelDao;
    @Resource
     private AssetAssemblyDao assetAssemblyDao;
    @Resource
    private SysUserClient sysUserClient;
    @Resource
    private IAssetAssemblyService assetAssemblyService;

    @Resource
    private IAssetCategoryModelService assetCategoryModelService;
    @Resource
    private AssetLinkRelationServiceImpl assetLinkRelationService;

    @Resource
    private SysMessageSender sysMessageSender;
    private Object lock = new Object();

    private static int MAX_FILE_COUNT=5;
    private static int MAX_STR_LENGTH=300;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        LogUtils.info(logger, "资产状态处理开始,参数request:{}", statusJumpRequest);
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        //参数校验
        if(StringUtils.isNotBlank(statusJumpRequest.getFileInfo())){
           if( MAX_FILE_COUNT<statusJumpRequest.getFileInfo().split(";").length){
               throw  new BusinessException("上传附件不允许超过5个!");
            }
        }
        if(StringUtils.isNotBlank(statusJumpRequest.getNote()) && statusJumpRequest.getNote().length()>MAX_STR_LENGTH){
            throw  new BusinessException("备注不允许超过300字符!");
        }
        if(CollectionUtils.isEmpty(statusJumpRequest.getAssetInfoList())){
            throw  new BusinessException("至少选择一个资产!");
        }
        // 配置调接口时是加密类型的id,(不用@Encode)使用手动解密
        if (loginUser != null) {
            statusJumpRequest.getAssetInfoList().forEach(e -> {
                // 不是数字类型,进行解密
                if (!StringUtils.isNumeric(e.getAssetId())) {
                    e.setAssetId(aesEncoder.decode(e.getAssetId(), loginUser.getUsername()));
                }
            });
        }

        // 1.校验参数信息,当前流程的资产是否都满足当前状态
        List<Integer> assetIdList = statusJumpRequest.getAssetInfoList().stream().map(e -> DataTypeUtils.stringToInteger(e.getAssetId())).collect(Collectors.toList());

        List<Asset> assetsInDb = assetDao.findByIds(assetIdList);
        BusinessExceptionUtils.isTrue(CollectionUtils.isNotEmpty(assetsInDb) && assetIdList.size() == assetsInDb.size(), "所选资产已被操作,请刷新后重试");

        // 当前所有资产的可执行操作与当前状态一致
        assetsInDb.forEach(asset -> {
            if (!asset.getAssetStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                LogUtils.info(logger, "资产状态不匹配 request:{}", statusJumpRequest);
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });

        //资产 报废执行  前置条件 组件报废已经执行
        if(statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.SCRAP_EXECUTEE)){

            List<AssetAssemblyRequest> assemblys = statusJumpRequest.getAssetAssemblyRequest();
            try {

                for(AssetAssemblyRequest t:assemblys){
                    int k=t.getRemove()+t.getScrap()+t.getSmash();
                    if(k<=0){
                        throw  new BusinessException("请报废完所有组件在提交!");
                    }
                }
            }catch (Exception e){
                throw  new BusinessException("请报废完所有组件在提交!");
            }

        }

        // 2.不是变更完成,提交至工作流
        if (!AssetFlowEnum.CHANGE_COMPLETE.equals(statusJumpRequest.getAssetFlowEnum())) {
            // 先更改为下一个状态,后续失败进行回滚
//            setInProcess(statusJumpRequest, assetsInDb);
            List<String> procInstIds = startActivity(statusJumpRequest, loginUser);
            try{
                updateData(statusJumpRequest, assetsInDb);
            }catch (Exception e){
                ActionResponse actionResponse= baseLineClient.deleteProcessInstance(procInstIds);
                if (actionResponse == null ||!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    LogUtils.error(logger, "删除流程失败");
                    throw new BusinessException("删除工作流失败");
                }
                throw new BusinessException(e.getMessage());
            }

        }
        else {
            for (int i = 0; i < assetsInDb.size(); i++) {
                //变更失败回滚信息并发送消息 只针对操作系统、组件、软件信息的回滚(不适用所有)
                boolean isSuccess = statusJumpRequest.getAssetInfoList().get(i).isSuccess();
                Asset asset = assetsInDb.get(i);
                String assetId =asset.getStringId();
                if (!isSuccess) {
                    //将引起配置扫描的的变更内容回滚
                    AssetRollbackRequest rollbackRequest = new AssetRollbackRequest();
                    List<RollbackEntity> rollbackEntities = assetDao.queryRollackInnfo(assetId);
                    rollbackRequest.setAssetId(assetId);
                    rollbackRequest.setGmtModified(System.currentTimeMillis());
                    rollbackRequest.setModifyUser(Objects.isNull(LoginUserUtil.getLoginUser()) ? "0" : LoginUserUtil.getLoginUser().getStringId());

                    rollbackRequest.setRollBackInfo(baseConverter.convert(rollbackEntities, RollBack.class));
                    assetDao.startRollback(rollbackRequest);

                    //消息发送到变更人
                    StringBuilder content = new StringBuilder();
                    content.append("由于");
                    HashSet<String> addInfo = new HashSet<>();
                    HashSet<String> deleteInfo = new HashSet<>();
                    HashSet<String> updateInfo = new HashSet<>();
                    rollbackEntities.forEach(v -> {
                        if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.ADD)) {
                            addInfo.add(v.getName());
                        } else if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.DELETE)) {
                            deleteInfo.add(v.getName());
                        } else if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.UPDATE)
                                && "operation_system_name".equals(v.getFiledName())) {
                            updateInfo.add(v.getFiledValue());
                        }
                    });
                    if (CollectionUtils.isNotEmpty(addInfo)) {
                        content.append("增加:");
                        addInfo.forEach(v->content.append("[").append(v).append("],"));
                        content.replace(content.length() - 1,content.length()," ");
                    }
                    if (CollectionUtils.isNotEmpty(deleteInfo)) {
                        content.append("删除:");
                        deleteInfo.forEach(v->content.append("[").append(v).append("],"));
                        content.replace(content.length() - 1,content.length()," ");
                    }
                    if (CollectionUtils.isNotEmpty(updateInfo)) {
                        content.append("变更:");
                        updateInfo.forEach(v->content.append(v));
                        content.append("操作系统为").append(asset.getOperationSystemName());
                    }
                    content.append("导致配置失败,编号[").append(asset.getNumber()).append("]资产变更失败.");
                    SysMessageRequest request = new SysMessageRequest();
                    request.setTopic("代办任务");
                    request.setSummary("资产变更");
                    request.setContent(content.toString());
                    request.setSendUserId(Objects.isNull(LoginUserUtil.getLoginUser()) ? 0 : LoginUserUtil.getLoginUser().getId());
                    request.setOrigin(1);//资产管理来源
                    request.setReceiveUserId(asset.getModifyUser());
                    request.setOther("{\"id\":" + asset.getStringId() + "}");
                    messageSender.sendMessage(request);
                }
                //变更成功触发漏扫
                else  {
                    Integer needVulScan = assetOperationRecordDao.getNeedVulScan(DataTypeUtils.stringToInteger(assetId));
                    if (needVulScan != null && needVulScan == 1) {
                        try {
                            ActionResponse actionResponse = baseLineClient.scan(assetId);
                            LogUtils.info(logger, "调用漏洞模块,assetId:{}, 结果:{}", assetId, JsonUtil.object2Json(actionResponse));
                            if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                                LogUtils.error(logger, "调用漏洞模块失败");
                            }
                        } catch (Exception ex) {
                            LogUtils.error(logger, "调用漏洞模块异常:{}", ex);
                        }
                    }
                }
                //删除回滚内容
                assetDao.deleteRollBack(assetId);
            }
            updateData(statusJumpRequest, assetsInDb);
        }


        //处理  报废  退回关联的业务
        dealRelation(statusJumpRequest);

        // 3.数据库操作
        // updateData(statusJumpRequest, assetsInDb);
        // 4.记录操作日志
        dealOperLog(statusJumpRequest,assetIdList,assetsInDb);
        return ActionResponse.success();
    }
    private void dealOperLog(AssetStatusJumpRequest statusJumpRequest,List<Integer> assetIdList, List<Asset> assetsInDb){
        List<String> numberList = assetsInDb.stream().map(t -> t.getNumber()).collect(Collectors.toList());
        String numbers=StringUtils.join(numberList,",");
        String ids = StringUtils.join(assetIdList, ",");

        if (!AssetFlowEnum.CHANGE_COMPLETE.equals(statusJumpRequest.getAssetFlowEnum()) && assetIdList.size()>1) {

            LogUtils.recordOperLog(new BusinessData(statusJumpRequest.getAssetFlowEnum().getNextOperaLog(),
                    ids, numbers, statusJumpRequest, BusinessModuleEnum.ASSET_INFO_MANAGE, statusJumpRequest.getAssetFlowEnum().getBusinessPhaseEnum()));
        }
    }
    private void dealRelation(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        //组件报废
        List<AssetAssemblyRequest> assetAssemblys = statusJumpRequest.getAssetAssemblyRequest();
        AssetAssemblyScrapRequest assetAssemblyScrapRequest=new AssetAssemblyScrapRequest();
        assetAssemblyScrapRequest.setAssetAssemblyRequestList(assetAssemblys);
        assetAssemblyScrapRequest.setAssetId(assetAssemblys.get(0).getAssetId());
        assetAssemblyService.scrapUpdate(assetAssemblyScrapRequest);

        //业务管理
        List<Integer> assetIdList = statusJumpRequest.getAssetInfoList().stream().map(e -> DataTypeUtils.stringToInteger(e.getAssetId())).collect(Collectors.toList());
        if(statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.RETIRE_EXECUTEE)
                ||statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.SCRAP_EXECUTEE)){
            //删除关联业务的资产
            List<String> assetIds = assetIdList.stream().map(e -> e.toString()).collect(Collectors.toList());
            assetBusinessRelationDao.deleteByAssetId(assetIds);

            //配置
            /**
             * 配置
             */
            ActionResponse actionResponse= baseLineClient.removeAsset(assetIdList);
            if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                throw new BusinessException("资产退役调用漏洞模块失败");
            }
            return;
        }

        //  准入
        if(statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.RETIRE_APPLICATION)
                ||statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.SCRAP_APPLICATION)
                ||AssetFlowEnum.NET_IN_TO_SCRAP_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum())){


            /**
             * 准入
             */
            List<ActivityHandleRequest> requestList = new ArrayList<>();
            statusJumpRequest.getAssetInfoList().forEach(assetInfo -> {
                ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();

                activityHandleRequest.setId(assetInfo.getAssetId());

                requestList.add(activityHandleRequest);
            });
            AssetEntryRequest assetEntryRequest=new AssetEntryRequest();
            assetEntryRequest.setAssetActivityRequests(requestList);
            assetEntryRequest.setUpdateStatus(DataTypeUtils.integerToString(AssetEnterStatusEnum.NO_ENTER.getCode()));
            //准入来源设置
            assetEntryRequest.setEntrySource(AssetEntrySourceEnum.ASSET_SCRAP);
            //设置权限类型
            List<String> tag=Arrays.asList("asset:info:list:bfsq");
            if(statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.RETIRE_APPLICATION)){
                //准入来源设置
                assetEntryRequest.setEntrySource(AssetEntrySourceEnum.ASSET_RETIRE);
                //消息类型
                tag=Arrays.asList("asset:info:list:thsq");
            }

            //准入更新
            entryService.updateEntryStatus(assetEntryRequest);

            //获取特定权限的用户id
            List<Integer> userIds = getALLUserIdByPermission(tag);
            if(CollectionUtils.isEmpty(userIds)){
                boolean isRetire=AssetFlowEnum.RETIRE_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum());
                throw new BusinessException("请先维护具备"+(isRetire==true?"退回":"报废")+"执行权限的人员");
            }
            //发消息
            SysMessageRequest sysMessageRequest=new SysMessageRequest();
            List<SysMessageRequest> sysMessageRequests=new ArrayList<>();

            userIds.forEach(t->{
                SysMessageRequest v=new SysMessageRequest();
                v.setReceiveUserId(t);
                sysMessageRequests.add(v);
            });
            sysMessageSender.batchSendMessage(sysMessageRequests);



        }


    }

    private  List<Integer> getALLUserIdByPermission(List<String> tag){
        //获取权限人员id
        ActionResponse usersOfHaveRight = sysUserClient.getUsersOfHaveRight(tag);
        if (null == usersOfHaveRight || !RespBasicCode.SUCCESS.getResultCode().equals(usersOfHaveRight.getHead().getCode())) {
            throw new BusinessException("获取权限用具id 失败！");
        }
        List<HashMap<String,String>> users=(List<HashMap<String,String>>)usersOfHaveRight.getBody();

        List<Integer> userIds = users.stream()
                .map(t -> DataTypeUtils.stringToInteger(aesEncoder.decode(t.get("stringId"), LoginUserUtil.getLoginUser().getUsername())))
                .collect(Collectors.toList());

        return  userIds;
    }
    @Override
    public ActionResponse statusJump(AssetStatusJumpRequest statusJumpRequest) throws Exception {

        return null;
    }

    private AssetCorrectIInfoResponse correctingAssetOfbaseLine(ActionResponse<AssetCorrectIInfoResponse> baseLineResponse, Asset asset) throws Exception {
        AssetCorrectIInfoResponse assetCorrectIInfoResponse = baseLineResponse.getBody();
        //资产漏洞和配置模块 “修复”成功
         if(baseLineResponse.getBody().equals(AssetBaseLineEnum.SUCCESS.getMsg())){
            // 改变资产状态
             changeAssetStatusToNetIn(asset);
            assetCorrectIInfoResponse.setNeedManualPush("0");
            logger.info("资产继续入网");
        }
        //  资产漏洞或配置模块 “修复”失败、配置未完成等情况
        else if(baseLineResponse.getBody().equals(AssetBaseLineEnum.FALI.getMsg())){
            assetCorrectIInfoResponse.setNeedManualPush("1");
             logger.info("资产手动继续入网");
        }else{
             assetCorrectIInfoResponse.setNeedManualPush("0");
             logger.info("资产处于配置中");
         }
        assetCorrectIInfoResponse.setCheckStatus(baseLineResponse.getBody().getCheckStatus());
        assetCorrectIInfoResponse.setConfigStatus(baseLineResponse.getBody().getConfigStatus());
        return  assetCorrectIInfoResponse;
    }
    @Override
    public AssetCorrectIInfoResponse assetCorrectingOfbaseLine(AssetCorrectRequest assetCorrectRequest) throws Exception {
        ActivityHandleRequest activityHandleRequest=assetCorrectRequest.getActivityHandleRequest();
        Asset assetOfDB = assetDao.getById(activityHandleRequest.getStringId());
        if(assetOfDB==null){
            throw new BusinessException("资产不存在！");
        }
        //判断是否是计算机设备
        Integer id = assetCategoryModelDao.getByName(AssetCategoryEnum.COMPUTER.getName()).getId();
        List<Integer> categoryModels = assetLinkRelationService.getCategoryNodeList(Arrays.asList(id));
        Set<String> categoryModelsStr = categoryModels.stream().map(t -> t.toString()).collect(Collectors.toSet());
        if(categoryModelsStr.add(assetOfDB.getCategoryModel())){
            throw new BusinessException("只允许计算设备进行此项操作！");
        }
        ActionResponse<AssetCorrectIInfoResponse> baseLineResponse=baseLineClient.rectification( activityHandleRequest.getStringId());
        if (null == baseLineResponse || !RespBasicCode.SUCCESS.getResultCode().equals(baseLineResponse.getHead().getCode())) {
            LogUtils.error(logger, "调用配置模块失败");
            throw  new BusinessException("调用配置模块失败");
        }
        //配置工作流
        ActionResponse baseLineActivityResponse = baseLineActivity(baseLineResponse, activityHandleRequest);
        if (null == baseLineActivityResponse || !RespBasicCode.SUCCESS.getResultCode().equals(baseLineActivityResponse.getHead().getCode())) {
            LogUtils.error(logger, "资产整改配置工作流异常!");
            throw  new BusinessException("资产整改配置工作流异常");
        }
        AssetCorrectIInfoResponse assetCorrectIInfoResponse = baseLineResponse.getBody();
        assetCorrectIInfoResponse.setNeedManualPush("0");
        if(AssetBaseLineEnum.SUCCESS.getMsg().equals(baseLineResponse.getBody().getConfigStatus())
                ||AssetBaseLineEnum.FALI.getMsg().equals(baseLineResponse.getBody().getConfigStatus())){
            assetCorrectIInfoResponse.setNeedManualPush("1");
        }

        LogUtils.recordOperLog(
                new BusinessData("执行配置基准整改", assetOfDB.getId(), assetOfDB.getNumber(),
                        assetCorrectRequest, BusinessModuleEnum.ASSET_INFO_MANAGE, BusinessPhaseEnum.NONE)
        );
        return assetCorrectIInfoResponse;
    }
    @Override
    public AssetCorrectIInfoResponse assetCorrectingInfo(AssetCorrectRequest assetCorrectRequest) throws Exception {
        ActivityHandleRequest activityHandleRequest=assetCorrectRequest.getActivityHandleRequest();
        Asset assetOfDB = assetDao.getById(activityHandleRequest.getStringId());
        if(assetOfDB==null){
            throw new BusinessException("资产不存在！");
        }

        ActionResponse<AssetCorrectIInfoResponse> vlunResponse= baseLineClient.situationOfVul(activityHandleRequest.getStringId());

        if (null == vlunResponse || !RespBasicCode.SUCCESS.getResultCode().equals(vlunResponse.getHead().getCode())) {
            LogUtils.error(logger, "调用漏洞模块失败");
            throw  new BusinessException("调用漏洞模块失败");
        }

        AssetCorrectIInfoResponse assetCorrectIInfoResponse = vlunResponse.getBody();
        //整改流程--漏洞步骤处于进行中状态
        if(!assetCorrectIInfoResponse.getScan()||! assetCorrectIInfoResponse.getDeal()){
            assetCorrectIInfoResponse.setNeedManualPush("0");
            return  assetCorrectIInfoResponse;
        }
        //获取流程实例id    (漏洞步骤完成)
        Integer assetId = DataTypeUtils.stringToInteger(activityHandleRequest.getStringId());
        List<AssetOperationRecord> assetOperationRecords = assetOperationRecordDao.listByAssetIds(Arrays.asList(assetId));

        activityHandleRequest.setProcInstId(assetOperationRecords.get(0).getTaskId().toString());
        vlunActivity(assetCorrectIInfoResponse, activityHandleRequest);
        assetCorrectIInfoResponse.setNeedManualPush("1");

        //修改整改标志字段
        Asset asset=new Asset();
        asset.setId(DataTypeUtils.stringToInteger(activityHandleRequest.getStringId()));
        asset.setRectification(1);
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getByName(AssetCategoryEnum.COMPUTER.getName());
        List<Integer> categoryNodeList = assetLinkRelationService.getCategoryNodeList(Arrays.asList(assetCategoryModel.getId()));
        Set<String> categoryNodeStrList = categoryNodeList.stream().map(t -> t.toString()).collect(Collectors.toSet());
        if(categoryNodeStrList.add(assetOfDB.getCategoryModel())){
            asset.setRectification(4);
        }
        assetDao.update(asset);
        return assetCorrectIInfoResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer netToCorrect(List<String> assetIds) throws Exception {
        List<AssetOperationRecord> assetOperationRecordList=new ArrayList<>();
        for(String assetId: assetIds){
            AssetOperationRecord assetOperationRecord=new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(assetId);
            assetOperationRecord.setOriginStatus(AssetFlowEnum.NET_IN_TO_CORRECT.getCurrentAssetStatus().getCode());
            assetOperationRecord.setTargetStatus(AssetFlowEnum.NET_IN_TO_CORRECT.getAgreeStatus().getCode());
            assetOperationRecord.setContent(AssetFlowEnum.NET_IN_TO_CORRECT.getNextMsg());
            //获取资产登记保存的流程实例
            AssetOperationRecord  lastRecord = assetOperationRecordDao.getLastByAssetId(assetId);
            assetOperationRecord.setTaskId(lastRecord.getTaskId());
            assetOperationRecordList.add(assetOperationRecord);

            //修改整改标志字段
            Asset asset=new Asset();
            asset.setId(DataTypeUtils.stringToInteger(assetId));
            asset.setRectification(3);
            assetDao.update(asset);

            //漏扫
            ActionResponse scan;
            scan = baseLineClient.scan(assetId);

        }
        return assetOperationRecordDao.insertBatch(assetOperationRecordList);
    }

    @Override
    public Integer continueNetIn(ActivityHandleRequest activityHandleRequest) throws Exception {
        Asset assetOfDB = assetDao.getById(activityHandleRequest.getStringId());
        Integer id = assetCategoryModelDao.getByName(AssetCategoryEnum.COMPUTER.getName()).getId();
        List<String> computerIdList = assetLinkRelationService.getCategoryNodeList(Arrays.asList(id)).stream().map(t->t.toString()).collect(Collectors.toList());


        Integer safeId = assetCategoryModelDao.getByName(AssetCategoryEnum.COMPUTER.getName()).getId();
        List<String> safeIdList = assetLinkRelationService.getCategoryNodeList(Arrays.asList(safeId)).stream().map(t->t.toString()).collect(Collectors.toList());
        // 非 孤岛 /可借用计算设备
        boolean  k=computerIdList.contains(assetOfDB.getCategoryModel()) && assetOfDB.getIsOrphan()!=1 && assetOfDB.getIsBorrow()!=1;
        Asset asset=new Asset();
        asset.setId(assetOfDB.getId());
        if(k && safeIdList.contains(assetOfDB.getCategoryModel())){
            asset.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        }
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        asset.setRectification(1);
        Integer update = assetDao.update(asset);
        //推进工作流
        Map<String,String> formDdata=new HashMap<>();
        formDdata.put("assetRegisterResult","continueRegister");
        activityHandleRequest.setFormData(formDdata);
        ActionResponse actionResponse = activityClient.completeTask(activityHandleRequest);
        if(actionResponse==null || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())){
            throw new BusinessException("工作流异常！");
        }
       /* LogUtils.recordOperLog(
                new BusinessData("通过安全整改", assetOfDB.getId(), assetOfDB.getNumber(),
                        activityHandleRequest, BusinessModuleEnum.ASSET_INFO_MANAGE, BusinessPhaseEnum.NONE)
        );*/
        return update;
    }


    private void changeAssetStatusToNetIn(Asset assetOfDB) throws Exception {
        //改变资产状态
        Asset asset=new Asset();
        asset.setId(assetOfDB.getId());
       // asset.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        //判断孤岛设备
        /*List<String> categoryModels = assetCategoryModelDao.getCategoryModelsByParentName(AssetCategoryEnum.COMPUTER.getName());
        if(AssetIsBorrowEnum.REFUSE.equals(assetOfDB.getIsBorrow())
                && AssetIsOrphanEunm.REFUSE.equals(assetOfDB.getIsOrphan())
                &&categoryModels.contains(assetOfDB.getCategoryModel())){

            asset.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        }else{
            asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        }*/
        assetDao.update(asset);
    }
    public AssetFlowEnum getCorrectingSource( String assetId){
        AssetOperationRecord assetOperationRecord= assetOperationRecordDao.getLastByAssetId(assetId);
        if(assetOperationRecord.getOriginStatus().equals(AssetStatusEnum.NET_IN.getCode()) && assetOperationRecord
                .getTargetStatus().equals(AssetStatusEnum.CORRECTING)){
           return AssetFlowEnum.NET_IN_TO_CORRECT;
        }else{
            return AssetFlowEnum.CORRECT;
        }
    }
    private AssetCorrectIInfoResponse  netIntoCorrect(ActionResponse<AssetCorrectIInfoResponse> baseLineResponse,String assetId) throws Exception {
        AssetCorrectIInfoResponse assetCorrectIInfoResponse = baseLineResponse.getBody();
        assetCorrectIInfoResponse.setCheckStatus(baseLineResponse.getBody().getCheckStatus());
        assetCorrectIInfoResponse.setConfigStatus(baseLineResponse.getBody().getConfigStatus());
        assetCorrectIInfoResponse.setNeedManualPush("0");
        return  assetCorrectIInfoResponse;
    }
    private ActionResponse baseLineActivity(ActionResponse<AssetCorrectIInfoResponse> baseLineResponse, ActivityHandleRequest activityHandleRequest) {
        Map<String,String> formData=new HashMap<>(1);
        formData.put("baselineRectifyResult","success");
        activityHandleRequest.setFormData(formData);
        if(baseLineResponse.getBody().equals(AssetBaseLineEnum.SUCCESS.getMsg())){
             return activityClient.completeRunningTaskByProcInstId(activityHandleRequest);
        }else if(baseLineResponse.getBody().equals(AssetBaseLineEnum.FALI.getMsg())) {
            formData.put("baselineRectifyResult","fail");
            return activityClient.completeRunningTaskByProcInstId(activityHandleRequest);
        }
        return ActionResponse.success();
    }
    private ActionResponse vlunActivity(AssetCorrectIInfoResponse assetCorrectIInfoResponse, ActivityHandleRequest activityHandleRequest){
        Map<String,String> formData=new HashMap<>(1);
        formData.put("vulRectifyResult","success");
        activityHandleRequest.setFormData(formData);
        if(assetCorrectIInfoResponse.getFailureCount()<=0){
            return activityClient.completeRunningTaskByProcInstId(activityHandleRequest);
        }else {
            formData.put("vulRectifyResult","fail");
           return activityClient.completeRunningTaskByProcInstId(activityHandleRequest);
        }
    }
    private void setInProcess(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        Integer lastAssetStatus = statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode();

        List<Asset> updateAssetList = new ArrayList<>(assetsInDb.size());
        assetsInDb.forEach(e -> {
            Asset asset = new Asset();
            asset.setId(e.getId());
            asset.setAssetStatus(AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree()).getCode());
            updateAssetList.add(asset);
        });

        transactionTemplate.execute(transactionStatus -> {
            updateAssetList.forEach(asset -> {
                try {
                    int effectRow = assetDao.updateAssetStatusWithLock(asset, lastAssetStatus);
                    if (effectRow <= 0) {
                        throw new BusinessException("资产更新失败");
                    }
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    LogUtils.warn(logger, "资产更新失败{}", e);
                    throw new BusinessException("操作失败,请刷新页面后重试");
                }
            });
            return null;
        });
    }

    private List<String>  startActivity(AssetStatusJumpRequest assetStatusRequest, LoginUser loginUser) throws Exception {
        ParamterExceptionUtils.isNull(assetStatusRequest.getFormData(), "formData参数错误");
        // 为满足需求,同时工作流模块无法达到要求;"整改"不通过退回至待检查时,重置formData:将执行意见改为1,将下一步人设置为上一步"检查"的操作人
        if (assetStatusRequest.getAssetFlowEnum().equals(AssetFlowEnum.CORRECT)
                && Boolean.FALSE.equals(assetStatusRequest.getWaitCorrectToWaitRegister())) {
            // 整改是单个资产
            Integer lastCheckUser = assetOperationRecordDao.getCreateUserByAssetId(DataTypeUtils.stringToInteger(assetStatusRequest.getAssetInfoList().get(0).getAssetId()));
            Map<String, Object> formData = new HashMap<>(1);
            formData.put("safetyChangeResult", "1");
            formData.put("safetyCheckUser", aesEncoder.encode(lastCheckUser.toString(), loginUser.getUsername()));
            assetStatusRequest.setFormData(formData);
        }
        //设置权限的用户id
        List<String> tag=Arrays.asList("asset:info:list:bfsq");
        List<Integer> userIds = getALLUserIdByPermission(tag);
       // 设置 formData
        Map<String,String> formData=new HashMap<>();
        if(AssetFlowEnum.RETIRE_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())){
            formData.put("retireImplementUser",StringUtils.join(userIds,","));
        }
        if(AssetFlowEnum.SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
                || AssetFlowEnum.NET_IN_TO_SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())){
            formData.put("scrapImplementUser",StringUtils.join(userIds,","));
        }

        // 1.退役申请需要启动流程,其他步骤完成流程
        if (AssetFlowEnum.RETIRE_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
                || AssetFlowEnum.SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
                || AssetFlowEnum.NET_IN_TO_SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
        ) {
            // 启动流程
            List<StatusJumpAssetInfo> assetInfoList = assetStatusRequest.getAssetInfoList();
            List<String> procInstIds=new ArrayList<>();
            for (StatusJumpAssetInfo assetInfo : assetInfoList) {
                ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
                manualStartActivityRequest.setAssignee(loginUser.getId().toString());
                manualStartActivityRequest.setBusinessId(assetInfo.getAssetId());
                if (AssetFlowEnum.RETIRE_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
                        || AssetFlowEnum.NET_IN_TO_SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())) {
                    manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.ASSET_RETIRE.getCode());
                } else {
                    manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.ASSET_SCRAP.getCode());
                }

                manualStartActivityRequest.setFormData(assetStatusRequest.getFormData());

                ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);

                LogUtils.info(logger, "请求工作流参数:{},请求工作流结果: {}", manualStartActivityRequest, JsonUtil.object2Json(actionResponse));
                if (actionResponse == null) {
                    throw new BusinessException("启动工作流失败");

                }
                if (!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    throw new BusinessException((String) actionResponse.getBody());
                }
                procInstIds.add((String)actionResponse.getBody());
            }
            return  procInstIds;

        } else {
            // 非启动退役流程
            List<ActivityHandleRequest> requestList = new ArrayList<>();
            assetStatusRequest.getAssetInfoList().forEach(assetInfo -> {
                ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
                // 由于工作流传参接收问题,此处不能直接使用同一个formData对象,必须new新对象
                activityHandleRequest.setFormData(new HashMap<Object, Object>(assetStatusRequest.getFormData()));
                activityHandleRequest.setTaskId(assetInfo.getTaskId());

                requestList.add(activityHandleRequest);
            });

                ActionResponse actionResponse = activityClient.completeTaskBatch(requestList);
                LogUtils.info(logger, "请求工作流结果: {}", JsonUtil.object2Json(actionResponse));
                if (actionResponse == null) {
                    throw new BusinessException("启动工作流失败");

                }
                if (!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    throw new BusinessException(actionResponse.getBody().toString());
                }
            return Lists.newArrayList();
        }
    }

    /**
     * 更新数据库:资产、操作记录<br>
     * 如果是退役,删除通联关系
     *
     * @param statusJumpRequest
     * @param assetsInDb        库中的数据
     */
    private void updateData(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        List<AssetOperationRecord> operationRecordList = new ArrayList<>();
        List<Asset> updateAssetList = new ArrayList<>();
        List<Integer> deleteLinkRelationIdList = new ArrayList<>();

        Long currentTime = System.currentTimeMillis();
        LoginUser loginUser;
        // 变更->配置完成可能没有token',没有用户信息;保存record表需要使用该字段,默认为0存储
        if (Objects.isNull(loginUser = LoginUserUtil.getLoginUser())) {
            loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setName("");
        }

        for (Asset asset : assetsInDb) {
            AssetStatusEnum nextStatus = statusJumpRequest.getAgree() == true ? statusJumpRequest.getAssetFlowEnum().getAgreeStatus()
                    : statusJumpRequest.getAssetFlowEnum().getRefuseStatus();
            if (AssetFlowEnum.RETIRED.equals(statusJumpRequest.getAssetFlowEnum())) {
                // 退役删除通联关系
                deleteLinkRelationIdList.add(asset.getId());
            }
            asset.setAssetStatus(nextStatus.getCode());
            asset.setGmtModified(currentTime);
            asset.setModifyUser(loginUser.getId());
            updateAssetList.add(asset);

            // 保存操作记录
            operationRecordList.add(convertAssetOperationRecord(statusJumpRequest, currentTime, loginUser.getId(), loginUser.getName(), asset.getStringId(), nextStatus.getCode()));
        }
        assetOperationRecordDao.insertBatch(operationRecordList);
        assetDao.updateAssetBatch(updateAssetList);
        if (CollectionUtils.isNotEmpty(deleteLinkRelationIdList)) {
            assetLinkRelationDao.deleteByAssetIdList(deleteLinkRelationIdList);
        }

    }

    private AssetOperationRecord convertAssetOperationRecord(AssetStatusJumpRequest statusJumpRequest, Long currentTime, Integer loginUserId, String loginUserName, String assetId, Integer nextStatus) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setOriginStatus(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode());
        assetOperationRecord.setTargetStatus(nextStatus);
        assetOperationRecord.setContent(statusJumpRequest.getAssetFlowEnum().getNextMsg());
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setGmtCreate(currentTime);
        assetOperationRecord.setOperateUserId(loginUserId);
        assetOperationRecord.setOperateUserName(loginUserName);
        assetOperationRecord.setCreateUser(loginUserId);
        assetOperationRecord.setNote(statusJumpRequest.getNote() == null ? "" : statusJumpRequest.getNote());
        assetOperationRecord.setFileInfo(statusJumpRequest.getFileInfo() == null ? "" : statusJumpRequest.getFileInfo());
        assetOperationRecord.setCheckUserId(statusJumpRequest.getCheckUserId()==null ?0:DataTypeUtils.stringToInteger(statusJumpRequest.getCheckUserId()));
        assetOperationRecord.setCheckUserName(statusJumpRequest.getCheckUserName()==null?"":statusJumpRequest.getCheckUserName());
        assetOperationRecord.setExecuteUserId(statusJumpRequest.getExecuteUserId()==null?0:DataTypeUtils.stringToInteger(statusJumpRequest.getExecuteUserId()));
        assetOperationRecord.setExecuteUserName(statusJumpRequest.getExecuteUserName()==null?"":statusJumpRequest.getExecuteUserName());
        return assetOperationRecord;
    }

    private Integer getTaksIdByBusinessKey(String key){
        ActionResponse actionResponse=activityClient.getTaksIdByBusinessKey(key);
        if(actionResponse==null || !actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())){
            throw new BusinessException("通过businessKey获取taksId 出错");
        }
        String taskId=(String)actionResponse.getBody();
        return  Integer.valueOf(taskId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String entryExecution(AssetEntryRequest request) {
        List<Integer> assetIds = request.getAssetActivityRequests().stream()
                .map(ActivityHandleRequest::getId).collect(Collectors.toList());
        List<Asset> oldAssets = assetDao.findByIds(assetIds);
        List<Asset> newAssets;
        synchronized (lock) {
            //并发处理 验证资产状态
            for (Asset oldAsset : oldAssets) {
                if (!EnumUtil.equals(assetDao.getByAssetId(oldAsset.getStringId()).getAssetStatus(), AssetStatusEnum.NET_IN_CHECK)) {
                    throw new BusinessException("编号为[" + oldAsset.getName() + "]的资产已被他人操作，请勿重复操作");
                }
            }
            newAssets = oldAssets.stream().map(v -> {
                v.setFirstEnterNett(System.currentTimeMillis());
                v.setModifyUser(LoginUserUtil.getLoginUser().getId());
                v.setGmtModified(System.currentTimeMillis());
                v.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                return v;
            }).collect(Collectors.toList());
            //更新资产状态
            assetDao.updateAssetBatch(newAssets);
        }
        //推动工作流
        ActionResponse response=activityClient.completeTaskBatch(request.getAssetActivityRequests());
        if (response==null || !response.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())){
            logger.warn("工作流参数：{}",request.getAssetActivityRequests());
            throw new BusinessException("调用工作流模块失败");
        }
        //记录操作日志
        if (newAssets.size() > 1) {
            StringBuilder ids = new StringBuilder();
            //numbers 资产编号
            StringBuilder numbers = new StringBuilder();
            newAssets.stream().forEach(v->{
                ids.append(v.getStringId()).append(",");
                numbers.append(v.getNumber()).append(",");
            });
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.BATCH_ENTRY_EXECUTION.getName(), ids.deleteCharAt(ids.length()-1).toString()
                    , numbers.deleteCharAt(numbers.length()-1).toString(), null, BusinessModuleEnum.ASSET_INFO_MANAGE, BusinessPhaseEnum.ASSET_BATCH_ACCESS));
        }else {
            newAssets.forEach(v -> LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ENTRY_EXECUTION.getName(), v.getId()
                    , v.getNumber(), v, BusinessModuleEnum.ASSET_INFO_MANAGE, BusinessPhaseEnum.ACCESS)));
        }
        //下发准入指令
        SecurityContext context=SecurityContextHolder.getContext();
        new Thread(() -> {
            SecurityContext finalContext = context;
            entryService.updateEntryStatus(request,finalContext );
        }).start();

        return "";
    }
}

