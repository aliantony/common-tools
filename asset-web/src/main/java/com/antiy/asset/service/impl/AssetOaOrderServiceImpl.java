package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.asset.entity.AssetOaOrderApprove;
import com.antiy.asset.intergration.SysUserClient;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.service.IAssetOaOrderService;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetOaOrderStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.query.AssetOaOrderQuery;
import com.antiy.asset.vo.query.QxTagQuery;
import com.antiy.asset.vo.request.AssetOaOrderApplyRequest;
import com.antiy.asset.vo.request.AssetOaOrderApproveRequest;
import com.antiy.asset.vo.request.AssetOaOrderRequest;
import com.antiy.asset.vo.response.AssetOaOrderApplyResponse;
import com.antiy.asset.vo.response.AssetOaOrderApproveResponse;
import com.antiy.asset.vo.response.AssetOaOrderResponse;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * OA订单表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderServiceImpl extends BaseServiceImpl<AssetOaOrder> implements IAssetOaOrderService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderDao assetOaOrderDao;
    @Resource
    private AssetOaOrderApplyDao assetOaOrderApplyDao;
    @Resource
    private AssetOaOrderApproveDao assetOaOrderApproveDao;
    @Resource
    private AssetOaOrderHandleDao assetOaOrderHandleDao;
    @Resource
    private AssetLendRelationDao assetLendRelationDao;
    @Resource
    private AssetDao assetDao;

    @Resource
    private BaseConverter<AssetOaOrderRequest, AssetOaOrder> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrder, AssetOaOrderResponse> responseConverter;
    @Resource
    private BaseConverter<AssetOaOrderApplyRequest, AssetOaOrderApply> applyRequestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApply, AssetOaOrderApplyResponse> applyResponseConverter;
    @Resource
    private BaseConverter<AssetOaOrderApproveRequest, AssetOaOrderApprove> approveRequestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApprove, AssetOaOrderApproveResponse> approveResponseConverter;

    @Resource
    private AesEncoder aesEncoder;
    @Resource
    private SysMessageSender messageSender;
    @Resource
    private SysUserClient sysUserClient;
    @Resource
    private BaseClient client;

    @Value("${getUsersByQxTagUrl}")
    private String getUsersByQxTagUrl;

    @Override
    public Integer saveAssetOaOrder(AssetOaOrderRequest request) throws Exception {
        AssetOaOrder assetOaOrder = requestConverter.convert(request, AssetOaOrder.class);
        assetOaOrder.setOrderStatus(AssetOaOrderStatusEnum.WAIT_HANDLE.getCode());
        //保存订单信息
        assetOaOrderDao.insert(assetOaOrder);
        //保存申请信息
        AssetOaOrderApplyRequest assetOaOrderApplyRequest = request.getAssetOaOrderApplyRequest();
        AssetOaOrderApply assetOaOrderApply = applyRequestConverter.convert(assetOaOrderApplyRequest, AssetOaOrderApply.class);
        assetOaOrderApply.setOrderNumber(assetOaOrder.getNumber());
        assetOaOrderApplyDao.insert(assetOaOrderApply);
        //保存审核信息
        List<AssetOaOrderApproveRequest> assetOaOrderApproveRequests = request.getAssetOaOrderApproveRequests();
        List<AssetOaOrderApprove> assetOaOrderApproves = approveRequestConverter.convert(assetOaOrderApproveRequests, AssetOaOrderApprove.class);
        for (AssetOaOrderApprove assetOaOrderApprove : assetOaOrderApproves) {
            assetOaOrderApprove.setOrderNumber(assetOaOrder.getNumber());
        }
        assetOaOrderApproveDao.insertBatch(assetOaOrderApproves);
        //todo 发送消息
        sendMessage(request, assetOaOrder);
        return assetOaOrder.getId();
    }

    /**
     * 新产生订单发送消息
     */
    void sendMessage(AssetOaOrderRequest assetOaOrderRequest, AssetOaOrder assetOaOrder) {
        logger.info("--------发送消息--------订单id:{}", assetOaOrder.getId());
        String message = "";
        String tagStr = "";
        Integer source = null;
        source = 1;
        if (assetOaOrderRequest.getOrderType().equals(AssetOaOrderTypeEnum.INNET.getCode())) {
            //入网订单
            message = "OA入网申请订单";
            tagStr = "asset";
        } else if (assetOaOrderRequest.getOrderType().equals(AssetOaOrderTypeEnum.BACK.getCode())) {
            //退回订单
            message = "OA退回申请订单";
            tagStr = "asset";
        } else if (assetOaOrderRequest.getOrderType().equals(AssetOaOrderTypeEnum.SCRAP.getCode())) {
            //报废订单
            message = "OA报废申请订单";
            tagStr = "asset";
        } else if (assetOaOrderRequest.getOrderType().equals(AssetOaOrderTypeEnum.LEND.getCode())) {
            //出借订单
            message = "OA出借申请订单";
            tagStr = "asset";
        }

        List<SysMessageRequest> sysMessageRequests = new ArrayList<>();
        //获取特定权限的用户id
        List<String> tags = new ArrayList<String>();
        tags.add(tagStr);
        List<Integer> userIds = getALLUserIdByPermission(tags);
        if(CollectionUtils.isEmpty(userIds)){
            throw new BusinessException("请先维护具备" + AssetOaOrderTypeEnum.getValueByCode(assetOaOrderRequest.getOrderType()).getMsg() + "执行权限的人员");
        }

        final String taskType = message;
        userIds.forEach(t->{
            SysMessageRequest sysMessageRequest = new SysMessageRequest();
            sysMessageRequest.setTopic("OA订单");
            sysMessageRequest.setSummary("OA订单管理");
            sysMessageRequest.setContent(new StringBuilder("您有一条由系统提交的[" + taskType + "]任务，请尽快处理").toString());
            sysMessageRequest.setOrigin(1);//资产管理来源
            sysMessageRequest.setReceiveUserId(t);
            sysMessageRequest.setOther("{\"id\":" + assetOaOrder.getId() + "}");
            sysMessageRequests.add(sysMessageRequest);
        });
        messageSender.batchSendMessage(sysMessageRequests);
    }

    @Override
    public Integer updateAssetOaOrder(AssetOaOrderRequest request) throws Exception {
        AssetOaOrder assetOaOrder = requestConverter.convert(request, AssetOaOrder.class);
        return assetOaOrderDao.update(assetOaOrder);
    }

    @Override
    public List<AssetOaOrderResponse> findListAssetOaOrder(AssetOaOrderQuery query) throws Exception {
        List<AssetOaOrder> assetOaOrderList = assetOaOrderDao.findQuery(query);
        List<AssetOaOrderResponse> assetOaOrderResponses = responseConverter.convert(assetOaOrderList, AssetOaOrderResponse.class);
        for (AssetOaOrderResponse assetOaOrderResponse : assetOaOrderResponses) {
            assetOaOrderResponse.setOrderStatusName(AssetOaOrderStatusEnum.getValueByCode(assetOaOrderResponse.getOrderStatus()).getMsg());
            assetOaOrderResponse.setOrderTypeName(AssetOaOrderTypeEnum.getValueByCode(assetOaOrderResponse.getOrderType()).getMsg());
        }
        return assetOaOrderResponses;
    }

    @Override
    public PageResult<AssetOaOrderResponse> findPageAssetOaOrder(AssetOaOrderQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrder(query));
    }

    @Override
    public AssetOaOrderResponse getDetailById(Integer id) throws Exception {
        //查询订单
        AssetOaOrder assetOaOrder = assetOaOrderDao.getById(id);
        if (assetOaOrder == null) {
            return null;
        }
        AssetOaOrderResponse assetOaOrderResponse = responseConverter.convert(assetOaOrder, AssetOaOrderResponse.class);
        assetOaOrderResponse.setOrderStatusName(AssetOaOrderStatusEnum.getValueByCode(assetOaOrderResponse.getOrderStatus()).getMsg());
        assetOaOrderResponse.setOrderTypeName(AssetOaOrderTypeEnum.getValueByCode(assetOaOrderResponse.getOrderType()).getMsg());
        //查询申请信息
        AssetOaOrderApply assetOaOrderApply = assetOaOrderApplyDao.getByOrderNumber(assetOaOrder.getNumber());
        AssetOaOrderApplyResponse assetOaOrderApplyResponse = applyResponseConverter.convert(assetOaOrderApply, AssetOaOrderApplyResponse.class);
        Date applyTime = new Date(assetOaOrderApplyResponse.getApplyTime());
        String applyTimeStr = DateUtils.getDataString(applyTime, DateUtils.WHOLE_FORMAT).replaceAll("-", "/");
        assetOaOrderApplyResponse.setApplyTimeStr(applyTimeStr);
        //如果是退回和报废，还需要查询资产ip，mac
        HashMap<String, Object> result = null;
        if (!StringUtils.isEmpty(assetOaOrderApply.getAssetNumber())) {
            result = assetOaOrderApplyDao.getIpAndMacByAssetNumber(assetOaOrderApply.getAssetNumber());
            if (result != null) {
                assetOaOrderApplyResponse.setAssetIp(result.get("ip").toString());
                assetOaOrderApplyResponse.setAssetMac(result.get("mac").toString());
            }
        }
        //如果是出借，还需要拼接出借时间
        if (assetOaOrderApply.getLendStartTime() != null && assetOaOrderApply.getLendEndTime() != null) {
            Date startDate = new Date(assetOaOrderApply.getLendStartTime());
            Date endDate = new Date(assetOaOrderApply.getLendEndTime());
            String startTime = DateUtils.getDataString(startDate, DateUtils.NO_TIME_FORMAT).replaceAll("-", "/");
            String endTime = DateUtils.getDataString(endDate, DateUtils.NO_TIME_FORMAT).replaceAll("-", "/");
            assetOaOrderApplyResponse.setLendTime(startTime + "-" + endTime);
        }
        assetOaOrderResponse.setAssetOaOrderApplyResponse(assetOaOrderApplyResponse);
        //查询审批信息
        List<AssetOaOrderApprove> assetOaOrderApproves = assetOaOrderApproveDao.getByOrderNumber(assetOaOrder.getNumber());
        List<AssetOaOrderApproveResponse> assetOaOrderApproveResponses = approveResponseConverter.convert(assetOaOrderApproves, AssetOaOrderApproveResponse.class);
        if (!CollectionUtils.isEmpty(assetOaOrderApproveResponses)) {
            for (AssetOaOrderApproveResponse assetOaOrderApproveResponse : assetOaOrderApproveResponses) {
                Date approveTime = new Date(assetOaOrderApproveResponse.getApproveTime());
                String approveTimeStr = DateUtils.getDataString(approveTime, DateUtils.WHOLE_FORMAT).replaceAll("-", "/");
                assetOaOrderApproveResponse.setApproveTimeStr(approveTimeStr);
            }
        }
        assetOaOrderResponse.setAssetOaOrderApproveResponses(assetOaOrderApproveResponses);
        //资产详情
        if (result != null) {
            Map<String, Object> assetInfo = new HashMap<String, Object>();
            assetInfo.put("assetId", aesEncoder.encode(result.get("assetId").toString(), LoginUserUtil.getLoginUser().getUsername()));
            assetInfo.put("isInnet", result.get("isInnet"));
            assetInfo.put("assetStatus", AssetStatusEnum.getAssetByCode(Integer.parseInt(result.get("assetStatus").toString())).getMsg());
            assetOaOrderResponse.setAssetInfo(assetInfo);
        }
        setAssetStatusOrId(assetOaOrder, assetOaOrderResponse);
        return assetOaOrderResponse;
    }

    void setAssetStatusOrId(AssetOaOrder assetOaOrder, AssetOaOrderResponse assetOaOrderResponse) throws Exception{
        List<Integer> assetStatusList = new ArrayList<Integer>();
        if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.INNET.getCode())) {
            //入网处理
            assetStatusList.add(AssetStatusEnum.NET_IN.getCode());
            assetStatusList.add(AssetStatusEnum.WAIT_REGISTER.getCode());
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.BACK.getCode())) {
            //退回处理
            assetStatusList.add(AssetStatusEnum.NET_IN.getCode());
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.SCRAP.getCode())) {
            //报废处理
            assetStatusList.add(AssetStatusEnum.NET_IN.getCode());
            assetStatusList.add(AssetStatusEnum.RETIRE.getCode());
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.LEND.getCode())) {
            //出借处理
            //List<String> assetIds = assetOaOrderHandleDao.getAssetIdWhenLend();
            AssetLendRelationQuery query = new AssetLendRelationQuery();
            List<String> areaIdsOfCurrentUser = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
            query.setAreaIds(areaIdsOfCurrentUser);
            query.setLendStatus(2);
            List<String> assetIds = assetLendRelationDao.getLendRelationAssetIdList(query);
            assetOaOrderResponse.setAssetIds(assetIds);
        }
        assetOaOrderResponse.setAssetStatusList(assetStatusList);
    }

    @Override
    public boolean getStatus(Integer id) throws Exception {
        AssetOaOrder assetOaOrder = assetOaOrderDao.getById(id);
        if (AssetOaOrderStatusEnum.WAIT_HANDLE.getCode().equals(assetOaOrder.getOrderStatus())) {
            //待处理状态，说明可以进行处理
            return true;
        }
        return false;
    }

    private  List<Integer> getALLUserIdByPermission(List<String> tags){
        logger.info("--------------查询用户---------，tag:{}", tags.toString());
        //获取权限人员id
        QxTagQuery qxTagQuery = new QxTagQuery();
        qxTagQuery.setTags(tags);

        ActionResponse<List<Map<String, Object>>> response = (ActionResponse<List<Map<String, Object>>>) client.post(qxTagQuery, new ParameterizedTypeReference<ActionResponse>() {
        }, getUsersByQxTagUrl);
        if (response == null || !StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
            logger.info("请求url:{},参数：{}", getUsersByQxTagUrl, qxTagQuery);
            throw new BusinessException("调用用户模块获取资产管理权限用户信息失败");
        }

        List<Integer> userIds = new ArrayList<Integer>();
        response.getBody().forEach(v -> {
            if(LoginUserUtil.getLoginUser() == null){
                userIds.add(Integer.parseInt(v.get("stringId").toString()));
            }else{
                userIds.add(Integer.parseInt(aesEncoder.decode(v.get("stringId").toString(),LoginUserUtil.getLoginUser().getUsername())));
            }
        });

        return userIds;
    }
}
